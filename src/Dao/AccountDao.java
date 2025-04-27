package Dao;

import Modal.Account;
import Util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AccountDao {

    // ✅ Create a new account and return generated account ID
    public int createAccount(int userId, String accountType) {
        String query = "INSERT INTO accounts (User_id, Account_type, Balance) VALUES (?, ?, 0.00)";
        int generatedAccountId = -1;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, userId);
            stmt.setString(2, accountType);

            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                ResultSet generatedKeys = stmt.getGeneratedKeys();
                if (generatedKeys.next()) {
                    generatedAccountId = generatedKeys.getInt(1);
                }
            }
        } catch (SQLException e) {
            System.out.println("❌ Error creating account: " + e.getMessage());
        }
        return generatedAccountId;
    }

    // ✅ Get account balance
    public double getBalance(int accountId) {
        String query = "SELECT balance FROM accounts WHERE account_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, accountId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getDouble("balance");
            }
        } catch (SQLException e) {
            System.out.println("❌ Error fetching balance: " + e.getMessage());
        }
        return -1;
    }

    // ✅ Deposit money into an account
    public boolean deposit(int accountId, double amount) {
        if (amount <= 0) {
            System.out.println("❌ Deposit amount must be greater than zero.");
            return false;
        }

        String query = "UPDATE accounts SET balance = balance + ? WHERE account_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setDouble(1, amount);
            stmt.setInt(2, accountId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("❌ Error during deposit: " + e.getMessage());
            return false;
        }
    }

    // ✅ Withdraw money from an account
    public boolean withdraw(int accountId, double amount) {
        double currentBalance = getBalance(accountId);

        if (currentBalance < 0) {
            System.out.println("❌ Account not found.");
            return false;
        }
        if (amount <= 0 || amount > currentBalance) {
            System.out.println("❌ Invalid withdrawal amount or insufficient funds.");
            return false;
        }

        String query = "UPDATE accounts SET balance = balance - ? WHERE account_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setDouble(1, amount);
            stmt.setInt(2, accountId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("❌ Error during withdrawal: " + e.getMessage());
            return false;
        }
    }

    // ✅ Transfer funds between accounts with proper transaction logging
    public boolean transferFunds(int fromAccountId, int toAccountId, double amount) {
        if (amount <= 0) {
            System.out.println("❌ Transfer amount must be greater than zero.");
            return false;
        }

        double fromBalance = getBalance(fromAccountId);
        if (fromBalance < amount) {
            System.out.println("❌ Insufficient funds for transfer.");
            return false;
        }

        Connection conn = null;
        PreparedStatement withdrawStmt = null;
        PreparedStatement depositStmt = null;
        PreparedStatement transactionStmt = null;

        String withdrawQuery = "UPDATE accounts SET balance = balance - ? WHERE account_id = ?";
        String depositQuery = "UPDATE accounts SET balance = balance + ? WHERE account_id = ?";
        String transactionQuery = "INSERT INTO transaction (account_id, transaction_type, amount, transaction_date, reference_account) VALUES (?, ?, ?, NOW(), ?)";

        try {
            conn = DatabaseConnection.getConnection();
            conn.setAutoCommit(false); // Begin transaction

            // ✅ Step 1: Withdraw from sender
            withdrawStmt = conn.prepareStatement(withdrawQuery);
            withdrawStmt.setDouble(1, amount);
            withdrawStmt.setInt(2, fromAccountId);
            if (withdrawStmt.executeUpdate() == 0) {
                conn.rollback();
                return false;
            }

            // ✅ Step 2: Deposit into receiver
            depositStmt = conn.prepareStatement(depositQuery);
            depositStmt.setDouble(1, amount);
            depositStmt.setInt(2, toAccountId);
            if (depositStmt.executeUpdate() == 0) {
                conn.rollback();
                return false;
            }

            // ✅ Step 3: Insert transaction for sender
            transactionStmt = conn.prepareStatement(transactionQuery);
            transactionStmt.setInt(1, fromAccountId);
            transactionStmt.setString(2, "TRANSFER_OUT");
            transactionStmt.setDouble(3, -amount);
            transactionStmt.setInt(4, toAccountId); // Reference to recipient account
            transactionStmt.executeUpdate();

            // ✅ Step 4: Insert transaction for receiver
            transactionStmt = conn.prepareStatement(transactionQuery);
            transactionStmt.setInt(1, toAccountId);
            transactionStmt.setString(2, "TRANSFER_IN");
            transactionStmt.setDouble(3, amount);
            transactionStmt.setInt(4, fromAccountId); // Reference to sender account
            transactionStmt.executeUpdate();

            // ✅ Step 5: Commit transaction
            conn.commit();
            System.out.println("✅ Transfer successful!");
            return true;
        } catch (SQLException e) {
            System.out.println("❌ Error during fund transfer: " + e.getMessage());
            if (conn != null) {
                try {
                    conn.rollback(); // Rollback on failure
                } catch (SQLException ex) {
                    System.out.println("❌ Rollback failed: " + ex.getMessage());
                }
            }
            return false;
        } finally {
            try {
                if (withdrawStmt != null) withdrawStmt.close();
                if (depositStmt != null) depositStmt.close();
                if (transactionStmt != null) transactionStmt.close();
                if (conn != null) conn.setAutoCommit(true);
            } catch (SQLException e) {
                System.out.println("❌ Error closing resources: " + e.getMessage());
            }
        }
    }

    // ✅ Get user accounts
    public List<Account> getUserAccounts(int userId) {
        List<Account> accounts = new ArrayList<>();
        String query = "SELECT * FROM accounts WHERE user_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Account account = new Account(
                        rs.getInt("Account_id"),
                        rs.getInt("user_id"),
                        rs.getString("Account_type"),
                        rs.getDouble("Balance")
                );
                accounts.add(account);
            }
        } catch (SQLException e) {
            System.out.println("❌ Error fetching user accounts: " + e.getMessage());
        }
        return accounts;
    }
}

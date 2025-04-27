package Dao;

import Modal.Transaction;
import Util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TransactionDao {

    // ✅ Record a transaction
    public boolean recordTransaction(int account_id, String type, double amount, Integer referenceAccount) {
        String query = "INSERT INTO transaction (account_id, transaction_type, amount,transaction_date, reference_account) VALUES (?, ?, ?, NOW(), ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, account_id);
            stmt.setString(2, type);
            stmt.setDouble(3, amount);
            if (referenceAccount != null) {
                stmt.setInt(4, referenceAccount);
            } else {
                stmt.setNull(4, Types.INTEGER);
            }

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error recording transaction: " + e.getMessage());
            return false;
        }
    }

    // ✅ Get transaction history for an account
    public List<Transaction> getTransactionHistory(int account_id) {
        List<Transaction> history = new ArrayList<>();
        String query = "SELECT * FROM transaction WHERE account_id = ? ORDER BY transaction_date DESC";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, account_id);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Transaction transaction = new Transaction(
                        rs.getInt("transaction_id"),
                        rs.getInt("account_id"),
                        rs.getString("transaction_type"),
                        rs.getDouble("amount"),
                        rs.getTimestamp("transaction_date"),
                        rs.getInt("reference_account") == 0 ? null : rs.getInt("reference_account")
                );
                history.add(transaction);
            }
        } catch (SQLException e) {
            System.out.println("Error fetching transaction history: " + e.getMessage());
        }
        return history;
    }

    // ✅ Get all transactions in the system
    public List<Transaction> getAllTransactions() {
        List<Transaction> transactions = new ArrayList<>();
        String query = "SELECT * FROM transaction ORDER BY transaction_date DESC";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                Transaction transaction = new Transaction(
                        rs.getInt("transaction_id"),
                        rs.getInt("account_id"),
                        rs.getString("transaction_type"),
                        rs.getDouble("amount"),
                        rs.getTimestamp("transaction_date"),
                        rs.getInt("reference_account") == 0 ? null : rs.getInt("reference_account")
                );
                transactions.add(transaction);
            }
        } catch (SQLException e) {
            System.out.println("Error fetching all transactions: " + e.getMessage());
        }
        return transactions;
    }
}

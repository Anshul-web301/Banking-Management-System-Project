package Service;

import Dao.TransactionDao;
import Modal.Transaction;


import java.util.List;

public class TransactionService {
    private static TransactionDao transactionDao = new TransactionDao();

    public TransactionService() {
        this.transactionDao = new TransactionDao();
    }

    // ✅ Deposit money
    public boolean deposit(int account_id, double amount) {
        if (amount <= 0) {
            System.out.println("Deposit amount must be greater than zero.");
            return false;
        }
        return transactionDao.recordTransaction(account_id, "DEPOSIT", amount, null);
    }

    // ✅ Withdraw money
    public boolean withdraw(int account_id, double amount, double currentBalance) {
        if (amount <= 0) {
            System.out.println("Withdrawal amount must be greater than zero.");
            return false;
        }
        if (amount > currentBalance) {
            System.out.println("Insufficient funds.");
            return false;
        }
        return transactionDao.recordTransaction(account_id, "WITHDRAWAL", amount, null);
    }

    // ✅ Transfer funds
    public boolean transferFunds(int fromaccount_id, int toaccount_id, double amount, double fromBalance) {
        if (amount <= 0) {
            System.out.println("Transfer amount must be greater than zero.");
            return false;
        }
        if (fromBalance < amount) {
            System.out.println("Insufficient funds.");
            return false;
        }

        // Record transactions for both accounts
        boolean withdrawSuccess = transactionDao.recordTransaction(fromaccount_id, "TRANSFER", amount, toaccount_id);
        boolean depositSuccess = transactionDao.recordTransaction(toaccount_id, "TRANSFER", amount, fromaccount_id);

        return withdrawSuccess && depositSuccess;
    }

    // ✅ Get transaction history for a specific account
    public static List<Transaction> getTransactionHistory(int account_id) {
        return transactionDao.getTransactionHistory(account_id);
    }

    // ✅ Get all transactions in the system
    public List<Transaction> getAllTransactions() {
        return transactionDao.getAllTransactions();
    }
}

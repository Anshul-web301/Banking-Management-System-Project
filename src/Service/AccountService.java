package Service;
import Dao.AccountDao;
import Modal.Account;
import java.util.List;
public class AccountService {
    private AccountDao accountDAO;

    public AccountService() {
        this.accountDAO = new AccountDao();
    }

    // Create an account and return generated account ID
    public int createAccount(int userId, String accountType) {
        if (!accountType.equalsIgnoreCase("SAVINGS") && !accountType.equalsIgnoreCase("CURRENT")) {
            System.out.println("Invalid account type! Please enter SAVINGS or CURRENT.");
            return -1;
        }
        return accountDAO.createAccount(userId, accountType);
    }

    // Get account balance
    public double checkBalance(int accountId) {
        double balance = accountDAO.getBalance(accountId);
        if (balance < 0) {
            System.out.println("Account not found!");
        }
        return balance;
    }

    // Deposit money into an account
    public boolean deposit(int accountId, double amount) {
        if (amount <= 0) {
            System.out.println("Deposit amount must be greater than zero.");
            return false;
        }
        return accountDAO.deposit(accountId, amount);
    }

    // Withdraw money from an account
    public boolean withdraw(int accountId, double amount) {
        if (amount <= 0) {
            System.out.println("Withdrawal amount must be greater than zero.");
            return false;
        }

        double currentBalance = checkBalance(accountId);
        if (currentBalance < amount) {
            System.out.println("Insufficient funds!");
            return false;
        }

        return accountDAO.withdraw(accountId, amount);
    }

    // Transfer funds between accounts
    public boolean transferFunds(int fromAccountId, int toAccountId, double amount) {
        if (amount <= 0) {
            System.out.println("Transfer amount must be greater than zero.");
            return false;
        }
        if (fromAccountId == toAccountId) {
            System.out.println("Cannot transfer to the same account.");
            return false;
        }


        return accountDAO.transferFunds(fromAccountId, toAccountId, amount);
    }

    // Get all accounts of a user
    public List<Account> getUserAccounts(int userId) {
        return accountDAO.getUserAccounts(userId);
    }
}

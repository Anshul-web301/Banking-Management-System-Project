package App;
import Modal.Transaction;
import Modal.User;
import Dao.TransactionDao;
import Service.AccountService;
import Service.TransactionService;
import Service.UserService;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static final TransactionDao transactionDao = new TransactionDao();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        UserService userService = new UserService();
        AccountService accountService = new AccountService();
        TransactionService transactionService = new TransactionService();

        User loggedInUser = null;

        while (true) {
            System.out.println("\n===== Banking Management System =====");
            System.out.println("1. Sign Up");
            System.out.println("2. Sign In");
            System.out.println("3. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            if (choice == 1) {
                System.out.print("Enter Username: ");
                String username = scanner.nextLine();
                System.out.print("Enter Password: ");
                String password = scanner.nextLine();

                if (userService.register(username, password)) {
                    System.out.println("User Registered Successfully!");
                } else {
                    System.out.println("Registration failed. Username may already exist.");
                }

            } else if (choice == 2) {
                System.out.print("Enter Username: ");
                String username = scanner.nextLine();
                System.out.print("Enter Password: ");
                String password = scanner.nextLine();

                loggedInUser = userService.login(username, password);
                if (loggedInUser != null) {
                    System.out.println("Login Successful! Welcome, " + loggedInUser.getUsername());

                    handleBankingOperations(scanner, loggedInUser, accountService, transactionService);
                } else {
                    System.out.println("Invalid Credentials. Please try again.");
                }

            } else if (choice == 3) {
                System.out.println("Thank you for using BMS! Goodbye.");
                break;
            } else {
                System.out.println("Invalid choice. Please select a valid option.");
            }
        }

        scanner.close();
    }

    // Banking operations menu
    private static <AccountCreationException extends Throwable> void handleBankingOperations(Scanner scanner, User user, AccountService accountService, TransactionService transactionService) {
        while (true) {
            System.out.println("\n=====Welcome To Banking Management System =====");
            System.out.println("1. Create Account");
            System.out.println("2. DEPOSIT Money");
            System.out.println("3. Withdraw Money");
            System.out.println("4. Transfer Funds");
            System.out.println("5. Check Balance");
            System.out.println("6. Transaction History");
            System.out.println("7. Logout");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    System.out.print("Enter Account type (SAVINGS/CURRENT): ");
                    String accountType = scanner.next().toUpperCase();

                    int accountId = accountService.createAccount(user.getUserId(), accountType);
                    if (accountId > 0) {
                        System.out.println("Account Created Successfully! Your Account ID is: " + accountId);
                    } else {
                        System.out.println("Failed to Create Account.");
                    }
                    break;


                case 2:
                    System.out.print("Enter Account ID: ");
                    while (!scanner.hasNextInt()) {
                        System.out.println("Invalid input! Please enter a valid account ID.");
                        scanner.next();
                    }
                    int depositAccountId = scanner.nextInt();

                    System.out.print("Enter amount to DEPOSIT: ");
                    while (!scanner.hasNextDouble()) {
                        System.out.println("Invalid input! Please enter a valid amount.");
                        scanner.next();
                    }
                    double depositAmount = scanner.nextDouble();

                    if (accountService.deposit(depositAccountId, depositAmount)) {
                        System.out.println("DEPOSIT Successful!");
                    } else {
                        System.out.println("DEPOSIT failed. Check Account details or balance.");
                    }
                    break;

                case 3:
                    System.out.print("Enter Account ID: ");
                    int withdrawAccountId = scanner.nextInt();
                    System.out.print("Enter Amount to withdraw: ");
                    double withdrawAmount = scanner.nextDouble();
                    if (accountService.withdraw(withdrawAccountId, withdrawAmount)) {
                        System.out.println("Withdrawal Successful!");
                    } else {
                        System.out.println("Insufficient Balance or error occurred.");
                    }
                    break;
                case 4:
                    System.out.print("Enter your Account ID: ");
                    int fromaccount_id = scanner.nextInt();
                    System.out.print("Enter Recipient account ID: ");
                    int toaccount_id = scanner.nextInt();
                    System.out.print("Enter amount to Transfer: ");
                    double transferAmount = scanner.nextDouble();
                    if (accountService.transferFunds(fromaccount_id, toaccount_id, transferAmount)) {
                        System.out.println("Transfer Successful!");
                    } else {
                        System.out.println("Transfer failed. Please check the account details and balance.");
                    }
                    break;

                case 5:
                    System.out.print("Enter Account ID: ");
                    int balanceAccountId = scanner.nextInt();
                    double balance = accountService.checkBalance(balanceAccountId);
                    if (balance >= 0) {
                        System.out.println("Current Balance is: $" + balance);
                    } else {
                        System.out.println("Error Retrieving balance.");
                    }
                    break;
                case 6:
                    System.out.print("Enter account ID to fetch transaction history (or 0 to exit): ");
                    accountId = scanner.nextInt();
                    if (accountId == 0) break;

                    System.out.println("Fetching transaction history for account ID: " + accountId);
                    List<Transaction> history = transactionService.getTransactionHistory(accountId);

                    if (history.isEmpty()) {
                        System.out.println("No transactions found.");
                    } else {
                        history.forEach(System.out::println);
                    }

                    break;


                case 7:
                    System.out.println("Logging out...");
                    return;

                default:
                    System.out.println("Invalid choice. Please select a valid option.");
            }
        }
    }
}






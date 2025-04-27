package Modal;

public class Account {
    private final int accountId;
    private final int userId;
    private final String accountType;
    private final double balance;

    public Account(int accountId, int userId, String accountType, double balance) {
        this.accountId = accountId;
        this.userId = userId;
        this.accountType = accountType;
        this.balance = balance;
    }

    public int getAccountId() { return accountId; }
    public int getUserId() { return userId; }
    public String getAccountType() { return accountType; }
    public double getBalance() { return balance; }
}

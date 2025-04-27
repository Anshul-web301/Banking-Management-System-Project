package Modal;

import java.sql.Timestamp;

public class Transaction {
    private int transaction_id;
    private int account_id;
    private String transactionType;
    private double amount;
    private Timestamp transactionDate;
    private Integer referenceAccount;

    public Transaction(int transactionId, int accountId, String transactionType, double amount, Timestamp transactionDate, Integer referenceAccount) {
        this.transaction_id = transactionId;
        this.account_id = accountId;
        this.transactionType = transactionType;
        this.amount = amount;
        this.transactionDate = transactionDate;
        this.referenceAccount = referenceAccount;
    }

    // Getters and Setters
    public int gettransaction_id() { return transaction_id; }
    public void settransaction_id(int transaction_id) { this.transaction_id = transaction_id; }

    public int getaccount_id() { return account_id; }
    public void setaccount_id(int account_id) { this.account_id = account_id; }

    public String getTransactionType() { return transactionType; }
    public void setTransactionType(String transactionType) { this.transactionType = transactionType; }

    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }

    public Timestamp getTransactionDate() { return transactionDate; }
    public void setTransactionDate(Timestamp transactionDate) { this.transactionDate = transactionDate; }

    public Integer getReferenceAccount() { return referenceAccount; }
    public void setReferenceAccount(Integer referenceAccount) { this.referenceAccount = referenceAccount; }

    @Override
    public String toString() {
        return "Transaction ID: " + transaction_id +
                ", Account ID: " + account_id +
                ", Type: " + transactionType +
                ", Amount: $" + amount +
                ", Date: " + transactionDate +
                (referenceAccount != null ? ", Reference Account: " + referenceAccount : "");
    }

    public Object getTransactionId() {
        return null;
    }

    public Object getType() {
        return null;
    }

    public Boolean getDate() {
        return null;
    }

    public Object getAccount_Id() {
        return null;
    }

    public Object gettransaction_type() {
        return null;
    }

    public Object getamount() {
        return null;
    }

    public Object getreferenceAccount() {
        return null;
    }
}

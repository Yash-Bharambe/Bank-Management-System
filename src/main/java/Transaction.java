import java.sql.Timestamp;

public class Transaction {
    private int txnId;
    private int accId;
    private String type;
    private double amount;
    private Timestamp date;

    public Transaction(int txnId, int accId, String type, double amount, Timestamp date) {
        this.txnId = txnId;
        this.accId = accId;
        this.type = type;
        this.amount = amount;
        this.date = date;
    }

    public int getTxnId() { return txnId; }
    public int getAccId() { return accId; }
    public String getType() { return type; }
    public double getAmount() { return amount; }
    public Timestamp getDate() { return date; }

    @Override
    public String toString() {
        return String.format("Txn ID: %d | Type: %s | Amount: %.2f | Date: %s",
                           txnId, type, amount, date);
    }
}
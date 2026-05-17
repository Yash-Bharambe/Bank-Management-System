public class Account {
    int id;
    String name;
    double balance;
    String password;

    public Account(int id, String name, double balance, String password) {
        this.id = id;
        this.name = name;
        this.balance = balance;
        this.password = password;
    }

    // For backward compatibility, but we'll update
    public Account(int id, String name, double balance) {
        this(id, name, balance, "");
    }
}
import java.util.List;

public class AccountService {
    private AccountDAO dao;

    public AccountService() {
        this.dao = new AccountDAO();
    }

    public boolean login(int id, String password) {
        return dao.login(id, password);
    }

    public void createAccount(int id, String name, double balance, String password) {
        if (id <= 0) {
            System.out.println("Invalid account ID!");
            return;
        }
        if (name == null || name.trim().isEmpty()) {
            System.out.println("Name cannot be empty!");
            return;
        }
        if (balance < 0) {
            System.out.println("Initial balance cannot be negative!");
            return;
        }
        if (password == null || password.trim().isEmpty()) {
            System.out.println("Password cannot be empty!");
            return;
        }

        Account acc = new Account(id, name.trim(), balance, password.trim());
        dao.createAccount(acc);
    }

    public Account getAccount(int id) {
        return dao.getAccount(id);
    }

    public void deposit(int id, double amount) {
        dao.deposit(id, amount);
    }

    public void withdraw(int id, double amount) {
        dao.withdraw(id, amount);
    }

    public void transfer(int fromId, int toId, double amount) {
        dao.transfer(fromId, toId, amount);
    }

    public void viewTransactionHistory(int accId) {
        List<Transaction> transactions = dao.getTransactionHistory(accId);
        if (transactions.isEmpty()) {
            System.out.println("No transactions found!");
            return;
        }

        System.out.println("\n--- Transaction History ---");
        for (Transaction txn : transactions) {
            System.out.println(txn);
        }
    }

    public void deleteAccount(int id) {
        dao.deleteAccount(id);
    }

    public boolean deleteAccountWithPassword(int id, String password) {
    Account acc = getAccount(id);
    if (acc == null) {
        System.out.println("Account not found!");
        return false;
    }
    if (!acc.password.equals(password)) {
        System.out.println("Incorrect password! Account deletion denied.");
        return false;
    }
    dao.deleteAccount(id);
    return true;
}

    public void viewAllAccounts() {
        List<Account> accounts = dao.getAllAccounts();
        if (accounts.isEmpty()) {
            System.out.println("No accounts found!");
            return;
        }

        System.out.println("\n--- All Accounts ---");
        for (Account acc : accounts) {
            System.out.printf("ID: %d | Name: %s | Balance: %.2f%n",
                            acc.id, acc.name, acc.balance);
        }
    }

    public void searchAccounts(String name) {
        List<Account> accounts = dao.searchByName(name);
        if (accounts.isEmpty()) {
            System.out.println("No accounts found with that name!");
            return;
        }

        System.out.println("\n--- Search Results ---");
        for (Account acc : accounts) {
            System.out.printf("ID: %d | Name: %s | Balance: %.2f%n",
                            acc.id, acc.name, acc.balance);
        }
    }
}
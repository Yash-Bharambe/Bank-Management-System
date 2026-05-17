import java.sql.*;
import java.util.*;

public class AccountDAO {

    public void createAccount(Account acc) {
        try {
            Connection con = DBConnection.getConnection();
            if (con == null) {
                System.out.println("Database connection failed!");
                return;
            }

            String query = "INSERT INTO accounts VALUES (?, ?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(query);

            ps.setInt(1, acc.id);
            ps.setString(2, acc.name);
            ps.setDouble(3, acc.balance);
            ps.setString(4, acc.password);

            ps.executeUpdate();
            System.out.println("Account Created!");

            con.close();
        } catch (SQLIntegrityConstraintViolationException e) {
            System.out.println("Account ID already exists!");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Account creation failed!");
        }
    }

    public Account getAccount(int id) {
        try {
            Connection con = DBConnection.getConnection();
            if (con == null) return null;

            PreparedStatement ps = con.prepareStatement(
                "SELECT * FROM accounts WHERE id=?"
            );
            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new Account(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getDouble("balance"),
                    rs.getString("password")
                );
            }

            con.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Failed to get account!");
        }
        return null;
    }

    public boolean login(int id, String password) {
        Account acc = getAccount(id);
        return acc != null && acc.password.equals(password);
    }

    public void deposit(int id, double amount) {
        if (amount <= 0) {
            System.out.println("Deposit amount must be positive!");
            return;
        }

        try {
            Connection con = DBConnection.getConnection();
            if (con == null) return;

            con.setAutoCommit(false);

            // Update balance
            PreparedStatement ps = con.prepareStatement(
                "UPDATE accounts SET balance=balance+? WHERE id=?"
            );
            ps.setDouble(1, amount);
            ps.setInt(2, id);
            int rows = ps.executeUpdate();

            if (rows == 0) {
                con.rollback();
                System.out.println("Account not found!");
                return;
            }

            // Insert transaction
            ps = con.prepareStatement(
                "INSERT INTO transactions (acc_id, type, amount) VALUES (?, 'deposit', ?)"
            );
            ps.setInt(1, id);
            ps.setDouble(2, amount);
            ps.executeUpdate();

            con.commit();
            System.out.println("Deposited successfully!");

            con.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Deposit failed!");
        }
    }

    public void withdraw(int id, double amount) {
        if (amount <= 0) {
            System.out.println("Withdraw amount must be positive!");
            return;
        }

        try {
            Connection con = DBConnection.getConnection();
            if (con == null) return;

            con.setAutoCommit(false);

            // Check balance
            PreparedStatement ps = con.prepareStatement(
                "SELECT balance FROM accounts WHERE id=?"
            );
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (!rs.next()) {
                con.rollback();
                System.out.println("Account not found!");
                return;
            }

            double currentBalance = rs.getDouble("balance");
            if (amount > currentBalance) {
                con.rollback();
                System.out.println("Insufficient balance!");
                return;
            }

            // Update balance
            ps = con.prepareStatement(
                "UPDATE accounts SET balance=balance-? WHERE id=?"
            );
            ps.setDouble(1, amount);
            ps.setInt(2, id);
            ps.executeUpdate();

            // Insert transaction
            ps = con.prepareStatement(
                "INSERT INTO transactions (acc_id, type, amount) VALUES (?, 'withdraw', ?)"
            );
            ps.setInt(1, id);
            ps.setDouble(2, amount);
            ps.executeUpdate();

            con.commit();
            System.out.println("Withdrawn successfully!");

            con.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Withdraw failed!");
        }
    }

    public void transfer(int fromId, int toId, double amount) {
        if (amount <= 0) {
            System.out.println("Transfer amount must be positive!");
            return;
        }
        if (fromId == toId) {
            System.out.println("Cannot transfer to same account!");
            return;
        }

        try {
            Connection con = DBConnection.getConnection();
            if (con == null) return;

            con.setAutoCommit(false);

            // Check sender balance
            PreparedStatement ps = con.prepareStatement(
                "SELECT balance FROM accounts WHERE id=?"
            );
            ps.setInt(1, fromId);
            ResultSet rs = ps.executeQuery();

            if (!rs.next()) {
                con.rollback();
                System.out.println("Sender account not found!");
                return;
            }

            double senderBalance = rs.getDouble("balance");
            if (amount > senderBalance) {
                con.rollback();
                System.out.println("Insufficient balance!");
                return;
            }

            // Check receiver exists
            ps = con.prepareStatement("SELECT id FROM accounts WHERE id=?");
            ps.setInt(1, toId);
            rs = ps.executeQuery();
            if (!rs.next()) {
                con.rollback();
                System.out.println("Receiver account not found!");
                return;
            }

            // Withdraw from sender
            ps = con.prepareStatement(
                "UPDATE accounts SET balance=balance-? WHERE id=?"
            );
            ps.setDouble(1, amount);
            ps.setInt(2, fromId);
            ps.executeUpdate();

            // Deposit to receiver
            ps = con.prepareStatement(
                "UPDATE accounts SET balance=balance+? WHERE id=?"
            );
            ps.setDouble(1, amount);
            ps.setInt(2, toId);
            ps.executeUpdate();

            // Insert transactions
            ps = con.prepareStatement(
                "INSERT INTO transactions (acc_id, type, amount) VALUES (?, 'transfer_out', ?)"
            );
            ps.setInt(1, fromId);
            ps.setDouble(2, amount);
            ps.executeUpdate();

            ps = con.prepareStatement(
                "INSERT INTO transactions (acc_id, type, amount) VALUES (?, 'transfer_in', ?)"
            );
            ps.setInt(1, toId);
            ps.setDouble(2, amount);
            ps.executeUpdate();

            con.commit();
            System.out.println("Transfer successful!");

            con.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Transfer failed!");
        }
    }

    public List<Transaction> getTransactionHistory(int accId) {
        List<Transaction> transactions = new ArrayList<>();
        try {
            Connection con = DBConnection.getConnection();
            if (con == null) return transactions;

            PreparedStatement ps = con.prepareStatement(
                "SELECT * FROM transactions WHERE acc_id=? ORDER BY date DESC"
            );
            ps.setInt(1, accId);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                transactions.add(new Transaction(
                    rs.getInt("txn_id"),
                    rs.getInt("acc_id"),
                    rs.getString("type"),
                    rs.getDouble("amount"),
                    rs.getTimestamp("date")
                ));
            }

            con.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Failed to get transaction history!");
        }
        return transactions;
    }

    public void deleteAccount(int id) {
        try {
            Connection con = DBConnection.getConnection();
            if (con == null) return;

            con.setAutoCommit(false);

            // Delete transactions first
            PreparedStatement ps = con.prepareStatement(
                "DELETE FROM transactions WHERE acc_id=?"
            );
            ps.setInt(1, id);
            ps.executeUpdate();

            // Delete account
            ps = con.prepareStatement("DELETE FROM accounts WHERE id=?");
            ps.setInt(1, id);
            int rows = ps.executeUpdate();

            if (rows == 0) {
                con.rollback();
                System.out.println("Account not found!");
                return;
            }

            con.commit();
            System.out.println("Account deleted successfully!");

            con.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Account deletion failed!");
        }
    }

    public List<Account> getAllAccounts() {
        List<Account> accounts = new ArrayList<>();
        try {
            Connection con = DBConnection.getConnection();
            if (con == null) return accounts;

            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM accounts");

            while (rs.next()) {
                accounts.add(new Account(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getDouble("balance"),
                    rs.getString("password")
                ));
            }

            con.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Failed to get accounts!");
        }
        return accounts;
    }

    public List<Account> searchByName(String name) {
        List<Account> accounts = new ArrayList<>();
        try {
            Connection con = DBConnection.getConnection();
            if (con == null) return accounts;

            PreparedStatement ps = con.prepareStatement(
                "SELECT * FROM accounts WHERE name LIKE ?"
            );
            ps.setString(1, "%" + name + "%");

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                accounts.add(new Account(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getDouble("balance"),
                    rs.getString("password")
                ));
            }

            con.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Search failed!");
        }
        return accounts;
    }
}
import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {
    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/bankDB",
                "root",
                "yashkavya"   // change this
            );
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }
}
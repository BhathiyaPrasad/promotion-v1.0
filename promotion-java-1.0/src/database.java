import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class database {
    static final String DB_URL = "jdbc:mysql://localhost:3307/fsms-data";
    static final String USER = "PosUser";
    static final String PASS = "MasterPos1212";

    public static void main(String[] args) {
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
                Statement stmt = conn.createStatement();) {
            String sql = "Select * from " + DB_URL;
            stmt.executeUpdate(sql);
            System.out.println("Database created Successfully");
            System.out.print(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

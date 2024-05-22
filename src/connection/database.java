package connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class database {
    // Database URL
    static final String DB_URL = "jdbc:mysql://localhost:3307/data";
    // Database credentials
    static final String USER = "PosUser";
    static final String PASS = "MasterPos1212";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, USER, PASS);
    }

    public static void main(String[] args) {
        Connection conn = null;
        try {
            conn = getConnection();
            if (conn != null) {
                System.out.println("Connected to the database!");
            } else {
                System.out.println("Failed to connect to the database.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

package Util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/banking_system";
    private static final String USER = "root";
    private static final String PASSWORD = "Admin123";

    public static Connection connection;

    // Method to establish a database connection
    public static Connection getConnection() {
        try {
            // Load MySQL JDBC Driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Establish connection
            if (connection == null || connection.isClosed()) {
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
                System.out.println("✅ Database connected successfully!");
            }
        } catch (ClassNotFoundException e) {
            System.out.println("❌ MySQL JDBC Driver not found!");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("❌ Connection to database failed!");
            e.printStackTrace();
        }
        return connection;
    }

    // Method to close the connection
    public static void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("✅ Database connection closed successfully!");
            }
        } catch (SQLException e) {
            System.out.println("❌ Error closing the database connection!");
            e.printStackTrace();
        }
    }
}

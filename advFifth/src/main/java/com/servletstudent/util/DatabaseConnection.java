package com.servletstudent.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Database Connection Utility Class
 *
 * Provides a singleton-style database connection for PostgreSQL.
 * This class manages the database connection lifecycle and provides
 * a centralized point for database configuration.
 *
 * <p>Usage Example:</p>
 * <pre>
 *     Connection conn = DatabaseConnection.getConnection();
 *     // Use the connection...
 *     DatabaseConnection.closeConnection(conn);
 * </pre>
 *
 * @author Developer
 * @version 1.0
 */
public class DatabaseConnection {

    // ==================== Database Configuration ====================

    /** Database driver class name for PostgreSQL */
    private static final String DB_DRIVER = "org.postgresql.Driver";

    /** Database connection URL */
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/spring";

    /** Database username */
    private static final String DB_USER = "postgres";

    /** Database password */
    private static final String DB_PASSWORD = "Sanket05";

    /**
     * Private constructor to prevent instantiation
     * This is a utility class with only static methods
     */
    private DatabaseConnection() {
    }

    /**
     * Loads the database driver class.
     * Called once when the class is first loaded.
     */
    static {
        try {
            Class.forName(DB_DRIVER);
            System.out.println("PostgreSQL Driver loaded successfully.");
        } catch (ClassNotFoundException e) {
            System.err.println("PostgreSQL Driver not found: " + e.getMessage());
        }
    }

    /**
     * Establishes and returns a new database connection.
     *
     * @return Connection object for database operations
     * @throws SQLException if connection fails
     */
    public static Connection getConnection() throws SQLException {
        try {
            Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            System.out.println("Database connection established.");
            return conn;
        } catch (SQLException e) {
            System.err.println("Failed to establish database connection: " + e.getMessage());
            throw e;
        }
    }

    /**
     * Safely closes a database connection.
     * Handles null connections and logs any errors.
     *
     * @param conn The connection to close
     */
    public static void closeConnection(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
                System.out.println("Database connection closed.");
            } catch (SQLException e) {
                System.err.println("Error closing connection: " + e.getMessage());
            }
        }
    }

    /**
     * Tests the database connection.
     * Useful for verifying configuration during setup.
     *
     * @return true if connection is successful, false otherwise
     */
    public static boolean testConnection() {
        try (Connection conn = getConnection()) {
            if (conn != null && !conn.isClosed()) {
                System.out.println("Database connection test: SUCCESS");
                return true;
            }
        } catch (SQLException e) {
            System.err.println("Database connection test: FAILED - " + e.getMessage());
        }
        return false;
    }
}

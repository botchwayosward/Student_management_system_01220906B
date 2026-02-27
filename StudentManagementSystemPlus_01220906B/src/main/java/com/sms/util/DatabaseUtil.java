package com.sms.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseUtil {
    private static final String URL = "jdbc:sqlite:sms.db";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL);
    }

    public static void initializeDatabase() {
        // The SQL command to physically create the table in the .db file
        String sql = "CREATE TABLE IF NOT EXISTS students (" +
                "studentId TEXT PRIMARY KEY, " +
                "fullName TEXT, " +
                "programme TEXT, " +
                "level INTEGER, " +
                "gpa REAL, " +
                "email TEXT, " +
                "phone TEXT, " +
                "enrollmentDate TEXT, " +
                "status TEXT)";

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            System.out.println("Database successfully initialized and table created!");
        } catch (SQLException e) {
            System.err.println("DB Initialization Error: " + e.getMessage());
        }
    }
}
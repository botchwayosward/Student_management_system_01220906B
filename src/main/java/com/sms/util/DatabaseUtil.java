package com.sms.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseUtil {
    private static final String URL = "jdbc:sqlite:data/sms_plus.db";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL);
    }

    public static void initialize() {
        // This creates the table structure required by the assignment
        String sql = """
            CREATE TABLE IF NOT EXISTS students (
                student_id TEXT PRIMARY KEY,
                full_name TEXT NOT NULL,
                programme TEXT NOT NULL,
                level INTEGER NOT NULL,
                gpa REAL NOT NULL,
                email TEXT,
                phone_number TEXT,
                date_added TEXT,
                status TEXT
            );
        """;

        try (Connection conn = getConnection(); Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            System.out.println("Database initialized and table created.");
        } catch (SQLException e) {
            System.err.println("DB Initialization Error: " + e.getMessage());
        }
    }
}
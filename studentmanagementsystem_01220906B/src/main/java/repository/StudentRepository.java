package repository;

import domain.Student;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class StudentRepository {

    public StudentRepository() {
        createTable();
    }

    private void createTable() {
        String tableSql = "CREATE TABLE IF NOT EXISTS students (" +
                "studentId TEXT PRIMARY KEY, " +
                "fullName TEXT, " +
                "programme TEXT, " +
                "level INTEGER, " +
                "gpa REAL, " +
                "status TEXT, " +
                "dateAdded TEXT)";

        // This makes searching by name much faster as the database grows
        String indexSql = "CREATE INDEX IF NOT EXISTS idx_student_name ON students(fullName)";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute(tableSql);
            stmt.execute(indexSql);
        } catch (SQLException e) {
            System.out.println("Error setting up database: " + e.getMessage());
        }
    }

    public void saveStudent(Student student) {
        String sql = "INSERT INTO students(studentId, fullName, programme, level, gpa, status, dateAdded) VALUES(?,?,?,?,?,?,?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            pstmt.setString(1, student.getStudentId());
            pstmt.setString(2, student.getFullName());
            pstmt.setString(3, student.getProgramme());
            pstmt.setInt(4, student.getLevel());
            pstmt.setDouble(5, student.getGpa());
            pstmt.setString(6, "Active");
            pstmt.setString(7, timestamp);

            pstmt.executeUpdate();
            System.out.println("\n[SUCCESS]: Registration complete.");
        } catch (SQLException e) {
            System.out.println("\n[ERROR]: ID already exists.");
        }
    }

    public void getAllStudents() {
        String sql = "SELECT * FROM students";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            System.out.println("\n--- Student Directory ---");
            System.out.printf("%-12s | %-18s | %-10s | %-5s%n", "ID", "Name", "GPA", "Status");
            System.out.println("------------------------------------------------------------");
            while (rs.next()) {
                System.out.printf("%-12s | %-18s | %-5.2f | %-10s%n",
                        rs.getString("studentId"), rs.getString("fullName"),
                        rs.getDouble("gpa"), rs.getString("status"));
            }
        } catch (SQLException e) { System.out.println("Error: " + e.getMessage()); }
    }

    public void getStudentById(String id) {
        String sql = "SELECT * FROM students WHERE studentId = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                System.out.println("\n--- Record Found ---");
                System.out.println("Name: " + rs.getString("fullName") + " [" + rs.getString("status") + "]");
                System.out.println("GPA: " + rs.getDouble("gpa"));
            } else { System.out.println("\n[NOT FOUND]"); }
        } catch (SQLException e) { System.out.println("Error: " + e.getMessage()); }
    }

    public void deleteStudent(String id) {
        String sql = "DELETE FROM students WHERE studentId = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, id);
            if (pstmt.executeUpdate() > 0) System.out.println("\n[SUCCESS]: Deleted.");
            else System.out.println("\n[ERROR]: Not found.");
        } catch (SQLException e) { System.out.println("Error: " + e.getMessage()); }
    }

    public void updateStudentFull(String id, int level, double gpa, String status) {
        String sql = "UPDATE students SET level = ?, gpa = ?, status = ? WHERE studentId = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, level);
            pstmt.setDouble(2, gpa);
            pstmt.setString(3, status);
            pstmt.setString(4, id);
            if (pstmt.executeUpdate() > 0) System.out.println("\n[SUCCESS]: Updated.");
            else System.out.println("\n[ERROR]: Not found.");
        } catch (SQLException e) { System.out.println("Error: " + e.getMessage()); }
    }

    public void exportToTextFile() {
        String sql = "SELECT * FROM students";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql);
             PrintWriter writer = new PrintWriter(new FileWriter("student_report.txt"))) {
            writer.println("STUDENT MANAGEMENT SYSTEM REPORT - " + LocalDateTime.now());
            while (rs.next()) {
                writer.printf("%-10s %-20s %-5.2f%n", rs.getString("studentId"), rs.getString("fullName"), rs.getDouble("gpa"));
            }
            System.out.println("\n[SUCCESS]: File exported.");
        } catch (Exception e) { System.out.println("Export failed."); }
    }

    public void getStatistics() {
        String sql = "SELECT COUNT(*) as t, AVG(gpa) as a, MAX(gpa) as h FROM students";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            if (rs.next()) {
                System.out.println("\n--- DATA INSIGHTS ---");
                System.out.println("Count: " + rs.getInt("t") + " | Avg GPA: " + String.format("%.2f", rs.getDouble("a")) + " | Top GPA: " + rs.getDouble("h"));
            }
        } catch (SQLException e) { System.out.println("Stats error."); }
    }
}
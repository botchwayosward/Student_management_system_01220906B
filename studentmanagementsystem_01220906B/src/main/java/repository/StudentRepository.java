package repository;

import domain.Student;
import java.sql.*;

public class StudentRepository {

    public StudentRepository() {
        createTable();
    }

    private void createTable() {
        String sql = "CREATE TABLE IF NOT EXISTS students (" +
                "studentId TEXT PRIMARY KEY, " +
                "fullName TEXT, " +
                "programme TEXT, " +
                "level INTEGER, " +
                "gpa REAL, " +
                "email TEXT, " +
                "phoneNumber TEXT, " +
                "status TEXT, " +
                "dateAdded TEXT)";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println("Error creating table: " + e.getMessage());
        }
    }

    public void saveStudent(Student student) {
        String sql = "INSERT INTO students(studentId, fullName, programme, level, gpa, email, phoneNumber, status, dateAdded) VALUES(?,?,?,?,?,?,?,?,?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, student.getStudentId());
            pstmt.setString(2, student.getFullName());
            pstmt.setString(3, student.getProgramme());
            pstmt.setInt(4, student.getLevel());
            pstmt.setDouble(5, student.getGpa());
            pstmt.setString(6, student.getEmail());
            pstmt.setString(7, student.getPhoneNumber());
            pstmt.setString(8, student.getStatus());
            pstmt.setString(9, student.getDateAdded() != null ? student.getDateAdded().toString() : "N/A");

            pstmt.executeUpdate();
            System.out.println("\n[SUCCESS]: Student saved successfully!");
        } catch (SQLException e) {
            System.out.println("\n[ERROR]: Could not save student. (ID might already exist)");
        }
    }

    public void getAllStudents() {
        String sql = "SELECT * FROM students";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            System.out.println("\n--- Registered Students List ---");
            System.out.printf("%-15s | %-20s | %-15s | %-5s%n", "ID", "Name", "Prog", "GPA");
            System.out.println("------------------------------------------------------------------");

            while (rs.next()) {
                System.out.printf("%-15s | %-20s | %-15s | %-5.2f%n",
                        rs.getString("studentId"),
                        rs.getString("fullName"),
                        rs.getString("programme"),
                        rs.getDouble("gpa"));
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving students: " + e.getMessage());
        }
    }

    public void getStudentById(String id) {
        String sql = "SELECT * FROM students WHERE studentId = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                System.out.println("\n--- Student Found ---");
                System.out.println("ID: " + rs.getString("studentId"));
                System.out.println("Name: " + rs.getString("fullName"));
                System.out.println("Programme: " + rs.getString("programme"));
                System.out.println("Level: " + rs.getInt("level"));
                System.out.println("GPA: " + rs.getDouble("gpa"));
            } else {
                System.out.println("\n[NOT FOUND]: No student with ID " + id);
            }
        } catch (SQLException e) {
            System.out.println("Error searching for student: " + e.getMessage());
        }
    }

    public void deleteStudent(String id) {
        String sql = "DELETE FROM students WHERE studentId = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, id);
            int rowsDeleted = pstmt.executeUpdate();

            if (rowsDeleted > 0) {
                System.out.println("\n[SUCCESS]: Student with ID " + id + " was deleted.");
            } else {
                System.out.println("\n[ERROR]: No student found with ID " + id);
            }
        } catch (SQLException e) {
            System.out.println("Error deleting student: " + e.getMessage());
        }
    }

    // NEW METHOD: Update Student
    public void updateStudent(String id, int newLevel, double newGpa) {
        String sql = "UPDATE students SET level = ?, gpa = ? WHERE studentId = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, newLevel);
            pstmt.setDouble(2, newGpa);
            pstmt.setString(3, id);

            int rowsUpdated = pstmt.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("\n[SUCCESS]: Student updated successfully!");
            } else {
                System.out.println("\n[ERROR]: Student not found.");
            }
        } catch (SQLException e) {
            System.out.println("Error updating student: " + e.getMessage());
        }
    }
}
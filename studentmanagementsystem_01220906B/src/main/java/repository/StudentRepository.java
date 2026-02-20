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

    // This is the method the Service is looking for!
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
            System.out.println("Student saved successfully to database!");
        } catch (SQLException e) {
            System.out.println("Error saving student: " + e.getMessage());
        }
    }

    public void getAllStudents() {
        String sql = "SELECT * FROM students";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            System.out.println("\n--- Registered Students List ---");
            System.out.printf("%-15s | %-20s | %-5s | %-5s%n", "ID", "Name", "Level", "GPA");
            System.out.println("------------------------------------------------------------");

            while (rs.next()) {
                System.out.printf("%-15s | %-20s | %-5s | %-5.2f%n",
                        rs.getString("studentId"),
                        rs.getString("fullName"),
                        rs.getInt("level"),
                        rs.getDouble("gpa"));
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving students: " + e.getMessage());
        }
    }
}
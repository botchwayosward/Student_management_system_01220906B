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
            pstmt.setString(9, student.getDateAdded().toString());

            pstmt.executeUpdate();
            System.out.println("Student saved successfully!");
        } catch (SQLException e) {
            System.out.println("Error saving student: " + e.getMessage());
        }
    }
}
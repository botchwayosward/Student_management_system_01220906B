package com.sms.repository;

import com.sms.domain.Student;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentRepository {

    public void save(Student student) throws SQLException {
        String sql = "INSERT OR REPLACE INTO students (studentId, fullName, programme, level, gpa, email, phone, enrollmentDate, status) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, student.getStudentId());
            pstmt.setString(2, student.getFullName());
            pstmt.setString(3, student.getProgramme());
            pstmt.setInt(4, student.getLevel());
            pstmt.setDouble(5, student.getGpa());
            pstmt.setString(6, student.getEmail());
            pstmt.setString(7, student.getPhone());
            pstmt.setString(8, student.getEnrollmentDate() != null ? student.getEnrollmentDate().toString() : null);
            pstmt.setString(9, student.getStatus());

            pstmt.executeUpdate();
        }
    }

    public List<Student> findAll() throws SQLException {
        List<Student> students = new ArrayList<>();
        String sql = "SELECT * FROM students";
        try (Connection conn = DatabaseUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                String dateStr = rs.getString("enrollmentDate");
                students.add(new Student(
                        rs.getString("studentId"),
                        rs.getString("fullName"),
                        rs.getString("programme"),
                        rs.getInt("level"),
                        rs.getDouble("gpa"),
                        rs.getString("email"),
                        rs.getString("phone"),
                        dateStr != null ? java.time.LocalDate.parse(dateStr) : null,
                        rs.getString("status")
                ));
            }
        }
        return students;
    }

    /**
     * Deletes a student by their ID.
     * This method name must match the call made in StudentService.
     */
    public void delete(String studentId) throws SQLException {
        String sql = "DELETE FROM students WHERE studentId = ?";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, studentId);
            pstmt.executeUpdate();
        }
    }
}
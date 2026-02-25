package com.sms.repository;

import com.sms.domain.Student;
import com.sms.util.DatabaseUtil;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class StudentRepository {
    public void save(Student s) throws SQLException {
        String sql = "INSERT INTO students VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, s.getStudentId());
            pstmt.setString(2, s.getFullName());
            pstmt.setString(3, s.getProgramme());
            pstmt.setInt(4, s.getLevel());
            pstmt.setDouble(5, s.getGpa());
            pstmt.setString(6, s.getEmail());
            pstmt.setString(7, s.getPhoneNumber());
            pstmt.setString(8, s.getDateAdded().toString());
            pstmt.setString(9, s.getStatus());
            pstmt.executeUpdate();
        }
    }

    public List<Student> findAll() throws SQLException {
        List<Student> students = new ArrayList<>();
        try (Connection conn = DatabaseUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM students")) {
            while (rs.next()) {
                students.add(new Student(
                        rs.getString(1), rs.getString(2), rs.getString(3),
                        rs.getInt(4), rs.getDouble(5), rs.getString(6),
                        rs.getString(7), LocalDate.parse(rs.getString(8)), rs.getString(9)
                ));
            }
        }
        return students;
    }
}
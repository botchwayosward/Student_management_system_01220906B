package com.sms.domain;

import java.time.LocalDate;

public class Student {
    private String studentId;
    private String fullName;
    private String programme;
    private int level;
    private double gpa;
    private String email;
    private String phone; // Added field
    private LocalDate enrollmentDate; // Added field
    private String status;

    public Student() {}

    public Student(String studentId, String fullName, String programme, int level,
                   double gpa, String email, String phone, LocalDate enrollmentDate, String status) {
        this.studentId = studentId;
        this.fullName = fullName;
        this.programme = programme;
        this.level = level;
        this.gpa = gpa;
        this.email = email;
        this.phone = phone;
        this.enrollmentDate = enrollmentDate;
        this.status = status;
    }

    // Getters
    public String getStudentId() { return studentId; }
    public String getFullName() { return fullName; }
    public String getProgramme() { return programme; }
    public int getLevel() { return level; }
    public double getGpa() { return gpa; }
    public String getEmail() { return email; }

    // These are the "Symbols" the compiler was missing:
    public String getPhone() { return phone; }
    public LocalDate getEnrollmentDate() { return enrollmentDate; }

    public String getStatus() { return status; }

    // Setters
    public void setStudentId(String studentId) { this.studentId = studentId; }
    public void setFullName(String fullName) { this.fullName = fullName; }
    public void setGpa(double gpa) { this.gpa = gpa; }
    public void setPhone(String phone) { this.phone = phone; }
    public void setEnrollmentDate(LocalDate enrollmentDate) { this.enrollmentDate = enrollmentDate; }
}
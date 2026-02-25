package com.sms.domain;

import java.time.LocalDate;

public class Student {
    private String studentId;
    private String fullName;
    private String programme;
    private int level;
    private double gpa;
    private String email;
    private String phoneNumber;
    private LocalDate dateAdded;
    private String status;

    public Student(String studentId, String fullName, String programme, int level,
                   double gpa, String email, String phoneNumber, LocalDate dateAdded, String status) {
        this.studentId = studentId;
        this.fullName = fullName;
        this.programme = programme;
        this.level = level;
        this.gpa = gpa;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.dateAdded = dateAdded;
        this.status = status;
    }

    // Getters - used by the UI to show data in the table
    public String getStudentId() { return studentId; }
    public String getFullName() { return fullName; }
    public String getProgramme() { return programme; }
    public int getLevel() { return level; }
    public double getGpa() { return gpa; }
    public String getEmail() { return email; }
    public String getPhoneNumber() { return phoneNumber; }
    public LocalDate getDateAdded() { return dateAdded; }
    public String getStatus() { return status; }
}
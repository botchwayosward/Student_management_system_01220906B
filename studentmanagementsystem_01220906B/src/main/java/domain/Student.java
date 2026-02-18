package domain;

import java.time.LocalDate;

public class Student {
    private String studentId;
    private String fullName;
    private String programme;
    private int level;
    private double gpa;
    private String email;          
    private String phoneNumber;
    private String status;
    private LocalDate dateAdded;

    public Student() {
        this.dateAdded = LocalDate.now();
    }
}

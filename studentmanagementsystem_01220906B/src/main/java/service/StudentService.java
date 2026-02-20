package service;

import domain.Student;
import repository.StudentRepository;
import java.util.Scanner;

public class StudentService {
    private StudentRepository repository;

    public StudentService() {
        this.repository = new StudentRepository();
    }

    public void registerStudent(Student student, Scanner scanner) {
        double gpa = student.getGpa();

        while (gpa < 0.0 || gpa > 4.0) {
            System.out.println("\n[VALIDATION ERROR]: GPA " + gpa + " is invalid.");
            System.out.print("Please enter a valid GPA between 0.0 and 4.0: ");

            if (scanner.hasNextDouble()) {
                gpa = scanner.nextDouble();
            } else {
                System.out.println("Please enter a numeric value.");
                scanner.next(); // Clear invalid input
            }
        }

        student.setGpa(gpa);

        repository.saveStudent(student);
        System.out.println("\nSuccess: Student data validated and saved to database.");
    }
}
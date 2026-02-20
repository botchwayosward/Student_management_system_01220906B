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
        double currentGpa = student.getGpa();

        // Validation Loop for GPA
        while (currentGpa < 0.0 || currentGpa > 4.0) {
            System.out.print("Enter Student GPA (0.0 - 4.0): ");
            if (scanner.hasNextDouble()) {
                currentGpa = scanner.nextDouble();
                scanner.nextLine(); // Clear buffer
            } else {
                System.out.println("Invalid input. Please enter a numeric GPA.");
                scanner.next();
            }
        }

        student.setGpa(currentGpa);
        repository.saveStudent(student);
    }

    public void displayAllStudents() {
        repository.getAllStudents();
    }
}
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

        // If the initial GPA is invalid, start the loop
        if (currentGpa < 0.0 || currentGpa > 4.0) {
            while (currentGpa < 0.0 || currentGpa > 4.0) {
                System.out.println("\n[VALIDATION ERROR]: " + currentGpa + " is not allowed.");
                System.out.print("Please enter a valid GPA (0.0 - 4.0): ");

                if (scanner.hasNextDouble()) {
                    currentGpa = scanner.nextDouble();
                    scanner.nextLine(); // THE FIX: Clear the "Enter" key from memory
                } else {
                    System.out.println("Numbers only, please!");
                    scanner.next(); // Clear bad text
                }
            }
        }

        // IMPORTANT: We update the student object with the NEW GPA you just typed
        student.setGpa(currentGpa);

        // Now we save the UPDATED student
        repository.saveStudent(student);
    }

    public void displayAllStudents() {
        repository.getAllStudents();
    }
}
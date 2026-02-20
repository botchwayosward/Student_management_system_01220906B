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
        while (currentGpa < 0.0 || currentGpa > 4.0) {
            System.out.print("Enter Student GPA (0.0 - 4.0): ");
            if (scanner.hasNextDouble()) {
                currentGpa = scanner.nextDouble();
                scanner.nextLine();
            } else {
                System.out.println("Invalid input. Please enter a number.");
                scanner.next();
            }
        }
        student.setGpa(currentGpa);
        repository.saveStudent(student);
    }

    public void displayAllStudents() {
        repository.getAllStudents();
    }

    public void searchStudent(String id) {
        repository.getStudentById(id);
    }

    public void removeStudent(String id) {
        repository.deleteStudent(id);
    }

    // NEW METHOD:
    public void modifyStudent(String id, int newLevel, double newGpa) {
        // Simple check before passing to repo
        if (newGpa < 0.0 || newGpa > 4.0) {
            System.out.println("[ERROR]: Update failed. GPA must be between 0.0 and 4.0.");
            return;
        }
        repository.updateStudent(id, newLevel, newGpa);
    }
}
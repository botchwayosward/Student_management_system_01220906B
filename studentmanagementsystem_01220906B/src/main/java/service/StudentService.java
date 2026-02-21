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
        double g = student.getGpa();
        while (g < 0.0 || g > 4.0) {
            System.out.print("Valid GPA Required (0.0 - 4.0): ");
            if (scanner.hasNextDouble()) { g = scanner.nextDouble(); scanner.nextLine(); }
            else { scanner.next(); }
        }
        student.setGpa(g);
        repository.saveStudent(student);
    }

    public void displayAllStudents() { repository.getAllStudents(); }
    public void searchStudent(String id) { repository.getStudentById(id); }
    public void removeStudent(String id) { repository.deleteStudent(id); }
    public void generateReport() { repository.exportToTextFile(); }
    public void displayStats() { repository.getStatistics(); }

    public String validateStatus(Scanner sc) {
        while (true) {
            System.out.print("Status: 1.Active 2.Graduated 3.Leave -> ");
            if (sc.hasNextInt()) {
                int c = sc.nextInt(); sc.nextLine();
                if (c == 1) return "Active";
                if (c == 2) return "Graduated";
                if (c == 3) return "On Leave";
            } else { sc.next(); }
            System.out.println("Invalid.");
        }
    }

    public void modifyStudent(String id, int l, double g, String s) {
        repository.updateStudentFull(id, l, g, s);
    }
}
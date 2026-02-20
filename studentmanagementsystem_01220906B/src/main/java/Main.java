import domain.Student;
import service.StudentService;
import java.util.Scanner;
import java.util.Random;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        StudentService studentService = new StudentService();
        Random rand = new Random();

        Student newStudent = new Student();
        // Generating a random ID so we don't get "Duplicate Key" errors
        newStudent.setStudentId("ID-" + rand.nextInt(9000));
        newStudent.setFullName("Interactive User");
        newStudent.setProgramme("Computer Science");
        newStudent.setLevel(100);

        // Start with an invalid GPA to trigger the interactive loop
        newStudent.setGpa(9.9);

        System.out.println("--- Student Management System ---");

        // 1. Register and Validate (The loop is here)
        studentService.registerStudent(newStudent, scanner);

        // 2. View all students
        System.out.println("\nFinal Database Contents:");
        studentService.displayAllStudents();

        scanner.close();
    }
}
import domain.Student;
import service.StudentService;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        StudentService studentService = new StudentService();
        int choice = 0;

        System.out.println("=== UNIVERSITY STUDENT MANAGEMENT SYSTEM ===");

        do {
            System.out.println("\nMAIN MENU");
            System.out.println("1. Register New Student");
            System.out.println("2. View All Students");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");

            if (scanner.hasNextInt()) {
                choice = scanner.nextInt();
                scanner.nextLine(); // Clear the buffer
            } else {
                System.out.println("Invalid input. Please enter a number (1-3).");
                scanner.next(); // Clear invalid input
                continue;
            }

            switch (choice) {
                case 1:
                    // Interactive Registration
                    Student newStudent = new Student();

                    System.out.print("Enter Student ID: ");
                    newStudent.setStudentId(scanner.nextLine());

                    System.out.print("Enter Full Name: ");
                    newStudent.setFullName(scanner.nextLine());

                    System.out.print("Enter Programme: ");
                    newStudent.setProgramme(scanner.nextLine());

                    System.out.print("Enter Level (e.g., 100): ");
                    if(scanner.hasNextInt()) {
                        newStudent.setLevel(scanner.nextInt());
                        scanner.nextLine();
                    }

                    // Set a placeholder GPA to trigger the validation loop in Service
                    newStudent.setGpa(-1.0);

                    // Pass to service for GPA validation and saving
                    studentService.registerStudent(newStudent, scanner);
                    break;

                case 2:
                    studentService.displayAllStudents();
                    break;

                case 3:
                    System.out.println("Exiting system... Goodbye!");
                    break;

                default:
                    System.out.println("Invalid choice. Please select 1, 2, or 3.");
            }

        } while (choice != 3);

        scanner.close();
    }
}
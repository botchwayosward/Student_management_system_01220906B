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
            System.out.println("3. Search Student by ID");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");

            if (scanner.hasNextInt()) {
                choice = scanner.nextInt();
                scanner.nextLine();
            } else {
                System.out.println("Invalid input. Please enter a number (1-4).");
                scanner.next();
                continue;
            }

            switch (choice) {
                case 1:
                    Student newStudent = new Student();
                    System.out.print("Enter Student ID: ");
                    newStudent.setStudentId(scanner.nextLine());
                    System.out.print("Enter Full Name: ");
                    newStudent.setFullName(scanner.nextLine());
                    System.out.print("Enter Programme: ");
                    newStudent.setProgramme(scanner.nextLine());
                    System.out.print("Enter Level: ");
                    if(scanner.hasNextInt()) {
                        newStudent.setLevel(scanner.nextInt());
                        scanner.nextLine();
                    }
                    newStudent.setGpa(-1.0);
                    studentService.registerStudent(newStudent, scanner);
                    break;

                case 2:
                    studentService.displayAllStudents();
                    break;

                case 3:
                    System.out.print("Enter the Student ID to search for: ");
                    String searchId = scanner.nextLine();
                    studentService.searchStudent(searchId);
                    break;

                case 4:
                    System.out.println("Exiting system... Goodbye!");
                    break;

                default:
                    System.out.println("Invalid choice.");
            }

        } while (choice != 4);

        scanner.close();
    }
}
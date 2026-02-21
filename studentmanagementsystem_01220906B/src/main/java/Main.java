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
            System.out.println("1. Register Student");
            System.out.println("2. View All");
            System.out.println("3. Search by ID");
            System.out.println("4. Delete Record");
            System.out.println("5. Update Record");
            System.out.println("6. Export Report");
            System.out.println("7. View System Statistics");
            System.out.println("8. Exit");
            System.out.print("Enter choice: ");

            if (scanner.hasNextInt()) {
                choice = scanner.nextInt();
                scanner.nextLine();
            } else {
                scanner.next();
                continue;
            }

            switch (choice) {
                case 1:
                    Student s = new Student();
                    System.out.print("ID: "); s.setStudentId(scanner.nextLine());
                    System.out.print("Name: "); s.setFullName(scanner.nextLine());
                    System.out.print("Prog: "); s.setProgramme(scanner.nextLine());
                    System.out.print("Level: "); s.setLevel(scanner.nextInt());
                    s.setGpa(-1.0); studentService.registerStudent(s, scanner);
                    break;
                case 2: studentService.displayAllStudents(); break;
                case 3:
                    System.out.print("ID: "); studentService.searchStudent(scanner.nextLine());
                    break;
                case 4:
                    System.out.print("ID: ");
                    String idD = scanner.nextLine();
                    System.out.print("Confirm (yes/no)? ");
                    if(scanner.nextLine().equalsIgnoreCase("yes")) studentService.removeStudent(idD);
                    break;
                case 5:
                    System.out.print("ID: "); String idU = scanner.nextLine();
                    System.out.print("Level: "); int l = scanner.nextInt();
                    System.out.print("GPA: "); double g = scanner.nextDouble();
                    String st = studentService.validateStatus(scanner);
                    studentService.modifyStudent(idU, l, g, st);
                    break;
                case 6: studentService.generateReport(); break;
                case 7: studentService.displayStats(); break;
                case 8: System.out.println("Goodbye!"); break;
                default: System.out.println("Invalid.");
            }
        } while (choice != 8);
        scanner.close();
    }
}
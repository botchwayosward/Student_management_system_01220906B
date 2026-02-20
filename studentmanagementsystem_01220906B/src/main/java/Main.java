import domain.Student;
import service.StudentService;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        StudentService studentService = new StudentService();

        Student newStudent = new Student();
        newStudent.setStudentId("01220906B");
        newStudent.setFullName("Interactive User");
        newStudent.setProgramme("Computer Science");
        newStudent.setLevel(100);

        newStudent.setGpa(9.9);

        System.out.println("--- Student Registration System ---");

        studentService.registerStudent(newStudent, scanner);

        System.out.println("Closing system...");
        scanner.close();
    }
}
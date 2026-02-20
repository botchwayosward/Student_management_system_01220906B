import domain.Student;
import repository.StudentRepository;

public class Main {
    public static void main(String[] args) {
        // 1. Create the 'Manager' (Repository)
        StudentRepository repo = new StudentRepository();

        // 2. Create a 'Sample Student'
        Student newStudent = new Student();
        newStudent.setStudentId("01220906B");
        newStudent.setFullName("Test User");
        newStudent.setProgramme("Computer Science");
        newStudent.setLevel(100);
        newStudent.setGpa(3.8);

        // 3. Tell the repo to save the student to the database
        repo.saveStudent(newStudent);

        System.out.println("Check your 'data' folder now!");
    }
}
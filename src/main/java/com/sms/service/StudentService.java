package com.sms.service;

import com.sms.domain.Student;
import com.sms.repository.StudentRepository;
import java.sql.SQLException;
import java.util.List;

public class StudentService {
    private final StudentRepository repository = new StudentRepository();

    public void validateAndAdd(Student student) throws Exception {
        // Validation logic required by Page 4 of the assignment
        if (student.getStudentId().length() < 4) {
            throw new Exception("ID must be at least 4 characters.");
        }
        if (student.getGpa() < 0.0 || student.getGpa() > 4.0) {
            throw new Exception("GPA must be between 0.0 and 4.0.");
        }
        repository.save(student);
    }

    public List<Student> getAll() throws SQLException {
        return repository.findAll();
    }
}
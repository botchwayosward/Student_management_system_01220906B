package com.sms.service;

import com.sms.domain.Student;
import com.sms.repository.StudentRepository;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service layer for business logic.
 * Fixed: Updated deleteStudent to call repository.delete() instead of deleteById().
 */
public class StudentService {
    private final StudentRepository repository = new StudentRepository();
    private double topThreshold = 3.5;
    private double riskThreshold = 2.0;

    public void validateAndAdd(Student student) throws Exception {
        if (student.getStudentId() == null || student.getStudentId().isEmpty()) {
            throw new Exception("Student ID is required.");
        }
        repository.save(student);
    }

    public List<Student> getAll() throws Exception {
        return repository.findAll();
    }

    public void deleteStudent(String id) throws Exception {
        // This now correctly calls the method defined in StudentRepository
        repository.delete(id);
    }

    public void setThresholds(double top, double risk) {
        this.topThreshold = top;
        this.riskThreshold = risk;
    }

    public List<Student> getTopPerformers() throws Exception {
        return repository.findAll().stream()
                .filter(s -> s.getGpa() >= topThreshold)
                .collect(Collectors.toList());
    }

    public List<Student> getAtRiskStudents() throws Exception {
        return repository.findAll().stream()
                .filter(s -> s.getGpa() < riskThreshold)
                .collect(Collectors.toList());
    }
}
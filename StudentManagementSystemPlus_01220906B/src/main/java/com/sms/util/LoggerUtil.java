package com.sms.util;

import com.sms.domain.Student;
import javafx.stage.FileChooser;
import java.io.File;
import java.io.PrintWriter;
import java.util.List;
import java.time.LocalDateTime;

public class LoggerUtil {

    /**
     * Commit #7 Fix: Added the missing log symbol.
     * This allows the Controller to log actions like "Student Added" or "Import Successful".
     */
    public static void log(String message) {
        System.out.println("[" + LocalDateTime.now() + "] LOG: " + message);
    }

    public static void exportToCSV(List<Student> students) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Student Report");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV Files", "*.csv"));
        fileChooser.setInitialFileName("Student_Report.csv");

        File file = fileChooser.showSaveDialog(null);

        if (file != null) {
            try (PrintWriter writer = new PrintWriter(file)) {
                writer.println("ID,Name,Programme,Level,GPA,Email,Phone,EnrollmentDate,Status");
                for (Student s : students) {
                    StringBuilder sb = new StringBuilder();
                    sb.append(s.getStudentId()).append(",");
                    sb.append(s.getFullName()).append(",");
                    sb.append(s.getProgramme()).append(",");
                    sb.append(s.getLevel()).append(",");
                    sb.append(s.getGpa()).append(",");
                    sb.append(s.getEmail()).append(",");
                    sb.append(s.getPhone()).append(",");
                    sb.append(s.getEnrollmentDate()).append(",");
                    sb.append(s.getStatus());
                    writer.println(sb.toString());
                }
                log("Data exported successfully to: " + file.getAbsolutePath());
            } catch (Exception e) {
                log("Export failed: " + e.getMessage());
            }
        }
    }
}
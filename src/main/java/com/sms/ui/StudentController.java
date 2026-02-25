package com.sms.ui;

import com.sms.domain.Student;
import com.sms.service.StudentService;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.time.LocalDate;

public class StudentController {
    // These link to the visual text boxes we will create later
    @FXML private TextField txtId, txtName, txtProg, txtGpa, txtEmail, txtPhone;
    @FXML private ComboBox<Integer> comboLevel;
    @FXML private ComboBox<String> comboStatus;

    private final StudentService service = new StudentService();

    @FXML
    public void initialize() {
        // Populate the dropdown choices
        if (comboLevel != null) {
            comboLevel.getItems().addAll(100, 200, 300, 400, 500, 600, 700);
        }
        if (comboStatus != null) {
            comboStatus.getItems().addAll("Active", "Inactive");
        }
    }

    @FXML
    private void handleSave() {
        try {
            // Create a student object from user input
            Student student = new Student(
                    txtId.getText(),
                    txtName.getText(),
                    txtProg.getText(),
                    comboLevel.getValue() == null ? 0 : comboLevel.getValue(),
                    Double.parseDouble(txtGpa.getText().isEmpty() ? "0" : txtGpa.getText()),
                    txtEmail.getText(),
                    txtPhone.getText(),
                    LocalDate.now(),
                    comboStatus.getValue()
            );

            // Send to service for validation and saving
            service.validateAndAdd(student);

            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Student saved successfully!");
            alert.show();

        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Error: " + e.getMessage());
            alert.show();
        }
    }
}
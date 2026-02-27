package com.sms.ui;

import com.sms.domain.Student;
import com.sms.service.StudentService;
import com.sms.util.LoggerUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory; // THIS WAS MISSING
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.time.LocalDate;
import java.util.Optional;

public class StudentController {
    @FXML private TextField txtId, txtName, txtProg, txtGpa, txtEmail, txtSearch;
    @FXML private ComboBox<Integer> comboLevel;
    @FXML private ComboBox<String> comboStatus;
    @FXML private Label lblTotal, lblAvgGpa;

    @FXML private TableView<Student> tblStudents;
    @FXML private TableColumn<Student, String> colId, colName, colProg, colStatus;
    @FXML private TableColumn<Student, Double> colGpa;

    @FXML private ListView<String> listTopPerformers, listAtRisk;

    private final StudentService service = new StudentService();
    private final ObservableList<Student> masterData = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        // Setup dropdowns
        if (comboLevel != null) comboLevel.getItems().addAll(100, 200, 300, 400, 500, 600, 700);
        if (comboStatus != null) comboStatus.getItems().addAll("Active", "Inactive");

        // Link Table Columns to Student properties
        colId.setCellValueFactory(new PropertyValueFactory<>("studentId"));
        colName.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        colProg.setCellValueFactory(new PropertyValueFactory<>("programme"));
        colGpa.setCellValueFactory(new PropertyValueFactory<>("gpa"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));

        loadData();
        setupSearch();

        // Listen for row selection to fill form
        tblStudents.getSelectionModel().selectedItemProperty().addListener((obs, oldV, newV) -> {
            if (newV != null) populateFields(newV);
        });
    }

    private void loadData() {
        try {
            masterData.setAll(service.getAll());
            updateDashboard();
            refreshAnalytics();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void updateDashboard() {
        lblTotal.setText(String.valueOf(masterData.size()));
        double avg = masterData.stream().mapToDouble(Student::getGpa).average().orElse(0.0);
        lblAvgGpa.setText(String.format("%.2f", avg));
    }

    private void setupSearch() {
        FilteredList<Student> filteredData = new FilteredList<>(masterData, p -> true);
        txtSearch.textProperty().addListener((obs, oldV, newV) -> {
            filteredData.setPredicate(s -> {
                if (newV == null || newV.isEmpty()) return true;
                String lower = newV.toLowerCase();
                return s.getFullName().toLowerCase().contains(lower) || s.getStudentId().contains(lower);
            });
        });
        tblStudents.setItems(filteredData);
    }

    @FXML
    private void handleSave() {
        try {
            Student s = new Student(
                    txtId.getText(), txtName.getText(), txtProg.getText(),
                    comboLevel.getValue() != null ? comboLevel.getValue() : 0,
                    Double.parseDouble(txtGpa.getText().isEmpty() ? "0" : txtGpa.getText()),
                    txtEmail.getText(), "000", LocalDate.now(), comboStatus.getValue()
            );

            service.validateAndAdd(s);
            LoggerUtil.log("Saved: " + s.getStudentId());
            loadData();
            clearFields();
            new Alert(Alert.AlertType.INFORMATION, "Student saved successfully!").show();
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Save failed: " + e.getMessage()).show();
        }
    }

    @FXML
    private void handleDelete() {
        Student selected = tblStudents.getSelectionModel().getSelectedItem();
        if (selected != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Delete " + selected.getFullName() + "?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                try {
                    service.deleteStudent(selected.getStudentId());
                    loadData();
                    clearFields();
                } catch (Exception e) { e.printStackTrace(); }
            }
        }
    }

    @FXML
    private void clearFields() {
        txtId.clear();
        txtId.setEditable(true);
        txtName.clear();
        txtProg.clear();
        txtGpa.clear();
        txtEmail.clear();
        if (comboLevel != null) comboLevel.getSelectionModel().clearSelection();
        if (comboStatus != null) comboStatus.getSelectionModel().clearSelection();
        tblStudents.getSelectionModel().clearSelection();
    }

    @FXML
    private void refreshAnalytics() {
        try {
            listTopPerformers.getItems().clear();
            service.getTopPerformers().forEach(s ->
                    listTopPerformers.getItems().add(s.getFullName() + " (" + s.getGpa() + ")"));

            listAtRisk.getItems().clear();
            service.getAtRiskStudents().forEach(s ->
                    listAtRisk.getItems().add(s.getFullName() + " (" + s.getGpa() + ")"));
        } catch (Exception e) { e.printStackTrace(); }
    }

    @FXML
    private void showSettings() {
        Dialog<double[]> dialog = new Dialog<>();
        dialog.setTitle("GPA Threshold Settings");
        dialog.setHeaderText("Set Performance Limits");

        ButtonType applyButton = new ButtonType("Apply", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(applyButton, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10); grid.setVgap(10);
        TextField topInput = new TextField("3.5");
        TextField riskInput = new TextField("2.0");

        grid.add(new Label("Top Performer ≥:"), 0, 0);
        grid.add(topInput, 1, 0);
        grid.add(new Label("At Risk < :"), 0, 1);
        grid.add(riskInput, 1, 1);
        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(btn -> btn == applyButton ?
                new double[]{Double.parseDouble(topInput.getText()), Double.parseDouble(riskInput.getText())} : null);

        dialog.showAndWait().ifPresent(thresholds -> {
            service.setThresholds(thresholds[0], thresholds[1]);
            refreshAnalytics();
        });
    }

    @FXML
    private void handleImport() {
        FileChooser chooser = new FileChooser();
        File file = chooser.showOpenDialog(null);
        if (file != null) {
            try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                String line; br.readLine(); // skip header
                while ((line = br.readLine()) != null) {
                    String[] d = line.split(",");
                    service.validateAndAdd(new Student(d[0], d[1], d[2], Integer.parseInt(d[3]),
                            Double.parseDouble(d[4]), d[5], "000", LocalDate.now(), d[7]));
                }
                loadData();
            } catch (Exception e) {
                new Alert(Alert.AlertType.ERROR, "Import Error: " + e.getMessage()).show();
            }
        }
    }

    @FXML
    private void handleExport() {
        LoggerUtil.exportToCSV(masterData);
        new Alert(Alert.AlertType.INFORMATION, "Data exported to data/student_report.csv").show();
    }

    private void populateFields(Student s) {
        txtId.setText(s.getStudentId());
        txtId.setEditable(false);
        txtName.setText(s.getFullName());
        txtProg.setText(s.getProgramme());
        txtGpa.setText(String.valueOf(s.getGpa()));
        txtEmail.setText(s.getEmail());
        comboLevel.setValue(s.getLevel());
        comboStatus.setValue(s.getStatus());
    }
}
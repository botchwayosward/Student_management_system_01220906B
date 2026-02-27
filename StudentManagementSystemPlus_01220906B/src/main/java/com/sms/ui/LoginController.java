package com.sms.ui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.io.IOException;

public class LoginController {
    @FXML private TextField txtUser;
    @FXML private PasswordField txtPass;
    @FXML private Label lblError;

    @FXML
    private void handleLogin() {
        String user = txtUser.getText();
        String pass = txtPass.getText();

        if ("admin".equals(user) && "1234".equals(pass)) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/sms/ui/main-view.fxml"));
                Parent root = loader.load();

                Stage stage = (Stage) txtUser.getScene().getWindow();
                stage.setScene(new Scene(root));
                stage.setTitle("Student Management System - Dashboard"); // "Plus" removed
                stage.centerOnScreen();
                stage.show();
            } catch (IOException e) {
                lblError.setText("Error loading dashboard.");
                e.printStackTrace();
            }
        } else {
            lblError.setText("Invalid username or password!");
        }
    }
}
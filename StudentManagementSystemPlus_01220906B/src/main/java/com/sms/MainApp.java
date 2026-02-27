package com.sms;

import com.sms.repository.DatabaseUtil;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainApp extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        // TRIGGER THE TABLE CREATION BEFORE THE UI LOADS
        DatabaseUtil.initializeDatabase();

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/sms/ui/login-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());

        stage.setTitle("Student Management System");
        stage.setScene(scene);
        stage.setResizable(true);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
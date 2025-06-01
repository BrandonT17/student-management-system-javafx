package com.example.app.controllers;

import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import java.io.IOException;
import javafx.scene.Parent;

public class MainGUIController {

    @FXML
    private void handleManageStudents(ActionEvent event) throws IOException {
        switchScene(event, "/com/example/app/fxml/Student.fxml");
    }

    @FXML
    private void handleManageCourses(ActionEvent event) throws IOException {
        switchScene(event, "/com/example/app/fxml/Course.fxml");
    }

    @FXML
    private void handleViewReports(ActionEvent event) throws IOException {
        switchScene(event, "/com/example/app/fxml/Report.fxml");
    }

    private void switchScene(ActionEvent event, String fxmlPath) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource(fxmlPath));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }
}

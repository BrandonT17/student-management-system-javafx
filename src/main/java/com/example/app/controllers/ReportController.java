package com.example.app.controllers;

import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import java.io.IOException;
import javafx.scene.Parent;

public class ReportController {

    @FXML
    private void handleStudentReport(ActionEvent event) {
        showAlert("Generate Student Report clicked");
    }

    @FXML
    private void handleCourseReport(ActionEvent event) {
        showAlert("Generate Course Report clicked");
    }

    @FXML
    private void handleBack(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/com/example/app/fxml/MainGUI.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

    private void showAlert(String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Reports");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}


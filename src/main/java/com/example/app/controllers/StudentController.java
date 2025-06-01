package com.example.app.controllers;

import com.example.app.models.Student;
import com.example.app.data.StudentRepository;

import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.Modality;
import javafx.geometry.Pos;
import javafx.fxml.FXMLLoader;
import java.io.IOException;
import javafx.scene.Parent;

public class StudentController {

    @FXML
    private void handleAddStudent(ActionEvent event) {
        Stage popupStage = new Stage();
        popupStage.setTitle("Add Student");

        TextField idField = new TextField();
        idField.setPromptText("Student ID");

        TextField nameField = new TextField();
        nameField.setPromptText("Name");

        TextField emailField = new TextField();
        emailField.setPromptText("Email");

        Button submitButton = new Button("Submit");
        submitButton.setOnAction(e -> {
            try {
                int id = Integer.parseInt(idField.getText().trim());
                String name = nameField.getText().trim();
                String email = emailField.getText().trim();

                if (name.isEmpty()) {
                    showAlert("Name cannot be empty.");
                    return;
                }

                Student newStudent = new Student(id, name, email);
                StudentRepository.addStudent(newStudent);

                // Confirmation popup
                showAlert("Student added:\n\nID: " + id + "\nName: " + name + "\nEmail: " + email);
                popupStage.close();

            } catch (NumberFormatException ex) {
                showAlert("Invalid ID: must be a number.");
            }
        });

        VBox layout = new VBox(10,
            new Label("Enter Student Info:"),
            idField,
            nameField,
            emailField,
            submitButton
        );
        layout.setStyle("-fx-padding: 20;");
        layout.setAlignment(Pos.CENTER);

        popupStage.setScene(new Scene(layout));
        popupStage.initModality(Modality.WINDOW_MODAL);
        popupStage.initOwner(((Node) event.getSource()).getScene().getWindow());
        popupStage.show();
    }

    @FXML
    private void handleViewAllStudents(ActionEvent event) {
        // Prepare student data
        StringBuilder sb = new StringBuilder();
        for (Student s : StudentRepository.getAllStudents()) {
            sb.append(s.getName())
              .append(" (").append(s.getId())
              .append(")")
              .append("\n");
        }
        if (sb.length() == 0) {
            sb.append("No students found.");
        }

        // Create a TextArea to display student info
        TextArea textArea = new TextArea(sb.toString());
        textArea.setEditable(false);
        textArea.setWrapText(true);

        // Put the TextArea inside a layout
        VBox layout = new VBox(10, new Label("All Students:"), textArea);
        layout.setStyle("-fx-padding: 20;");
        layout.setPrefSize(400, 300);

        // Create popup stage
        Stage popupStage = new Stage();
        popupStage.setTitle("View All Students");
        popupStage.setScene(new Scene(layout));
        popupStage.initModality(Modality.WINDOW_MODAL);
        popupStage.initOwner(((Node) event.getSource()).getScene().getWindow());

        popupStage.show();
    }


    @FXML
    private void handleViewStudentDetails(ActionEvent event) {
        showAlert("View Student Details clicked");
    }

    @FXML
    private void handleDeleteStudent(ActionEvent event) {
        Stage popupStage = new Stage();
        popupStage.setTitle("Delete Student");

        TextField idField = new TextField();
        idField.setPromptText("Student ID");

        TextField nameField = new TextField();
        nameField.setPromptText("Student Name");

        Button deleteButton = new Button("Delete");
        deleteButton.setOnAction(e -> {
            try {
                int id = Integer.parseInt(idField.getText());
                String name = nameField.getText();

                boolean removed = StudentRepository.getAllStudents().removeIf(s ->
                    s.getId() == id && s.getName().equalsIgnoreCase(name)
                );

                if (removed) {
                    showAlert("Student deleted: " + name + " (ID: " + id + ")");
                } else {
                    showAlert("No matching student found.");
                }

                popupStage.close();
            } catch (NumberFormatException ex) {
                showAlert("Invalid ID: must be a number.");
            }
        });

        VBox layout = new VBox(10,
            new Label("Enter Student Info to Delete:"),
            idField,
            nameField,
            deleteButton
        );
        layout.setStyle("-fx-padding: 20;");
        layout.setAlignment(Pos.CENTER);

        popupStage.setScene(new Scene(layout));
        popupStage.initModality(Modality.WINDOW_MODAL);
        popupStage.initOwner(((Node) event.getSource()).getScene().getWindow());
        popupStage.show();
    }

    @FXML
    private void handleBack(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/com/example/app/fxml/MainGUI.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Manage Students");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}


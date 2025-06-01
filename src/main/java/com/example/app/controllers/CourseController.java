package com.example.app.controllers;

import com.example.app.models.Course;
import com.example.app.data.CourseRepository;

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

public class CourseController {

    @FXML
    private void handleAddCourse(ActionEvent event) {
        Stage popupStage = new Stage();
        popupStage.setTitle("Add Course");

        TextField idField = new TextField();
        idField.setPromptText("Course ID");

        TextField nameField = new TextField();
        nameField.setPromptText("Course Name");

        Button submitButton = new Button("Submit");
        submitButton.setOnAction(e -> {
            String id = idField.getText();
            String name = nameField.getText();

            if (id.isEmpty() || name.isEmpty()) {
                showAlert("Both fields must be filled.");
                return;
            }

            Course course = new Course(id, name);
            CourseRepository.addCourse(course);

            Alert confirm = new Alert(Alert.AlertType.INFORMATION);
            confirm.setTitle("Success");
            confirm.setHeaderText(null);
            confirm.setContentText("Course added: " + name + " (" + id + ")");
            confirm.showAndWait();

            popupStage.close();
        });

        VBox layout = new VBox(10, new Label("Enter Course Info:"), idField, nameField, submitButton);
        layout.setAlignment(Pos.CENTER);
        layout.setStyle("-fx-padding: 20;");

        popupStage.setScene(new Scene(layout));
        popupStage.initModality(Modality.WINDOW_MODAL);
        popupStage.initOwner(((Node) event.getSource()).getScene().getWindow());
        popupStage.show();
    }

    @FXML
    private void handleSelectCourse(ActionEvent event) {
        showAlert("Select Course clicked");
        // TODO: Show list of courses to select from
    }

    @FXML
    private void handleViewAllCourses(ActionEvent event) {
        StringBuilder sb = new StringBuilder();
        for (Course c : CourseRepository.getAllCourses()) {
            sb.append("ID: ").append(c.getCourseID())
              .append(", Name: ").append(c.getCourseName())
              .append("\n");
        }
        if (sb.length() == 0) {
            sb.append("No courses found.");
        }
        showAlert(sb.toString());
    }

    @FXML
    private void handleRemoveCourse(ActionEvent event) {
        Stage popupStage = new Stage();
        popupStage.setTitle("Remove Course");

        TextField idField = new TextField();
        idField.setPromptText("Enter Course ID to remove");

        Button submitButton = new Button("Remove");
        submitButton.setOnAction(e -> {
            String id = idField.getText();
            Course course = CourseRepository.findCourseById(id);

            if (course != null) {
                CourseRepository.removeCourse(course);
                showAlert("Course removed: " + course.getCourseName());
                popupStage.close();
            } else {
                showAlert("Course not found.");
            }
        });

        VBox layout = new VBox(10, new Label("Remove a Course by ID:"), idField, submitButton);
        layout.setAlignment(Pos.CENTER);
        layout.setStyle("-fx-padding: 20;");

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
        alert.setTitle("Manage Courses");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}


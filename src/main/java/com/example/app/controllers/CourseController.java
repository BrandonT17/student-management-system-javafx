package com.example.app.controllers;

import com.example.app.models.Course;
import com.example.app.models.Student;
import com.example.app.data.CourseRepository;
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
import java.util.List;
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
        if (CourseRepository.getAllCourses().isEmpty()) {
            showAlert("No courses available.");
            return;
        }

        Stage selectStage = new Stage();
        selectStage.setTitle("Select a Course");

        ComboBox<Course> courseComboBox = new ComboBox<>();
        courseComboBox.getItems().addAll(CourseRepository.getAllCourses());
        courseComboBox.setPromptText("Select a Course");

        Button proceedButton = new Button("Proceed");
        proceedButton.setOnAction(ev -> {
            Course selectedCourse = courseComboBox.getValue();
            if (selectedCourse == null) {
                showAlert("Please select a course.");
                return;
            }

            selectStage.close();

            Stage optionsStage = new Stage();
            optionsStage.setTitle("Options for " + selectedCourse.getCourseName());

            Button enrollStudentBtn = new Button("Enroll Student");
            Button viewStudentsBtn = new Button("View Students");
            Button manageAssignmentsBtn = new Button("Manage Assignments");
            Button viewInfoBtn = new Button("View Course Info");
            Button goBackBtn = new Button("Go Back");

            enrollStudentBtn.setOnAction(e -> handleEnrollStudent(selectedCourse));
            viewStudentsBtn.setOnAction(a -> showAlert("Viewing students in " + selectedCourse.getCourseName()));
            manageAssignmentsBtn.setOnAction(a -> showAlert("Manage assignments in " + selectedCourse.getCourseName()));
            viewInfoBtn.setOnAction(a -> showAlert("Course Info:\nName: " + selectedCourse.getCourseName() + "\nID: " + selectedCourse.getCourseID()));
            goBackBtn.setOnAction(a -> optionsStage.close());

            VBox optionsLayout = new VBox(10,
                new Label("Options for: " + selectedCourse.getCourseName()),
                enrollStudentBtn,
                viewStudentsBtn,
                manageAssignmentsBtn,
                viewInfoBtn,
                goBackBtn
            );
            optionsLayout.setAlignment(Pos.CENTER);
            optionsLayout.setStyle("-fx-padding: 20;");

            optionsStage.setScene(new Scene(optionsLayout));
            optionsStage.initModality(Modality.WINDOW_MODAL);
            optionsStage.initOwner(((Node) event.getSource()).getScene().getWindow());
            optionsStage.show();
        });

        VBox layout = new VBox(10, new Label("Choose a course to manage:"), courseComboBox, proceedButton);
        layout.setAlignment(Pos.CENTER);
        layout.setStyle("-fx-padding: 20;");

        selectStage.setScene(new Scene(layout));
        selectStage.initModality(Modality.WINDOW_MODAL);
        selectStage.initOwner(((Node) event.getSource()).getScene().getWindow());
        selectStage.show();
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

    private void handleEnrollStudent(Course course) {
        List<Student> availableStudents = StudentRepository.getAllStudents().stream()
            .filter(s -> !course.getStudents().contains(s))
            .toList();

        if (availableStudents.isEmpty()) {
            showAlert("No available students to enroll.");
            return;
        }

        Stage popupStage = new Stage();
        popupStage.setTitle("Enroll Student");

        ComboBox<Student> studentComboBox = new ComboBox<>();
        studentComboBox.getItems().addAll(availableStudents);
        studentComboBox.setPromptText("Select Student");

        Button enrollButton = new Button("Enroll");
        enrollButton.setOnAction(e -> {
            Student selected = studentComboBox.getValue();
            if (selected != null) {
                course.addStudent(selected);
                showAlert("Student enrolled: " + selected.getName());
                popupStage.close();
            } else {
                showAlert("Please select a student.");
            }
        });

        VBox layout = new VBox(10, new Label("Enroll a student into: " + course.getCourseName()), studentComboBox, enrollButton);
        layout.setAlignment(Pos.CENTER);
        layout.setStyle("-fx-padding: 20;");

        popupStage.setScene(new Scene(layout));
        popupStage.initModality(Modality.WINDOW_MODAL);
        popupStage.show();
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Manage Courses");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}


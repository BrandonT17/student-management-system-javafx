package com.example.app.models;

import java.util.ArrayList;
import java.util.List;

public class Course {

    private String courseName;
    private String courseID;
    private List<Student> students;
    private List<Assignment> assignments;

    public Course(String courseID, String courseName) {
        this.courseID = courseID;
        this.courseName = courseName;
        this.students = new ArrayList<>();
        this.assignments = new ArrayList<>();
    }

    public String getCourseName() {
        return courseName;
    }

    public String getCourseID() {
        return courseID;
    }

    public List<Student> getStudents() {
        return students;
    }

    public List<Assignment> getAssignments() {
        return assignments;
    }

    public void addStudent(Student student) {
        students.add(student);
    }

    public void removeStudent(Student student) {
        students.remove(student);
    }

    public void addAssignment(Assignment assignment) {
        assignments.add(assignment);
    }

    public void removeAssignment(Assignment assignment) {
        assignments.remove(assignment);
    }

    @Override
    public String toString() {
        return courseID + " - " + courseName;
    }
}


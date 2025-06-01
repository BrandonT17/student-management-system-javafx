package com.example.app.data;

import com.example.app.models.Student;
import java.util.ArrayList;
import java.util.List;

public class StudentRepository {
    private static final List<Student> students = new ArrayList<>();

    public static void addStudent(Student student) {
        students.add(student);
    }

    public static List<Student> getAllStudents() {
        return new ArrayList<>(students);
    }

    public static void clear() {
        students.clear();
    }
}


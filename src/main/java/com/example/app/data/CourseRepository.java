package com.example.app.data;

import com.example.app.models.Course;
import java.util.ArrayList;
import java.util.List;

public class CourseRepository {
    private static final List<Course> courses = new ArrayList<>();

    public static void addCourse(Course course) {
        courses.add(course);
    }

    public static List<Course> getAllCourses() {
        return new ArrayList<>(courses); // return a copy to prevent external modification
    }

    public static void removeCourse(Course course) {
        courses.remove(course);
    }

    public static void clear() {
        courses.clear();
    }

    public static Course findCourseById(String id) {
        for (Course course : courses) {
            if (course.getCourseID().equalsIgnoreCase(id)) {
                return course;
            }
        }
        return null;
    }
}


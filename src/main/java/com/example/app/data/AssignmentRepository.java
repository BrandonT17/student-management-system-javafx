package com.example.app.data;

import com.example.app.models.Assignment;
import java.util.ArrayList;
import java.util.List;

public class AssignmentRepository {
    private static final List<Assignment> assignments = new ArrayList<>();

    public static void addAssignment(Assignment assignment) {
        assignments.add(assignment);
    }

    public static List<Assignment> getAllAssignments() {
        return new ArrayList<>(assignments); // return copy
    }

    public static void removeAssignment(Assignment assignment) {
        assignments.remove(assignment);
    }

    public static void clear() {
        assignments.clear();
    }

    public static Assignment findByName(String name) {
        for (Assignment a : assignments) {
            if (a.getName().equalsIgnoreCase(name)) {
                return a;
            }
        }
        return null;
    }
}


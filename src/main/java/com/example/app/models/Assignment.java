package com.example.app.models;

public class Assignment {
    private String name;
    private int maxScore;
    private Course course;

    public Assignment(String name, int maxScore, Course course) {
        this.name = name;
        this.maxScore = maxScore;
        this.course = course;
    }

    public String getName() {
        return name;
    }

    public int getMaxScore() {
        return maxScore;
    }

    public void setMaxScore(int maxScore) {
        this.maxScore = maxScore;
    }

    public Course getCourse() {
        return course;
    }

    @Override
    public String toString() {
        return name.toUpperCase() + " (" + maxScore + " pts)";
    }
}


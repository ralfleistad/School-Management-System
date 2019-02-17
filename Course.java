/*
 * TITLE: Course.java
 * ABSTRACT: This class handles the validation upon creating Course-objects. Also each student's score is stored withing a HashMap with the corresponding student.
 * AUTHOR: Ralf Leistad
 * DATE: 10/08/2018
 */

import java.util.ArrayList;
import java.util.HashMap;

public class Course {
    private int courseNum;
    private String courseTitle;
    private int maxEnrollment;
    private String courseLocation;
    private Instructor courseInstructor;
    public static ArrayList<Course> allCourses = new ArrayList<>();

    public ArrayList<Student> enrolledStudents = new ArrayList<>();
    public double courseTotal = 0;

    public HashMap<Student, Double> studentScores = new HashMap<>();

    public Course(int courseNum, String courseTitle, int maxEnrollment, String courseLocation) {
        boolean isValid = true;

        for(Course course : allCourses){
            if(course.courseNum == courseNum){
                System.out.println("Course addition failed - Course number " + courseNum + " already used.");
                isValid = false;
            }
        }

        if(isValid){
            this.courseNum = courseNum;
            this.courseTitle = courseTitle;
            this.maxEnrollment = maxEnrollment;
            this.courseLocation = courseLocation;
            allCourses.add(this);
        }
    }

    public Course() {
    }

    public void populateMap(Student stud, double score){
        studentScores.put(stud, score);
    }

    public void updateLocation(String newLocation){
        this.courseLocation = newLocation;
    }

    public void setCourseInstructor(Instructor courseInstructor) {
        this.courseInstructor = courseInstructor;
    }

    public Instructor getCourseInstructor() {
        return courseInstructor;
    }

    public int getCourseNum() {
        return courseNum;
    }

    public String getCourseTitle() {
        return courseTitle;
    }

    public int getMaxEnrollment() {
        return maxEnrollment;
    }

    public String getCourseLocation() {
        return courseLocation;
    }

    public double getCourseAvg(){
        return (this.courseTotal / this.enrolledStudents.size());
    }

    @Override
    public String toString(){
        return "\t" + getCourseTitle();
    }
}

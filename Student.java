/*
 * TITLE: Student.java
 * ABSTRACT: This class handles the validation upon creating Student-objects.
 * AUTHOR: Ralf Leistad
 * DATE: 10/08/2018
 */

import java.util.ArrayList;

public class Student {
    private int studentId;
    private String studentName;
    public static ArrayList<Student> allStudents = new ArrayList<>();

    public Student(int studentId, String studentName) {
        boolean isValid = true;

        for(Student stud : allStudents){
            if(stud.getStudentId() == studentId){
                isValid = false;
                System.out.println("Student info reading failed - Student ID " + studentId + " already used.");
            }
        }

        if(isValid){
            this.studentId = studentId;
            this.studentName = studentName;
            allStudents.add(this);
        }
    }

    public int getStudentId() {
        return studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    @Override
    public String toString(){
        int courses = 0;
        double totalScore = 0;
        StringBuilder sb = new StringBuilder();
        sb.append("Student number: ");
        sb.append(String.valueOf(this.getStudentId()));
        sb.append("\nName: ");
        sb.append(this.getStudentName());
        sb.append("\nCourses enrolled:");
        for(Course course : Course.allCourses){
            if(course.enrolledStudents.contains(this)){
                sb.append("\n\t");
                sb.append(String.valueOf(course.getCourseNum()));
                sb.append(": ");
                sb.append(String.valueOf(course.studentScores.get(this)));
                courses++;
                totalScore += course.studentScores.get(this);
            }
        }
        sb.append("\nCourse Average: ");
        sb.append(String.valueOf(totalScore/courses));

        return sb.toString();
    }
}

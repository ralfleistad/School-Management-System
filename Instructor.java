/*
 * TITLE: Instructor.java
 * ABSTRACT: This class handles the validation upon creating Intructor-objects.
 * AUTHOR: Ralf Leistad
 * DATE: 10/08/2018
 */

import java.util.ArrayList;

public class Instructor {
    private int employeeNum;
    private String instructorName;
    private String instructorMail;
    private String phoneNum;
    public static ArrayList<Instructor> allInstructors = new ArrayList<>();

    public Instructor(int employeeNum, String instructorName, String instructorMail, String phoneNum) {
        boolean isValid = true;

        for(Instructor instructor : allInstructors){
            if(instructor.getEmployeeNum() == employeeNum){
                isValid = false;
                System.out.println("Instructor info reading failed - Employee number " + employeeNum + " already used.");
            }
        }

        if(isValid){
            this.employeeNum = employeeNum;
            this.instructorName = instructorName;
            this.instructorMail = instructorMail;
            this.phoneNum = phoneNum;
            allInstructors.add(this);
        }
    }

    public int getEmployeeNum() {
        return employeeNum;
    }

    public String getInstructorName() {
        return instructorName;
    }

    public String getInstructorMail() {
        return instructorMail;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("Intructor number: ");
        sb.append(this.getEmployeeNum());
        sb.append("\nName: ");
        sb.append(this.getInstructorName());
        sb.append("\nCourses Teaching: ");
        for(Course course : Course.allCourses){
            if(this.equals(course.getCourseInstructor())){
                sb.append("\n\t");
                sb.append(String.valueOf(course.getCourseNum()));
                sb.append(": ");
                sb.append(String.valueOf(course.enrolledStudents.size()));
                sb.append(" enrolled");
            }
        }
        return sb.toString();
    }

}

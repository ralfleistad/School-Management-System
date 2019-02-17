/*
 * TITLE: School.java
 * ABSTRACT: This program reads information from a file and creates instructor-, course- and student-objects based on that information.
 *           A course can not be deleted from the registry if it has enrolled students and when students graduate they get removed from all courses
 *           they were enrolled in.
 * AUTHOR: Ralf Leistad
 * DATE: 10/08/2018
 */
import java.io.*;

public class School {
    private String schoolName;

    public School(String name){
        this.schoolName = name;
    }

    /*
     * The methods 'getOnlyInstructor' and 'getOnlyStudent' returns
     * an object of that class. These were made to refactor the code and avoid looping through each ArrayList
     * every time I had to verify if parameter-value corresponded to an actual objects value.
     */

    // METHOD THAT RETRIEVES INFORMATION FROM ALL OTHER CLASSES
    public void schoolInfo(){
        System.out.println("School name: " + this.schoolName +
                            "\nInstructor information");
        for(Instructor inst : Instructor.allInstructors){
            System.out.println("\t" + inst.getInstructorName());
        }

        System.out.println("Course Information");
        for(Course course : Course.allCourses){
            System.out.println("\t" + course.getCourseTitle());
        }

        System.out.println("Student information");
        for(Student stud : Student.allStudents){
            System.out.println("\t" + stud.getStudentName());
        }
    }

    // METHOD READS FROM FILE AND CREATES OBJECTS BASED ON INFORMATION FROM FILE
    public void readData(String infoFile){
        //File file = new File(infoFile);
        int whatInfoCount = 0;

        try(BufferedReader reader = new BufferedReader(new FileReader(infoFile))){
            String line;

            while((line = reader.readLine()) != null){

                if(line.matches("\\d+")){
                //if(Integer.valueOf(line) instanceof Integer){
                    int informationAmount = Integer.valueOf(line);

                    for(int i = 0; i < informationAmount; i++){
                        String information = reader.readLine();
                        String[] splittedInfo = information.split(",");
                        if(splittedInfo.length == 4) {
                            if(whatInfoCount == 0) {
                                //ADD INSTRUCTORS HERE
                                Instructor tempInstructor = new Instructor(Integer.valueOf(splittedInfo[0]), splittedInfo[1], splittedInfo[2], splittedInfo[3]);
                            }
                            else if(whatInfoCount == 1 ) {
                                //ADD COURSES HERE
                                Course tempCourse = new Course(Integer.valueOf(splittedInfo[0]), splittedInfo[1], Integer.valueOf(splittedInfo[2]), splittedInfo[3]);
                            }
                        }
                        else if(splittedInfo.length == 2){
                            //ADD STUDENTS HERE
                            Student tempStudent = new Student(Integer.valueOf(splittedInfo[0]), splittedInfo[1]);
                        }
                    }
                    whatInfoCount++;
                }
            }
            System.out.println("Done.");
        } catch(FileNotFoundException FNFexc){
            System.out.println("Could not find file...");
        } catch(IOException exc){
            System.out.println("IOE exception, sadly");
        }
    }

    // SEARCH FOR INSTRUCTOR BY EMAIL
    public void searchByEmail(String email){
        boolean found = false;
        String result = "";
        System.out.println("Search key: " + email);

        for(Instructor instructor : Instructor.allInstructors){
            if(email.equalsIgnoreCase(instructor.getInstructorMail())){
                result = "Employee number: " + instructor.getEmployeeNum() +
                            "\nName: " + instructor.getInstructorName() +
                            "\nPhone: " + instructor.getPhoneNum();
                found = true;
            }
        }

        if(!found){
            result = "No employee with email " + email;
        }
        System.out.println(result);
    }

    // CREATES A NEW INSTRUCTOR OBJECT
    public void addInstructor(int num, String name, String email, String phoneNum){
        Instructor tempInstructor = new Instructor(num, name, email, phoneNum);
    }

    // CREATES A NEW COURSE OBJECT
    public void addCourse(int num, String title, int enrollment, String location){
        Course tempCourse = new Course(num, title, enrollment, location);
    }

    // ASSIGNS AN INSTRUCTOR TO A COURSE
    public void assignInstructor(int courseNum, int instructorNum){
        if((getOnlyInstructor(instructorNum) != null) && (getCourse(courseNum) != null)){
            getCourse(courseNum).setCourseInstructor(getOnlyInstructor(instructorNum));
        }
        else{
            System.out.println("Instructor " + instructorNum + " does not exist.");
        }
    }

    // REGISTERS A STUDENT IN A COURSE
    public void register(int courseNum, int studentId){
        if((getOnlyStudent(studentId) != null) && (getCourse(courseNum) != null)){
            if(getCourse(courseNum).enrolledStudents.size() < getCourse(courseNum).getMaxEnrollment()) {
                getCourse(courseNum).enrolledStudents.add(getOnlyStudent(studentId));
            }
            else{
                System.out.println("Registration failed - Class is full.");
            }
        }
        else{
            System.out.println("Student " + studentId + " does not exist.");
        }
    }

    // GIVES A STUDENT I SPECIFIED SCORE IN A COURSE
    public void putScore(int courseNum, int studentId, double score){
        if((getOnlyStudent(studentId) != null) && (getCourse(courseNum) != null)){
            if(getCourse(courseNum).enrolledStudents.contains(getOnlyStudent(studentId))) {
                getCourse(courseNum).courseTotal += score;
                getCourse(courseNum).populateMap(getOnlyStudent(studentId), score);
            }
            else {
                System.out.println("Student " + studentId + " (" + getOnlyStudent(studentId).getStudentName() + ") is not enrolled in " + courseNum);
            }
        }
    }

    // UNREGISTERS A STUDENT FROM A COURSE
    public void unRegister(int courseNum, int studentId){
        int index = 0;
        boolean found = false;
        if((getOnlyStudent(studentId) != null) && (getCourse(courseNum) != null)){
            if(getCourse(courseNum).enrolledStudents.contains(getOnlyStudent(studentId))){
                index = getCourse(courseNum).enrolledStudents.indexOf(getOnlyStudent(studentId));
                //getOnlyCourse(courseNum).enrolledStudents.remove(getOnlyStudent(studentId));
                //getOnlyCourse(courseNum).courseTotal -= getOnlyCourse(courseNum).studentScores.get(getOnlyStudent(studentId));
                found = true;
            }
        }

        if(found){
            getCourse(courseNum).enrolledStudents.remove(index);
        }
    }

    // PRINTS GENERAL COURSE INFO ABOUT A SPECIFIC COURSE
    public void courseInfo(int courseNum){
        if(getCourse(courseNum) != null){
            System.out.println("Course Number: " + getCourse(courseNum).getCourseNum() +
                    "\nInstructor: " + getCourse(courseNum).getCourseInstructor().getInstructorName() +
                    "\nCourse Title: " + getCourse(courseNum).getCourseTitle() +
                    "\nRoom: " + getCourse(courseNum).getCourseLocation() +
                    "\nTotal Enrolled: " + getCourse(courseNum).enrolledStudents.size() +
                    "\nCourse Average: " + getCourse(courseNum).getCourseAvg());
        }
    }

    // PRINTS OVERALL COURSE INFO
    public void courseInfo(){
        System.out.println("Number of courses: " + Course.allCourses.size());
        for(Course course : Course.allCourses){
            System.out.println("\t" + course.getCourseNum() + ": " + course.enrolledStudents.size() + " enrolled");
        }
    }

    // DELETES A COURSE
    public void deleteCourse(int courseNum){
        Course tempCourse = getCourse(courseNum);
        if(tempCourse != null){
            if(tempCourse.enrolledStudents.size() > 0){
                System.out.print("\nCourse deletion failed - Enrolled student(s) in the class");
            }
            else if(tempCourse.enrolledStudents.size() == 0){
                Course.allCourses.remove(tempCourse);
            }
        }
    }

    public void addStudent(int studId, String studName){
        Student tempStudent = new Student(studId, studName);
    }

    public Instructor getInstructor(int courseNum){
        return getCourse(courseNum).getCourseInstructor();
    }

    public Student getStudent(int studentId){
        for(Course course : Course.allCourses){
            if(course.enrolledStudents.contains(getOnlyStudent(studentId))){
                return getOnlyStudent(studentId);
            }
        }
        return null;
    }

    public void graduateStudent(int studentId){
        for(Course course : Course.allCourses){
            if(course.enrolledStudents.contains(getOnlyStudent(studentId))){
                course.enrolledStudents.remove(getOnlyStudent(studentId));
                course.courseTotal -= course.studentScores.get(getOnlyStudent(studentId));
            }
        }
    }

    // METHODS FOR RETRIEVING AN OBJECT OF COURSE, INSTRUCTOR OR STUDENT
    public Course getCourse(int courseNum){
        int index = 0;
        boolean found = false;

        for(Course course : Course.allCourses){
            if(course.getCourseNum() == courseNum){
                index = Course.allCourses.indexOf(course);
                found = true;
            }
        }

        if(found){
            return Course.allCourses.get(index);
        }
        else{
            return null;
        }
    }

    public Instructor getOnlyInstructor(int employeeNum){
        int index = 0;
        boolean found = false;

        for(Instructor inst : Instructor.allInstructors){
            if(inst.getEmployeeNum() == employeeNum){
                index = Instructor.allInstructors.indexOf(inst);
                found = true;
            }
        }

        if(found){
            return Instructor.allInstructors.get(index);
        }
        else{
            return null;
        }
    }

    public Student getOnlyStudent(int studentId){
        int index = 0;
        boolean found = false;

        for(Student stud : Student.allStudents){
            if(stud.getStudentId() == studentId){
                index = Student.allStudents.indexOf(stud);
                found = true;
            }
        }

        if(found){
            return Student.allStudents.get(index);
        }
        else{
            return null;
        }
    }

}

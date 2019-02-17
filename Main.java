public class Main {

    public static void main(String[] args) {
        School SCD = new School("SCD");

        SCD.readData("files/test12.txt");

        SCD.assignInstructor (301, 300);
        SCD.assignInstructor (302, 300);
        SCD.register (301, 1111);
        SCD.register (301, 2222);

        SCD.putScore (301, 1111, 100.0);
        SCD.putScore (301, 2222, 100.0);

        SCD.deleteCourse(301);
        SCD.deleteCourse(302);
        System.out.println("\n\n===== ANSWER: Only 1 course (301 with 2 enrolled). =====\n");
        SCD.courseInfo();
    }
}

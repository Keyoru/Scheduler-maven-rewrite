package scheduler;
import java.io.*;
import java.util.*;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;


public class App {  
        public static void main(String[] args) throws IOException {
            FileReader fileReader = new FileReader(new File("scheduler-input-draf1.xlsx"));
            HashMap<UUID, course> Courses = fileReader.readCoursesFromSheet();




            courseScheduler scheduler = new courseScheduler();

            scheduler.courseMap = Courses;

            for (Map.Entry<UUID, course> entry : Courses.entrySet()) {
                UUID courseId = entry.getKey();
                course course = entry.getValue();
    
                System.out.println("Course ID: " + course.courseID);
                System.out.println("num of slots" + course.nbOfSlots);
            }

            scheduler.ScheduleCourses();
            scheduler.displaySchedule();
            scheduler.outputExcel();

    }
}

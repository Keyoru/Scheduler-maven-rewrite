package scheduler;
import java.io.*;
import java.util.*;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;


public class App {  
        public static void main(String[] args) throws IOException {
            FileReader fileReader = new FileReader(new File("data/scheduler-input-draf1.xlsx"));
            HashMap<UUID, course> Courses = fileReader.readCoursesFromSheet();

            courseScheduler scheduler = new courseScheduler();

            scheduler.courseMap = Courses;

            scheduler.ScheduleCourses();
            scheduler.displaySchedule();
            scheduler.outputExcel();

    }
}

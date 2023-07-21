package scheduler;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;


public class App {  
        public static void main(String[] args) {

            courseScheduler scheduler = new courseScheduler();
            LinkedList<Integer> instructorDays1 = new LinkedList<>(List.of(0, 2)); 
            LinkedList<String> conflictingCourses1 = new LinkedList<>(List.of("MTH202"));
            course course1 = new course("CSE101", "Course 1", 3, 1, 2,
                    "John Doe", instructorDays1, 0, 2, conflictingCourses1,
                    "Type 1", 1);
            System.out.println(course1.TimeSlotIndexEnd);
            LinkedList<Integer> instructorDays2 = new LinkedList<>(List.of(0, 2)); // Monday, Wednesday
            LinkedList<String> conflictingCourses2 = new LinkedList<>(List.of("CSE101"));
            course course2 = new course("MTH202", "Course 2", 4, 1, 2,
                    "Jane Smith", instructorDays2, 0, 1, conflictingCourses2,
                    "Type 2", 1);
            LinkedList<Integer> instructorDays3 = new LinkedList<>(List.of(4)); // Friday
            LinkedList<String> conflictingCourses3 = new LinkedList<>(List.of("CSE101", "MTH202"));
            course course3 = new course("ENG101", "Course 3", 3, 1, 1,
                    "Sarah Johnson", instructorDays3, 5, 6, conflictingCourses3,
                    "Type 3", 1);
            LinkedList<Integer> instructorDays4 = new LinkedList<>(List.of(0, 2)); // Monday, Wednesday
            LinkedList<String> conflictingCourses4 = new LinkedList<>(List.of("CSE101", "MTH202"));
            course course4 = new course("PHY201", "Course 4", 4, 2, 2,
                    "Michael Brown", instructorDays4, 3, 6, conflictingCourses4,
                    "Type 4", 1);
            LinkedList<Integer> instructorDays5 = new LinkedList<>(List.of(3, 4)); // Thursday, Friday
            LinkedList<String> conflictingCourses5 = new LinkedList<>(List.of("MTH202"));
            course course5 = new course("BIO101", "Course 5", 3, 1, 2,
                    "Emily Davis", instructorDays5, 2, 4, conflictingCourses5,
                    "Type 5", 1);
            LinkedList<Integer> instructorDays6 = new LinkedList<>(List.of(1, 2, 3)); // Tuesday, Thursday
            LinkedList<String> conflictingCourses6 = new LinkedList<>(List.of("CSE101", "PHY201"));
            course course6 = new course("ENG202", "Course 6", 3, 1, 2,
                    "David Wilson", instructorDays6, 1, 6, conflictingCourses6,
                    "Type 6", 4);
            
            LinkedList<Integer> instructorDays7 = new LinkedList<>(List.of(0, 1)); // Monday, Tuesday
            LinkedList<String> conflictingCourses7 = new LinkedList<>(List.of("CSE101", "ENG202"));
            course course7 = new course("MTH101", "Course 7", 3, 1, 3,
                    "Robert Johnson", instructorDays7, 0, 3, conflictingCourses7,
                    "Type 7", 1);
            LinkedList<Integer> instructorDays8 = new LinkedList<>(List.of(1, 2)); // Tuesday, Wednesday
            LinkedList<String> conflictingCourses8 = new LinkedList<>(List.of("MTH101"));
            course course8 = new course("CSE202", "Course 8", 4, 1, 2,
                    "Amy Wilson", instructorDays8, 2, 5, conflictingCourses8,
                    "Type 8", 1);
            LinkedList<Integer> instructorDays9 = new LinkedList<>(List.of(2, 3, 4)); // Wednesday, Thursday, Friday
            LinkedList<String> conflictingCourses9 = new LinkedList<>(List.of("ENG202"));
            course course9 = new course("PHY101", "Course 9", 3, 1, 3,
                    "William Davis", instructorDays9, 1, 5, conflictingCourses9,
                    "Type 9", 1);
            LinkedList<Integer> instructorDays10 = new LinkedList<>(List.of(0, 3)); // Monday, Thursday
            LinkedList<String> conflictingCourses10 = new LinkedList<>(List.of("CSE202", "PHY101"));
            course course10 = new course("MTH201", "Course 10", 4, 1, 3,
                    "Laura Brown", instructorDays10, 0, 4, conflictingCourses10,
                    "Type 10", 1);
            LinkedList<Integer> instructorDays11 = new LinkedList<>(List.of(1, 4)); // Tuesday, Friday
            LinkedList<String> conflictingCourses11 = new LinkedList<>(List.of("MTH201"));
            course course11 = new course("BIO202", "Course 11", 3, 1, 2,
                    "Michael Wilson", instructorDays11, 3, 5, conflictingCourses11,
                    "Type 11", 1);
            LinkedList<Integer> instructorDays12 = new LinkedList<>(List.of(0, 2, 4)); // Monday, Wednesday, Friday
            LinkedList<String> conflictingCourses12 = new LinkedList<>(List.of("CSE202", "BIO202"));
            course course12 = new course("ENG102", "Course 12", 3, 1, 3,
                    "Sophia Johnson", instructorDays12, 1, 6, conflictingCourses12,
                    "Type 12", 1);
            LinkedList<Integer> instructorDays13 = new LinkedList<>(List.of(0, 1, 2)); // Monday, Tuesday, Wednesday
            LinkedList<String> conflictingCourses13 = new LinkedList<>(List.of("ENG102", "PHY201"));
            course course13 = new course("CHEM101", "Course 13", 3, 1, 3,
                    "Daniel Smith", instructorDays13, 2, 5, conflictingCourses13,
                    "Type 13", 1);
            LinkedList<Integer> instructorDays14 = new LinkedList<>(List.of(2, 3, 4)); // Wednesday, Thursday, Friday
            LinkedList<String> conflictingCourses14 = new LinkedList<>(List.of("CHEM101"));
            course course14 = new course("CSE301", "Course 14", 3, 1, 3,
                    "Olivia Davis", instructorDays14, 3, 6, conflictingCourses14,
                    "Type 14", 1);
            LinkedList<Integer> instructorDays15 = new LinkedList<>(List.of(1, 3, 4)); // Tuesday, Thursday, Friday
            LinkedList<String> conflictingCourses15 = new LinkedList<>(List.of("CSE301", "ENG202"));
            course course15 = new course("MTH301", "Course 15", 4, 1, 3,
                    "Ethan Wilson", instructorDays15, 1, 5, conflictingCourses15,
                    "Type 15", 1);
            LinkedList<Integer> instructorDays16 = new LinkedList<>(List.of(0, 1, 4)); // Monday, Tuesday, Friday
            LinkedList<String> conflictingCourses16 = new LinkedList<>(List.of("MTH301"));
            course course16 = new course("PHY301", "Course 16", 3, 1, 2,
                    "Ava Johnson", instructorDays16, 2, 4, conflictingCourses16,
                    "Type 16", 1);
            LinkedList<Integer> instructorDays17 = new LinkedList<>(List.of(2, 3)); // Wednesday, Thursday
            LinkedList<String> conflictingCourses17 = new LinkedList<>(List.of("PHY301", "ENG101"));
            course course17 = new course("BIO301", "Course 17", 4, 1, 2,
                    "Jacob Smith", instructorDays17, 3, 5, conflictingCourses17,
                    "Type 17", 1);
            LinkedList<Integer> instructorDays18 = new LinkedList<>(List.of(1, 2, 4)); // Tuesday, Wednesday, Friday
            LinkedList<String> conflictingCourses18 = new LinkedList<>(List.of("BIO301", "CSE101"));
            course course18 = new course("ENG301", "Course 18", 3, 1, 3,
                    "Mia Davis", instructorDays18, 1, 6, conflictingCourses18,
                    "Type 18", 1);
            LinkedList<Integer> instructorDays19 = new LinkedList<>(List.of(0, 1, 3)); // Monday, Tuesday, Thursday
            LinkedList<String> conflictingCourses19 = new LinkedList<>(List.of("ENG301"));
            course course19 = new course("CHEM201", "Course 19", 4, 1, 2,
                    "James Brown", instructorDays19, 0, 4, conflictingCourses19,
                    "Type 19", 1);
            LinkedList<Integer> instructorDays20 = new LinkedList<>(List.of(2, 3, 4)); // Wednesday, Thursday, Friday
            LinkedList<String> conflictingCourses20 = new LinkedList<>(List.of("CHEM201", "PHY301"));
            course course20 = new course("CSE401", "Course 20", 3, 1, 3,
                    "Charlotte Wilson", instructorDays20, 3, 6, conflictingCourses20,
                    "Type 20", 1);  
            
            scheduler.courseMap.put(UUID.randomUUID(), course1);
            scheduler.courseMap.put(UUID.randomUUID(), course2);
            //scheduler.courseMap.put(UUID.randomUUID(), course3);
            //scheduler.courseMap.put(UUID.randomUUID(), course4);
            //scheduler.courseMap.put(UUID.randomUUID(), course5);
            //scheduler.courseMap.put(UUID.randomUUID(), course6);
            //scheduler.courseMap.put(UUID.randomUUID(), course7);
            //scheduler.courseMap.put(UUID.randomUUID(), course8);
            //scheduler.courseMap.put(UUID.randomUUID(), course9);
            //scheduler.courseMap.put(UUID.randomUUID(), course10);
            //scheduler.courseMap.put(UUID.randomUUID(), course11);
            //scheduler.courseMap.put(UUID.randomUUID(), course12);
            //scheduler.courseMap.put(UUID.randomUUID(), course13);
            //scheduler.courseMap.put(UUID.randomUUID(), course14);
            //scheduler.courseMap.put(UUID.randomUUID(), course15);
            //scheduler.courseMap.put(UUID.randomUUID(), course16);
            //scheduler.courseMap.put(UUID.randomUUID(), course17);
            //scheduler.courseMap.put(UUID.randomUUID(), course18);
            //scheduler.courseMap.put(UUID.randomUUID(), course19);
            //scheduler.courseMap.put(UUID.randomUUID(), course20);

                
            scheduler.ScheduleCourses();
            
            System.out.println();
            // Display the course schedule
            
            scheduler.displaySchedule();
            scheduler.outputExcel();

            //for(LinkedList<UUID>[] days:scheduler.schedule){
            //    for(LinkedList<UUID> slots:days){
            //        for(UUID courseUUID: slots){
            //            for(int i = 0; i < 5; i++){
            //                for(int j = 0; j < 6;j++){
            //                    System.out.print(scheduler.courseMap.get(courseUUID).isScheduled[i][j]);
            //                }
            //                System.out.println();
            //            }
            //        }
            //        System.out.println("~~~~~~~~~~~~~~~~~");
            //    }
            //}
        }
}

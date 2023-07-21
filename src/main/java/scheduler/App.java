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
                                "John Doe", instructorDays1, 0, 6, conflictingCourses1,
                                "Type 1", 1, " duration2");
                LinkedList<Integer> instructorDays2 = new LinkedList<>(List.of(0, 2)); // Monday, Wednesday
                LinkedList<String> conflictingCourses2 = new LinkedList<>(List.of("CSE101"));
                course course2 = new course("MTH202", "Course 2", 4, 1, 2,
                                "Jane Smith", instructorDays2, 0, 1, conflictingCourses2,
                                "Type 2", 1, "duration");

                scheduler.enqueueCourse(course1);
                scheduler.enqueueCourse(course2);
                // scheduler.courseMap.put(UUID.randomUUID(), course3);
                // scheduler.courseMap.put(UUID.randomUUID(), course4);
                // scheduler.courseMap.put(UUID.randomUUID(), course5);
                // scheduler.courseMap.put(UUID.randomUUID(), course6);
                // scheduler.courseMap.put(UUID.randomUUID(), course7);
                // scheduler.courseMap.put(UUID.randomUUID(), course8);
                // scheduler.courseMap.put(UUID.randomUUID(), course9);
                // scheduler.courseMap.put(UUID.randomUUID(), course10);
                // scheduler.courseMap.put(UUID.randomUUID(), course11);
                // scheduler.courseMap.put(UUID.randomUUID(), course12);
                // scheduler.courseMap.put(UUID.randomUUID(), course13);
                // scheduler.courseMap.put(UUID.randomUUID(), course14);
                // scheduler.courseMap.put(UUID.randomUUID(), course15);
                // scheduler.courseMap.put(UUID.randomUUID(), course16);
                // scheduler.courseMap.put(UUID.randomUUID(), course17);
                // scheduler.courseMap.put(UUID.randomUUID(), course18);
                // scheduler.courseMap.put(UUID.randomUUID(), course19);
                // scheduler.courseMap.put(UUID.randomUUID(), course20);

                scheduler.ScheduleCourses();

                scheduler.displaySchedule();

                scheduler.moveUnscheduledCourses();

                System.out.println();

                scheduler.rescheduleConflicts();

                System.out.println();

                System.out.println();
                // Display the course schedule
                scheduler.displaySchedule();
                System.out.println("coursequeue");
                for (UUID courseUUID : scheduler.courseQueue) {
                        System.out.print(scheduler.courseMap.get(courseUUID).courseID + " ");
                }
                System.out.println();

        }
}
package scheduler;

import java.util.LinkedList;
import java.util.UUID;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.File;
import java.io.FileOutputStream;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class courseScheduler {

    int days = 5; 
    int timeslots = 6;

    LinkedList<UUID>[][] schedule = new LinkedList[days][timeslots];
    Map<UUID, course> courseMap;
    Queue<UUID> courseQueue;

    private final List<List<Integer>> dayPairs = new ArrayList<>();

    private Set<UUID> alreadyRescheduled = new HashSet<>();

    
    //for courses with no place found to store them
    LinkedList<UUID> unscheduledCourseHeap = new LinkedList<>();
    LinkedList<UUID> coursesToBeRescheduled = new LinkedList<>();

    FileWriter fileWriter;
    PrintWriter printWriter; 
    File logFile;
    
    File outputFile;
    Workbook workbook;
    Sheet sheet;

    courseScheduler() {

        try {

            LocalDateTime currentTime = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM_dd_HH:mm");
            String timestamp = currentTime.format(formatter);
    
            String logFileName = "log_" + timestamp + ".txt";
            logFile = new File(logFileName);
            String outputFileName = "output_"+timestamp+".xlsx";
            outputFile = new File(outputFileName);
    
            workbook = new XSSFWorkbook();
            sheet = workbook.createSheet("Course Schedule");

            fileWriter = new FileWriter(logFile, true); // 'true' for appending to an existing file
            printWriter = new PrintWriter(fileWriter);
            courseMap = new LinkedHashMap<>();
            courseQueue = new LinkedList<UUID>();

            // Initialize day pairs (M,W) and (T,Th)
            dayPairs.add(List.of(2, 4)); // Wednesday, Friday
            dayPairs.add(List.of(0, 2)); // Monday, Wednesday
            dayPairs.add(List.of(1, 3)); // Tuesday, Thursday
            

            for(int i = 0;i < days;i++){
                for(int j = 0; j < timeslots; j++){
                    schedule[i][j] = new LinkedList<UUID>();
                }
            }

        } catch (IOException e) {
            System.out.println("An error occurred while writing to the log file: " + e.getMessage());
        }
    }

    public LinkedList<UUID> getUnscheduledCourseHeap(){
        return unscheduledCourseHeap;
    }

    public void enqueueCourse(course course){
        UUID courseUUID = UUID.randomUUID();
        courseMap.put(courseUUID, course);
        courseQueue.add(courseUUID);
    }

    public void ScheduleCourses() {
        
        for (UUID courseUUID : courseQueue) {
            addCourseHandleLectures(courseUUID);
        }
        
    }

    public void moveUnscheduledCourses(){
        for(UUID courseUUID:unscheduledCourseHeap){
            coursesToBeRescheduled.add(courseUUID);
        }
    }
    

    public void rescheduleConflicts() {
        if (!coursesToBeRescheduled.isEmpty()) {
            Iterator<UUID> iterator = coursesToBeRescheduled.iterator();
            while (iterator.hasNext()) {
                UUID unscheduledCourseUUID = iterator.next();
                if (!alreadyRescheduled.contains(unscheduledCourseUUID)) {
                    if(rescheduleConflictingCourse(unscheduledCourseUUID)){
                        alreadyRescheduled.add(unscheduledCourseUUID);
                    }
                }

                displaySchedule();
                
                if(courseMap.get(unscheduledCourseUUID).numberOfSessions == courseMap.get(unscheduledCourseUUID).sessionsScheduled ){
                    System.out.println("iterator removing " + courseMap.get(unscheduledCourseUUID).courseID);
                    iterator.remove(); // Safely remove the current element from coursesToBeRescheduled
                }
                
            }
            
        }
    }


    public void addCourseHandleLectures(UUID courseUUID){

        // this section adds the course's own code to the its conflict list
        // this helps with different sections                 
        String[] courseNameSplit = courseMap.get(courseUUID).courseID.split("-");
        String courseID = courseNameSplit[0];
    
        if (!courseMap.get(courseUUID).conflictingCourses.contains(courseID)) {
            courseMap.get(courseUUID).conflictingCourses.add(courseID);
        }

        // logic for sections here
        // if more than 1 section for a course
        // add -1 -2 -3 etc... to course ID  

        if(courseMap.get(courseUUID).numberOfSections > 1){
            printWriter.println("course.lectures > 1");
            // this section to handle adding a course with -i lecture appended, this happens when attempting to reschedule courses to fit other unscheduled courses
            for(int i = 1; i <= courseMap.get(courseUUID).numberOfSections;i++){
                
                //currentSection same exact attributes as course but with courseID += "-"+i
               course currentSection = new course(courseMap.get(courseUUID).courseID+"-"+i,courseMap.get(courseUUID).courseName,
               courseMap.get(courseUUID).numberOfCredits,courseMap.get(courseUUID).numberOfSections,
               courseMap.get(courseUUID).numberOfSessions,courseMap.get(courseUUID).instructorName,
               courseMap.get(courseUUID).instructorDays,courseMap.get(courseUUID).TimeSlotIndexstart, courseMap.get(courseUUID).TimeSlotIndexEnd,courseMap.get(courseUUID).conflictingCourses,
               courseMap.get(courseUUID).courseType,courseMap.get(courseUUID).nbOfSlots);
                
                UUID currentSectionUUID = UUID.randomUUID();
                courseMap.put(currentSectionUUID, currentSection);
                addCourse(currentSectionUUID);
            }
        }else{
            addCourse(courseUUID);
        }


    }

    //calls the other helper method depending on requirements 
    public void addCourse(UUID courseUUID) {
 
        printWriter.println("Adding course " + courseMap.get(courseUUID).courseID);
        if (attemptDayPairSchedule(courseUUID) && !(courseMap.get(courseUUID).numberOfSessions < courseMap.get(courseUUID).instructorDays.size())) {
            printWriter.println("day pair successful (returned true @addCourse())");
            return;
        }
        if (attemptEqualSpreadSchedule(courseUUID)) {
            printWriter.println("equal spread successful (returned true @addCourse())");
            return;
        }
    
        if (attemptAnySchedule(courseUUID)) {
            printWriter.println("any schedule successful (returned true @addCourse())");
            return;
        }
        System.out.println("adding to heap " +courseMap.get(courseUUID).courseID);
        unscheduledCourseHeap.add(courseUUID); 
        
    }



    // TODO different lectures on different day pairs or times if possible
    private boolean attemptDayPairSchedule(UUID courseId) {
        printWriter.println("attempting day pair scheduling");
        course course = courseMap.get(courseId);
        
        List<List<Integer>> availableDayPairs = new ArrayList<>();

        try{

            String scheduledCourseid = courseMap.get(courseId).courseID;
            String[] scheduledCourseSplit = scheduledCourseid.split("-");
            int scheduledCourseLectureNB = Integer.parseInt(scheduledCourseSplit[1]);

            List<Integer> dayPair = getNextDayPair(scheduledCourseLectureNB);
            if(canWorkWith(dayPair, course.instructorDays)){
                availableDayPairs.add(dayPair);
            }
        }catch(Exception e){
            
        }
        for (List<Integer> dayPair : dayPairs) {
            if (canWorkWith(dayPair, courseMap.get(courseId).instructorDays)) {
                availableDayPairs.add(dayPair);
            }
        } 

        for(List<Integer> dayPair: availableDayPairs){
            int pairSessions = course.numberOfSessions / 2;
            int dayIndex1 = dayPair.get(0);
            int dayIndex2 = dayPair.get(1);
            int timeSlotIndex = course.TimeSlotIndexstart;
            while (courseMap.get(courseId).sessionsScheduled < pairSessions && timeSlotIndex <= course.TimeSlotIndexEnd - course.nbOfSlots) {
                boolean canSchedule = true;
                for (int session = 0; session < course.nbOfSlots && courseMap.get(courseId).sessionsScheduled < pairSessions; session++) {
                    for (int slot = 0; slot < course.nbOfSlots; slot++) {
                        if (!isSlotAvailable(courseId, dayIndex1, timeSlotIndex + slot) ||
                                !isSlotAvailable(courseId, dayIndex2, timeSlotIndex + slot)) {
                            canSchedule = false;
                            break;
                        }
                    }
                    if (canSchedule) {
                        for (int slot = 0; slot < course.nbOfSlots; slot++) {
                            scheduleCourseInSlot(courseId, dayIndex1, timeSlotIndex + slot);
                            scheduleCourseInSlot(courseId, dayIndex2, timeSlotIndex + slot);
                        }
                    } else {
                        break;
                    }
                }
                timeSlotIndex++;
            }
            if (courseMap.get(courseId).sessionsScheduled >= courseMap.get(courseId).numberOfSessions) {
                return true;
            } else {
                printWriter.println("pair false");
            }
        }
    
     return false;
    }
    
    
    
    
    private boolean attemptEqualSpreadSchedule(UUID courseId) {
        
        printWriter.println("attempting equal spread schedule");
        for(int dayIndex: courseMap.get(courseId).instructorDays){

            int sessionsPerDay = courseMap.get(courseId).numberOfSessions / courseMap.get(courseId).instructorDays.size();
            int sessionsScheduled = 0;

            for(int i = courseMap.get(courseId).TimeSlotIndexstart; i < courseMap.get(courseId).TimeSlotIndexEnd && sessionsScheduled < sessionsPerDay; i++){
                if (isSlotAvailable(courseId, dayIndex, i)) {
                    if (courseMap.get(courseId).nbOfSlots > 1) { // case 1, each course lecture takes more than 1 slot of time
                        if (areSlotsAvailable(courseId,dayIndex, i,  i + courseMap.get(courseId).nbOfSlots - 1)) {
                            scheduleCourseInSlots(courseId, dayIndex, i, i + courseMap.get(courseId).nbOfSlots - 1);
                            sessionsScheduled++;
                        }
                    } else { // case 2 each course lecture is just 1 slot
                        scheduleCourseInSlot(courseId, dayIndex, i);
                        sessionsScheduled++;
                    }
                }
            }
        }
        printWriter.println("\n");
        printWriter.flush();    
        if (courseMap.get(courseId).sessionsScheduled >= courseMap.get(courseId).numberOfSessions) {
            return true;
        } else {
            printWriter.println("equal spread false");
            return false;
        }
    }


    private boolean attemptAnySchedule(UUID courseId) {

        printWriter.println("attempting any schedule");

        for (int dayIndex: courseMap.get(courseId).instructorDays) {
            for (int i = courseMap.get(courseId).TimeSlotIndexstart; i < courseMap.get(courseId).TimeSlotIndexEnd && courseMap.get(courseId).sessionsScheduled < courseMap.get(courseId).numberOfSessions; i++) {
                if (isSlotAvailable(courseId, dayIndex, i)) {
                    if (courseMap.get(courseId).sessionsScheduled >= courseMap.get(courseId).numberOfSessions) {
                        break;
                    }
                    if (courseMap.get(courseId).nbOfSlots > 1 && areSlotsAvailable(courseId, dayIndex, i, i + courseMap.get(courseId).nbOfSlots - 1)) {
                        scheduleCourseInSlots(courseId, dayIndex, i, i + courseMap.get(courseId).nbOfSlots -1);
                        courseMap.get(courseId).sessionsScheduled++;
                    } else if (courseMap.get(courseId).nbOfSlots == 1) {
                        scheduleCourseInSlot(courseId, dayIndex, i);
                        courseMap.get(courseId).sessionsScheduled++;
                    }
                    
                }
            }
        }

        if (courseMap.get(courseId).sessionsScheduled >= courseMap.get(courseId).numberOfSessions) {
            return true;
        }else{
            printWriter.println("any schedule failed (didnt fully schedule)");
        }
    
        return false;
    }
    

    private void scheduleCourseInSlot(UUID courseId, int dayIndex, int slotIndex){
        printWriter.println("added course to day: " + dayIndex + " time slot: "+ slotIndex);
        courseMap.get(courseId).sessionsScheduled ++;
        printWriter.println("scheduled sessions: " + courseMap.get(courseId).sessionsScheduled);
        schedule[dayIndex][slotIndex].add(courseId);

        courseMap.get(courseId).isScheduled[dayIndex][slotIndex] = true;
        printWriter.flush();
    }
    
    private void scheduleCourseInSlots(UUID courseId, int dayIndex, int SlotIndexStart, int slotIndexEnd){
        if(courseMap.get(courseId).TimeSlotIndexEnd < slotIndexEnd){
            return;
        }
        for(int i = SlotIndexStart; i <= slotIndexEnd; i++){
            printWriter.println("added course to day: " + dayIndex + " time slot: "+ i);
            schedule[dayIndex][i].add(courseId);
            courseMap.get(courseId).sessionsScheduled++;
            courseMap.get(courseId).isScheduled[dayIndex][i] = true;
        };
        printWriter.flush();
    }


    private boolean isSlotAvailable(UUID courseUUID, int dayIndex, int slotIndex) {
        //if(!courseMap.get(courseUUID).isAvailable[dayIndex][slotIndex]){
        //    return false;
        //}
        for (String conflict : courseMap.get(courseUUID).conflictingCourses) {
            for (UUID scheduledCourseUUID : schedule[dayIndex][slotIndex]) {

                
                if (courseMap.get(scheduledCourseUUID).instructorName.equals(courseMap.get(courseUUID).instructorName)) {
                    return false;
                }
    
                String scheduledCourseid = courseMap.get(scheduledCourseUUID).courseID;
                // parsing to get rid of -i of different sections
                // for example instead of comparing PHYS201-2 (present in slot) which isnt present in the course.conflictingCourses
                // we compare PHYS201
                String[] scheduledCourseSplit = scheduledCourseid.split("-");
                String scheduledCourseID = scheduledCourseSplit[0];
                if (scheduledCourseID.equals(conflict)) {
                    return false;
                }
            }
        }
        printWriter.println("slot av true");
        return true;
    }
    
    // checks for conflicts in several slots in a row, used for courses with longer than 1 slot lecture time
    private boolean areSlotsAvailable(UUID courseUUID, int dayIndex, int slotIndexStart, int slotIndexEnd) {
        
        if(slotIndexEnd >= courseMap.get(courseUUID).TimeSlotIndexEnd){
            return false;
        }
    
        for (int i = slotIndexStart; i <= slotIndexEnd; i++) {
            if (!isSlotAvailable(courseUUID, dayIndex, i)) {
                return false;
            }
        }
        return true;
    }

    private boolean rescheduleConflictingCourse(UUID unscheduledCourseUUID){
        course unscheduledCourse = courseMap.get(unscheduledCourseUUID);
        for(int dayIndex: unscheduledCourse.instructorDays){
            
            for(int slotIndex = unscheduledCourse.TimeSlotIndexstart; slotIndex < unscheduledCourse.TimeSlotIndexEnd;slotIndex++){   
                for(UUID scheduledCourseUUID: schedule[dayIndex][slotIndex]){
                    

                    // parsing to get rid of -i of different sections
                    // for example instead of comparing PHYS201-2 (present in slot) which isnt present in the course.conflictingCourses
                    // we compare PHYS201
                    String scheduledCourselectureid = courseMap.get(scheduledCourseUUID).courseID;
                    String[] scheduledCourseSplit = scheduledCourselectureid.split("-");
                    String scheduledCourseID = scheduledCourseSplit[0];
                    
                    String unscheduledCourselectureid = courseMap.get(unscheduledCourseUUID).courseID;
                    String[] unscheduledCourseSplit = unscheduledCourselectureid.split("-");
                    String unscheduledCourseID = unscheduledCourseSplit[0];

                    // dont reschedule if lectures are of same course
                    if(scheduledCourseID.equals(unscheduledCourseID)){
                        continue;
                    }
                    
                    if (unscheduledCourse.conflictingCourses.contains(scheduledCourseID)) {
                        printWriter.println("found conflict");

                        System.out.println("removing " + scheduledCourselectureid);
                        unscheduleCourseFromAll(scheduledCourseUUID);
                        addCourse(unscheduledCourseUUID);
                        unscheduledCourseHeap.remove(unscheduledCourseUUID);
                        addCourse(scheduledCourseUUID);
                        return true;
                    }

               } 
            }
        }
        return false;
    }
    
    public void unscheduleCourseFromSlot(UUID courseUUID, int dayIndex, int slotIndex){
        schedule[dayIndex][slotIndex].remove(courseUUID);
        printWriter.print(courseMap.get(courseUUID).isScheduled[dayIndex][slotIndex]);
        courseMap.get(courseUUID).isScheduled[dayIndex][slotIndex] = false;
    }
     
    public void unscheduleCourseFromAll(UUID courseUUID){
        courseMap.get(courseUUID).sessionsScheduled = 0;
        for(int i = 0; i < 5; i++){
            for(int j = 0; j < 6;j++){
                if(courseMap.get(courseUUID).isScheduled[i][j]){
                    unscheduleCourseFromSlot(courseUUID, i, j);
                }
            }
        }
        displaySchedule();
    }


    private List<Integer> getNextDayPair(int lectureIndex) {
        // Assumes dayPairs is a list containing pairs of day indices, e.g., [[0, 2], [1, 3], [2, 4]]
        return dayPairs.get(lectureIndex % dayPairs.size());
    }


    private static boolean canWorkWith(List<Integer> daypair, List<Integer> instructorDays) {
        for (int day : daypair) {
            if (!instructorDays.contains(day)) {
                return false; // The pair does not contain one of the working days
            }
        }
        return true; // All working days are present in the pair
    }


    public void outputExcel(){
        
        String[] timeSlots = {"08:00", "09:15", "10:30", "11:45", "13:00", "14:15"};
        String[] daysOfWeek = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday"};

        // Create the header row with days of the week
        Row headerRow = sheet.createRow(0);
        for (int i = 0; i < daysOfWeek.length; i++) {
            Cell cell = headerRow.createCell(i + 1);
            cell.setCellValue(daysOfWeek[i]);
        }

        // Iterate over time slots and populate the sheet with course IDs
        for (int i = 0; i < timeSlots.length; i++) {
            Row row = sheet.createRow(i + 1);
            Cell timeSlotCell = row.createCell(0);
            timeSlotCell.setCellValue(timeSlots[i]);

            for (int j = 0; j < daysOfWeek.length; j++) {
                Cell cell = row.createCell(j + 1);

                // Get the course ID for the specific time slot and day (replace this with your actual data retrieval logic)
                String courseId = "";
                String cellValue = "";
                try {
                    for(UUID courseUUID: schedule[j][i]){

                        courseId = courseMap.get(courseUUID).courseID;
                        cellValue += courseId + "\n";
                                
                    }
                } catch (Exception e) {
                    courseId = "";
                }
                
                

                // Set the course ID in the cell
                if (courseId != null) {
                    cell.setCellValue(cellValue);
                }
            }
        }        
        try (FileOutputStream outputStream = new FileOutputStream("CourseSchedule.xlsx")) {
            workbook.write(outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Close the workbook
        try {
            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Excel file generated successfully!");
    }


    public void displaySchedule(){
        for(int i = 0; i < days; i++){
            for(int j = 0; j < timeslots; j++){
                System.out.print("[");
                for(UUID courseUUID: schedule[i][j]){
                    System.out.print(courseMap.get(courseUUID).courseID+" ");
                }
                System.out.print("] ");
            }   
            System.out.println();
        }

        System.out.println("\nUnscheduled courses remaining: ");
        for(UUID unscheduledCourseUUID: unscheduledCourseHeap){
            System.out.println(courseMap.get(unscheduledCourseUUID).courseID);
        }
    }

}

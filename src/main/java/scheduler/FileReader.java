package scheduler;
import java.io.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalTime;
import java.util.UUID;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

//testing 

public class FileReader {
    private File file;

    public FileReader(File f) {
        this.file = f;
    }

    public HashMap<UUID, course> readCoursesFromSheet() throws IOException {
       HashMap<UUID, course> Courses = new HashMap<UUID, course>();

        FileInputStream fis = new FileInputStream(file);
        Workbook workbook = new XSSFWorkbook(fis);

        // Get the specific sheet by name
        Sheet sheet1 = workbook.getSheetAt(1);
        Sheet sheet2 = workbook.getSheetAt(0);

       

        if (sheet1 != null && sheet2 != null) {
            // Iterate over rows in both sheets simultaneously
            int numRows = Math.max(sheet1.getLastRowNum() + 1, sheet2.getLastRowNum() + 1);
            for (int rowNum = 1; rowNum < numRows; rowNum++) { // Start from 1 to skip the header row
                Row row1 = sheet1.getRow(rowNum);
                Row row2 = sheet2.getRow(rowNum);

                if (row1 != null && row2 != null) {
                    // Read data from the rows and create Course object
                    String course_id = row1.getCell(0).getStringCellValue();
                    String course_name = row1.getCell(1).getStringCellValue();
                    int num_credits = (int)row1.getCell(2).getNumericCellValue();
                    int num_sections = (int)row1.getCell(3).getNumericCellValue();
                    int num_sessions = (int)row1.getCell(4).getNumericCellValue();
                    String instructor_name = row1.getCell(5).getStringCellValue();
                    String instructor_days = row1.getCell(6).getStringCellValue();
                    String instructor_hours = row1.getCell(7).getStringCellValue();


                  LinkedList<String> timeslots = convertHourstoSlots(instructor_hours);
                  int[] slots = getSlotsIndicies(timeslots.getFirst() , timeslots.getLast());
                  int index1 = slots[0];
                  int index2 = slots[1];


                    LinkedList<String>Split_Days = Split_Days(instructor_days);
                    LinkedList<Integer>Instructor_days = new LinkedList<Integer>();

                    for(int i=0;i<Split_Days.size();i++){
                       Instructor_days.add(getDayIndex(Split_Days.get(i)));
                   }

                    String conflict_courses  = row2.getCell(0).getStringCellValue();
                    LinkedList<String> conflicting_courses = Split_Days(conflict_courses);

                    String course_type = row2.getCell(1).getStringCellValue();
                    String session_time = row2.getCell(2).getStringCellValue();


              

                      course course = new  course(course_id, course_name, num_credits,num_sections,num_sessions, instructor_name,
                      Instructor_days, index1, index2, conflicting_courses , course_type , calculateSlots(session_time));

        
                      Courses.put(UUID.randomUUID() , course);
                }
            }
         }
         workbook.close();
         fis.close();
 
         return Courses;
         }
        



    public static int calculateSlots(String duration) {
        String[] parts = duration.split("\\s+");

        int hours = 0;
        int minutes = 0;

        for (int i = 0; i < parts.length; i++) {
            if ( parts[i].equalsIgnoreCase("hour")  || parts[i].equalsIgnoreCase("hours") ) {
                hours = Integer.parseInt(parts[i - 1]);
            } else if (parts[i].equalsIgnoreCase("min") || parts[i].equalsIgnoreCase("mins") || parts[i].equalsIgnoreCase("minutes")) {
                minutes = Integer.parseInt(parts[i - 1]);
            }
            else if(parts[i].equalsIgnoreCase("internship") || parts[i].equalsIgnoreCase("no")){
                return 0;
            }
        }

        int totalMinutes = (hours * 60) + minutes;
        int slots = totalMinutes / 75;

        if(slots == 0){
            slots = 1;
        }

        return slots;
    }

    private boolean isFirstSheet(Sheet sheet) {
     // Get the first row in the sheet
      Row firstRow = sheet.getRow(0);
       if (firstRow != null) {
        // Iterate over cells in the first row
           for (Cell cell : firstRow) {
             // Check the header value of a specific column
            if (cell.getColumnIndex() == 0 && cell.getStringCellValue().equalsIgnoreCase("course code")) {
             return true;
           }
           else{
            break;
           }
         }
    }
   return false;
  }

     private int getDayIndex(String day) {
        switch (day) {
            case "Monday":
                return 0;
            case "Tuesday":
                return 1;
            case "Wednesday":
                return 2;
            case "Thursday":
                return 3;
            case "Friday":
                return 4;
            default:
                throw new IllegalArgumentException("Invalid day: " + day);
        }
    }

     private LinkedList<String> convertHourstoSlots(String instructorHours){
        LinkedList<String> slots = new LinkedList<>();
        String[] hours = instructorHours.split("/");

        for (String hour : hours) {
            slots.add(hour.trim());
        }

        return slots;
    }

    private LinkedList<String> Split_Days(String instructors_day){
        LinkedList<String> slots = new LinkedList<String>();
        String[] hours = instructors_day.split("/");

        for (String hour : hours) {
            slots.add(hour.trim());
        }

        return slots;
    }


      private int[] getSlotsIndicies(String hour1, String hour2) {
        LocalTime startTime = LocalTime.parse(hour1);
        LocalTime endTime = LocalTime.parse(hour2);


        LocalTime[] slots = {
            LocalTime.parse("08:00"), // Slot 0
            LocalTime.parse("09:30"), // Slot 1
            LocalTime.parse("11:00"), // Slot 2
            LocalTime.parse("13:00"), // Slot 3
            LocalTime.parse("14:30"), // Slot 4
            LocalTime.parse("16:00"), // Slot 5
            LocalTime.parse("17:15")  // Final hour
        };

        int startSlot = -1;
        int endSlot = -1;

        // Find the first slot
        if((startTime.isAfter(LocalTime.parse("12:15")) && startTime.isBefore(LocalTime.parse("13:00"))) // between 12:15 and 1 exclusive
            || startTime.equals(LocalTime.parse("12:15"))){
                startSlot = 3;
        }else{
            boolean slotFound = false;
            for (int i = 0; i < slots.length; i++) {
                if (startTime.isBefore(slots[i]) || startTime.equals(slots[i])) {
                    startSlot = i;
                    slotFound = true;
                    break;
                }
            }
            if(!slotFound){
                startSlot = slots.length-2;
            }
        }
// Find the last slot
        if((endTime.isAfter(LocalTime.parse("12:15")) && endTime.isBefore(LocalTime.parse("13:00"))) // between 12:15 and 1 exclusive
            || endTime.equals(LocalTime.parse("12:15"))){
            endSlot = 3;
        }else{
            boolean slotFound = false;
            for (int i = startSlot; i < slots.length; i++) {
                if (endTime.isBefore(slots[i]) || endTime.equals(slots[i])) {
                    endSlot = i-1;
                    slotFound = true;
                    break;
                }
                if(!slotFound){
                    endSlot = slots.length-1;
                }
            }
        }
        // Return the viable slots as an array
        return new int[]{startSlot, endSlot};
    }
}
package scheduler;

import java.util.LinkedList;

public class course {
   
   String courseID;
   String courseName;

   int numberOfCredits; 
   int numberOfSessions;   //per week
   int numberOfSections;  //if 2 sections and 2 sessions per week then each section has 2 sessions per week
   
   String instructorName;
   LinkedList<Integer> instructorDays;
   
   int TimeSlotIndexstart;
   int TimeSlotIndexEnd; 
   
   LinkedList<String> conflictingCourses;
   String courseType;
   int nbOfSlots;   //number of slots a single lecture takes
                    //if course lecture length is 1:15 then 1 slot, 2 hours length is 2 slots etc etc
   int sessionsScheduled;

   boolean[][] isScheduled;
   boolean[][] isAvailable;   // is used exclusively for rescheduling courses when unscheduled courses remain
                              // not for course conflcits and restrictions

    course(String ID, String name, int creds, int sections, int sessions, String instname,
     LinkedList<Integer> instdays, int index1, int index2, LinkedList<String> conflicts
     ,String Type, int Slots){
         courseID = ID;
         courseName = name;
         numberOfCredits = creds;
         numberOfSections = sections;
         numberOfSessions = sessions;
         instructorName = instname;
         instructorDays = instdays;
         TimeSlotIndexstart = index1;
         TimeSlotIndexEnd = index2;
         conflictingCourses = conflicts;
         courseType = Type;
         nbOfSlots = Slots;
         sessionsScheduled = 0;
         isScheduled = new boolean[5][6];
         isAvailable = new boolean[5][6];
         for(boolean[] array: isAvailable){
            for(boolean bool: array){
               bool = true;
            }
         }
     }
}

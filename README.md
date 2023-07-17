# outline of program
### File reader java class:
This section reads the course objects from the excel file line by line and uses that information to initialize course objects

**Note:**
**The exact structure of the excel file is subject to change.**

The Excel file is written in 2 sheets
E.G:

sheet 1:
![Pasted image 20230717122327](https://github.com/Keyoru/Scheduler-maven-rewrite/assets/120273123/ffce1796-db93-4269-8ee2-5442e3cafd16)

the hours are written in the form
HH:MM
using military time
##### Invalid times
2:00
7:00
##### valid times
14:00
07:00
**WIP:** implement method to automatically convert invalid times to valid times

sheet 2:

![Pasted image 20230717122342](https://github.com/Keyoru/Scheduler-maven-rewrite/assets/120273123/4106ac4d-8375-408e-936b-632c269c2e56)

this section also handles transforming information such as the days into equivalent attributes that will be used in the logic,   
* Days string into list of integers representing indices of the days in the 2D array
	"Monday/Wednesday" would be turned into an integer array [0,2] where the days are represented by integer indices.
* Hours string into a pair of integers, one representing starting time slot index, the second end timeslot index
	"08:00 / 09:30" would become an integer array [0,1]
* Session time string would be turned into a single integer representing the ammount of time a single session takes, each slot is 1 hour 15 mins or 75 mins in length
	"1 hour 15 minutes each" would become an integer 1
	"1 hour 30 minutes each" would become an integer 2
* Conflict courses string would be turned into a linked list of strings, each string is the course ID of the conflicting course
	"MATH102 / MATH201" would become ["MATH102", "MATH201]
the rest is read as is, either a numeric value or string value;

these course objects are then places into a HashMap where they are mapped to a UUID

### Course scheduler

this section is where the scheduling logic itself happens.

the courses are stored in a 2D array of LinkedLists. For efficiency, the linked list and all the methods in this class utilize the UUID of courses and not the course objects themselves; Calling the ***HashMap***.get(***UUID***) method to get the course object when needed.

the 2D array is 5 columns for the days.
and in each column is 6 rows for the timeslots.
A single course session may take up more than 1 timeslot if session length is longer than 1 hour 15 minutes.

the course schedule attempts these 3 different styles of scheduling in a specific priority
written in descending priority:
1. Attempt to schedule all the sessions of the course equally on any of the day pairs.
		these day pairs are:
		M/W
		T/TH
		W/F
if scheduling all the sessions of this course using this method it moves on to next method
2. Attempt to schedule all the sessions of the course equally across all available instructor days 
		E.G: 4 sessions might be spread 2 sessions a day on M/T if those are the only available days
if scheduling all the sessions of this course using this method it moves on to next method
3. Attempt to schedule all the sessions of the course across any available timeslot and day

Our current plan is to have this class export this 2D array structure into some database format for the front end website to utilize

### Front End (WIP)

will be a java GUI
structured similar to the excel file
each slot is an interactable button
clicking on that button will open a separate window that has the information of the courses scheduled in that time slot



package scheduler;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.UUID;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

public class user implements ActionListener {
	HashMap<UUID, course> CourseMap;

	public static void main(String[] arguments) throws IOException {

		FileReader fileReader = new FileReader(new File("data/scheduler-input-draf1.xlsx"));
		HashMap<UUID, course> Courses = fileReader.readCoursesFromSheet();

		courseScheduler scheduler = new courseScheduler();

		for(UUID courseUUID: Courses.keySet()){
			scheduler.enqueueCourse(Courses.get(courseUUID));
		}

		scheduler.ScheduleCourses();
		scheduler.displaySchedule();
		scheduler.outputExcel();

		HashMap<UUID, course> CourseMap = (HashMap<UUID, course>) scheduler.courseMap;
		LinkedList<String> l = new LinkedList<String>();
		l.add("this");
		l.add("is");
		l.add("a");
		l.add("test");


		Border bo1 = BorderFactory.createLineBorder(Color.gray, 1);
		Border bo2 = BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1);

		ImageIcon logo =  new ImageIcon("logo1.png");
		

		JLabel l1 = new JLabel();
		l1.setIcon(logo);
		l1.setFont(new Font("Comic Sans", Font.BOLD, 20));
		l1.setBackground(Color.gray);
		l1.setOpaque(true);
		l1.setHorizontalAlignment(JLabel.CENTER);
		l1.setVerticalTextPosition(JLabel.CENTER);
		l1.setBorder(bo2);

		JLabel l2 = new JLabel();
		l2.setText("Monday");
		l2.setFont(new Font("Comic Sans", Font.BOLD, 20));
		l2.setBackground(new Color(0x991D02));
		l2.setOpaque(true);
		l2.setHorizontalAlignment(JLabel.CENTER);
		l2.setVerticalTextPosition(JLabel.CENTER);
		l2.setBorder(bo1);

		JLabel l3 = new JLabel();
		l3.setText("Tuesday");
		l3.setFont(new Font("Comic Sans", Font.BOLD, 20));
		l3.setBackground(new Color(0x991D02));
		l3.setOpaque(true);
		l3.setHorizontalAlignment(JLabel.CENTER);
		l3.setVerticalTextPosition(JLabel.CENTER);
		l3.setBorder(bo1);

		JLabel l4 = new JLabel();
		l4.setText("Wednesday");
		l4.setFont(new Font("Comic Sans", Font.BOLD, 20));
		l4.setBackground(new Color(0x991D02));
		l4.setOpaque(true);
		l4.setHorizontalAlignment(JLabel.CENTER);
		l4.setVerticalTextPosition(JLabel.CENTER);
		l4.setBorder(bo1);

		JLabel l5 = new JLabel();
		l5.setText("Thursday");
		l5.setFont(new Font("Comic Sans", Font.BOLD, 20));
		l5.setBackground(new Color(0x991D02));
		l5.setOpaque(true);
		l5.setHorizontalAlignment(JLabel.CENTER);
		l5.setVerticalTextPosition(JLabel.CENTER);
		l5.setBorder(bo1);

		JLabel l6 = new JLabel();
		l6.setText("Friday");
		l6.setFont(new Font("Comic Sans", Font.BOLD, 20));
		l6.setBackground(new Color(0x991D02));
		l6.setOpaque(true);
		l6.setHorizontalAlignment(JLabel.CENTER);
		l6.setVerticalTextPosition(JLabel.CENTER);
		l6.setBorder(bo1);

		
		ArrayList<JLabel> days = new ArrayList<JLabel>();
		days.add(l2);days.add(l3);days.add(l4);days.add(l5);days.add(l6);

		JLabel l7 = new JLabel();
		l7.setText("08:00");
		l7.setFont(new Font("Comic Sans", Font.BOLD, 20));
		l7.setBackground(Color.gray);
		l7.setOpaque(true);
		l7.setHorizontalAlignment(JLabel.CENTER);
		l7.setVerticalTextPosition(JLabel.CENTER);
		l7.setBorder(bo2);

		JLabel l8 = new JLabel();
		l8.setText("09:30");
		l8.setFont(new Font("Comic Sans", Font.BOLD, 20));
		l8.setBackground(Color.gray);
		l8.setOpaque(true);
		l8.setHorizontalAlignment(JLabel.CENTER);
		l8.setVerticalTextPosition(JLabel.CENTER);
		l8.setBorder(bo2);

		JLabel l9 = new JLabel();
		l9.setText("11:00");
		l9.setFont(new Font("Comic Sans", Font.BOLD, 20));
		l9.setBackground(Color.gray);
		l9.setOpaque(true);
		l9.setHorizontalAlignment(JLabel.CENTER);
		l9.setVerticalTextPosition(JLabel.CENTER);
		l9.setBorder(bo2);

		JLabel l10 = new JLabel();
		l10.setText("13:00");
		l10.setFont(new Font("Comic Sans", Font.BOLD, 20));
		l10.setBackground(Color.gray);
		l10.setOpaque(true);
		l10.setHorizontalAlignment(JLabel.CENTER);
		l10.setVerticalTextPosition(JLabel.CENTER);
		l10.setBorder(bo2);

		JLabel l11 = new JLabel();
		l11.setText("14:30");
		l11.setFont(new Font("Comic Sans", Font.BOLD, 20));
		l11.setBackground(Color.gray);
		l11.setOpaque(true);
		l11.setHorizontalAlignment(JLabel.CENTER);
		l11.setVerticalTextPosition(JLabel.CENTER);
		l11.setBorder(bo2);

		JLabel l12 = new JLabel();
		l12.setText("16:00");
		l12.setFont(new Font("Comic Sans", Font.BOLD, 20));
		l12.setBackground(Color.gray);
		l12.setOpaque(true);
		l12.setHorizontalAlignment(JLabel.CENTER);
		l12.setVerticalTextPosition(JLabel.CENTER);
		l12.setBorder(bo2);

		ArrayList<JLabel> timeSlots = new ArrayList<JLabel>();
		timeSlots.add(l7);timeSlots.add(l8);timeSlots.add(l9);timeSlots.add(l10);timeSlots.add(l11);timeSlots.add(l12);

		JPanel first = new JPanel();
		first.setLayout(new GridLayout(1, 7));
		first.add(l1);
		first.add(l2);
		first.add(l3);
		first.add(l4);
		first.add(l5);
		first.add(l6);

		JPanel PanelTimeSlotOne = new JPanel();
		PanelTimeSlotOne.setBackground(Color.LIGHT_GRAY);
		PanelTimeSlotOne.setLayout(new GridLayout(1, 7));
		PanelTimeSlotOne.add(l7);

		JPanel PanelTimeSlotTwo = new JPanel();
		PanelTimeSlotTwo.setBackground(Color.LIGHT_GRAY);
		PanelTimeSlotTwo.setLayout(new GridLayout(1, 7));
		PanelTimeSlotTwo.add(l8);

		JPanel eleven = new JPanel();
		eleven.setBackground(Color.LIGHT_GRAY);
		eleven.setLayout(new GridLayout(1, 7));
		eleven.add(l9);

		JPanel one = new JPanel();
		one.setBackground(Color.LIGHT_GRAY);
		one.setLayout(new GridLayout(1, 7));
		one.add(l10);

		JPanel two = new JPanel();
		two.setBackground(Color.LIGHT_GRAY);
		two.setLayout(new GridLayout(1, 7));
		two.add(l11);

		JPanel four = new JPanel();
		four.setBackground(Color.LIGHT_GRAY);
		four.setLayout(new GridLayout(1, 7));
		four.add(l12);

		


		int num = 1;
		for (int i = 0; i < 6; i++) {
			for (int j = 0; j < 5; j++) {
				    JFrame f1 = new JFrame();
					f1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
					f1.setVisible(false);
					JButton b1 = new JButton();
					b1.addActionListener(u -> f1.setVisible(true));
					b1.setFocusable(false);
					b1.setFont(new Font("Comic Sans", Font.PLAIN, 10));
					b1.setBackground(Color.LIGHT_GRAY);
					b1.setBorder(bo1);
				
					String sInfo = "<html>" ;
					JLabel slotInfo = new JLabel();
					if (num< 6) {
						PanelTimeSlotOne.add(b1);
						
					} else if (num < 11) {
						PanelTimeSlotTwo.add(b1);
					} else if (num < 16) {
						eleven.add(b1);
					} else if (num < 21) {
						one.add(b1);
					} else if (num < 26) {
						two.add(b1);
					} else if (num < 31) {
						four.add(b1);
					}
					num++;
					
					   sInfo = days.get(j).getText() + " at " + timeSlots.get(i).getText();
						slotInfo.setText(sInfo);
						slotInfo.setFont(new Font("Comic Sans", Font.BOLD, 20));
						slotInfo.setBackground(Color.LIGHT_GRAY);
						slotInfo.setOpaque(true);
						slotInfo.setBorder(bo2);
						f1.add(slotInfo);
					
				JLabel ls1 = new JLabel();
				f1.setLayout(new GridLayout(7, 1, 1, 1));
 				String courseInfo = "<html>" ;
				String moreInfo = "<html>" ;
				try {
					for (UUID courseUUID : scheduler.schedule[j][i]) {

						
						courseInfo += CourseMap.get(courseUUID).courseID+  "   By "+CourseMap.get(courseUUID).instructorName+"<br>" ;
						moreInfo += CourseMap.get(courseUUID).courseID+  "   By "+CourseMap.get(courseUUID).instructorName+"<br>" ;
					}

					courseInfo += "</html>";
					moreInfo +=  "</html>";
					b1.setText(courseInfo);
					ls1.setText(courseInfo);
						ls1.setFont(new Font("Comic Sans", Font.BOLD, 20));
						ls1.setBackground(Color.LIGHT_GRAY);
						ls1.setOpaque(true);
						ls1.setBorder(bo2);
						f1.add(ls1);
				} catch (Exception e) {
					
				}
			}

		}

		//mwmJLabel 

		JPanel info = new JPanel();
		info.setLayout(new GridLayout(7, 1));
		info.setSize(750, 750);



		JPanel grid = new JPanel();
		grid.setLayout(new GridLayout(7, 1));
		grid.setSize(750, 750);
		grid.add(first);
		grid.add(PanelTimeSlotOne);
		grid.add(PanelTimeSlotTwo);
		grid.add(eleven);
		grid.add(one);
		grid.add(two);
		grid.add(four);



		

		JFrame main = new JFrame();
		main.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		main.setTitle("By ASF");
		main.setSize(1500, 1500);
		main.setLayout(new GridLayout(1, 1, 15, 15));
		main.setVisible(true);
		main.add(grid);
		//main.add(info);
		
		
	

	}

	@Override
	public void actionPerformed(ActionEvent e) {

	}

}

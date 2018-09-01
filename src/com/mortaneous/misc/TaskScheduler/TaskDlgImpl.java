/*
 * TaskDlgImpl.java
 */

package com.mortaneous.misc.TaskScheduler;

import javax.swing.*;
import javax.swing.GroupLayout.Alignment;

import java.awt.Frame;
import java.awt.Container;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import static javax.swing.LayoutStyle.ComponentPlacement;

public class TaskDlgImpl extends JDialog implements TaskDlg, ActionListener
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1248696464540787528L;
	//
	// GUI elements
	//
	private JLabel titleLabel;
	private JTextField titleField;
	private JLabel descLabel;
	private JTextField descField;
	private JLabel startLabel;
	private JLabel dateSeparator1;
	private JLabel dateSeparator2;
	private JLabel timeSeparator1;
	private JLabel timeSeparator2;
	private JLabel startMonthLabel;
	private JLabel startDayLabel;
	private JLabel startYearLabel;
	private JLabel startHourLabel;
	private JLabel startMinuteLabel;
	private JComboBox<Integer> startMonthCombo;
	private JComboBox<Integer> startDayCombo;
	private JComboBox<Integer> startYearCombo;
	private JComboBox<Integer> startHourCombo;
	private JComboBox<Integer> startMinuteCombo;
	private JLabel durationLabel;
	private JTextField durationDaysField;
	private JLabel durationDaysLabel;
	private JTextField durationHoursField;
	private JLabel durationHoursLabel;
	private JTextField durationMinutesField;
	private JLabel durationMinutesLabel;
	private JLabel prereqLabel;
	private JList<String> prereqList;
	private JButton addButton;
	private JButton cancelButton;
	
	//
	// Data
	//
	private String title;
	private String description;
	private int startMonth;
	private int startDay;
	private int startYear;
	private int startHour;
	private int startMinute;
	private int durationDays;
	private int durationHours;
	private int durationMinutes;
	private boolean dataAccepted;
	
	//
	// Properties
	//
	@Override
	public void setTitle(String title) { this.title = title; }
	@Override
	public String getTitle() { return title; }
	
	@Override
	public void setDescription(String description) { this.description = description; }
	@Override
	public String getDescription() { return description; }
	
	@Override
	public void setStartDateTime(int month, int day, int year, int hour, int minute)
	{
		startMonth = month;
		startDay = day;
		startYear = year;
		startHour = hour;
		startMinute = minute;
	}
	@Override
	public int getStartMonth() { return startMonth; }
	@Override
	public int getStartDay() { return startDay; }
	@Override
	public int getStartYear() { return startYear; }
	@Override
	public int getStartHour() { return startHour; }
	@Override
	public int getStartMinute() { return startMinute; }
	
	@Override
	public void setDuration(int days, int hours, int minutes)
	{
		durationDays = days;
		durationHours = hours;
		durationMinutes = minutes;
	}
	@Override
	public int getDurationDays() { return durationDays; }
	@Override
	public int getDurationHours() { return durationHours; }
	@Override
	public int getDurationMinutes() { return durationMinutes; }

	@Override
	public void open() { setVisible(true); }
	
	@Override
	public boolean isDataAccepted() { return dataAccepted; }
	
	//
	// Constructor
	//
	public TaskDlgImpl(Frame owner)
	{
		super(owner, "New Task", true);
		setSize(320, 200);
		setLocationRelativeTo(owner);
		setResizable(false);
		
		dataAccepted = false;
		
		initializeControls();
		createLayout();
	}
	
	private void initializeControls()
	{
		// Title
		titleLabel = new JLabel("Title");
		titleField = new JTextField();
		
		// Description
		descLabel = new JLabel("Description");
		descField = new JTextField();
		
		// Start Date/Time
		startLabel = new JLabel("Start Date/Time");
		startMonthLabel = new JLabel("month");
		startMonthCombo = new JComboBox<Integer>();
		startDayLabel = new JLabel("day");
		startDayCombo = new JComboBox<Integer>();
		startYearLabel = new JLabel("year");
		startYearCombo = new JComboBox<Integer>();
		startHourLabel = new JLabel("hour");
		startHourCombo = new JComboBox<Integer>();
		startMinuteLabel = new JLabel("min");
		startMinuteCombo = new JComboBox<Integer>();
		
		dateSeparator1 = new JLabel("/");
		dateSeparator2 = new JLabel("/");
		timeSeparator1 = new JLabel(":");
		timeSeparator2 = new JLabel(":");
		
		// Duration
		durationLabel = new JLabel("Duration");
		durationDaysField = new JTextField();
		durationDaysLabel = new JLabel("days");
		durationHoursField = new JTextField();
		durationHoursLabel = new JLabel("hours");
		durationMinutesField = new JTextField();
		durationMinutesLabel = new JLabel("minutes");

		// Prerequisites
		prereqLabel = new JLabel("Prerequisites");
		
		// Buttons
		addButton = new JButton("Add");
		cancelButton = new JButton("Cancel");
		
		//
		// Populate combo boxes
		//
		
		// Start Date/Time - Month
		for(int i=1; i<=12; i++) {
			startMonthCombo.addItem(i);
		}
		
		// Start Date/Time - Day
		for(int i=1; i<=31; i++) {
			startDayCombo.addItem(i);
		}
		
		// Start Date/Time - Year
		for(int i=2017; i<=2020; i++) {
			startYearCombo.addItem(i);
		}
		
		// Start Date/Time - Hour
		for(int i=0; i<=23; i++) {
			startHourCombo.addItem(i);
		}
		
		// Start Date/Time - Minute
		for(int i=0; i<=59; i++) {
			startMinuteCombo.addItem(i);
		}
		
		//
		// Create and populate lists
		//
		
		// Prerequisites
		String[] data = new String[] { "one", "two", "three", "four" };
		prereqList = new JList<String>(data);
		prereqList.setFixedCellWidth(150);
		
		//
		// Listeners
		//
		addButton.addActionListener(this);
		cancelButton.addActionListener(this);
	}
	
	private void createLayout()
	{
		Container pane = getContentPane();
		GroupLayout layout = new GroupLayout(pane);
		pane.setLayout(layout);
		
		layout.setAutoCreateContainerGaps(true);
		//layout.setAutoCreateGaps(true);
		
		GroupLayout.SequentialGroup horizGroup = layout.createSequentialGroup();
		horizGroup.addGroup(layout.createParallelGroup()
								.addComponent(titleLabel)
								.addComponent(descLabel)
								.addComponent(startLabel)
								.addComponent(durationLabel)
								.addComponent(prereqLabel)
							)
				  .addGap(20)
				  .addGroup(layout.createParallelGroup()
								.addGroup(layout.createSequentialGroup()
												.addComponent(titleField)
												.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
										 )
								.addComponent(descField)
								.addGroup(layout.createSequentialGroup()
												.addGroup(layout.createParallelGroup()
																.addComponent(startMonthCombo)
																.addComponent(startMonthLabel)
														 )
												.addGap(5)
												.addComponent(dateSeparator1)
												.addGap(5)
												.addGroup(layout.createParallelGroup()
																.addComponent(startDayCombo)
																.addComponent(startDayLabel)
														 )
												.addGap(5)
												.addComponent(dateSeparator2)
												.addGap(5)
												.addGroup(layout.createParallelGroup()
																.addComponent(startYearCombo)
																.addComponent(startYearLabel)
														 )
												.addGap(10)
												.addGroup(layout.createParallelGroup()
																.addComponent(startHourCombo)
																.addComponent(startHourLabel)
														 )
												.addGap(5)
												.addComponent(timeSeparator1)
												.addGap(5)
												.addGroup(layout.createParallelGroup()
																.addComponent(startMinuteCombo)
																.addComponent(startMinuteLabel)
														 )
										 )
								.addGroup(layout.createSequentialGroup()
												.addComponent(durationDaysField).addGap(5)
												.addComponent(durationDaysLabel).addGap(10)
												.addComponent(durationHoursField).addGap(5)
												.addComponent(durationHoursLabel).addGap(10)
												.addComponent(durationMinutesField).addGap(5)
												.addComponent(durationMinutesLabel)
										 )
								.addComponent(prereqList)
							)
				  .addGap(20)
				  .addGroup(layout.createParallelGroup()
								.addComponent(addButton)
								.addComponent(cancelButton)
						   )
				  .addGap(10);
		layout.setHorizontalGroup(horizGroup);
		
		GroupLayout.SequentialGroup vertGroup = layout.createSequentialGroup();
		vertGroup.addGroup(layout.createParallelGroup(Alignment.BASELINE)
								.addComponent(titleLabel)
								.addComponent(titleField)
								.addComponent(addButton)
							)
				 .addGap(10)
				 .addGroup(layout.createParallelGroup(Alignment.BASELINE)
								.addComponent(descLabel)
								.addComponent(descField)
								.addComponent(cancelButton)
							)
				 .addGap(10)
				 .addGroup(layout.createParallelGroup(Alignment.BASELINE)
								.addComponent(startLabel)
								.addGroup(layout.createSequentialGroup()
												.addComponent(startMonthCombo)
												.addComponent(startMonthLabel)
										 )
								.addComponent(dateSeparator1)
								.addGroup(layout.createSequentialGroup()
												.addComponent(startDayCombo)
												.addComponent(startDayLabel)
										 )
								.addComponent(dateSeparator2)
								.addGroup(layout.createSequentialGroup()
												.addComponent(startYearCombo)
												.addComponent(startYearLabel)
										 )
								.addGroup(layout.createSequentialGroup()
												.addComponent(startHourCombo)
												.addComponent(startHourLabel)
										 )
								.addComponent(timeSeparator1)
								.addGroup(layout.createSequentialGroup()
												.addComponent(startMinuteCombo)
												.addComponent(startMinuteLabel)
										 )
							)
				 .addGap(10)
				 .addGroup(layout.createParallelGroup()
								.addComponent(durationLabel)
								.addComponent(durationDaysField)
								.addComponent(durationDaysLabel)
								.addComponent(durationHoursField)
								.addComponent(durationHoursLabel)
								.addComponent(durationMinutesField)
								.addComponent(durationMinutesLabel)
						  )
				 .addGap(10)
				 .addGroup(layout.createParallelGroup()
								.addComponent(prereqLabel)
								.addComponent(prereqList)
						  )
				 .addGap(10);
		layout.setVerticalGroup(vertGroup);
		
		layout.linkSize(SwingConstants.HORIZONTAL, addButton, cancelButton);
		
		pack();
	}
	
	//
	// ActionListener interface
	//
	
	@Override
	public void actionPerformed(ActionEvent event)
	{
		Object source = event.getSource();
		
		if(source == addButton) {
			saveData();
			printData();
			dataAccepted = true;
			setVisible(false);
		}
		else if(source == cancelButton) {
			dataAccepted = false;
			setVisible(false);
		}

	}
	
	private void saveData()
	{
		title = titleField.getText();
		description = descField.getText();
		
		startMonth = ((int) startMonthCombo.getSelectedItem()) - 1;  // making months value zero-based
		startDay = (int) startDayCombo.getSelectedItem();
		startYear = (int) startYearCombo.getSelectedItem();
		startHour = (int) startHourCombo.getSelectedItem();
		startMinute = (int) startMinuteCombo.getSelectedItem();
		
		String text = durationDaysField.getText();
		durationDays = text.isEmpty() ? 0 : Integer.parseInt(text);
		text = durationHoursField.getText();
		durationHours = text.isEmpty() ? 0 : Integer.parseInt(text);
		text = durationMinutesField.getText();
		durationMinutes = text.isEmpty() ? 0 : Integer.parseInt(text);
	}
	
	private void printData()
	{
		System.out.println("Title = " + title);
		System.out.println("Description = " + description);
		System.out.println("Start Date/Time = " + startMonth + "/" + startDay + "/" + startYear + " " + startHour + ":" + startMinute);
		System.out.println("Duration = " + 
							(durationDays > 0 ? durationDays + " days" : "") + 
							(durationHours > 0 ? " + " + durationHours + " hours" : "") + 
							(durationMinutes > 0 ? " + " + durationMinutes + " minutes" : "")
						  );
						  
	}
}
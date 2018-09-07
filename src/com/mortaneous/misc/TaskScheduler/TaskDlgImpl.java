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

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.ArrayList;

import static javax.swing.LayoutStyle.ComponentPlacement;
import static javax.swing.JOptionPane.showMessageDialog;

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
	private JList<Task> prereqList;
	private DefaultListModel<Task> prereqListModel;
	private JScrollPane prereqScroll;
	private JLabel footNoteLabel;
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
	private List<Task> prereq;
	private List<Task> activeTasks;
	
	private boolean dataAccepted;
	private boolean isAdd;
	
	//
	// Properties
	//
	@Override
	public void setTaskTitle(String title) 
	{
		titleField.setText(this.title = title);
	}
	@Override
	public String getTaskTitle() { return title; }
	
	@Override
	public void setDescription(String description) 
	{
		descField.setText(this.description = description);
	}
	@Override
	public String getDescription() { return description; }
	
	@Override
	public void setStartDateTime(int month, int day, int year, int hour, int minute)
	{
		startMonthCombo.setSelectedItem(startMonth = month+1);
		startDayCombo.setSelectedItem(startDay = day);
		startYearCombo.setSelectedItem(startYear = year);
		
		startHourCombo.setSelectedItem(startHour = hour);
		startMinuteCombo.setSelectedItem(startMinute = minute);
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
		Integer daysInt = (durationDays = days);
		Integer hoursInt = (durationHours = hours);
		Integer minutesInt = (durationHours = hours);
		
		durationDaysField.setText(daysInt.toString());
		durationHoursField.setText(hoursInt.toString());
		durationMinutesField.setText(minutesInt.toString());
	}
	@Override
	public int getDurationDays() { return durationDays; }
	@Override
	public int getDurationHours() { return durationHours; }
	@Override
	public int getDurationMinutes() { return durationMinutes; }

	@Override
	public void setDependencies(List<Task> dependencies)
	{
		prereq.clear();
		
		prereq.addAll(dependencies);
		
		for(Task task : dependencies) {
			prereqList.setSelectedValue(task, true);
		}
	}
	@Override
	public List<Task> getDependencies() { return prereq; }
	
	@Override
	public void setActiveTasks(List<Task> activeTasks)
	{
		this.activeTasks.addAll(activeTasks);
		
		prereqListModel.clear();
		
		for(Task task : activeTasks) {
			if(title != task.getTitle()) {
				prereqListModel.addElement(task);
			}
		}
	}
	
	@Override
	public void open() { setVisible(true); }
	
	@Override
	public boolean isDataAccepted() { return dataAccepted; }
	
	//
	// Constructor
	//
	public TaskDlgImpl(Frame owner, boolean isAdd)
	{
		super(owner, isAdd ? "New Task" : "Modify Task", true);
		setSize(320, 200);
		setLocationRelativeTo(owner);
		setResizable(false);
		this.isAdd = isAdd;
		
		dataAccepted = false;
		prereq = new ArrayList<Task>();
		activeTasks = new ArrayList<Task>();
		
		initializeControls();
		createLayout();
	}
	
	private void initializeControls()
	{
		// Title
		titleLabel = new JLabel("Title*");
		titleField = new JTextField();
		
		// Description
		descLabel = new JLabel("Description");
		descField = new JTextField();
		
		// Start Date/Time
		startLabel = new JLabel("Start Date/Time*");
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
		durationLabel = new JLabel("Duration*");
		durationDaysField = new JTextField();
		durationDaysLabel = new JLabel("days");
		durationHoursField = new JTextField();
		durationHoursLabel = new JLabel("hours");
		durationMinutesField = new JTextField();
		durationMinutesLabel = new JLabel("minutes");

		// Prerequisites
		prereqLabel = new JLabel("Prerequisite Tasks");
		
		// Foot note
		footNoteLabel = new JLabel("* - required");
		
		// Buttons
		if(isAdd) {
			addButton = new JButton("Add Task");
		}
		else {
			addButton = new JButton("Accept");
		}
		
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
		//String[] data = new String[] { "one", "two", "three", "four", "five", "six", "four", "five", "six" };
		prereqListModel = new DefaultListModel<Task>();
		prereqList = new JList<Task>();
		prereqList.setModel(prereqListModel);
		
		prereqList.setFixedCellWidth(150);
		prereqScroll = new JScrollPane(prereqList);
		
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
								.addComponent(footNoteLabel)
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
												.addGap(20)
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
								// .addComponent(prereqList)
								.addComponent(prereqScroll)
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
				 .addGroup(layout.createParallelGroup(Alignment.CENTER)
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
				 .addGroup(layout.createParallelGroup(Alignment.BASELINE)
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
								// .addComponent(prereqList)
								.addComponent(prereqScroll)
						  )
				 .addPreferredGap(ComponentPlacement.UNRELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
				 //.addGap(10)
				 .addComponent(footNoteLabel);
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
			if(retrieveData()) {
				dataAccepted = true;
				setVisible(false);
			}
		}
		else if(source == cancelButton) {
			//dataAccepted = false;
			setVisible(false);
		}

	}
	
	private boolean retrieveData()
	{

		String title;
		int startMonth, startDay, startYear, startHour, startMinute;
		int durationDays, durationHours, durationMinutes;
		
		
		//
		// Process required fields first
		//
		
		// Title
		title = titleField.getText();
		if(title.isEmpty()) { 
			showMessageDialog(this, "Task [Title] required", "Invalid Entry", JOptionPane.ERROR_MESSAGE);
			return false; 
		}
		
		// Start Date/Time
		startMonth = ((int) startMonthCombo.getSelectedItem()) - 1;  // making months value zero-based
		startDay = (int) startDayCombo.getSelectedItem();
		startYear = (int) startYearCombo.getSelectedItem();
		startHour = (int) startHourCombo.getSelectedItem();
		startMinute = (int) startMinuteCombo.getSelectedItem();

		Calendar now = Calendar.getInstance();
		Calendar input = new GregorianCalendar(startYear, startMonth, startDay, startHour, startMinute);
		if(input.before(now)) {
			showMessageDialog(this, "Valid [Start Date/Time] required", "Invalid Entry", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		
		String text = durationDaysField.getText();
		durationDays = text.isEmpty() ? 0 : Integer.parseInt(text);
		text = durationHoursField.getText();
		durationHours = text.isEmpty() ? 0 : Integer.parseInt(text);
		text = durationMinutesField.getText();
		durationMinutes = text.isEmpty() ? 0 : Integer.parseInt(text);
		if(durationDays+durationHours+durationMinutes == 0) {
			showMessageDialog(this, "Task [Duration] required", "Invalid Entry", JOptionPane.ERROR_MESSAGE);
			return false;
		}

		this.title = title;
		this.description = descField.getText();
		this.startMonth = startMonth;
		this.startDay = startDay;
		this.startYear = startYear;
		this.startHour = startHour;
		this.startMinute = startMinute;
		this.durationDays = durationDays;
		this.durationHours = durationHours;
		this.durationMinutes = durationMinutes;
		
		return true;
	}
	
	private void saveData()
	{
		
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
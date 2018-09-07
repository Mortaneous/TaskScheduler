/*
 * SchedulerView.java
 */

package com.mortaneous.misc.TaskScheduler;

import javax.swing.*;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import java.util.List;
import java.util.ArrayList;

public class SchedulerView extends JFrame implements ActionListener, ITaskUpdateListener
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1068739284297034195L;

	private String appName;
	
	private TaskPanel panel;
	private JScrollPane view;
	
	private JMenuBar menuBar;
	
	private JMenu fileMenu;
	private JMenuItem fileExitMI;

	private JMenu taskMenu;
	private JMenuItem taskNewMI;
	
	//--------- DEBUG -----------//
	private JMenuItem test1MI;
	private JMenuItem test2MI;
	private JMenuItem test3MI;
	private boolean test1Active;
	private boolean test2Active;
	private boolean test3Active;
	private TaskNode task1;
	private TaskNode task2;
	private TaskNode task3;
	//--------- DEBUG -----------//
	
	private List<Task> tasks;
	
	public SchedulerView(String appName)
	{
		this.appName = appName;
		setTitle(appName);
		setSize(640, 480);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		initializeControls();
		
		tasks = new ArrayList<Task>();
		
		//addMouseListener(this);
	}
	
	public void initializeControls()
	{
		//
		// System Menu
		//
		menuBar = new JMenuBar();
		
		//
		// File menu
		//
		fileExitMI = new JMenuItem("Exit", KeyEvent.VK_X);
		fileExitMI.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F4, ActionEvent.ALT_MASK));
		fileExitMI.addActionListener(this);

		fileMenu = new JMenu("File");
		fileMenu.setMnemonic(KeyEvent.VK_F);
		fileMenu.add(fileExitMI);
		
		menuBar.add(fileMenu);
		
		//
		// Task menu
		//
		taskNewMI = new JMenuItem("New...", KeyEvent.VK_W);
		taskNewMI.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_K, ActionEvent.CTRL_MASK));
		taskNewMI.addActionListener(this);
		
		taskMenu = new JMenu("Task");
		taskMenu.setMnemonic(KeyEvent.VK_T);
		taskMenu.add(taskNewMI);
		
		menuBar.add(taskMenu);
		
		setJMenuBar(menuBar);
		
		//--------- DEBUG -----------//
		test1MI = new JMenuItem("Test 1 - Design task", KeyEvent.VK_1);
		test1MI.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_1, ActionEvent.ALT_MASK));
		test1MI.addActionListener(this);
		
		test2MI = new JMenuItem("Test 2 - Dev task", KeyEvent.VK_2);
		test2MI.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_2, ActionEvent.ALT_MASK));
		test2MI.addActionListener(this);
		
		test3MI = new JMenuItem("Test 3 - QA task", KeyEvent.VK_3);
		test3MI.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_3, ActionEvent.ALT_MASK));
		test3MI.addActionListener(this);
		
		taskMenu.addSeparator();
		taskMenu.add(test1MI);
		taskMenu.add(test2MI);
		taskMenu.add(test3MI);
		
		test1Active = false;
		test2Active = false;
		test3Active = false;
		
		task1 = new TaskNode("[1] DESIGN", "Requirements gathering", 5, 0, 0, 2018, 9, 13, 14, 46);
		task2 = new TaskNode("[2] CODING", "Coding and testing", 10, 0, 0, task1);
		task3 = new TaskNode("[3] TESTING", "QA testing", 15, 0, 0, task1);
		task3.addDependency(task2);
		//--------- DEBUG -----------//
		
		//
		// Content Pane
		//
		panel = new TaskPanel(this);
		view = new JScrollPane(panel);
		setContentPane(view);
		//add(view);
		
		//pack();
	}
	
	public void launch()
	{
		setVisible(true);
	}

	private void addTask(Task task)
	{
		tasks.add(task);
		panel.addTask(task);
	}
	
	private void removeTask(Task task)
	{
		tasks.remove(task);
		panel.removeTask(task);
	}
	
	//
	// ActionListener interface
	//
	@Override
	public void actionPerformed(ActionEvent event)
	{
		if(event.getSource() == fileExitMI) {
			System.exit(0);
		}
		else if(event.getSource() == taskNewMI) {
			TaskDlg dlg = new TaskDlgImpl(this, true);
			dlg.open();
			if(dlg.isDataAccepted()) {
				Task task = new TaskNode(dlg.getTaskTitle(), dlg.getDescription(),
										dlg.getDurationDays(), dlg.getDurationHours(), dlg.getDurationMinutes(),
										dlg.getStartYear(), dlg.getStartMonth(), dlg.getStartDay(), dlg.getStartHour(), dlg.getStartMinute());
				addTask(task);
				
				//pack();
				//view.setViewportView(panel);
			}
		}
		//--------- DEBUG -----------//
		else if(event.getSource() == test1MI) {

			if(test1Active) {
				removeTask(task1);
			}
			else {
				addTask(task1);
			}
			test1Active = !test1Active;
		}
		else if(event.getSource() == test2MI) {
			
			if(test2Active) {
				removeTask(task2);
			}
			else {
				addTask(task2);
			}
			test2Active = !test2Active;
		}
		else if(event.getSource() == test3MI) {

			if(test3Active) {
				removeTask(task3);
			}
			else {
				addTask(task3);
			}
			test3Active = !test3Active;
		}
		//--------- DEBUG -----------//
	}
	
	//
	// ITaskUpdateListener interface
	//
	@Override
	public void updateTask(Task task)
	{
		TaskDlg dlg = new TaskDlgImpl(this, false);

		dlg.setTaskTitle(task.getTitle());
		dlg.setDescription(task.getDescription());
		dlg.setStartDateTime(task.getStartMonth(), task.getStartDay(), task.getStartYear(), task.getStartHour(), task.getStartMinute());
		dlg.setDuration(task.getDurationDays(), task.getDurationHours(), task.getDurationMinutes());
		dlg.setDependencies(task.getDependencies());

		// System.out.println("Active Tasks: ");
		// for(Task t : tasks) {
			// System.out.println("  :" + t.getTitle());
		// }
		dlg.setActiveTasks(tasks);
		
		dlg.open();

		if(dlg.isDataAccepted()) {
			task.setTitle(dlg.getTaskTitle());
			task.setDescription(dlg.getDescription());
			task.setStartDateTime(dlg.getStartMonth(), dlg.getStartDay(), dlg.getStartYear(), dlg.getStartHour(), dlg.getStartMinute());
			task.setDuration(dlg.getDurationDays(), dlg.getDurationHours(), dlg.getDurationMinutes());
		}
	}
}
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

public class SchedulerView extends JFrame implements ActionListener
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
	private boolean test1Active;
	private boolean test2Active;
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
		test1MI = new JMenuItem("Test 1", KeyEvent.VK_1);
		test1MI.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_1, ActionEvent.ALT_MASK));
		test1MI.addActionListener(this);
		
		test2MI = new JMenuItem("Test 2", KeyEvent.VK_2);
		test2MI.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_2, ActionEvent.ALT_MASK));
		test2MI.addActionListener(this);
		
		taskMenu.addSeparator();
		taskMenu.add(test1MI);
		taskMenu.add(test2MI);
		
		test1Active = false;
		test2Active = false;
		
		task1 = new TaskNode("[1] DESIGN", "Requirements gathering", 5, 0, 0, 2018, 8, 13, 14, 46);
		task2 = new TaskNode("[2] CODING", "Coding and testing", 10, 0, 0, task1);
		task3 = new TaskNode("[3] TESTING", "QA testing", 10, 0, 0, task2);
		//--------- DEBUG -----------//
		
		//
		// Content Pane
		//
		panel = new TaskPanel();
		view = new JScrollPane(panel);
		setContentPane(view);
		//add(view);
		
		//pack();
	}
	
	public void launch()
	{
		setVisible(true);
	}
	
	@Override
	public void actionPerformed(ActionEvent event)
	{
		if(event.getSource() == fileExitMI) {
			System.exit(0);
		}
		else if(event.getSource() == taskNewMI) {
			TaskDlg dlg = new TaskDlgImpl(this);
			dlg.open();
			//System.out.println("Add new task? " + (dlg.isDataAccepted() ? "Yes" : "No"));
			if(dlg.isDataAccepted()) {
				Task task = new TaskNode(dlg.getTitle(), dlg.getDescription(),
										dlg.getDurationDays(), dlg.getDurationHours(), dlg.getDurationMinutes(),
										dlg.getStartYear(), dlg.getStartMonth(), dlg.getStartDay(), dlg.getStartHour(), dlg.getStartMinute());
				tasks.add(task);
				panel.addTask(task);
				
				//pack();
				view.setViewportView(panel);
			}
		}
		//--------- DEBUG -----------//
		else if(event.getSource() == test1MI) {
			// tasks.add(task1);
			// panel.addTask(task1);
			// tasks.add(task2);
			// panel.addTask(task2);
			// tasks.add(task3);
			// panel.addTask(task3);

			if(test1Active) {
				panel.removeTask(task1);
			}
			else {
				panel.addTask(task1);
			}
			test1Active = !test1Active;
		}
		else if(event.getSource() == test2MI) {
			//panel.removeTask(tasks.remove(1));
			
			if(test2Active) {
				panel.removeTask(task2);
			}
			else {
				panel.addTask(task2);
			}
			test2Active = !test2Active;
		}
		//--------- DEBUG -----------//
	}
}
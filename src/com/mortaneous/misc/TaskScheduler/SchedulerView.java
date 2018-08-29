/*
 * SchedulerView.java
 */

package com.mortaneous.misc.TaskScheduler;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public class SchedulerView extends JFrame implements ActionListener
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1068739284297034195L;

	private String appName;
	
	private TaskPanel panel;
	
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
	//--------- DEBUG -----------//
	
	public SchedulerView(String appName)
	{
		this.appName = appName;
		setTitle(appName);
		setSize(640, 480);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		initializeControls();
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
		
		taskMenu.add(new JSeparator());
		taskMenu.add(test1MI);
		taskMenu.add(test2MI);
		
		test1Active = false;
		test2Active = false;
		
		// taskView1 = new TaskElement(null, 350, 10, 50, 50, Color.RED);
		// taskView2 = new TaskElement(null, 100, 160);
		task1 = new TaskNode("[1] DESIGN", "Requirements gathering", 2018, 8, 13, 14, 46, 5, 0, 0);
		task2 = new TaskNode("[2] CODING", "Coding and testing", 10, 0, 0, task1);
		//--------- DEBUG -----------//
		
		//
		// Content Pane
		//
		add(panel = new TaskPanel());
		
		pack();
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
		//--------- DEBUG -----------//
		else if(event.getSource() == test1MI) {
			if(test1Active) {
				panel.removeTask(task1);
			}
			else {
				panel.addTask(task1);
			}
			test1Active = !test1Active;
		}
		else if(event.getSource() == test2MI) {
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
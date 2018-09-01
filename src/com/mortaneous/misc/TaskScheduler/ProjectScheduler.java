/*
 * ProjectScheduler.java
 */

package com.mortaneous.misc.TaskScheduler;

import javax.swing.SwingUtilities;

public class ProjectScheduler
{
	private static final String APP_NAME = "Project Scheduler"; 
	
	private SchedulerView view;
	
	public ProjectScheduler(SchedulerView view)
	{
		this.view = view;
	}
	
	//
	// Entry point
	//
	public static void main(String[] args)
	{
		SwingUtilities.invokeLater( () -> {
			SchedulerView view = new SchedulerView(APP_NAME);
			ProjectScheduler ts = new ProjectScheduler(view);

			view.setVisible(true);
		});
	}
	
}
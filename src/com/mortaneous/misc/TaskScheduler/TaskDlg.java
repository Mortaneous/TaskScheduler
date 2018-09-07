/*
 * TaskDlg.java
 */

package com.mortaneous.misc.TaskScheduler;

import java.util.List;

public interface TaskDlg
{
	//
	// Properties
	//
	
	public void setTaskTitle(String title);
	public String getTaskTitle();
	
	public void setDescription(String description);
	public String getDescription();
	
	public void setStartDateTime(int month, int day, int year, int hour, int minute);
	public int getStartMonth();
	public int getStartDay();
	public int getStartYear();
	public int getStartHour();
	public int getStartMinute();
	
	public void setDuration(int days, int hours, int minutes);
	public int getDurationDays();
	public int getDurationHours();
	public int getDurationMinutes();

	public void setDependencies(List<Task> dependencies);
	public List<Task> getDependencies();
	
	public void setActiveTasks(List<Task> activeTasks);
	
	public void open();
	public boolean isDataAccepted();
}
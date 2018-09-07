/*
 * Task.java
 */

package com.mortaneous.misc.TaskScheduler;

import java.util.List;
import java.util.Calendar;

public interface Task
{
	public void setTitle(String title);
	public String getTitle();

	public void setDescription(String description);
	public String getDescription();

	public void setDuration(int days, int hours, int minutes);
	public int getDurationDays();
	public int getDurationHours();
	public int getDurationMinutes();

	// public void setStartTime(Calendar startTime);
	public Calendar getStartTime();
	public void setStartDateTime(int month, int day, int year, int hour, int minute);
	public int getStartMonth();
	public int getStartDay();
	public int getStartYear();
	public int getStartHour();
	public int getStartMinute();

	public Calendar getFinishTime();
	
	public List<Task> getDependencies();
	
	public String toString();
}
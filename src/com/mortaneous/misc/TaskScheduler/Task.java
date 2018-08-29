/*
 * Task.java
 */

package com.mortaneous.misc.TaskScheduler;

import java.util.Calendar;

public interface Task
{
	public void setTitle(String title);
	public String getTitle();

	public void setDescription(String description);
	public String getDescription();

	public void setDuration(int days, int hours, int minutes);

	public void setStartTime(Calendar startTime);
	public Calendar getStartTime();

	public Calendar getFinishTime();
}
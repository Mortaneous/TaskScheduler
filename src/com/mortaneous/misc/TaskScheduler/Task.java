/*
 * Task.java
 */
 
package com.mortaneous.misc.TaskScheduler;

import java.util.Calendar;
 
public class Task
{
	private TaskDuration duration;
	private Calendar startTime;
	private Calendar finishTime;
	
	public Task()
	{
		this(new TaskDuration());
	}
	
	public Task(TaskDuration duration)
	{
		this.duration = duration;
	}
	
	public TaskDuration getDuration()
	{
		return duration;
	}
	
	public void setDuration(TaskDuration duration)
	{
		if(duration != null) {
			this.duration = duration;
		}
	}
	
	public Calendar getStartTime()
	{
		return startTime;
	}
	
	public void setStartTime(Calendar startTime)
	{
		if(startTime != null) {
			this.startTime = startTime;
		}
	}
	
	public Calendar getFinishTime()
	{
		return finishTime;
	}
	
	public void setEndTime(Calendar finishTime)
	{
		if(finishTime != null) {
			this.finishTime = finishTime;
		}
	}
}

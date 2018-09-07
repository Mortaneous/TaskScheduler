/*
 * TaskNode.java
 */
 
package com.mortaneous.misc.TaskScheduler;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Observable;
import java.util.Observer;
import java.util.List;
import java.util.ArrayList;
 
public class TaskNode extends Observable implements Task, Observer
{
	private String title;
	private String description;
	
	private TaskDuration duration;
	private Calendar startTime;
	private Calendar finishTime;
	
	private List<TaskNode> parents;
	
	//
	// Constructors
	//

	public TaskNode(String title, String description,
				int days, int hours, int minutes,
				TaskNode parent
			   )
	{
		initialize(title, description, days, hours, minutes);
		
		if(parent != null) {
			addDependency(parent);
		}
	}
	
	public TaskNode(String title, String description,
				int days, int hours, int minutes, 											// duration
				List<TaskNode> parents														// dependencies
			   )
	{
		initialize(title, description, days, hours, minutes);
		
		if(parents != null) {
			for(TaskNode task : parents) {
				addDependency(task);
			}
		}
	}
	
	public TaskNode(String title, String description,
				int days, int hours, int minutes, 											// duration
				int year, int month, int dayOfMonth, int hourOfDay, int minuteOfHour		// start date/time
			   )
	{
		initialize(title, description, days, hours, minutes);
		
		setStartTime(new GregorianCalendar(year, month, dayOfMonth, hourOfDay, minuteOfHour));
	}

	//
	// Common Initialization
	//
	
	private void initialize(String title, String description,
							int days, int hours, int minutes
						   )
	{
		this.title = title;
		this.description = description;
		
		this.startTime = null;
		this.finishTime = null;
		
		duration = new TaskDuration(days, hours, minutes);
	
		parents = new ArrayList<TaskNode>();
	}
	
	//
	// Title and Description
	//
	@Override
	public String getTitle()
	{
		return title;
	}
	
	@Override
	public void setTitle(String title)
	{
		this.title = title;
	}
	
	@Override
	public String getDescription()
	{
		return description;
	}
	
	@Override
	public void setDescription(String description)
	{
		this.description = description;
	}
	
	//
	// Duration
	//
	@Override
	public int getDurationDays() { return duration.getNumOfDays(); }
	@Override
	public int getDurationHours() { return duration.getNumOfHours(); }
	@Override
	public int getDurationMinutes() { return duration.getNumOfMinutes(); }
	@Override
	public void setDuration(int days, int hours, int minutes)
	{
		duration.setDuration(days, hours, minutes);
		calculateFinishTime();
	}
	
	//
	// Start Time
	//
	@Override
	public int getStartMonth() { return startTime.get(Calendar.MONTH); }
	@Override
	public int getStartDay() { return startTime.get(Calendar.DAY_OF_MONTH); }
	@Override
	public int getStartYear() { return startTime.get(Calendar.YEAR); }
	@Override
	public int getStartHour() { return startTime.get(Calendar.HOUR_OF_DAY); }
	@Override
	public int getStartMinute() { return startTime.get(Calendar.MINUTE); }
	@Override
	public Calendar getStartTime()
	{
		return startTime;
	}
	
	@Override
	public void setStartDateTime(int month, int day, int year, int hour, int minute)
	{
		setStartTime(new GregorianCalendar(year, month, day, hour, minute));
	}
	
	public void setStartTime(Calendar startTime)
	{
		if(parents.isEmpty()) {
			updateStartTime(startTime);
		}
	}

	private void updateStartTime(Calendar newStartTime)
	{
		this.startTime = newStartTime;
		calculateFinishTime();
	}
	
	private void updateStartTime(TaskNode task)
	{
		Calendar lastFinishTime = task.getFinishTime();
		
		for(TaskNode parent : parents) {
			if(lastFinishTime.before(parent.getFinishTime())) {
				lastFinishTime = parent.getFinishTime();
			}
		}
			
		updateStartTime((Calendar)lastFinishTime.clone());
	}

	//
	// Finish Time
	//
	@Override
	public Calendar getFinishTime()
	{
		return finishTime;
	}
	
	private void calculateFinishTime()
	{
		if(startTime != null) {
			finishTime = (Calendar) startTime.clone();
			finishTime.add(Calendar.MINUTE, (int) duration.getTotalMinutes());
		
			sendNotifications();
		}
	}
	
	//
	// Dependencies
	//
	@Override
	public List<Task> getDependencies()
	{
		List<Task> dependencies = new ArrayList<Task>(parents);
		
		return dependencies;
	}
	
	public void addDependency(TaskNode parent)
	{
		if(parent != null) {
			parents.add(parent);
			updateStartTime((TaskNode)parent);
			parent.registerObserver(this);
		}
	}
	
	//
	// toString()
	//
	@Override
	public String toString()
	{
		return getTitle();
	}

	//
	// Observable interface
	//
	public void registerObserver(Observer obj)
	{
		addObserver(obj);
	}

	public void unregisterObserver(Observer obj)
	{
		deleteObserver(obj);
	}
	
	private void sendNotifications()
	{
		setChanged();
		notifyObservers();
		clearChanged();
	}
	
	//
	// Observer interface
	//
	@Override
	public void update(Observable obj, Object arg)
	{
		updateStartTime((TaskNode) obj);
	}
	
}
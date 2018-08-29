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
				List<TaskNode> parents															// dependencies
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
				int year, int month, int dayOfMonth, int hourOfDay, int minuteOfHour,		// start date/time
				int days, int hours, int minutes 											// duration
			   )
	{
		this.title = title;
		this.description = description;

		duration = new TaskDuration(days, hours, minutes);
		parents = new ArrayList<TaskNode>();
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
		
		this.duration = new TaskDuration(days, hours, minutes);
	
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
	public void setDuration(int days, int hours, int minutes)
	{
		duration.setDuration(days, hours, minutes);
		calculateFinishTime();
	}
	
	//
	// Start Time
	//
	@Override
	public Calendar getStartTime()
	{
		return startTime;
	}
	
	@Override
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
	public void addDependency(TaskNode parent)
	{
		if(parent != null) {
			parents.add(parent);
			updateStartTime(parent);
			parent.registerObserver(this);
		}
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
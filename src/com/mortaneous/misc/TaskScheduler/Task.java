/*
 * Task.java
 */
 
package com.mortaneous.misc.TaskScheduler;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Observable;
import java.util.Observer;
import java.util.List;
import java.util.ArrayList;
 
public class Task extends Observable implements Observer
{
	private String name;
	private String description;
	
	private TaskDuration duration;
	private Calendar startTime;
	private Calendar finishTime;
	
	private List<Task> parents;
	
	//
	// Constructors
	//

	public Task(String name, String description,
				int days, int hours, int minutes,
				Task parent
			   )
	{
		initialize(name, description, days, hours, minutes);
		
		if(parent != null) {
			addDependency(parent);
		}
	}
	
	public Task(String name, String description,
				int days, int hours, int minutes, 											// duration
				List<Task> parents															// dependencies
			   )
	{
		initialize(name, description, days, hours, minutes);
		
		if(parents != null) {
			for(Task task : parents) {
				addDependency(task);
			}
		}
	}
	
	public Task(String name, String description,
				int year, int month, int dayOfMonth, int hourOfDay, int minuteOfHour,		// start date/time
				int days, int hours, int minutes 											// duration
			   )
	{
		this.name = name;
		this.description = description;

		duration = new TaskDuration(days, hours, minutes);
		parents = new ArrayList<Task>();
		setStartTime(new GregorianCalendar(year, month, dayOfMonth, hourOfDay, minuteOfHour));
	}

	//
	// Common Initialization
	//
	
	private void initialize(String name, String description,
							int days, int hours, int minutes
						   )
	{
		this.name = name;
		this.description = description;
		
		this.startTime = null;
		this.finishTime = null;
		
		this.duration = new TaskDuration(days, hours, minutes);
	
		parents = new ArrayList<Task>();
	}
	
	//
	// Name and Description
	//
	public String getName()
	{
		return name;
	}
	
	public void setName(String name)
	{
		this.name = name;
	}
	
	public String getDescription()
	{
		return description;
	}
	
	public void setDescription(String description)
	{
		this.description = description;
	}
	
	//
	// Duration
	//
	
	public void setDuration(int days, int hours, int minutes)
	{
		duration.setDuration(days, hours, minutes);
		calculateFinishTime();
	}
	
	//
	// Start Time
	//
	public Calendar getStartTime()
	{
		return startTime;
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
	
	private void updateStartTime(Task task)
	{
		Calendar lastFinishTime = task.getFinishTime();
		
		for(Task parent : parents) {
			if(lastFinishTime.before(parent.getFinishTime())) {
				lastFinishTime = parent.getFinishTime();
			}
		}
			
		updateStartTime((Calendar)lastFinishTime.clone());
	}

	//
	// Finish Time
	//
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
	public void addDependency(Task parent)
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
	// Observable interface
	//
	public void update(Observable obj, Object arg)
	{
		updateStartTime((Task) obj);
	}
	
}

/*

*/

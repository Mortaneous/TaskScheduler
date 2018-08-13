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

import java.time;
 
public class Task extends Observable implements Observer
{
	private String name;
	private String description;
	
	private TaskDuration duration;
	private Calendar startTime;
	private Calendar finishTime;
	
	private List<Task> parents;
	
	//
	// Contructors
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
	
	public String getDescription()
	{
		return description;
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
public class TestHarness
{
  public static void main(String[] args)
  {
    Task task = new Task("[1] DESIGN", "Requirements gathering", 2018, 8, 13, 14, 46, 5, 0, 0);
    printTask(task);
    
    Task task2 = new Task("[2] CODING", "Coding and testing", 10, 0, 0, task);
    printTask(task2);
    Task task3 = new Task("[3] QUALITY ASSURANCE", "Funcional and regression testing", 7, 0, 0, task2);
    printTask(task3);
    Task task4 = new Task("[4] DOCUMENTATION", "Product documentation", 2018, 8, 13, 14, 46, 3, 0, 0);
    printTask(task4);
    Task task5 = new Task("[5] USER ACCEPTANCE", "Customer acceptance testing", 14, 0, 0, task3);
    printTask(task5);
    task5.addDependency(task4);
    printTask(task5);
    

    System.out.println("*************************************************************************");
	System.out.println(" try to set " + task2.getName() + "'s start time manually (should not take effect)");
    Calendar cal = new GregorianCalendar(2018, 1, 1, 12, 0);
    task2.setStartTime(cal);
    printTask(task2);
    printTask(task3);
    printTask(task4);
    printTask(task5);
    
    System.out.println("*************************************************************************");
	System.out.println(" pull back " + task.getName() + "'s start time instead (" + task2.getName() + ", " + task3.getName() + " should follow)");
    task.setStartTime(cal);
    printTask(task);
    printTask(task2);
    printTask(task3);
    printTask(task4);
    printTask(task5);
    
    System.out.println("*************************************************************************");
	System.out.println(" delay " + task.getName() + "'s start time 1 month later than original schedule (" + task2.getName() + ", " + task3.getName() + ", " + task5.getName() + " should follow)");
    cal = new GregorianCalendar(2018, 9, 13, 14, 46);
    task.setStartTime(cal);
    printTask(task);
    printTask(task2);
    printTask(task3);
    printTask(task4);
    printTask(task5);
  }
  
  public static String getDisplayString(Calendar cal)
  {
    return (cal.get(Calendar.MONTH)+1) + "/" + cal.get(Calendar.DAY_OF_MONTH) + "/" + cal.get(Calendar.YEAR) + " " +
    	   cal.get(Calendar.HOUR_OF_DAY) + ":" + cal.get(Calendar.MINUTE);
    
  }
  
  public static void printTask(Task task)
  {
    System.out.println("=========================================");
    System.out.println(task.getName());
    System.out.println(task.getDescription());
    //System.out.println("-----------------------------------------");
    System.out.println("Start  : " + getDisplayString(task.getStartTime()));
    System.out.println("Finish : " + getDisplayString(task.getFinishTime()));
    System.out.println("=========================================");
  }
}
*/

/*
 * TaskDuration.java
 */
 
package com.mortaneous.misc.TaskScheduler;
 
import java.time.Duration;
 
public class TaskDuration
{
	private Duration duration;
	private String textDuration;
	private int days;
	private int hours;
	private int minutes;
	
	public TaskDuration()
	{
		duration = Duration.ZERO;
		textDuration = "";
	}
	
	public TaskDuration(int days, int hours, int minutes)
	{
		setDuration(days, hours, minutes);
	}
	
	public void setDuration(int days, int hours, int minutes)
	{
		String dur = "P";
		
		if(days != 0) {
			dur += days + "D";
		}
		
		if(hours != 0) {
			dur += "T" + hours + "H";
		}
		
		if(minutes != 0) {
			if(hours == 0) {
				dur += "T";
			}
			
			dur += minutes + "M";
		}
		
		duration = Duration.parse(textDuration = dur);
		this.days = days;
		this.hours = hours;
		this.minutes = minutes;
	}
	
	public String getTextDuration()
	{
		return textDuration;
	}
	
	public long getTotalDays()
	{
		return duration.toDays();
	}
	
	public long getTotalHours()
	{
		return duration.toHours();
	}
	
	public long getTotalMinutes()
	{
		return duration.toMinutes();
	}
	
	public int getNumOfDays()
	{
		return days;
	}
	
	public int getNumOfHours()
	{
		return hours;
	}
	
	public int getNumOfMinutes()
	{
		return minutes;
	}
	
  	public String toString()
    {
      return duration.toString();
    }
}
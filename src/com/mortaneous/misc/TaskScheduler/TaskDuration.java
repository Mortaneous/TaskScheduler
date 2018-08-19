/*
 * TaskDuration.java
 */
 
package com.mortaneous.misc.TaskScheduler;
 
import java.time.Duration;
 
public class TaskDuration
{
//	private int days;
//	private int hours;
//	private int minutes;
	private Duration duration;
	private String textDuration;
	
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
  
  	public String toString()
    {
      return duration.toString();
    }
}

/*
import java.time.Duration;
import java.util.Calendar;
import java.util.GregorianCalendar;
 
public class TestHarness
{
  public static void main(String[] args)
  {
    int minutes;
    Calendar cal = new GregorianCalendar(2018, 9, 31, 13, 35);
    System.out.println(">> " + getDisplayString(cal));
    minutes = 30;
    cal.add(Calendar.MINUTE, 1*24*60);
    System.out.println("plus 1d     >> " + getDisplayString(cal));
    cal.add(Calendar.MINUTE, minutes);
    System.out.println("plus 30mins  >> " + getDisplayString(cal));
    minutes = 1440; // 1 day
    cal.add(Calendar.MINUTE, minutes);
    System.out.println("plus 24hours >> " + getDisplayString(cal));
    cal.add(Calendar.MINUTE, 43200);
    System.out.println("plus 30d     >> " + getDisplayString(cal));
    
    Calendar cal2 = (GregorianCalendar) cal.clone();
    System.out.println("cal2 >> " + getDisplayString(cal2));
    cal.add(Calendar.MINUTE, 43200);
    System.out.println("plus 30d (cal2) >> " + getDisplayString(cal2));
    System.out.println("cal1 >> " + getDisplayString(cal));
    
    
    TaskDuration[] t = { new TaskDuration(1, 22, 3),
          				 new TaskDuration(0, 0, 3),
    					 new TaskDuration(0, 2, 0),
    					 new TaskDuration(1, 0, 0),
    					 new TaskDuration(1, 0, 3),
    					 new TaskDuration(1, 2, 0),
                         new TaskDuration(0, 2, 3), 
                         new TaskDuration(-1, 2, 3), 
                         new TaskDuration(0, -2, 0), 
                         new TaskDuration(0, 2, -3), 
                       };
    
    System.out.println("*** CONSTRUCTOR ***");
    for(int i=0; i<t.length; i++) {
      System.out.println("(" + i + ") " + t[i].getTextDuration());
    }
    
    System.out.println("\n*** PROPERTY SETTER ***");
    t[1].setDuration(0, 2, -3);
    t[2].setDuration(1, 23, 3);
    t[3].setDuration(0, 2, 0);
    for(int i=1; i<4; i++) {
      System.out.println("(" + i + ") " + t[i].getTextDuration() + " (" + t[i] + ")");
    }
    
    System.out.println("\n***\nt[0] = " + t[0].getTextDuration());
    System.out.println("total days    = " + t[0].getTotalDays());
    System.out.println("total hours   = " + t[0].getTotalHours());
    System.out.println("total minutes = " + t[0].getTotalMinutes());
    
  }
  
  public static String getDisplayString(Calendar cal)
  {
    return (cal.get(Calendar.MONTH)+1) + "/" + cal.get(Calendar.DAY_OF_MONTH) + "/" + cal.get(Calendar.YEAR) + " " +
    	   cal.get(Calendar.HOUR_OF_DAY) + ":" + cal.get(Calendar.MINUTE);
    
  }
}

*/

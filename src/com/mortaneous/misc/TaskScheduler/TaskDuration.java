package com.mortaneous.misc.TaskScheduler;
/*
 * TaskDuration.java
 */
 
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
	
	public String getTextDuration()
	{
		return textDuration;
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
	
}

/*
public class TestHarness
{
  public static void main(String[] args)
  {
    TaskDuration[] t = { new TaskDuration(1, 2, 3),
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
    
    for(int i=0; i<t.length; i++) {
      System.out.println("(" + i + ") " + t[i].getTextDuration());
    }
    
  }
}

*/
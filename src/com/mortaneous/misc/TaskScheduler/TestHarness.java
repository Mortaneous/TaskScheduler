package com.mortaneous.misc.TaskScheduler;

import java.util.Calendar;
import java.util.GregorianCalendar;

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
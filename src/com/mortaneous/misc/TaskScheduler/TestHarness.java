package com.mortaneous.misc.TaskScheduler;

import java.util.*;


public class TestHarness
{
  public static void main(String[] args)
  {
    TaskNode task = new TaskNode("[1] DESIGN", "Requirements gathering", 2018, 8, 13, 14, 46, 5, 0, 0);
    printTask(task);
    
    TaskNode task2 = new TaskNode("[2] CODING", "Coding and testing", 10, 0, 0, task);
    printTask(task2);
    TaskNode task3 = new TaskNode("[3] QUALITY ASSURANCE", "Funcional and regression testing", 7, 0, 0, task2);
    printTask(task3);
    TaskNode task4 = new TaskNode("[4] DOCUMENTATION", "Product documentation", 2018, 8, 13, 14, 46, 3, 0, 0);
    printTask(task4);
    TaskNode task5 = new TaskNode("[5] USER ACCEPTANCE", "Customer acceptance testing", 14, 0, 0, task3);
    printTask(task5);
    task5.addDependency((TaskNode)task4);
    printTask(task5);
    

    System.out.println("*************************************************************************");
	System.out.println(" try to set " + task2.getTitle() + "'s start time manually (should not take effect)");
    Calendar cal = new GregorianCalendar(2018, 1, 1, 12, 0);
    task2.setStartTime(cal);
    printTask(task2);
    printTask(task3);
    printTask(task4);
    printTask(task5);
    
    System.out.println("*************************************************************************");
	System.out.println(" pull back " + task.getTitle() + "'s start time instead (" + task2.getTitle() + ", " + task3.getTitle() + " should follow)");
    task.setStartTime(cal);
    printTask(task);
    printTask(task2);
    printTask(task3);
    printTask(task4);
    printTask(task5);
    
    System.out.println("*************************************************************************");
	System.out.println(" delay " + task.getTitle() + "'s start time 1 month later than original schedule (" + task2.getTitle() + ", " + task3.getTitle() + ", " + task5.getTitle() + " should follow)");
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
    System.out.println(task.getTitle());
    System.out.println(task.getDescription());
    //System.out.println("-----------------------------------------");
    System.out.println("Start  : " + getDisplayString(task.getStartTime()));
    System.out.println("Finish : " + getDisplayString(task.getFinishTime()));
    System.out.println("=========================================");
  }
}

/*
 * TaskPanel.java
 */

package com.mortaneous.misc.TaskScheduler;

import javax.swing.*;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import java.util.Map;
import java.util.HashMap;
import java.util.Calendar;

public class TaskPanel extends JPanel
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2227009623107193498L;
	private Map<Task, TaskView> taskMap;

	public TaskPanel()
	{
		setBorder(BorderFactory.createLineBorder(Color.BLACK));
		
		taskMap = new HashMap<Task, TaskView>();
	}
	
	public Dimension getPreferredSize()
	{
		return new Dimension(640,480);
	}
	
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		
		drawAllTasks(g);
	}
	
	public void addTask(Task task)
	{
		if(task != null) {
			int OFFSET = 1;
			TaskElement te = new TaskElement(task);
			te.setPosition(150, getNextAvailLoc(taskMap.size()));
			//System.out.println(te.getTitle() + " : " + te.getX() + "," + te.getY());
			taskMap.put(task, te);
			repaint(te.getX(), te.getY(), te.getWidth()+OFFSET, te.getHeight()+OFFSET);
		}
	}
	
	public void removeTask(Task task)
	{
		if(task != null) {
			int OFFSET = 1;
			TaskView tv = taskMap.get(task);
			taskMap.remove(task);
			repaint(tv.getX(), tv.getY(), tv.getWidth()+OFFSET, tv.getHeight()+OFFSET);
		}
	}
	
	private void drawModifiedTasks(Graphics g)
	{
		
	}
	
	private void drawAllTasks(Graphics g)
	{
		for(TaskView task : taskMap.values()) {
			drawTask(g, task);
		}
	}
	
	private void drawTask(Graphics g, TaskView task)
	{
		int x = task.getX();
		int y = task.getY();
		int w = task.getWidth();
		int h = task.getHeight();
		Color c = task.getColor();
		Color line = Color.BLACK;
		
		int titleHeight = 25;
		
		g.setColor(c);
		g.fill3DRect(x, y, w, h, true);
		g.setColor(line);
		g.drawRect(x, y, w, h);

		if(titleHeight < h) {
			g.drawLine(x, y+titleHeight, x+w, y = y + titleHeight);
			
			// title
			g.drawString(task.getTitle(), x+5, y  - (titleHeight-10) / 2);
			
			// start time
			g.drawString("Start", x+5, y = y + g.getFontMetrics().getHeight());
			g.drawString(": " + getDisplayString(task.getStartTime()), x+45, y);
			
			// finish time
			g.drawString("Finish", x+5, y = y + g.getFontMetrics().getHeight());
			g.drawString(": " + getDisplayString(task.getFinishTime()), x+45, y);
			
		}
	}
	
	public String getDisplayString(Calendar cal)
	{
	return (cal.get(Calendar.MONTH)+1) + "/" + cal.get(Calendar.DAY_OF_MONTH) + "/" + cal.get(Calendar.YEAR) + " " +
		   cal.get(Calendar.HOUR_OF_DAY) + ":" + cal.get(Calendar.MINUTE);

	}
	
	public void updateView(Graphics g)
	{
		processTasks();
		drawAllTasks(g);
		repaint();
	}
	
	private void processTasks()
	{
		
	}
	
	private int getNextAvailLoc(int index)
	{
		final int startPos = 10;
		final int spacer = 10;
		int nextPos = startPos + index * TaskElement.DEF_HEIGHT + index * spacer;
		//System.out.println("nextPos = " + nextPos);
		return nextPos;
	}
}
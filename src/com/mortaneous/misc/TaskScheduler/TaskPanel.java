/*
 * TaskPanel.java
 */

package com.mortaneous.misc.TaskScheduler;

import javax.swing.*;
import java.awt.Color;
import java.awt.Graphics;

import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Calendar;

public class TaskPanel extends JPanel
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2227009623107193498L;
	//private Map<Task, TaskView> taskMap;
	private List<TaskView> taskList;
	
	public TaskPanel()
	{
		//setBorder(BorderFactory.createLineBorder(Color.BLACK));
		
		//taskMap = new HashMap<Task, TaskView>();
		taskList = new ArrayList<TaskView>();
	}
	
	// public Dimension getPreferredSize()
	// {
		// return new Dimension(640,480);
	// }
	
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		
		drawAllTasks(g);
	}
	
	public void addTask(Task task)
	{
		if(task != null) {
			int OFFSET = 1;
			int MARGIN = 10;

			TaskView tv = new TaskElement(task);
			tv.setPosition(MARGIN, getNextAvailLoc(taskList.size()));
			taskList.add(tv);
			
			repaint(tv.getX(), tv.getY(), tv.getWidth()+OFFSET, tv.getHeight()+OFFSET);
			
			int newWidth = tv.getX() + tv.getWidth() + OFFSET + MARGIN;
			int newHeight = tv.getY() + tv.getHeight() + OFFSET + MARGIN;
			setSize(newWidth, newHeight);
			invalidate();
			
			System.out.println("Panel size = " + newWidth + " x " + newHeight);
		}
	}
	
	public void removeTask(Task task)
	{
		if(task != null) {
			int OFFSET = 1;

			Iterator<TaskView> it = taskList.iterator();
			TaskView deleted;
			TaskView node;
			
			while(it.hasNext()) {
				deleted = it.next();

				if(deleted.getTask() == task) {
					it.remove();
					repaint(deleted.getX(), deleted.getY(), deleted.getWidth()+OFFSET, deleted.getHeight()+OFFSET);

					while(it.hasNext()) {
						node = it.next();
						swapPositions(deleted, node);
						
						repaint(deleted.getX(), deleted.getY(), deleted.getWidth()+OFFSET, deleted.getHeight()+OFFSET);
					}

				}
			}

		}
	}
	
	private void swapPositions(TaskView t1, TaskView t2)
	{
		int x = t1.getX();
		int y = t1.getY();
		
		t1.setPosition(t2.getX(), t2.getY());
		t2.setPosition(x, y);
	}
	
	private void drawAllTasks(Graphics g)
	{
		for(TaskView task : taskList) {
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

		return nextPos;
	}
}
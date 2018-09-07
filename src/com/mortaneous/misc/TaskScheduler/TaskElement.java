/*
 * TaskElement.java
 */

package com.mortaneous.misc.TaskScheduler;

import java.awt.Color;
import java.awt.Dimension;
import java.util.Calendar;
import java.util.Observable;
import java.util.Observer;

public class TaskElement implements TaskView
{
	public static final int DEF_X = 0;
	public static final int DEF_Y = 0;
	public static final int DEF_WIDTH = 150;
	public static final int DEF_HEIGHT = 70;
	public static final Color DEF_COLOR = Color.CYAN;
	
	private int x;
	private int y;
	private int width;
	private int height;
	private Color color;
	
	private Task task;
	
	private boolean dirty;
	
	public TaskElement(Task task)
	{
		this(task, DEF_X, DEF_Y, DEF_WIDTH, DEF_HEIGHT, DEF_COLOR);
	}
	
	public TaskElement(Task task, int x, int y)
	{
		this(task, x, y, DEF_WIDTH, DEF_HEIGHT, DEF_COLOR);
	}
	
	public TaskElement(Task task, int x, int y, Color color)
	{
		this(task, x, y, DEF_WIDTH, DEF_HEIGHT, color);
	}

	public TaskElement(Task task, int x, int y, int width, int height)
	{
		this(task, x, y, width, height, DEF_COLOR);
	}
	
	public TaskElement(Task task, int x, int y, int width, int height, Color color)
	{
		this.task = task;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.color = color;
		
		// if(task instanceof TaskNode) {
			// ((TaskNode) task).registerObserver(this);
		// }
		
		this.dirty = false;
	}
	
	@Override
	public boolean isDirty() { return dirty; }
	
	@Override
	public void setDirty(boolean dirty) { this.dirty = dirty; }
	
	@Override
	public int getX() { return x; }
	@Override
	public void setX(int x) { this.x = x; }
	
	@Override
	public int getY() { return y; }
	@Override
	public void setY(int y) { this.y = y; }
	
	@Override
	public void setPosition(int x, int y)
	{
		setX(x);
		setY(y);
	}

	@Override
	public int getWidth() { return width; }

	@Override
	public int getHeight() { return height; }

	@Override
	public Dimension getDimension() { return new Dimension(width, height); }

	@Override
	public Color getColor() { return color; }
	@Override
	public void setColor(Color color) { this.color = color; }

	@Override
	public String getTitle() { return task != null ? task.getTitle() : ""; }

	@Override
	public String getDescription() { return task != null ? task.getDescription() : ""; }

	@Override
	public Calendar getStartTime() { return task != null ? task.getStartTime() : null; }

	@Override
	public Calendar getFinishTime() { return task != null ? task.getFinishTime() : null; }
	
	@Override
	public Task getTask() { return task; }
	
	//
	// Observer interface
	//
	// @Override
	// public void update(Observable o, Object args)
	// {
		// dirty = true;
	// }
}
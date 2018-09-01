/*
 * TaskView.java
 */

package com.mortaneous.misc.TaskScheduler;

import java.awt.Color;
import java.awt.Dimension;
import java.util.Calendar;

public interface TaskView
{
	public boolean isDirty();
	public void setDirty(boolean dirty);
	
	public int getX();
	public void setX(int x);
	public int getY();
	public void setY(int y);
	public void setPosition(int x, int y);
	public int getWidth();
	public int getHeight();
	public Dimension getDimension();
	public Color getColor();

	public String getTitle();
	public String getDescription();
	public Calendar getStartTime();
	public Calendar getFinishTime();
	
	public Task getTask();
}
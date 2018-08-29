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
	public int getY();
	public int getWidth();
	public int getHeight();
	public Dimension getDimension();
	public Color getColor();
	public String getTitle();
	public String getDescription();
	public Calendar getStartTime();
	public Calendar getFinishTime();
}
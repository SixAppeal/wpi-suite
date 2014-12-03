/*******************************************************************************
 * Copyright (c) 2014 -- WPI Suite
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors: Ryan Orlando, Thomas Meehan
 ******************************************************************************/


package edu.wpi.cs.wpisuitetng.modules.taskmanager.draganddrop;


import java.awt.event.MouseEvent;

import edu.wpi.cs.wpisuitetng.modules.taskmanager.view.columnar.TaskView;

/**
 *  This is a custom class to watch the mouse actions on a task View
 * @author RnOrlando
 * @author tmeehan
 *
 */
public class TaskDraggableMouseListener extends DraggableMouseListener{
	
	private TaskView taskAssoc;
	
	/**
	 * Constructor
	 * @param t Task view task is listening on
	 */
	
	public TaskDraggableMouseListener(TaskView t) {
		super(t);
		this.taskAssoc = t;
		
	}
	/**
	 * @see  java.awt.event.MouseAdapter.mouseClicked
	 */
	@Override
	public void mouseClicked(MouseEvent e) 
	{
		taskAssoc.getGateway().toPresenter("TaskPresenter", "viewTask", taskAssoc.getTask());
	}

	/**
	 * @see  java.awt.event.MouseAdapter.mouseReleased
	 */
	@Override
	public void mouseReleased(MouseEvent e) 
	{
		taskAssoc.getGateway().toPresenter("TaskPresenter", "viewTask", taskAssoc.getTask());
	}

	/**
	 * @see  java.awt.event.MouseAdapter.mouseEntered
	 */
	@Override
	public void mouseEntered(MouseEvent e) 
	{
		assoc.setBackground(TaskView.HOVER_COLOR);
	}

	/**
	 * @see java.awt.event.MouseAdapter.mouseExited
	 */
	@Override
	public void mouseExited(MouseEvent e) {
		assoc.setBackground(TaskView.BACKGROUND_COLOR);
	}

	/**
	 * @see  edu.wpi.cs.wpisuitetng.modules.taskmanager.draganddrop.DraggableMouseListener.mousePressed
	 */
	@Override
	public void mousePressed(MouseEvent e) 
	{
		taskAssoc.getGateway().toPresenter("TaskPresenter", "editTask", taskAssoc.getTask());
		super.mousePressed(e);
	}
}

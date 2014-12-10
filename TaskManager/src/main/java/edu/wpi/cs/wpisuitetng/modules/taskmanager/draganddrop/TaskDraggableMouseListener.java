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
 * @author srojas
 *
 */
public class TaskDraggableMouseListener extends DraggableMouseListener{

	private TaskView taskAssoc;
	private boolean hasMoved;

	/**
	 * Constructor
	 * @param t Task view task is listening on
	 */

	public TaskDraggableMouseListener(TaskView t) {
		super(t);
		this.taskAssoc = t;
		this.hasMoved = false;

	}

	//TODO @wmtemple, add single click selector
	/**
	 * @see  java.awt.event.MouseAdapter.mouseClicked
	 */
	@Override
	public void mouseClicked(MouseEvent e) 
	{
		if(e.getClickCount()==2){
			taskAssoc.getGateway().toPresenter("TaskPresenter", "editTask", taskAssoc.getTask());
		}
	}

	/**
	 * @see  java.awt.event.MouseAdapter.mouseReleased
	 */
	@Override
	public void mouseReleased(MouseEvent e) 
	{
		if(e.getClickCount()==2){
			taskAssoc.getGateway().toPresenter("TaskPresenter", "editTask", taskAssoc.getTask());
		}
	}

	/**
	 * @see  java.awt.event.MouseAdapter.mouseEntered
	 */
	@Override
	public void mouseEntered(MouseEvent e) 
	{
		taskAssoc.highlight();
	}

	/**
	 * @see java.awt.event.MouseAdapter.mouseExited
	 */
	@Override
	public void mouseExited(MouseEvent e) {
		taskAssoc.unhighlight();
	}

	/**
	 * @see  edu.wpi.cs.wpisuitetng.modules.taskmanager.draganddrop.DraggableMouseListener.mousePressed
	 */
	@Override
	public void mousePressed(MouseEvent e) 
	{
		if (e.getClickCount() == 1 ) {
			taskAssoc.getGateway().toPresenter("TaskPresenter", "selectTask", new Boolean(e.isControlDown()), taskAssoc);
		} else if (e.getClickCount() == 2) {
			taskAssoc.getGateway().toPresenter("TaskPresenter", "editTask", taskAssoc.getTask());
			taskAssoc.getGateway().toPresenter("TaskPresenter", "deselectAllTasks");
		}
		super.mousePressed(e);
	}
}

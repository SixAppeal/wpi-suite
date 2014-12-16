/*******************************************************************************
 * Copyright (c) 2014 -- WPI Suite
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors: Will Rensselaer, Thomas Meehan, Will Rensselaer, Alex Shoop, Ryan Orlando, Troy Hughes
 ******************************************************************************/


package edu.wpi.cs.wpusuitetng.modules.taskmanager.draganddrop;

import static org.junit.Assert.*;

import java.awt.event.MouseEvent;

import org.junit.Before;
import org.junit.Test;

import edu.wpi.cs.wpisuitetng.modules.taskmanager.draganddrop.TaskDraggableMouseListener;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.model.Task;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.presenter.Gateway;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.view.columnar.TaskView;

public class TestTaskDraggableMouseListener {
	TaskDraggableMouseListener testTaskDraggableMouseListener;
	TaskView testTaskView;
	Task testTask;
	Gateway mockGateway;
	Gateway spy;
	
	
	@Before
	public void setup(){
		testTask = new Task();
		
		testTaskView = new TaskView(testTask);
		testTaskDraggableMouseListener = new TaskDraggableMouseListener(testTaskView);
		
		
/*
 * Below is attempt for utilizing Mock Objects for testing. Had troubles because of void methods. 
 * Thought about making public identifiers for methods to implement testing. Still in process of figuring
 * out best way to 'fully' test these. 
 */
//		mockGateway = new Gateway();
//		spy = spy(mockGateway);
		
//		when(spy.toPresenter(new String(), new String(), new Object())).thenReturn("Mock Message 1");
//		when(mockGateway.toPresenter(new String(), new String(), new Object())).thenReturn("Iz Returned");
		
	}

	@Test
	public void testConstructor(){
		assertNotNull(testTaskDraggableMouseListener);
	}

	@Test
	public void testMouseEntered(){
		testTaskDraggableMouseListener.mouseEntered(new MouseEvent(testTaskView, 0, 0, 0, 0, 0, 0, false));
		assertEquals(testTaskView.getBackground(), TaskView.HOVER_COLOR);
	
	}
	@Test
	public void testMouseExited(){
		testTaskDraggableMouseListener.mouseExited(new MouseEvent(testTaskView, 0, 0, 0, 0, 0, 0, false));
		assertEquals(testTaskView.getBackground(), TaskView.BACKGROUND_COLOR);
	
	}
	
	
}

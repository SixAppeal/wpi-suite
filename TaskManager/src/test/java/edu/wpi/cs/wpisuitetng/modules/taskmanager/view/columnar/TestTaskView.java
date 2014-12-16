/*******************************************************************************
 * Copyright (c) 2014 -- WPI Suite
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors: SixAppeal
 ******************************************************************************/

package edu.wpi.cs.wpisuitetng.modules.taskmanager.view.columnar;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import edu.wpi.cs.wpisuitetng.modules.taskmanager.model.Task;

/**
 * Runs tests on the TaskView class
 * @author wavanrensselaer
 */
public class TestTaskView {
	TaskView taskView;
	Task task;
	Task task2;
	
	@Before
	public void setup() {
		task = new Task();
		task2 = new Task();
		task2.setTitle("A task");
		taskView = new TaskView(task);
	}
	
	@Test
	public void testConstructor() {
		assertNotNull(taskView.getTask());
		assertEquals(task, taskView.getTask());
	}
	
	@Test
	public void testSetState() {
		taskView.setState(task2);
		assertEquals(task2, taskView.getTask());
	}
}

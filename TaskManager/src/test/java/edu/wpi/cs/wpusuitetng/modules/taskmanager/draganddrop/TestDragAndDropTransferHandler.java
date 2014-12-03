
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

import org.junit.Before;
import org.junit.Test;

import edu.wpi.cs.wpisuitetng.modules.taskmanager.draganddrop.DragAndDropTransferHandler;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.draganddrop.TransferableTaskString;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.model.Task;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.view.columnar.TaskView;

public class TestDragAndDropTransferHandler 
{

	DragAndDropTransferHandler test;
	Task testTask;
	TaskView testTaskView;
	
	@Before
	public void setup()
	{
		test = new DragAndDropTransferHandler();
		testTask = new Task();
		testTaskView = new TaskView(testTask);
	}
	
	@Test
	public void testConstructor()
	{
		assertNotNull(test);
	}
	
	@Test
	public void testCreateTransferable()
	{
		assertEquals(test.createTransferable(testTaskView), new TransferableTaskString(testTask) );
	}
	
	
}

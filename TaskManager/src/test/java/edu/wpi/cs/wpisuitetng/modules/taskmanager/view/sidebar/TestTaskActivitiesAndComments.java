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

package edu.wpi.cs.wpisuitetng.modules.taskmanager.view.sidebar;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import edu.wpi.cs.wpisuitetng.modules.taskmanager.model.Task;

public class TestTaskActivitiesAndComments {

	TaskActivitiesAndComments testTaskAaC;
	
	
	@Before
	public void setup(){
		testTaskAaC = new TaskActivitiesAndComments(new Task());
		
	}
	
	@Test
	public void testConstructor(){
		assertNotNull(testTaskAaC);
	}
}

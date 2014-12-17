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

import edu.wpi.cs.wpisuitetng.modules.taskmanager.model.Stage;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.model.StageList;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.model.Task;
/**
 * runs test on taskeditview
 * @author thhughes
 *
 */
public class TestTaskEditView {
	Task ttask;
	StageList tsl;
	Stage tstage1;
	Stage tstage2;
	TaskEditView testTEV;
	
	
	
	@Before
	public void Setup(){
		ttask = new Task();
		tstage1 = new Stage("Stage1");
		tstage2 = new Stage("Stage2");
		
		tsl = new StageList();
		tsl.add(tstage1);
		tsl.add(tstage2);
		

		
		
		
	}
	
	@Test
	public void testTEVConstructor(){
		testTEV = new TaskEditView(ttask, tsl);
		assertNotNull(testTEV);
		assertEquals(testTEV.getTask(), ttask);
		
	}
	
	
}


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

import static org.junit.Assert.assertNotNull;

import javax.swing.JComponent;
import javax.swing.JPanel;

import org.junit.Before;
import org.junit.Test;

import edu.wpi.cs.wpisuitetng.modules.taskmanager.draganddrop.DraggableMouseListener;

public class TestDraggableMouseListener {
	
	JComponent testJComp;
	DraggableMouseListener testDML;
	
	@Before
	public void Setup(){
		testJComp = new JPanel();
		testDML = new DraggableMouseListener(testJComp);
	}
	
	@Test
	public void testConstructor(){
		assertNotNull(testDML);
	}

}

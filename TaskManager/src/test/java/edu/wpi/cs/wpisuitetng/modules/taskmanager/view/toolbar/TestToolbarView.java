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

package edu.wpi.cs.wpisuitetng.modules.taskmanager.view.toolbar;

import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;

public class TestToolbarView 
{

	private ToolbarView toolbarView;
	private ToolbarView invisToolbarView;
	
	@Before
	public void setup() 
	{
		toolbarView = new ToolbarView();
		invisToolbarView = new ToolbarView();
		
	}
	
	@Test
	public void testConstructor() 
	{
		assertNotNull(toolbarView);
		assertNotNull(invisToolbarView);
	}
	
	

}

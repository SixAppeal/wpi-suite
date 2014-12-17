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

package edu.wpi.cs.wpisuitetng.modules.taskmanager.model;

import static org.junit.Assert.*;

import java.text.SimpleDateFormat;

import org.junit.Before;
import org.junit.Test;

/**
 * Test cases for the Activity Class
 * @author krpeffer
 *
 */
public class TestTaskActivity {
	
	Activity activity;
	
	/**
	 * set up creating an activity
	 */
	@Before
	public void setup(){
		this.activity = new Activity();
		// Default: 
		// activity 	= ""
	}
	
	/**
	 * tests the activity constructor
	 */
	@Test
	public void testConstructor(){
		Activity newActivity = new Activity();
		assertNotNull(newActivity);
		assertEquals(newActivity.getActivity(), "");
	}
	
	/**
	 * Tests to make sure that the string is returned correctly
	 */
	@Test
	public void testGetActivity(){
		Activity newActivity = new Activity();
		assertEquals(newActivity.getActivity(), "");
		newActivity.setActivity("Hello");
		assertEquals(newActivity.getActivity(), "Hello");
	}
	
	/**
	 * tests to make sure that strings are set to the activity properly
	 */
	@Test
	public void testSetActivity(){
		Activity newActivity = new Activity();
		newActivity.setActivity("This Task Was Created");
		assertEquals(newActivity.getActivity(), "This Task Was Created");
		newActivity.setActivity("Added New History");
		assertEquals(newActivity.getActivity(), "Added New History");
	}
	
	/**
	 * tests to make sure that the toString method returns the activity
	 */
	@Test
	public void testActivityToString(){
		Activity newActivity = new Activity();
		newActivity.setActivity("This is a test string");
		assertEquals(newActivity.toString(), new SimpleDateFormat("h:mm a, MM/dd/yyyy").format(newActivity.getDate())
				+ "> " + newActivity.getUser() + ": This is a test string");
	}
}

package edu.wpi.cs.wpisuitetng.modules.taskmanager.model;

import static org.junit.Assert.*;

public class TestTaskActivity {
	
	Activity activity;
	
	/**
	 * set up creating an activity
	 */
	public void setup(){
		this.activity = new Activity();
		// Default: 
		// activity 	= ""
	}
	
	/**
	 * tests the activity constructor
	 */
	public void testConstructor(){
		Activity newActivity = new Activity();
		assertNotNull(newActivity);
		assertEquals(newActivity.getActivity(), "");
	}
	
	/**
	 * Tests to make sure that the string is returned correctly
	 */
	public void testGetActivity(){
		Activity newActivity = new Activity();
		assertEquals(newActivity.getActivity(), "");
		newActivity.setActivity("Hello");
		assertEquals(newActivity.getActivity(), "Hello");
	}
	
	/**
	 * tests to make sure that strings are set to the activity properly
	 */
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
	public void testActivityToString(){
		Activity newActivity = new Activity();
		newActivity.setActivity("This is a test string");
		assertEquals(newActivity.toString(), "This is a test string");
	}
}

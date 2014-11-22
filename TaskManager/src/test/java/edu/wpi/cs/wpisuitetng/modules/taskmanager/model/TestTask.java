package edu.wpi.cs.wpisuitetng.modules.taskmanager.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;

/**
 * Test Suite to test Task Class
 * 
 * @author nhhughes
 * @author srojas
 * @author jrhennessy
 * @author Thhughes
 * 
 *
 */
public class TestTask {

	Task task;

	/**
	 * set up creating a task
	 */
	@Before
	public void setup() {
		this.task = new Task();
		/*
		 * Default: 
		 * Title 		= Dummy
		 * Description	= Dummy
		 * Efforts 		= 1
		 * Date 		= new Date()
		 * Status		= New
		 * 
		 */
	}
	
	/**
	 * Tests that the constructor works properly
	 */
	@Test
	public void testConstructor() {
		Task newTask = new Task();
		assertNotNull(newTask);
		assertEquals(newTask.getTitle(), "Dummy");
		assertEquals(newTask.getActivities().size(),0);
		assertEquals(newTask.getActualEffort(), new Integer(1));
		assertEquals(newTask.getAssignedTo().size(), 0);
		assertEquals(newTask.getDescription(), "Dummy");
		assertEquals(newTask.getDueDate(), new Date());
		assertEquals(newTask.getEstimatedEffort(), new Integer(1));
		assertEquals(newTask.getId(), 0);
		assertEquals(newTask.getStatus(), new TaskStatus("New"));

	}
	
	/**
	 * tests the copy from function. makes sure constructor with no title initializes to "" 
	 * copies the title from another one and makes sure it changed
	 */
	@Test
	public void testCopyConstructor() {
		Task newTask = new Task(this.task);
		assertEquals(newTask.getTitle(), "Dummy");
	}

	/**
	 * Placeholder for future tests
	 */
	@Test
	public void testSetActivities() {
		//TODO Write Comprehensive Tests once we start using activities
		assertEquals(1, 1); 
	}
	
	
	/**
	 * Function to test that the actual effort changes with the set method and is NOT negative 
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testSetActualEffort() {

		Task newTask = new Task();
		assertEquals(newTask.getActualEffort(), new Integer(1));
		newTask.setActualEffort(10);
		assertEquals(newTask.getActualEffort(), new Integer(10));
		newTask.setActualEffort(-50);
		assertEquals(newTask.getActualEffort(), new Integer(1));

	}
	
	/**
	 * Function to test that the estimated effort changes with the set method and is not negative
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testSetEstimatedEffort() {
		//TODO Write Comprehensive Tests once we start using actual effort

		Task newTask = new Task();
		assertEquals(newTask.getEstimatedEffort(), new Integer(1));
		newTask.setEstimatedEffort(10);
		assertEquals(newTask.getEstimatedEffort(), new Integer(10));
		newTask.setEstimatedEffort(-50);
		assertEquals(newTask.getEstimatedEffort(), new Integer(1));
	}
}

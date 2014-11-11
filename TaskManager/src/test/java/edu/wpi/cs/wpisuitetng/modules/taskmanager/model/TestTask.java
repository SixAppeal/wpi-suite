/**
 * 
 */
package edu.wpi.cs.wpisuitetng.modules.taskmanager.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import edu.wpi.cs.wpisuitetng.modules.taskmanager.TaskManager;

/**
 * @author nhhughes
 * @author srojas
 * @author jrhennessy
 *
 */
public class TestTask {

	Task task;

	/**
	 * set up creating a task
	 */
	@Before
	public void setup() {
		this.task = new Task("testing da tasks");
	}
	/**
	 * Tests that the constructor works properly
	 */
	@Test
	public void testConstructor() {
		Task newTask = new Task();
		assertNotNull(newTask);
		assertEquals(newTask.title, "");
		assertEquals(this.task.title, "testing da tasks");
		assertEquals(newTask.activities.size(),0);
		assertEquals(newTask.actualEffort, new Integer(-1));
		assertEquals(newTask.assignedTo.size(), 0);
		assertEquals(newTask.column, new Integer(0));
		assertEquals(newTask.description, "");
		assertEquals(newTask.dueDate, null);
		assertEquals(newTask.estimatedEffort, new Integer(-1));
		assertEquals(newTask.id, 0);
		assertEquals(newTask.status, null);

	}
	/**
	 * tests the copy from function. makes sure constructor with no title initializes to ""
	 * copies the title from another one and makes sure it changed
	 */
	@Test
	public void testCopyFrom() {
		Task newTask = new Task();
		assertEquals(newTask.title, "");
		newTask.copyFrom(this.task);
		assertEquals(newTask.title, "testing da tasks");
	}

	/*
	 * 
	 */
	@Test
	public void testSetActivities() {
		//TODO Write Comprehensive Tests once we start using activities
		assertEquals(1, 1); 
	}
	/**
	 * tests that the title truncates if you use one longer than 99 chars
	 */
	@Test
	public void testSetTitle(){
		Task newTask = new Task();
		assertEquals(newTask.title,"");
		newTask.setTitle("0123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789000000000000000000000000000000000");
		assertEquals(newTask.title,"012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678");

	}
	
	/**
	 * Function to test that the actual effort changes with the set method and is NOT negative 
	 */
	@Test
	public void testSetActualEffort() {

		Task newTask = new Task();
		assertEquals(newTask.actualEffort, new Integer(-1));
		newTask.setActualEffort(10);
		assertEquals(newTask.actualEffort, new Integer(10));
		newTask.setActualEffort(-50);
		assertEquals(newTask.actualEffort, new Integer(-1));

	}
	/**
	 * Function to test that the estimated effort changes with the set method and is not negative
	 */
	@Test
	public void testSetEstimatedEffort() {
		//TODO Write Comprehensive Tests once we start using actual effort

		Task newTask = new Task();
		assertEquals(newTask.estimatedEffort, new Integer(-1));
		newTask.setEstimatedEffort(10);
		assertEquals(newTask.estimatedEffort, new Integer(10));
		newTask.setEstimatedEffort(-50);
		assertEquals(newTask.estimatedEffort, new Integer(-1));
	}
	


}

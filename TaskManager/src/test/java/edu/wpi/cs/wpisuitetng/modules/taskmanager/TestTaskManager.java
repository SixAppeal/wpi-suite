package edu.wpi.cs.wpisuitetng.modules.taskmanager;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import edu.wpi.cs.wpisuitetng.modules.taskmanager.TaskManager;

/**
 * 
 * A class for testing TaskManager's module
 *
 */
public class TestTaskManager {

	TaskManager taskManager;
	
	@Before
	public void setup() {
		//taskManager = new TaskManager();
	}
	
	@Test
	public void testConstructor() {
		//assertNotNull(taskManager.name);
		//assertNotNull(taskManager.tabs);
	}
	
	@Test
	public void testGetName() {
		//assertEquals("Task Manager", taskManager.getName());
	}
	
	@Test
	public void testGetTabs() {
		//assertNotNull(taskManager.getTabs());
	}
	
}

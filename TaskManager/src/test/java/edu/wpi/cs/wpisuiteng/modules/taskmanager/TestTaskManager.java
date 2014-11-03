package edu.wpi.cs.wpisuiteng.modules.taskmanager;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

/**
 * 
 * @author wmtemple
 *
 */
public class TestTaskManager {

	TaskManager taskManager;
	
	@Before
	public void setup() {
		taskManager = new TaskManager();
	}
	
	@Test
	public void testConstructor() {
		assertNotNull(taskManager.name);
		assertNotNull(taskManager.tabs);
	}
	
	@Test
	public void testGetName() {
		assertEquals(taskManager.name, taskManager.getName());
		assertEquals("Task Manager", taskManager.getName());
	}
	
	@Test
	public void testGetTabs() {
		assertEquals(taskManager.tabs, taskManager.getTabs());
	}
	
}

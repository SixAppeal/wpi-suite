/**
 * 
 */
package edu.wpi.cs.wpisuitetng.modules.taskmanager.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import edu.wpi.cs.wpisuitetng.modules.taskmanager.TaskManager;

/**
 * @author nathan
 * @author santiago
 * @author jill
 *
 */
public class TestTask {

	Task task;
	
	
	@Before
	public void setup() {
		this.task = new Task("testing da tasks");
	}
	
	@Test
	public void testConstructor() {
        Task newTask = new Task();
        assertNotNull(newTask);
        assertEquals(newTask.title, "");
    }
	
	
}

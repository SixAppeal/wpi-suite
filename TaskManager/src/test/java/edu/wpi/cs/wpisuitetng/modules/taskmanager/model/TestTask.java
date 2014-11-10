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
	
	@Test
	public void testCopyFrom() {
		Task newTask = new Task();
		assertEquals(newTask.title, "");
		newTask.copyFrom(this.task);
		assertEquals(newTask.title, "testing da tasks");
	}
	
	@Test
	public void testSetActivities() {
		//TODO Write Comprehensive Tests once we start using activities
		assertEquals(1, 1); 
	}
		
	@Test
	public void testSetActualEffort() {
		//TODO Write Comprehensive Tests once we start using actual effort
		//TODO Make Validity Checking in Constructor and in setter to make sure no negative effort is set
		assertEquals(1, 1);
	}
	
	@Test 
	public void testToJson() {
		//TODO Figure out what the JSON format is for Task
		assertEquals(1, 1);
	}
	
	
}

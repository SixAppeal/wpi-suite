/**
 * 
 */
package edu.wpi.cs.wpisuitetng.modules.taskmanager.model;

import org.junit.Before;

import edu.wpi.cs.wpisuitetng.modules.taskmanager.TaskManager;

/**
 * @author nathan, santiago, jill
 *
 */
public class TestTask {

	Task task;
	
	
	@Before
	public void setup() {
		this.task = new Task("testing da tasks");
	}
	
	
	
	
}

package edu.wpi.cs.wpisuitetng.modules.taskmanager.presenter;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

/**
 * Tests for the TaskPresenter class
 * @author Will Van Rensselaer, Dan Seaman
 *
 */
public class TestTaskPresenter {

	Gateway gateway;
	TaskPresenter presenter;
	
	/**
	 * Create gateway and presenter objects for testing
	 */
	@Before
	public void setup() {
		gateway = new Gateway();
		presenter = new TaskPresenter();
		
		gateway.addPresenter("TaskPresenter", presenter);
		
	}
	
	/**
	 * Test the constructor of TaskPresenter
	 */
	@Test
	public void testConstructor() {
		assertNotNull(presenter.gateway);
		assertNull(presenter.tasks);
	}
}

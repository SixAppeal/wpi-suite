package edu.wpi.cs.wpisuitetng.modules.taskmanager.presenter;


import org.junit.Before;
import org.junit.Test;

/**
 * Tests for the TaskPresenter class
 * 
 * @author wavanrensselaer
 * @author dpseaman
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
		
		gateway.addPresenter("TaskPresenter", presenter);
		
	}
	
	/**
	 * Test the constructor of TaskPresenter
	 */
	@Test
	public void testConstructor() {
//		assertNotNull(presenter.gateway);
//		assertNull(presenter.tasks);
	}
}

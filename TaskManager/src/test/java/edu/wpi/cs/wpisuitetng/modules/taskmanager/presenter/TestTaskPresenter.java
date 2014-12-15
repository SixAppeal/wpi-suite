package edu.wpi.cs.wpisuitetng.modules.taskmanager.presenter;


import org.junit.Before;
import org.junit.Test;

import edu.wpi.cs.wpisuitetng.modules.taskmanager.localcache.Cache;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.localcache.ThreadSafeLocalCache;

/**
 * Tests for the TaskPresenter class
 * 
 * @author wavanrensselaer
 * @author dpseaman
 */
public class TestTaskPresenter {

	Gateway gateway;
	TaskPresenter presenter;
	Cache localCache;
	
	/**
	 * Create gateway and presenter objects for testing
	 */
	@Before
	public void setup() {
		gateway = new Gateway();
		localCache = new ThreadSafeLocalCache();
		presenter = new TaskPresenter(localCache);
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

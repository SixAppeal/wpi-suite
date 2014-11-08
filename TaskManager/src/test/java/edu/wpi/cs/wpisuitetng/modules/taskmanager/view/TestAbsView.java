package edu.wpi.cs.wpisuitetng.modules.taskmanager.view;

import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;

import edu.wpi.cs.wpisuitetng.modules.taskmanager.presenter.Gateway;

/**
 * Tests the constructor for the AbsView class
 * @author Will Van Rensselaer, Dan Seaman
 */
public class TestAbsView {
	Gateway gateway;
	AbsView view;
	
	/**
	 * Sets up the objects for testing
	 */
	@Before
	public void setup() {
		gateway = new Gateway();
		view = new AbsView(gateway) { };
	}
	
	/**
	 * Tests that the gateway object is set during construction
	 */
	@Test
	public void testConstructor() {
		assertNotNull(view.gateway);
	}
}

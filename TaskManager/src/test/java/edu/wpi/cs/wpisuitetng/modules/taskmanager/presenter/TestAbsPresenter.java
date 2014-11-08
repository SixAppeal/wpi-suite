package edu.wpi.cs.wpisuitetng.modules.taskmanager.presenter;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

/**
 * Tests the constructor for the AbsPresenter class
 * @author Will Van Rensselaer, Dan Seaman
 */
public class TestAbsPresenter {
	Gateway gateway;
	AbsPresenter presenter;
	
	/**
	 * Initializes objects for testing
	 */
	@Before
	public void setup() {
		gateway = new Gateway();
		presenter = new AbsPresenter(gateway) { };
	}
	
	/**
	 * Tests that the gateway object is set during construction
	 */
	@Test
	public void testConstructor() {
		assertNotNull(presenter.gateway);
	}
}

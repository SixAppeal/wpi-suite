package edu.wpi.cs.wpisuitetng.modules.taskmanager.presenter;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import edu.wpi.cs.wpisuitetng.modules.taskmanager.presenter.Gateway;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.view.TestView;

/**
 * Tests the methods of the Gateway class
 * 
 * @author wavanrensselaer
 * @author dpseaman
 */
public class TestGateway {
	TestPresenter presenter1;
	TestPresenter presenter2;
	TestView view1;
	TestView view2;
	Gateway gateway;
	
	/**
	 * Initializes objects for testing
	 */
	@Before
	public void setup() {
		gateway = new Gateway();
		presenter1 = new TestPresenter();
		presenter2 = new TestPresenter();
		view1 = new TestView();
		view2 = new TestView();
		
		gateway.addPresenter("presenter1", presenter1);
		gateway.addPresenter("presenter2", presenter2);
		gateway.addView("view1", view1);
		gateway.addView("view2", view2);
	}
	
	/**
	 * Tests that the presenter field and views field is set in the construction
	 * of a Gateway object
	 */
	@Test
	public void testConstructor() {
		assertNotNull(gateway.presenters);
		assertNotNull(gateway.views);
	}
	
	/**
	 * Tests that presenters are added correctly
	 */
	@Test
	public void testAddPresenter() {
		assertEquals(presenter1, gateway.presenters.get("presenter1"));
		assertEquals(presenter2, gateway.presenters.get("presenter2"));
		assertNull(gateway.presenters.get("presenter3"));
	}
	
	/**
	 * Tests that views are added correctly
	 */
	@Test
	public void testAddView() {
		assertEquals(view1, gateway.views.get("view1"));
		assertEquals(view2, gateway.views.get("view2"));
		assertNull(gateway.views.get("view3"));
	}
	
	/**
	 * Tests that data can successfully be sent to a view
	 */
	@Test
	public void testToView() {
		gateway.toView("view1", "testMethod", 2);
		assertEquals(2, view1.getNumber());
		gateway.toView("view2", "testMethod", 3);
		assertEquals(3, view2.getNumber());
		
		// Should throw exception
		gateway.toView("view3", "testMethod", 4);
		gateway.toView("view2", "otherMethod", 5);
	}
	
	/**
	 * Tests that data can successfully be sent to a presenter
	 */
	@Test
	public void testToPresenter() {
		gateway.toPresenter("presenter1", "testMethod", 1, false);
		assertEquals(1, presenter1.getNumber());
		assertEquals(false, presenter1.getBool());
		gateway.toPresenter("presenter2", "testMethod", 4, false);
		assertEquals(4, presenter2.getNumber());
		assertEquals(false, presenter2.getBool());
		
		// Should throw exception
		gateway.toPresenter("presenter3", "testMethod", 4, true);
		gateway.toPresenter("presenter2", "otherMethod", 5, false);
	}
}

package edu.wpi.cs.wpisuitetng.modules.taskmanager.view.columnar;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import edu.wpi.cs.wpisuitetng.modules.taskmanager.view.columnar.MultiColumnView;

/**
 * Tests for the multi-column view
 * @author wavanrensselaer
 */
public class TestColumnView {
	MultiColumnView multiColumnView;
	
	/**
	 * Initialize a <code>MultiColumnView</code> for testing
	 */
	@Before
	public void setup() {
		multiColumnView = new MultiColumnView();
	}
	
	/**
	 * Tests the constructor of MultiColumnView
	 */
	@Test
	public void testConstructor() {
		assertNotNull(multiColumnView.columns);
		assertNotNull(multiColumnView.container);
		assertNotNull(multiColumnView.multiColumnPanel);
		assertNotNull(multiColumnView.scrollPane);
	}
}

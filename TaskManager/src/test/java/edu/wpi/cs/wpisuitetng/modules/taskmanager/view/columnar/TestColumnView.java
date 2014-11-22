package edu.wpi.cs.wpisuitetng.modules.taskmanager.view.columnar;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import edu.wpi.cs.wpisuitetng.modules.taskmanager.model.Task;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.view.columnar.ColumnView;

/**
 * Tests for the multi-column view
 * @author wavanrensselaer
 */
public class TestColumnView {
	ColumnView multiColumnView;
	
	/**
	 * Initialize a <code>MultiColumnView</code> for testing
	 */
	@Before
	public void setup() {
		multiColumnView = new ColumnView();
	}
	
	/**
	 * Tests the constructor of MultiColumnView
	 */
	@Test
	public void testConstructor() {
	}
	
	public void testRemoveTask() {

	}
}

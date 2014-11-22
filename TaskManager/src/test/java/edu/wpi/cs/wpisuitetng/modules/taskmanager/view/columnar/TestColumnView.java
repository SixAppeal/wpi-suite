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
		assertNotNull(multiColumnView.columns);
		assertNotNull(multiColumnView.container);
		assertNotNull(multiColumnView.multiColumnPanel);
		assertNotNull(multiColumnView.scrollPane);
	}
	
	public void testRemoveTask() {
		Task t = new Task();
		multiColumnView.addTask(t);
		assertEquals(multiColumnView.columns.get(0).getTaskCount(), 1);
		t.setTitle("Testing");
		multiColumnView.removeTask(t);
		assertEquals(multiColumnView.columns.get(0).getTaskCount(), 0);
	}
}

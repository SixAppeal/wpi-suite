package edu.wpi.cs.wpisuitetng.modules.taskmanager.view.columnar;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import edu.wpi.cs.wpisuitetng.modules.taskmanager.model.Stage;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.model.StageList;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.model.Task;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.view.columnar.ColumnView;

/**
 * Tests for the ColumnView class
 * @author wavanrensselaer
 */
public class TestColumnView {
	ColumnView columnView;
	StageList stages;
	Task[] tasks;
	
	@Before
	public void setup() {
		columnView = new ColumnView();
		stages = new StageList();
		stages.add(new Stage("New"));
		stages.add(new Stage("Scheduled"));
		stages.add(new Stage("In Progress"));
		stages.add(new Stage("Complete"));
		
		tasks = new Task[] {
			new Task(),
			new Task(),
			new Task()
		};
	}

	@Test
	public void testConstructor() {
		assertNotNull(columnView.getTasks());
		assertNotNull(columnView.getStages());
		assertArrayEquals(new Task[0], columnView.getTasks());
		columnView.setStages(stages);
		assertEquals(stages, columnView.getStages());
	}
	
	@Test
	public void testSetTasks() {
		columnView.setTasks(tasks);
		assertArrayEquals(tasks, columnView.getTasks());
	}
	
	@Test
	public void testSetStages() {
		columnView.setStages(stages);
		assertEquals(stages, columnView.getStages());
	}
	
	@Test
	public void testSetState() {
		columnView.setState(tasks, stages);
		assertArrayEquals(tasks, columnView.getTasks());
		assertEquals(stages, columnView.getStages());
	}
}

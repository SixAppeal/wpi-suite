package edu.wpi.cs.wpisuitetng.modules.taskmanager.view.columnar;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import edu.wpi.cs.wpisuitetng.modules.taskmanager.model.Stage;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.model.Task;

/**
 * Runs tests on the StageView class
 * @author wavanrensselaer
 */
public class TestStageView {
	StageView stageView;
	Stage stage;
	Stage stage2;
	Task[] tasks;
	
	@Before
	public void setup() {
		stage = new Stage("New");
		stage2 = new Stage("Scheduled");
		tasks = new Task[] {
			new Task(),
			new Task(),
			new Task()
		};
		stageView = new StageView(stage, tasks);
	}
	
	@Test
	public void testConstructor() {
		assertNotNull(stageView.getStage());
		assertNotNull(stageView.getTasks());
		assertEquals(stage, stageView.getStage());
		assertArrayEquals(tasks, stageView.getTasks());
	}
	
	@Test
	public void testSetStage() {
		stageView.setStage(stage2);
		assertEquals(stage2, stageView.getStage());
	}
	
	@Test
	public void testSetTasks() {
		stageView.setTasks(new Task[0]);
		assertArrayEquals(new Task[0], stageView.getTasks());
	}
	
	@Test
	public void testSetState() {
		stageView.setState(stage, tasks);
		assertEquals(stage, stageView.getStage());
		assertArrayEquals(tasks, stageView.getTasks());
	}
}

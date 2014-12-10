package edu.wpi.cs.wpisuitetng.modules.taskmanager.view.columnar;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import edu.wpi.cs.wpisuitetng.modules.taskmanager.model.Stage;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.model.Task;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.view.columnar.StageView;


public class TestStageDragDropPanel {
	
	StageView testStageView;
	Stage stage;
	Stage stage2;
	Task[] tasks;
	
	StageDragDropPanel testStageDnDPanel;
	
	
	@Before
	public void setup(){
		stage = new Stage("New");
		stage2 = new Stage("Scheduled");
		tasks = new Task[] {
			new Task(),
			new Task(),
			new Task()
		};
		testStageView = new StageView(stage, tasks);
		testStageDnDPanel = new StageDragDropPanel(testStageView);
	}
	
	@Test
	public void testGetStageView(){
		assertEquals(testStageDnDPanel.getStageView(), testStageView);
		
	}
	
	
}

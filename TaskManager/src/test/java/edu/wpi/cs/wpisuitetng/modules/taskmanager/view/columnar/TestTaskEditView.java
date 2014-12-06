package edu.wpi.cs.wpisuitetng.modules.taskmanager.view.columnar;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import edu.wpi.cs.wpisuitetng.modules.taskmanager.model.Stage;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.model.StageList;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.model.Task;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.view.sidebar.TaskEditView;

public class TestTaskEditView {
	Task ttask;
	StageList tsl;
	Stage tstage1;
	Stage tstage2;
	TaskEditView testTEV;
	
	
	
	@Before
	public void Setup(){
		ttask = new Task();
		tstage1 = new Stage("Stage1");
		tstage2 = new Stage("Stage2");
		
		tsl = new StageList();
		tsl.add(tstage1);
		tsl.add(tstage2);
		

		
		
		
	}
	
	@Test
	public void testTEVConstructor(){
		testTEV = new TaskEditView(ttask, tsl);
		assertNotNull(testTEV);
		assertEquals(testTEV.getTask(), ttask);
		
	}
	
	
}

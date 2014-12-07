package edu.wpi.cs.wpisuitetng.modules.taskmanager.view.sidebar;

import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;

public class TestTaskDetailView {
	
	TaskDetailView tdv;
	
	
	@Before
	public void setup(){
		tdv = new TaskDetailView();
		
	}
	
	@Test
	public void testConstructor(){
		assertNotNull(tdv);
	}
}

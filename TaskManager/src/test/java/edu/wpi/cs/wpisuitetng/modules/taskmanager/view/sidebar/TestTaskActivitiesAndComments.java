package edu.wpi.cs.wpisuitetng.modules.taskmanager.view.sidebar;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class TestTaskActivitiesAndComments {

	TaskActivitiesAndComments testTaskAaC;
	
	
	@Before
	public void setup(){
		testTaskAaC = new TaskActivitiesAndComments();
		
	}
	
	@Test
	public void testConstructor(){
		assertNotNull(testTaskAaC);
	}
}

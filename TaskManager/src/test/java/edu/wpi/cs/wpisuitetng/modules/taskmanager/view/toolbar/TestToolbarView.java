package edu.wpi.cs.wpisuitetng.modules.taskmanager.view.toolbar;

import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;

public class TestToolbarView 
{

	private ToolbarView toolbarView;
	private ToolbarView invisToolbarView;
	
	@Before
	public void setup() 
	{
		toolbarView = new ToolbarView();
		invisToolbarView = new ToolbarView();
		
	}
	
	@Test
	public void testConstructor() 
	{
		assertNotNull(toolbarView);
		assertNotNull(invisToolbarView);
	}
	
	

}

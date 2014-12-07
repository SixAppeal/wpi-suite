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
		toolbarView = new ToolbarView(true);
		invisToolbarView = new ToolbarView(false);
		
	}
	
	@Test
	public void testConstructor() 
	{
		assertNotNull(toolbarView);
		assertNotNull(invisToolbarView);
		assertNotNull(toolbarView.getaddButton());
		assertNotNull(invisToolbarView.getaddButton());
	}
	
	

}

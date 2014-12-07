package edu.wpi.cs.wpisuitetng.modules.taskmanager.view.toolbar;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import edu.wpi.cs.wpisuitetng.modules.taskmanager.view.toolbar.CreateTaskButtonPanel.CreatePanelAction;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.view.toolbar.CreateTaskButtonPanel.CreateTaskAction;



public class TestCreateTaskButtonPanel 
{
	private CreateTaskButtonPanel panel;
	private CreatePanelAction panelAction;
	private CreateTaskAction taskAction;
	
	
	@Before
	public void setup() 
	{
		panel = new CreateTaskButtonPanel();
		panelAction = panel.new CreatePanelAction();
		taskAction = panel.new CreateTaskAction();
	}
	
	@Test
	public void testConstructor() 
	{
		assertNotNull(panel);
		assertNotNull(panel.getTaskButton());
		assertNotNull(panel.getPanelButton());
		assertNotNull(panelAction);
		assertNotNull(taskAction);
		
		boolean namesGoodPanelAction = false;
		boolean namesGoodTaskAction = false;
		//Make sure panel Action has it's name as Create Task
		for(Object key: panelAction.getKeys())
		{
			if(panelAction.getValue((String) key).equals("Create Panel"))
			{
				namesGoodPanelAction = true;
			}
		}
		for(Object key: taskAction.getKeys())
		{
			if(taskAction.getValue((String) key).equals("Create Task"))
			{
				namesGoodTaskAction = true;
			}
		}
		assertTrue(namesGoodPanelAction);
		assertTrue(namesGoodTaskAction);
	}
}

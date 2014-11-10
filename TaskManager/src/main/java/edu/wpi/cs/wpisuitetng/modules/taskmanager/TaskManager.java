 package edu.wpi.cs.wpisuitetng.modules.taskmanager;


import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;

import edu.wpi.cs.wpisuitetng.janeway.modules.IJanewayModule;
import edu.wpi.cs.wpisuitetng.janeway.modules.JanewayTabModel;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.toolbar.view.*;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.mainBody.view.*;


/**
 * 
 * @author SixAppeal
 *
 * A module for managing tasks within Teams
 *
 */
public class TaskManager implements IJanewayModule {

	//Access Level: Package
	String name;
	List<JanewayTabModel> tabs;
	
	ToolbarView attempt = new ToolbarView(true);
	MainBody mb_attempt = new MainBody(true);
	
	/**
	 * Constructs a TaskManager module and its tabs for the Janeway client.
	 */
	public TaskManager() {
		
		name = "Task Manager";
		tabs = new ArrayList<JanewayTabModel>();
		tabs.add(new JanewayTabModel(
				name,
				new ImageIcon(),
				attempt,
				mb_attempt
				));

		
	}
	
	/**
	 * @see edu.wpi.cs.wpisuitetng.janeway.modules.IJanewayModule#getName()
	 */
	@Override
	public String getName() {
		return name;
	}

	/**
	 * @see edu.wpi.cs.wpisuitetng.janeway.modules.IJanewayModule#getTabs()
	 */
	@Override
	public List<JanewayTabModel> getTabs() {
		return tabs;
	}

}

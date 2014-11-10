/*******************************************************************************
 * Copyright (c) 2013 WPI-Suite
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Team Rolling Thunder
 ******************************************************************************/
package edu.wpi.cs.wpisuitetng.modules.taskmanager.mainBody.view;


import java.awt.Color;
import java.awt.FlowLayout;
import javax.swing.border.LineBorder;

import edu.wpi.cs.wpisuitetng.janeway.gui.container.toolbar.DefaultToolbarView;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.mainBody.components.view.TaskHolder;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.mainBody.components.view.testTask;

/**
 * Sets up upper toolbar of RequirementManager tab
 * 
 *
 * @version $Revision: 1.0 $
 * @author justinhess
 */
@SuppressWarnings("serial")
public class MainBody  extends DefaultToolbarView {

	// Below are the default holders - need to include names for them
	// Wasnt sure what their names should be to start with. 
	private static TaskHolder default_1 = new TaskHolder("Holder One");
	private static TaskHolder default_2 = new TaskHolder("Holder Two");
	private static TaskHolder default_3 = new TaskHolder("Holder Three");
	private static TaskHolder default_4 = new TaskHolder("Holder Four");
	// Test Tasks
	private static testTask tTask_1 = new testTask("The Name", "data");
	private static testTask tTask_2 = new testTask("Meow", "data");
	private static testTask tTask_3 = new testTask("i Think it's working!", "data");
	private static testTask tTask_4 = new testTask("Much name, wow", "data");
	
	
	/**
	 * Creates and positions the task panel container in the main body panel
	 * @param visible boolean
	 */
	public MainBody(boolean visible) {
		setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		this.setLayout(new FlowLayout(FlowLayout.LEFT));
		testingSetup();
		
		this.add(default_1);
		this.add(default_2);
		this.add(default_3);
		this.add(default_4);


		
        
	}
	
	public void addPane(TaskHolder toAdd){
		this.add(toAdd);
	}
	
	private void testingSetup(){
		default_1.addTask(tTask_1);
		default_1.addTask(tTask_1);
		default_2.addTask(tTask_3);
		default_2.addTask(tTask_3);
		default_2.addTask(tTask_3);
		default_2.addTask(tTask_3);
		default_4.addTask(tTask_4);
		default_4.addTask(tTask_2);
		default_4.addTask(tTask_3);
	}



}

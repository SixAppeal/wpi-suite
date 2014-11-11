
package edu.wpi.cs.wpisuitetng.modules.taskmanager.mainBody.view;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.LineBorder;

import edu.wpi.cs.wpisuitetng.janeway.gui.container.toolbar.DefaultToolbarView;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.mainBody.view.components.TaskHolder;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.mainBody.view.components.testTask;

/**
 * Sets up upper mainbodyContent of TaskManager tab
 * 
 * @author thhughes
 * @author rwang
 */
@SuppressWarnings("serial")
public class MainBodyContent  extends JPanel {
	/* This class is used to construct the viewing portal into the taskmanager. 
	 * 		The viewing port that is constructed shows the organization of the workflow
	 * 	
	 * 	Future Edits:
	 * 		Consider making this class a singleton
	 * 		Create an organizational method to allow us to find a specific TaskHolder and add a TaskDisplay to it. 
	 * 		Create a method to add a taskHolder in between any two task holders. 
	 */
	
	/**	
	 * Below are private test values for the testing of the GUI
	 */	
	// Test TaskHolders
	private static TaskHolder default_1 = new TaskHolder("Holder One");
	private static TaskHolder default_2 = new TaskHolder("Holder Two");
	private static TaskHolder default_3 = new TaskHolder("Holder Three");
	private static TaskHolder default_4 = new TaskHolder("Holder Four");
	private static TaskHolder default_5 = new TaskHolder("numba 5");
	// Test TasksDisplayers
	private static testTask tTask_1 = new testTask("The Name", "data");
	private static testTask tTask_2 = new testTask("Meow", "data");
	private static testTask tTask_3 = new testTask("i Think it's working!", "data");
	private static testTask tTask_4 = new testTask("Much name, wow", "data");
	
	
	// Body Workflow JPanel for the Scrolling panel to be overlayed on top of. 
	private JPanel body_workflow = new JPanel();
	
	
	/**
	 * Creates and positions the task panel container in the main body panel
	 * @param visible boolean
	 */
	public MainBodyContent(boolean visible) {
		/*
		 * 	->Not currently used, though can be implemented for later renditions of multiple views of taskmanager
		 * 
		 * This class is used to display the workflow for the task manager
		 */
		
		// Set the layout for the mainbodycontent pane
		this.setLayout(new BorderLayout());
		
		/*	Below set's up the scroll panel: 
		 * 		Scroll panel works by making a scroll panel, and putting at a panel inside it (let's call that panel A). 
		 * 		Then you put things inside panel A just like you would any other panel
		 * 		Finally, add the scroll panel to the MainBodyContent pane
		 * 
		 * This will put the scroll panel on top of the mainBodyContent panel as a window into panel A
		 */
		JScrollPane body_ScollBar = new JScrollPane(body_workflow);		
		this.add(body_ScollBar, BorderLayout.CENTER);


		// Test the gui to make sure it works
		testingSetup();
		

		


		
        
	}
	/**
	 * This method creates a task holder and adds it to the workflow
	 * 
	 * @param toAdd Should Be a JPanel that is a TaskHolder
	 */
	public void addPane(TaskHolder toAdd){
		/* 
		 * Future Iteration: 
		 * 		Need some way of keeping track of all the testHolders added to the workflow
		 */
		body_workflow.add(toAdd);
	}
	
	/**
	 * Runs the test panes for the GUI
	 */
	private void testingSetup(){
		/*	This is used to test the GUI
		 * 
		 * 	Notes from testing: 
		 * 	The system will not allow the placement of the same taskHolder onto the workflow twice 
		 * 	- It will however allow the placement of multiple tasks
		 * 	-- Reasoning? The add task function creates a new instantiation of a task display from a given object
		 * 				  This means that it will be independant from the task, thus allowed to be multiply applied. 
		 * 
		 * 	NOTE: when adding a taskHolder into the workflow, it imediately adds it to the end of the workflow. 
		 * 
		 */
		
		
		
		default_1.addTask(tTask_1);
		default_1.addTask(tTask_1);
		default_2.addTask(tTask_3);
		default_2.addTask(tTask_3);
		default_2.addTask(tTask_3);
		default_2.addTask(tTask_3);
		default_4.addTask(tTask_4);
		default_4.addTask(tTask_2);
		default_4.addTask(tTask_3);
		
		default_5.addTask(tTask_1);
		default_5.addTask(tTask_1);
		default_5.addTask(tTask_3);
		default_5.addTask(tTask_3);
		default_5.addTask(tTask_3);
		default_5.addTask(tTask_3);
		default_5.addTask(tTask_4);
		default_5.addTask(tTask_2);
		default_5.addTask(tTask_3);
		
		
		this.addPane(default_1);
		this.addPane(default_2);
		this.addPane(default_3);
		this.addPane(default_4);
		this.addPane(default_5);

	}



}

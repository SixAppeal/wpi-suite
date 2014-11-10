
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
import edu.wpi.cs.wpisuitetng.modules.taskmanager.mainBody.components.view.TaskHolder;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.mainBody.components.view.testTask;

/**
 * Sets up upper toolbar of RequirementManager tab
 * 
 *
 * @version $Revision: 1.0 $
 * @author thhughes
 */
@SuppressWarnings("serial")
public class MainBody  extends DefaultToolbarView {

	// Below are the default holders - need to include names for them
	// Wasnt sure what their names should be to start with. 
	private static TaskHolder default_1 = new TaskHolder("Holder One");
	private static TaskHolder default_2 = new TaskHolder("Holder Two");
	private static TaskHolder default_3 = new TaskHolder("Holder Three");
	private static TaskHolder default_4 = new TaskHolder("Holder Four");
	private static TaskHolder default_5 = new TaskHolder("numba 5");
	// Test Tasks
	private static testTask tTask_1 = new testTask("The Name", "data");
	private static testTask tTask_2 = new testTask("Meow", "data");
	private static testTask tTask_3 = new testTask("i Think it's working!", "data");
	private static testTask tTask_4 = new testTask("Much name, wow", "data");
	
	private JPanel body_workflow = new JPanel();
	
	
	/**
	 * Creates and positions the task panel container in the main body panel
	 * @param visible boolean
	 */
	public MainBody(boolean visible) {
		/* 
		 * 
		 * 
		 * 
		 */
		
		
		
		
		this.setLayout(new BorderLayout());
		
		//JPanel body_workflow = new JPanel();
		JScrollPane body_ScollBar = new JScrollPane(body_workflow);
		
		this.add(body_ScollBar, BorderLayout.CENTER);
		
		
		//setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		//this.setLayout(new FlowLayout(FlowLayout.LEFT));
		//this.setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
		
		testingSetup();
		

		


		
        
	}
	
	public void addPane(TaskHolder toAdd){
		body_workflow.add(toAdd);
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

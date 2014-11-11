package edu.wpi.cs.wpisuitetng.modules.taskmanager.mainBody.view.components;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

/**
 * This class creates a task holder to be placed in the workflow
 * 
 * @author thhughes
 * @author rwang
 *
 */


@SuppressWarnings("serial")
public class TaskHolder extends JPanel{
	/*	This class creates the task holders
	 * 		It is also the thing to 
	 * 	
	 * 	Future Edits:
	 * 		Consider making this class a singleton
	 * 		Create an organizational method to allow us to find a specific TaskHolder and add a TaskDisplay to it. 
	 * 		Create a method to add a taskHolder in between any two task holders. 
	 */
	private static testTask tTask_1 = new testTask("The Name", "data");
	private static testTask tTask_2 = new testTask("two Name", "data");
	
	private JPanel body = new JPanel();
	
	public TaskHolder(String holderTitle_data){
		
		this.setPreferredSize(new Dimension(300, 600));
	    setBorder(new LineBorder(new Color(0, 0, 0), 3, true));
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		
		// Set up the title Pane
		JPanel title = new JPanel();
		title.setLayout(new BoxLayout(title, BoxLayout.X_AXIS));
		JLabel holderTitle = new JLabel(holderTitle_data);
		holderTitle.setFont(new Font("Lucida Grande", Font.BOLD, 20));
		title.add(holderTitle);
		
		
		// Set up the body pane
		body.setLayout(new BoxLayout(body, BoxLayout.Y_AXIS));

		// Add the panes to the Main Holder
		this.add(title);
		this.add(body);
		



		// Run Testing Setup - working - (thhughes, 11/10/2014)
		//testingSetup();
		
	}
	
	public void addTask(testTask toAdd){
		if(this.getNumTasks() == 0){
			//body.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		}
		
		TaskDisplay newTask = new TaskDisplay(toAdd);
		body.add(newTask);
	}
	
	
	private void testingSetup(){
		this.addTask(tTask_1);
		this.addTask(tTask_2);
		
	}
	public int getNumTasks(){
		return body.getComponentCount();
	}

}

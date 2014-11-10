package edu.wpi.cs.wpisuitetng.modules.taskmanager.mainBody.components.view;

import javax.swing.JPanel;
import javax.swing.BoxLayout;
import javax.swing.JLabel;


import javax.swing.border.LineBorder;

import java.awt.Color;
import java.awt.Font;

@SuppressWarnings("serial")
public class TaskDisplay extends JPanel {
	// Takes in the task that it will hold 
	// TODO Make method to make tasks (or they are incorporated into the constructor?)
	testTask tTask = new testTask("TestTaskName", "TestTaskData");
	String nameField = new String("Name");
	
	
	public TaskDisplay(testTask tTask){
		this.tTask = tTask;
		
		// Set the Border of the panel 
		setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
	
		// Set the layout of this pannel
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		
		// Configure the namefield of the of the task for displaying
		JLabel nameLabel_field = new JLabel(nameField + ":");
		nameLabel_field.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
		// Place the data for the name
		JLabel nameLabel_data = new JLabel(tTask.getName());
		nameLabel_data.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
		
		// Push everthing to the panel
		add(nameLabel_field);
		add(nameLabel_data);

		
	}
	
	

}

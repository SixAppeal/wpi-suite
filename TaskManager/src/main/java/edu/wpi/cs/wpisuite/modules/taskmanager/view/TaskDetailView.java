package edu.wpi.cs.wpisuite.modules.taskmanager.view;

import java.awt.Color;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 * 
 * @author tmeehan
 * @author wmtemple
 * 
 * A view for Task Details
 *
 */

class TaskModel{}

public class TaskDetailView extends JPanel {

	/**
	 * Eclipse generated Serial Version UID
	 */
	private static final long serialVersionUID = -3161594397243801607L;

	private TaskModel task;
	JLabel idLabel;
	
	public TaskDetailView(TaskModel task){
		
		this.task = task;
		
		//Prep JPanel with formatting
		this.setBorder(BorderFactory.createTitledBorder("Task Detail"));
		this.setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
		
		//Start sticking contents in Panel
		
		idLabel = new JLabel("Task ID: " + task.id.toString() + " ");
		idLabel.setAlignmentX(RIGHT_ALIGNMENT);
		idLabel.setForeground(new Color(155, 155, 155));
		
		this.add(idLabel);
		//this.add(new JLabel("Title", SwingConstants.LEFT));
		
	}
	
	
}

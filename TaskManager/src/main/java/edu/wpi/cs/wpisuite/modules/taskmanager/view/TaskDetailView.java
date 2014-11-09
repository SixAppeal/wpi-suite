package edu.wpi.cs.wpisuite.modules.taskmanager.view;

import java.awt.Color;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import edu.wpi.cs.wpisuitetng.modules.taskmanager.model.Task;

/**
 * 
 * @author tmeehan
 * @author wmtemple
 * 
 * A view for Task Details
 *
 */

@SuppressWarnings("unused")
public class TaskDetailView extends JPanel {

	/**
	 * Eclipse generated Serial Version UID
	 */
	private static final long serialVersionUID = -3161594397243801607L;

	private Task task;
	JLabel idLabel;
	JLabel nameLabel;
	JLabel dateLabel;
	JLabel desLabel;
	JLabel statLabel;
	JLabel estLabel;
	JLabel actLabel;
	
	public TaskDetailView(Task task){
		
		this.task = task;
		
		//Prep JPanel with formatting
		this.setBorder(BorderFactory.createTitledBorder("Task Detail"));
		this.setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
		
		//Start sticking contents in Panel
		
		
		idLabel = new JLabel("Task ID: " + task.getId());
		idLabel.setAlignmentX(RIGHT_ALIGNMENT);
		idLabel.setForeground(new Color(155, 155, 155));
		
		this.add(idLabel);
		
		nameLabel = new JLabel("Task name: " + task.getTitle());
		nameLabel.setAlignmentX(LEFT_ALIGNMENT);
		nameLabel.setForeground(new Color(0, 0, 0));
		
		this.add(nameLabel);
		
		dateLabel = new JLabel ("Task due date: " + task.getDueDate().toString());
		dateLabel.setAlignmentX(LEFT_ALIGNMENT);
		dateLabel.setForeground(new Color(0, 0, 0));
		
		this.add(dateLabel);
		
		desLabel = new JLabel ("Task description: " + task.getDescription());
		desLabel.setAlignmentX(LEFT_ALIGNMENT);
		desLabel.setForeground(new Color (0, 0, 0));
		
		this.add(desLabel);
		
		statLabel = new JLabel ("Task status: " + task.getStatus().toString());
		statLabel.setAlignmentX(LEFT_ALIGNMENT);
		statLabel.setForeground(new Color (0, 0, 0));
		
		this.add(statLabel);
		
		estLabel = new JLabel ("Estimated effort: " + task.getEstimatedEffort().toString());
		estLabel.setAlignmentX(LEFT_ALIGNMENT);
		estLabel.setForeground(new Color (0, 0, 0));
		
		this.add(estLabel);
		
		actLabel = new JLabel ("Actual effort: " + task.getActualEffort().toString());
		actLabel.setAlignmentX(LEFT_ALIGNMENT);
		actLabel.setForeground(new Color (0, 0, 0));
		
		this.add(actLabel);

		//this.add(new JLabel("Title", SwingConstants.LEFT));
		
	}
	
	
}

package edu.wpi.cs.wpisuitetng.modules.taskmanager.view;

import java.awt.GridLayout;
import java.util.Date;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.BorderFactory;

import edu.wpi.cs.wpisuitetng.modules.taskmanager.model.TaskStatus;

/**
 * 
 * @author wmtemple
 *
 * A view to be displayed when creating or modifying a task object in the GUI.
 *
 */
public class TaskEditView extends JPanel {

	/**
	 * Eclipse-generated Serial Version UID
	 */
	private static final long serialVersionUID = -8331650108561001757L;
	
	JTextField titleEntry;
	JTextField descEntry;
	JComboBox<TaskStatus> statusBox;
	JSpinner estEffortSpinner; 
	//JDatePicker dueDate;
	
	/**
	 * Create a new TaskEditView, and assume that it is NOT for a new task.
	 */
	public TaskEditView () {
		this(false);
	}
	
	/**
	 * Create a new Task Edit View
	 * 
	 * @param isNewTask Defines whether or not the task being edited is a new task (cosmetic)
	 */
	public TaskEditView (boolean isNewTask) {
		
		String paneTitle = isNewTask?"New Task":"Edit Task";
		
		// This layout will give us a 2 wide grid with n rows
		GridLayout layout = new GridLayout(0,2);
		
		this.setBorder(BorderFactory.createTitledBorder(paneTitle));
		this.setLayout(layout);
		
		JLabel titleLabel = new JLabel("Title :");
		titleLabel.setAlignmentX(RIGHT_ALIGNMENT);
		JLabel descLabel = new JLabel("Description :");
		descLabel.setAlignmentX(RIGHT_ALIGNMENT);
		
		titleEntry = new JTextField();
		descEntry = new JTextField();
		
		this.add( titleLabel );
		this.add( titleEntry );
		this.add( descLabel );
		this.add( descEntry );
		
	}
}

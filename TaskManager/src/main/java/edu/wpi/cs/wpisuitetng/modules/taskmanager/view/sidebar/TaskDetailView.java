package edu.wpi.cs.wpisuitetng.modules.taskmanager.view.sidebar;

import java.awt.Color;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import net.miginfocom.swing.MigLayout;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.model.Task;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.presenter.Gateway;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.view.IView;

/**
 * 
 * @author tmeehan
 * @author wmtemple
 * @author krpeffer
 * 
 * A view for Task Details
 *
 */

@SuppressWarnings("unused")
public class TaskDetailView extends JPanel implements IView {


	/**
	 * Generated Serial Number
	 */
	private static final long serialVersionUID = -8972626054612267276L;

	private Gateway gateway;
	
	/**
	 * Declare all the JLabels the detail view will need
	 */
	JLabel idLabel;
	JLabel taskIdLabel;			//Displays the Task's ID
	
	JLabel nameLabel;
	JLabel taskNameLabel;		//Displays the Task's Title
	
	JLabel dateLabel;
	JLabel taskDateLabel;		//Displays the Task's Due Date
	
	JLabel desLabel;
	JLabel taskDesLabel;		//Displays the Task's Description
	
	JLabel statLabel;
	JLabel taskStatLabel;		//Displays the Task's Status
	
	JLabel estLabel;
	JLabel taskEstLabel;		//Displays the Task's Estimated Effort value
	
	JLabel actLabel;
	JLabel taskActLabel;		//Displays the Task's Actual Effor Value
	
	JLabel assignedToLabel;
	JLabel taskAssignedToLabel;	//Displays the Task's Assigned Members in a single string
	
	public TaskDetailView() {
		
		String paneTitle = "Task Detail";
		
		/**
		 * Declares the MiGLayout
		 * "wrap 2" Says the layout can only have 2 columns
		 * [right] The right column's size grows as needed
		 * [left, 100::, grow] The left column has a base size of 100, but can grow if needed
		 * "" Row settings are left empty for our purpose
		 */
		MigLayout detailView = new MigLayout(
				"wrap 2",
				"[right] [left, 100::, grow]",
				"");
		
		/**
		 * Creates the pane border and title
		 * Sets the current layout to the previously declared MiGLayout
		 */
		this.setBorder(BorderFactory.createTitledBorder(paneTitle));
		this.setLayout(detailView);
		this.setOpaque(false);
		
		/**
		 * creates all the JLabels for the given task
		 */
		idLabel = new JLabel("Task ID: ");
		taskIdLabel = new JLabel();				
		
		nameLabel = new JLabel("Task name:");
		taskNameLabel = new JLabel();						
		
		dateLabel = new JLabel ("Task due date:");
		taskDateLabel = new JLabel ();			
		
		desLabel = new JLabel ("Task Description:");
		taskDesLabel = new JLabel ();					
		
		statLabel = new JLabel ("Task Status:");
		taskStatLabel = new JLabel();			
		
		estLabel = new JLabel ("Estimated Effort:");
		taskEstLabel = new JLabel();
		
		actLabel = new JLabel ("Actual Effort:");
		taskActLabel = new JLabel ();
		
		assignedToLabel = new JLabel("Members Assigned:");
		taskAssignedToLabel = new JLabel();
		
		/**
		 * Adds all the JLabels to the MiGLayout
		 */
		this.add(idLabel);
		this.add(taskIdLabel);
		
		this.add(nameLabel);
		this.add(taskNameLabel);
		
		this.add(dateLabel);
		this.add(taskDateLabel);
		
		this.add(desLabel);
		this.add(taskDesLabel);
		
		this.add(statLabel);
		this.add(taskStatLabel);
		
		this.add(estLabel);
		this.add(taskEstLabel);
		
		this.add(actLabel);
		this.add(taskActLabel);
		
		this.add(assignedToLabel);
		this.add(taskAssignedToLabel);	
	}
	
	/**
	 * Update the view to display the info for a different task
	 * @param t The new task to display.
	 */
	public void updateView( Task t ) {
		taskIdLabel.setText(String.valueOf(t.getId()));
		taskNameLabel.setText(t.getTitle());
		taskDateLabel.setText(t.getDueDate().toString());
		taskDesLabel.setText(t.getDescription());
		taskStatLabel.setText(t.getStatus().toString());
		taskEstLabel.setText(t.getEstimatedEffort().toString());
		taskActLabel.setText(t.getActualEffort().toString());
		taskAssignedToLabel.setText(t.getMemberList());
		this.revalidate();
	}

	/**
	 * @see IView.setGateway
	 * @param gateway
	 */
	@Override
	public void setGateway(Gateway gateway) {
		this.gateway = gateway;
	}
	
}

package edu.wpi.cs.wpisuitetng.modules.taskmanager.view.sidebar;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import edu.wpi.cs.wpisuitetng.modules.taskmanager.model.Task;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.presenter.Gateway;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.view.IView;

/**
 * 
 * @author tmeehan
 * @author wmtemple
 * @author krpeffer
 * @author wavanrensselaer
 * 
 * A view for Task Details
 *
 */
public class TaskDetailView extends JPanel implements IView {


	/**
	 * Generated Serial Number
	 */
	private static final long serialVersionUID = -8972626054612267276L;

	private Gateway gateway;
	
	private Task t;
	
	/**
	 * Declare all the JLabels the detail view will need
	 */
	JLabel taskNameLabel;		//Displays the Task's Title

	JLabel descLabel;
	JLabel taskDescLabel;		//Displays the Task's Description
	
	JLabel dateLabel;
	JLabel taskDateLabel;		//Displays the Task's Due Date

	JLabel estLabel;
	JLabel taskEstLabel;		//Displays the Task's Estimated Effort value
	
	JLabel actLabel;
	JLabel taskActLabel;		//Displays the Task's Actual Effor Value
	
	JButton archiveButton; //Archives the task.
	JButton editButton; //Opens the task for editing.
	
	public TaskDetailView() {
		Color labelColor = new Color(160, 160, 160);
		
		taskNameLabel = new JLabel("", JLabel.CENTER);

		descLabel = new JLabel("Description");
		descLabel.setForeground(labelColor);
		taskDescLabel = new JLabel();

		dateLabel = new JLabel("Due Date");
		dateLabel.setForeground(labelColor);
		taskDateLabel = new JLabel();
		
		estLabel = new JLabel ("Est. Effort");
		estLabel.setForeground(labelColor);
		taskEstLabel = new JLabel();
		
		actLabel = new JLabel ("Act. Effort");
		actLabel.setForeground(labelColor);
		taskActLabel = new JLabel();
		
		archiveButton = new JButton("Archive");
		archiveButton.addActionListener( new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				archiveTask();
			}
		});
		
		editButton = new JButton("Edit");
		editButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				editTask();
			}
		});
		editButton.setEnabled(false);
		
		this.setLayout(new GridBagLayout());
		this.setOpaque(false);
		
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.anchor = GridBagConstraints.PAGE_START;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.weightx = 1.0;
		gbc.insets = new Insets(20, 20, 0, 20);
		gbc.gridwidth = 2;
		gbc.gridx = 0;
		gbc.gridy = 0;
		this.add(taskNameLabel, gbc);
		
		gbc.gridy = 1;
		this.add(descLabel, gbc);
		
		gbc.insets.top = 5;
		gbc.gridy = 2;
		this.add(taskDescLabel, gbc);
		
		gbc.insets.top = 20;
		gbc.gridy = 3;
		this.add(dateLabel, gbc);
		
		gbc.insets.top = 5;
		gbc.gridy = 4;
		this.add(taskDateLabel, gbc);
		
		gbc.insets.top = 20;
		gbc.gridwidth = 1;
		gbc.gridy = 5;
		this.add(estLabel, gbc);
		
		gbc.gridx = 1;
		this.add(actLabel, gbc);
		
		gbc.insets.top = 5;
		gbc.gridx = 0;
		gbc.gridy = 6;
		this.add(taskEstLabel, gbc);
		
		gbc.gridx = 1;
		this.add(taskActLabel, gbc);
		
		gbc.anchor = GridBagConstraints.FIRST_LINE_START;
		gbc.fill = GridBagConstraints.NONE;
		gbc.insets.top = 20;
		gbc.weighty = 1.0;
		gbc.gridx = 0;
		gbc.gridy = 7;
		this.add(archiveButton, gbc);
		
		gbc.anchor = GridBagConstraints.FIRST_LINE_END;
		//gbc.gridwidth = 2;
		gbc.gridx = 1;
		this.add(editButton, gbc);
	}
	
	/**
	 * Update the view to display the info for a different task
	 * @param t The new task to display.
	 */
	public void updateView( Task t ) {
		
		this.t = t;
		
		taskNameLabel.setText("<html>" + this.t.getTitle() + "</html>");
		taskDescLabel.setText("<html>" + this.t.getDescription() + "</html>");
		taskDateLabel.setText("<html>" + this.t.getDueDate().toString() + "</html>");
		taskEstLabel.setText(this.t.getEstimatedEffort().toString());
		taskActLabel.setText(this.t.getActualEffort().toString());
		editButton.setEnabled(true);
		this.revalidate();
	}
	
	/**
	 * Opens a task for editing in the parent view
	 */
	public void editTask() {
		this.gateway.toPresenter("TaskPresenter", "editTask", t);
	}
	
	/**
	 * Archives the task in the presenter.
	 */
	public void archiveTask() {
		this.gateway.toPresenter("TaskPresenter", "archiveTask", t);
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

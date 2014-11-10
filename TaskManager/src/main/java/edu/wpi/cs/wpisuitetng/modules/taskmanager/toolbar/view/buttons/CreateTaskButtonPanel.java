package edu.wpi.cs.wpisuitetng.modules.taskmanager.toolbar.view.buttons;


import java.awt.Dimension;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.SpringLayout;
import javax.swing.SwingConstants;

import edu.wpi.cs.wpisuitetng.janeway.gui.container.toolbar.ToolbarGroupView;
//import edu.wpi.cs.wpisuitetng.modules.taskmanager.toolbar.view.TestToolbar.SwingAction;
//import edu.wpi.cs.wpisuitetng.modules.taskmanager.toolbar.view.TestToolbar.SwingAction_1;


/**
 * @author 
 * @author troyhughes
 * @author Raywang
 * 
 * @author justinhess
 * @version $Revision: 1.0 $
 */
@SuppressWarnings("serial")
public class CreateTaskButtonPanel extends ToolbarGroupView {
	
	// Initialize the buttons for the toolbar
	private JButton createTaskButton = new JButton("<html>Create<br />Task</html>");
	private JButton createPanelButton = new JButton("<html>Create<br />Task</html>");
	// Initialize the actions for the buttons
	private final Action createTask = new CreateTaskAction();
	private final Action createPanel = new CreatePanelAction();
	// Initialize the panel for everything to be held
	private final JPanel contentPanel = new JPanel();
	
	public CreateTaskButtonPanel() {
		super(""); //TODO What is this??? I'm so confused. Is this dead code?
					// Yes, the code is dead :P
		SpringLayout springLayout = new SpringLayout();
		
		// Define the size and layout of the content panel
		//this.contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.X_AXIS));
		this.contentPanel.setLayout(springLayout);
		this.setPreferredWidth(570);
		
		// Set the alignment of the First button (task button)
		//this.createTaskButton.setHorizontalAlignment(SwingConstants.CENTER);
		springLayout.putConstraint(SpringLayout.NORTH, createTaskButton, 10, SpringLayout.NORTH, this.contentPanel);
		springLayout.putConstraint(SpringLayout.WEST, createTaskButton, 10, SpringLayout.WEST, this.contentPanel);
		springLayout.putConstraint(SpringLayout.SOUTH, createTaskButton, -10, SpringLayout.SOUTH, this.contentPanel);
		// Set the width of createTaskButton
		Dimension d_task = createTaskButton.getPreferredSize();
		d_task.width = 250;
		createTaskButton.setPreferredSize(d_task); 
		
		
		
		// Layout of the create Panel Button
		springLayout.putConstraint(SpringLayout.NORTH, createPanelButton, 10, SpringLayout.NORTH, this.contentPanel);
		springLayout.putConstraint(SpringLayout.WEST, createPanelButton, 10, SpringLayout.EAST, createTaskButton);
		springLayout.putConstraint(SpringLayout.SOUTH, createPanelButton, -10, SpringLayout.SOUTH, this.contentPanel);
//		springLayout.putConstraint(SpringLayout.EAST, createPanelButton, 40, SpringLayout.EAST, this.contentPanel);
		
		createPanelButton.setPreferredSize(d_task); 
		
		
		// the action listener for the Create Requirement Button
		createTaskButton.setAction(createTask);
		//action listener for the Create Iteration Button
		createPanelButton.setAction(createPanel);
		
		
		contentPanel.add(createTaskButton);
		contentPanel.add(createPanelButton);
		// set opaque is for the weird gradient toggle thing that it does when you hover over the pannel that the buttons are on
		contentPanel.setOpaque(false);
		// Put everything above on the pannel
		this.add(contentPanel);
	}
	
	/**
	 * Method getCreateButton.
	 * 
	 * @return JButton
	 */
	public JButton getPanelButton() {
		return createPanelButton;
	}
	
	/**
	 * Method getCreateIterationButton.
	 * 
	 * @return JButton
	 */
	public JButton getTaskButton() {
		return createTaskButton;
	}
	
	/**
	 * Called when the mouse enters this toolbar group
	 * 
	 * Delete this override to get the hover effects back
	 */
	@Override
	public void mouseEntered() {

	}

	/**
	 * Called when the mouse exits this toolbar group
	 * 
	 * Delete this override to get the hover effects back
	 */
	@Override
	public void mouseExited() {

	}
	
	
	
	
	
	
	private class CreateTaskAction extends AbstractAction {
		/**
		 * @author troyhughes
		 * This is an action listener for the button Create Task - this should communicate with the 
		 * controller to make new tasks etc. 
		 */
		private static final long serialVersionUID = 2L;
		private boolean decision = false;
		
		public CreateTaskAction() {
			putValue(NAME, "Create Task");
			putValue(SHORT_DESCRIPTION, "FUCK YEA, I'M HOVER TEXT");
		}
		public void actionPerformed(ActionEvent e) {
			if(decision){
				putValue(NAME, "Action Occured!");
				decision = !decision;
			}
			else {
				putValue(NAME, "Occurance Two!");
				decision = !decision;
			}
		}
	}
	private class CreatePanelAction extends AbstractAction {
		/**
		 * @author troyhughes
		 * This is an action listener for the button Create panel - this should communicate with the 
		 * controller to make new panels that tasks will be stored on. 
		 */
		private static final long serialVersionUID = 2L;
		private boolean decision = false;
		
		public CreatePanelAction() {
			putValue(NAME, "Create Panel");
			putValue(SHORT_DESCRIPTION, "and I'm not :(");
		}
		public void actionPerformed(ActionEvent e) {
			if(decision){
				putValue(NAME, "Action Occured!");
				decision = !decision;
			}
			else {
				putValue(NAME, "Occurance Two!");
				decision = !decision;
			}
		}
	}
	
}

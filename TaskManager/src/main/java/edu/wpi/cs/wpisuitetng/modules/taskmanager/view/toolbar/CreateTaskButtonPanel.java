package edu.wpi.cs.wpisuitetng.modules.taskmanager.view.toolbar;


import java.awt.Dimension;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.SpringLayout;

import edu.wpi.cs.wpisuitetng.janeway.gui.container.toolbar.ToolbarGroupView;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.presenter.Gateway;


/**
 * @author 
 * @author thhughes
 * @author rwang3
 * 
 * @author justinhess
 * @version $Revision: 1.0 $
 */
@SuppressWarnings("serial")
/**
*
* Creates a panel that holds buttons for the toolbar. In this specfic panel
* there are two button: Create Task, and Create Panel
*
*/
public class CreateTaskButtonPanel extends ToolbarGroupView {
	private JButton createTaskButton = new JButton("<html>Create<br />Task<html>");
	private JButton createPanelButton = new JButton("<html>Create<br />Panel<html>");
	private final Action createTask = new CreateTaskAction();
	private final Action createPanel = new CreatePanelAction();
	private final JPanel contentPanel = new JPanel();
	private Gateway gateway;

	/** 
	 * @see ToolbarGroupView.ToolbarGroupView
	 */
	public CreateTaskButtonPanel(){
		super("");
		SpringLayout springLayout = new SpringLayout();
		
		this.contentPanel.setLayout(springLayout);
		this.setPreferredWidth(570);

		// Set the alignment of the First button (task button)
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
		
		createPanelButton.setPreferredSize(d_task); 

		// Action listener for createTaskButton
		createTaskButton.setAction(createTask);
		// Action listener for createPanelButton
		createPanelButton.setAction(createPanel);

		contentPanel.add(createTaskButton);
		//uncomment the line below once the controller can handle creating multiple columns
		//contentPanel.add(createPanelButton);
		contentPanel.setOpaque(false);
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
	 * @Override - is suppressing parent class functionality
	 * 
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

	/**
	*
	* @author thhughes
	* @author rwang3
	* This is an action listener for the Create Panel button.
	* This class communicates with the controller that makes new tasks 
	* to be displayed in the column view
	*
	**/
	public class CreatePanelAction extends AbstractAction {

		public CreatePanelAction(){
			putValue(NAME, "Create Panel");
		}

		public void actionPerformed(ActionEvent e) {
			gateway.toPresenter("Panel", "panelCreate");
		}
	}

	/**
	*
	* @author thhughes
	* @author rwang3
	* This is an action listener for the Create Panel button.
	* This class communicates with the controller that makes new tasks 
	* to be displayed in the column view
	*
	**/
	public class CreateTaskAction extends AbstractAction {

		public CreateTaskAction(){
			putValue(NAME, "Create Task");
		}

		public void actionPerformed(ActionEvent e) {
			gateway.toPresenter("Task", "taskCreate");
		}
	}
}

package edu.wpi.cs.wpisuitetng.modules.taskmanager.view.toolbar;


import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JPanel;

import edu.wpi.cs.wpisuitetng.janeway.gui.container.toolbar.ToolbarGroupView;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.presenter.Gateway;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.view.IView;


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
public class CreateTaskButtonPanel extends ToolbarGroupView implements IView {
	private JButton createTaskButton = new JButton("<html>Create<br />Task</html>");
	private JButton createPanelButton = new JButton("<html>Create<br />Panel</html>");
	private final Action createTask = new CreateTaskAction();
	private final Action createPanel = new CreatePanelAction();
	private final JPanel contentPanel = new JPanel();
	private Gateway gateway;

	/** 
	 * @see ToolbarGroupView.ToolbarGroupView
	 */
	public CreateTaskButtonPanel(){
		super("");
		
		this.contentPanel.setLayout(new GridBagLayout());
		
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.weighty = 1.0;
		gbc.anchor = GridBagConstraints.LINE_START;

		// Action listener for createTaskButton
		createTaskButton.setAction(createTask);
		
		
		// Action listener for createPanelButton
		createPanelButton.setAction(createPanel);

		contentPanel.add(createTaskButton, gbc);
		//uncomment the line below once the controller can handle creating multiple columns
		//contentPanel.add(createPanelButton);
		//contentPanel.add(refreshPanelButton);
		contentPanel.setOpaque(false);
		this.add(contentPanel);
	}


	/**
	 * returns the panel create button
	 * 
	 * @return JButton
	 */
	public JButton getPanelButton() {
		return createPanelButton;
	}

	/**
	 * returns the task create button
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
			gateway.toPresenter("TaskPresenter", "toolbarCreate");
		}
	}

	/**
	 * @see IView.setGateway
	 */
	@Override
	public void setGateway(Gateway gateway) {
		this.gateway = gateway;
	}
}

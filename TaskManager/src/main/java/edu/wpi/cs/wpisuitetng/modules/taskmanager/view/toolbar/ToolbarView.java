package edu.wpi.cs.wpisuitetng.modules.taskmanager.view.toolbar;


import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;

import edu.wpi.cs.wpisuitetng.janeway.gui.container.toolbar.DefaultToolbarView;
import edu.wpi.cs.wpisuitetng.janeway.gui.container.toolbar.ToolbarGroupView;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.presenter.Gateway;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.view.IView;

/**
 * Sets up upper toolbar of RequirementManager tab
 * 
 *
 * @version $Revision: 1.0 $
 * @author thhughes
 * @author rwang
 */
@SuppressWarnings("serial")
/**
 * @see DefaultToolbarView
 * @author thhughes
 * @author rwang3
 *
 */
public class ToolbarView extends DefaultToolbarView implements IView {

	public CreateTaskButtonPanel addButtons = new CreateTaskButtonPanel();
	private Gateway gateway;
	
	/**
	 * Constructs a ToolbarView object with an option that togles visibility
	 * @param visible boolean
	 */
	public ToolbarView(boolean visible) {

		this.addGroup(addButtons);

	}
	

	/**
	 * Method getReqButton.
	
	 * @return RequirementButtonsPanel */
	public CreateTaskButtonPanel getaddButton() {
		return addButtons;
	}

	/**
	 * @see IView.setGateway
	 */
	@Override
	public void setGateway(Gateway gateway) {
		this.gateway = gateway;	
	}
	

	
	
	
}

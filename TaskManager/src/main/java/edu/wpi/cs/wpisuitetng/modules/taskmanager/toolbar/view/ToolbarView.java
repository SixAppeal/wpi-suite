package edu.wpi.cs.wpisuitetng.modules.taskmanager.toolbar.view;


import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;

import edu.wpi.cs.wpisuitetng.janeway.gui.container.toolbar.DefaultToolbarView;
import edu.wpi.cs.wpisuitetng.janeway.gui.container.toolbar.ToolbarGroupView;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.toolbar.view.buttons.CreateTaskButtonPanel;

/**
 * Sets up upper toolbar of RequirementManager tab
 * 
 *
 * @version $Revision: 1.0 $
 * @author thhughes
 * @author rwang
 */
public class ToolbarView extends DefaultToolbarView {

	public CreateTaskButtonPanel addButtons = new CreateTaskButtonPanel();
	
	/**
	 * Creates and positions option buttons in upper toolbar
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
	

	
	
	
}

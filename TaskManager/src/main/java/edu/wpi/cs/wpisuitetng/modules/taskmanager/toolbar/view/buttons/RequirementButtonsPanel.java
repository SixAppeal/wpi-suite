/*******************************************************************************
 * Copyright (c) 2013 WPI-Suite
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * Contributors: Team Rolling Thunder
 ******************************************************************************/
package edu.wpi.cs.wpisuitetng.modules.taskmanager.toolbar.view.buttons;


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
 * @author justinhess
 * @version $Revision: 1.0 $
 */
@SuppressWarnings("serial")
public class RequirementButtonsPanel extends ToolbarGroupView {
	
	// Initialize the buttons for the toolbar
	private JButton createTaskButton = new JButton("<html>Create<br />Task</html>");
	private JButton createPanelButton = new JButton("<html>Create<br />Task</html>");
	// Initialize the actions for the buttons
	private final Action createTask = new CreateTaskAction();
	private final Action createPanel = new CreatePanelAction();
	// Initialize the panel for everything to be held
	private final JPanel contentPanel = new JPanel();
	
	public RequirementButtonsPanel() {
		super(""); //TODO What is this??? I'm so confused. Is this dead code?
		SpringLayout springLayout = new SpringLayout();
		
		// Define the size and layout of the content panel
		//this.contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.X_AXIS));
		this.contentPanel.setLayout(springLayout);
		this.setPreferredWidth(700);
		
		// Set the alignment of the First button (task button)
		//this.createTaskButton.setHorizontalAlignment(SwingConstants.CENTER);
		springLayout.putConstraint(SpringLayout.NORTH, createTaskButton, 10, SpringLayout.NORTH, this.contentPanel);
		springLayout.putConstraint(SpringLayout.WEST, createTaskButton, 10, SpringLayout.WEST, this.contentPanel);
		springLayout.putConstraint(SpringLayout.SOUTH, createTaskButton, 10, SpringLayout.SOUTH, this.contentPanel);
		
		springLayout.putConstraint(SpringLayout.NORTH, createPanelButton, 10, SpringLayout.NORTH, this.contentPanel);
		springLayout.putConstraint(SpringLayout.WEST, createPanelButton, 10, SpringLayout.EAST, createTaskButton);
		springLayout.putConstraint(SpringLayout.SOUTH, createPanelButton, 10, SpringLayout.SOUTH, this.contentPanel);
		
		// the action listener for the Create Requirement Button
		createTaskButton.setAction(createTask);
		//action listener for the Create Iteration Button
		createPanelButton.setAction(createPanel);
		
		
		contentPanel.add(createTaskButton);
		contentPanel.add(createPanelButton);
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
	
	private class CreateTaskAction extends AbstractAction {
		/**
		 * 
		 */
		private static final long serialVersionUID = 2L;
		private boolean decision = false;
		
		public CreateTaskAction() {
			putValue(NAME, "Create Task");
			putValue(SHORT_DESCRIPTION, "Some short description");
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
		 * 
		 */
		private static final long serialVersionUID = 2L;
		private boolean decision = false;
		
		public CreatePanelAction() {
			putValue(NAME, "Create Panel");
			putValue(SHORT_DESCRIPTION, "Some short description");
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

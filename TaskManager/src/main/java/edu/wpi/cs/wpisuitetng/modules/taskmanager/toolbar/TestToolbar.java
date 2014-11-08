package edu.wpi.cs.wpisuitetng.modules.taskmanager.toolbar;

import javax.swing.JPanel;
import javax.swing.SpringLayout;
import javax.swing.JButton;
import java.awt.TextField;
import java.awt.Color;
import java.awt.Component;
import javax.swing.Box;
import javax.swing.AbstractAction;
import java.awt.event.ActionEvent;
import javax.swing.Action;

public class TestToolbar extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private final Action action = new SwingAction();

	/**
	 * Create the panel.
	 */
	public TestToolbar() {
		SpringLayout springLayout = new SpringLayout();
		setLayout(springLayout);
		
		JButton btnForgeTask = new JButton("Forge Task");
		springLayout.putConstraint(SpringLayout.NORTH, btnForgeTask, 10, SpringLayout.NORTH, this);
		springLayout.putConstraint(SpringLayout.WEST, btnForgeTask, 54, SpringLayout.WEST, this);
		springLayout.putConstraint(SpringLayout.SOUTH, btnForgeTask, 62, SpringLayout.NORTH, this);
		btnForgeTask.setAction(action);
		add(btnForgeTask);
		
		TextField textField = new TextField();
		springLayout.putConstraint(SpringLayout.NORTH, textField, 0, SpringLayout.NORTH, btnForgeTask);
		springLayout.putConstraint(SpringLayout.SOUTH, textField, -238, SpringLayout.SOUTH, this);
		springLayout.putConstraint(SpringLayout.EAST, textField, -10, SpringLayout.EAST, this);
		textField.setBackground(Color.LIGHT_GRAY);
		textField.setForeground(Color.LIGHT_GRAY);
		textField.setFont(null);
		add(textField);
		
		Component glue = Box.createGlue();
		springLayout.putConstraint(SpringLayout.WEST, textField, 69, SpringLayout.EAST, glue);
		springLayout.putConstraint(SpringLayout.WEST, glue, 199, SpringLayout.WEST, this);
		springLayout.putConstraint(SpringLayout.EAST, btnForgeTask, 0, SpringLayout.EAST, glue);
		springLayout.putConstraint(SpringLayout.NORTH, glue, 135, SpringLayout.NORTH, this);
		add(glue);
		
		Component glue_1 = Box.createGlue();
		add(glue_1);
		
		Component glue_2 = Box.createGlue();
		add(glue_2);
		
		Component glue_3 = Box.createGlue();
		add(glue_3);

	}
	private class SwingAction extends AbstractAction {
		/**
		 * 
		 */
		private static final long serialVersionUID = 2L;
		private boolean decision = false;
		
		public SwingAction() {
			putValue(NAME, "SwingAction");
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

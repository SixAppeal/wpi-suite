package edu.wpi.cs.wpisuitetng.modules.taskmanager.toolbar.view;

import javax.swing.BorderFactory;
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
	private final Action action_1 = new SwingAction_1();

	/**
	 * Create the panel.
	 */
	public TestToolbar() {
		SpringLayout springLayout = new SpringLayout();
		setLayout(springLayout);
		setBorder(BorderFactory.createLineBorder(Color.black));
		
		JButton btnForgeTask = new JButton("Forge Task");
		springLayout.putConstraint(SpringLayout.NORTH, btnForgeTask, 10, SpringLayout.NORTH, this);
		springLayout.putConstraint(SpringLayout.WEST, btnForgeTask, 54, SpringLayout.WEST, this);
		springLayout.putConstraint(SpringLayout.SOUTH, btnForgeTask, 62, SpringLayout.NORTH, this);
		btnForgeTask.setAction(action);
		add(btnForgeTask);
		
		TextField textField = new TextField();
		springLayout.putConstraint(SpringLayout.WEST, textField, 268, SpringLayout.WEST, this);
		springLayout.putConstraint(SpringLayout.EAST, textField, -10, SpringLayout.EAST, this);
		springLayout.putConstraint(SpringLayout.EAST, btnForgeTask, -69, SpringLayout.WEST, textField);
		springLayout.putConstraint(SpringLayout.NORTH, textField, 0, SpringLayout.NORTH, btnForgeTask);
		springLayout.putConstraint(SpringLayout.SOUTH, textField, -238, SpringLayout.SOUTH, this);
	
		textField.setBackground(Color.LIGHT_GRAY);
		textField.setForeground(Color.LIGHT_GRAY);
		textField.setFont(null);
		add(textField);
		
		JButton btnButtonMan = new JButton("Button Man");
		btnButtonMan.setAction(action_1);
		springLayout.putConstraint(SpringLayout.NORTH, btnButtonMan, 6, SpringLayout.SOUTH, btnForgeTask);
		springLayout.putConstraint(SpringLayout.WEST, btnButtonMan, 79, SpringLayout.WEST, this);
		add(btnButtonMan);

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
	private class SwingAction_1 extends AbstractAction {
		/**
		 * 
		 */
		private static final long serialVersionUID = 2L;
		private boolean decision = false;
		
		public SwingAction_1() {
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

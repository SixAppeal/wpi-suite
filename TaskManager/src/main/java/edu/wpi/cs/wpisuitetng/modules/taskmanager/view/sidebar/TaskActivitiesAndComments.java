/*******************************************************************************
 * Copyright (c) 2013 WPI-Suite
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Team Sixappeal
 ******************************************************************************/
package edu.wpi.cs.wpisuitetng.modules.taskmanager.view.sidebar;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;

import edu.wpi.cs.wpisuitetng.janeway.config.ConfigManager;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.model.Activity;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.model.Comment;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.model.Task;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.presenter.Gateway;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.view.IView;

/**
 * A view for Task Details
 * 
 * @author tmeehan
 * @author wmtemple
 * @author krpeffer
 * @author rwang3
 * @author wavanrensselaer
 * @author tmeehan
 *
 */
public class TaskActivitiesAndComments extends JPanel implements IView {

	/**
	 * Eclipse Generated serial number
	 */
	private static final long serialVersionUID = 2294726701087412148L;

	private Gateway gateway;
	
	private Task t;
	
	JTabbedPane activitiesAndComments;

	/**
	 * Declare all the JLabels and Panels to be placed in the Tabbed View
	 */
	JComponent activitiesPanel;
	JComponent activitiesLabel;
	JList<Activity> taskActivitiesList; //Displays the Task's activity history
	
	JComponent commentPanel;
	JComponent commentLabel;
	JList<Comment> taskCommentList;	//Displays the Task's comment history
	JTextArea taskCommentArea;
	
	JButton saveCommentButton; //Saves the comment
	/**
	 * Constructor
	 */
	public TaskActivitiesAndComments() {
		Color labelColor = new Color(160, 160, 160);
		
		activitiesAndComments = new JTabbedPane();
		
		activitiesPanel = new JPanel();
		commentPanel = new JPanel();
		
		activitiesLabel = new JLabel("Task History");
		activitiesLabel.setForeground(labelColor);
		taskActivitiesList = new JList<Activity> ();
		
		commentLabel = new JLabel ("Comments");
		commentLabel.setForeground(labelColor);
		taskCommentList = new JList<Comment>();
		
		saveCommentButton = new JButton("Save");
		saveCommentButton.addActionListener( new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addToComments();
			}
		});
		saveCommentButton.setEnabled(true);
		
		//history layout
		GridBagLayout activitiesLayout = new GridBagLayout();
		
		activitiesPanel.setLayout(activitiesLayout);
		activitiesPanel.setOpaque(false);
		
		GridBagConstraints activitiesgbc = new GridBagConstraints();
		activitiesgbc.anchor = GridBagConstraints.PAGE_START;
		activitiesgbc.fill = GridBagConstraints.HORIZONTAL;
		activitiesgbc.weightx = 1.0;
		activitiesgbc.insets = new Insets(20, 20, 0, 20);
		activitiesgbc.gridwidth = 2;
		activitiesgbc.gridx = 0;
		activitiesgbc.gridy = 0;
		
		activitiesgbc.insets.top = 20;
		activitiesgbc.gridx = 0;
		activitiesPanel.add(activitiesLabel,activitiesgbc);
		
		activitiesgbc.insets.top = 5;
		activitiesgbc.gridy = 1;
		activitiesPanel.add(taskActivitiesList, activitiesgbc);
		
		//comments layout
		GridBagLayout commentsLayout = new GridBagLayout();
		commentPanel.setLayout(commentsLayout);
		
		GridBagConstraints commentsgbc = new GridBagConstraints();
		commentsgbc.anchor = GridBagConstraints.FIRST_LINE_START;
		commentsgbc.fill = GridBagConstraints.NONE;
		commentsgbc.insets.top = 20;
		commentsgbc.weighty = 1.0;		
		commentsgbc.insets.top = 20;
		commentsgbc.gridy = 0;
		commentsgbc.gridx = 0;
		commentPanel.add(commentLabel,commentsgbc);
		
		commentsgbc.insets.top = 5;
		commentsgbc.gridy = 1;
		commentPanel.add(taskCommentList,commentsgbc);
		
		commentsgbc.gridy = 2;
		commentPanel.add(taskCommentArea);
		
		commentsgbc.gridx = 1;
		commentPanel.add(saveCommentButton, commentsgbc);
		
		activitiesAndComments.addTab("History", activitiesPanel);
		activitiesAndComments.addTab("Comments", commentPanel);
	}
	
	/**
	 * Update the view to display the info for a different task
	 * @param t The new task to display.
	 */
	public void updateView( Task t ) {
		
		this.t = t;

		taskActivitiesList.setListData(t.getActivities().toArray(new Activity[0]));
		taskCommentList.setListData(t.getComments().toArray(new Comment[0]));
		saveCommentButton.setEnabled(true);
		this.revalidate();
	}
	
	/**
	 * adds the comment in the text area to the list of comments in the task
	 * after you click the save button
	 */
	public void addToComments(){
		t.addComment(ConfigManager.getConfig().getUserName(), taskCommentArea.getText());
		updateView(t);
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

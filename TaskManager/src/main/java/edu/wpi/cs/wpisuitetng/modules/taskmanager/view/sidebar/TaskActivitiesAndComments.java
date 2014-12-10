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

import java.awt.BorderLayout;
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
import edu.wpi.cs.wpisuitetng.modules.taskmanager.model.StageList;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.model.Task;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.presenter.Gateway;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.view.IView;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.view.sidebar.histNcom.CommentView;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.view.sidebar.histNcom.HistoryView;

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
	//JComponent activitiesPanel;
	HistoryView activitiesPanel;
	JComponent activitiesLabel;
	JList<Activity> taskActivitiesList; //Displays the Task's activity history
	
	CommentView commentPanel;
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
		
		//activitiesPanel = new JPanel();
		activitiesPanel = new HistoryView();
		commentPanel = new CommentView(t);
		
		
//		commentLabel = new JLabel ("Comments");
//		commentLabel.setForeground(labelColor);
//		taskCommentList = new JList<Comment>();
//		
//		taskCommentArea = new JTextArea();
//		
//		saveCommentButton = new JButton("Save");
//		saveCommentButton.addActionListener( new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//				addToComments();
//			}
//		});
//		saveCommentButton.setEnabled(true);
		
//		//comments layout
//		GridBagLayout commentsLayout = new GridBagLayout();
//		commentPanel.setLayout(commentsLayout);
//		
//		GridBagConstraints commentsgbc = new GridBagConstraints();
//		commentsgbc.anchor = GridBagConstraints.FIRST_LINE_START;
//		commentsgbc.fill = GridBagConstraints.HORIZONTAL;
//		commentsgbc.insets.top = 20;
//		commentsgbc.weighty = 0.;
//		commentsgbc.weightx = 1.;
//		commentsgbc.insets.top = 20;
//		commentsgbc.gridy = 0;
//		commentsgbc.gridx = 0;
//		commentPanel.add(commentLabel,commentsgbc);
//		
//		commentsgbc.insets.top = 5;
//		commentsgbc.gridy = 1;
//		commentsgbc.weighty = 1.;
//		commentsgbc.weightx = 0.;
//		commentPanel.add(taskCommentList,commentsgbc);
//		commentsgbc.weighty = 0.;
//		commentsgbc.weightx = 1.;
//		commentsgbc.gridy = 2;
//		commentPanel.add(taskCommentArea, commentsgbc);
//		
//		commentsgbc.gridx = 1;
//		commentPanel.add(saveCommentButton, commentsgbc);
		
		activitiesAndComments.addTab("History", activitiesPanel);
		activitiesAndComments.addTab("Comments", commentPanel);
		
		this.setLayout(new BorderLayout());
		this.add(activitiesAndComments, BorderLayout.CENTER);
	}
	
	/**
	 * Update the view to display the info for a different task
	 * @param t The new task to display.
	 */
	public void updateView( Task task ) {
		
		this.t = task;

		//taskActivitiesList.setListData(t.getActivities().toArray(new Activity[0]));
		activitiesPanel.displayActivities(task);
		//taskCommentList.setListData(task.getComments().toArray(new Comment[0]));
		commentPanel.displayActivities(task);
		//saveCommentButton.setEnabled(true);
		this.revalidate();
	}
	
	/**
	 * adds the comment in the text area to the list of comments in the task
	 * after you click the save button
	 */
	public void addToComments(){
		t.addComment(ConfigManager.getConfig().getUserName(), taskCommentArea.getText());
		updateView(t);
		taskCommentArea.setText("");
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

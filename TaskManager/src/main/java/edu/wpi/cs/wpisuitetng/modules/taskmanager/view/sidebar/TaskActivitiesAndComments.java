/*******************************************************************************
 * Copyright (c) 2013 WPI-Suite
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Team Six-Appeal
 ******************************************************************************/
package edu.wpi.cs.wpisuitetng.modules.taskmanager.view.sidebar;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import edu.wpi.cs.wpisuitetng.janeway.config.ConfigManager;
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
 * 
 *
 */
public class TaskActivitiesAndComments extends JPanel implements IView {

	/**
	 * Eclipse Generated serial number
	 */
	private static final long serialVersionUID = 2294726701087412148L;

	@SuppressWarnings("unused")
	private Gateway gateway;
	
	private Task t;
	
	JTabbedPane activitiesAndComments;

	/**
	 * Declare all the JLabels and Panels to be placed in the Tabbed View
	 */
	HistoryView activitiesPanel;
	JComponent activitiesLabel;
	
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
		
		activitiesPanel = new HistoryView();
		commentPanel = new JPanel();
		
		activitiesLabel = new JLabel("Task History");
		activitiesLabel.setForeground(labelColor);
		
		commentLabel = new JLabel ("Comments");
		commentLabel.setForeground(labelColor);
		taskCommentList = new JList<Comment>();
		
		taskCommentArea = new JTextArea();
		taskCommentArea.setLineWrap(true);
		taskCommentArea.setWrapStyleWord(true);
		taskCommentArea.addKeyListener(new KeyListener(){
			@Override
		    public void keyPressed(KeyEvent e){
		        if(e.getKeyCode() == KeyEvent.VK_ENTER){
		        	e.consume();
		        	if(taskCommentArea.getText().trim().equals("")){
		        		taskCommentArea.setText("");
		        	}
		        	else{
		        		addToComments();
		        	}
		        	
		        }

		        
		    }

		    @Override
		    public void keyTyped(KeyEvent e) {
		    }

		    @Override
		    public void keyReleased(KeyEvent e) {
		    }
		});
		taskCommentArea.getDocument().addDocumentListener(new DocumentListener(){
			@Override
			public void changedUpdate(DocumentEvent e) {
			    if(taskCommentArea.getText().trim().equals("")){
			    	 saveCommentButton.setEnabled(false);
			    }
			    else {
			    	saveCommentButton.setEnabled(true);
			    }
			  }
			@Override
			public void removeUpdate(DocumentEvent e) {
				if(taskCommentArea.getText().trim().equals("")){
			    	 saveCommentButton.setEnabled(false);
			    }
			    else {
			    	saveCommentButton.setEnabled(true);
			    }
			  }
			@Override
			public void insertUpdate(DocumentEvent e) {
				if(taskCommentArea.getText().trim().equals("")){
			    	 saveCommentButton.setEnabled(false);
			    }
			    else {
			    	saveCommentButton.setEnabled(true);
			    }
			  }
		});
		
		saveCommentButton = new JButton("Save");
		saveCommentButton.setEnabled(false);
		saveCommentButton.addActionListener( new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addToComments();
			}
		});
		
		
		//history layout

		
		
		//comments layout
		GridBagLayout commentsLayout = new GridBagLayout();
		commentPanel.setLayout(commentsLayout);
		
		GridBagConstraints commentsgbc = new GridBagConstraints();
		commentsgbc.anchor = GridBagConstraints.FIRST_LINE_START;
		commentsgbc.fill = GridBagConstraints.HORIZONTAL;
		commentsgbc.insets.top = 20;
		commentsgbc.weighty = 0.;
		commentsgbc.weightx = 1.;
		commentsgbc.insets.top = 20;
		commentsgbc.gridy = 0;
		commentsgbc.gridx = 0;
		commentPanel.add(commentLabel,commentsgbc);
		
		commentsgbc.insets.top = 5;
		commentsgbc.gridy = 1;
		commentsgbc.weighty = 1.;
		commentsgbc.weightx = 0.;
		commentPanel.add(taskCommentList,commentsgbc);
		commentsgbc.weighty = 0.;
		commentsgbc.weightx = 1.;
		commentsgbc.gridy = 2;
		commentPanel.add(taskCommentArea, commentsgbc);
		
		commentsgbc.gridx = 1;
		commentPanel.add(saveCommentButton, commentsgbc);
		
		activitiesAndComments.addTab("History", activitiesPanel);
		activitiesAndComments.addTab("Comments", commentPanel);
		
		this.setLayout(new BorderLayout());
		this.add(activitiesAndComments, BorderLayout.CENTER);
	}
	
	/**
	 * Update the view to display the info for a different task
	 * @param t The new task to display.
	 */
	public void updateView( Task t ) {
		
		this.t = t;

//		taskActivitiesList.setListData(t.getActivities().toArray(new Activity[0]));
		activitiesPanel.displayActivities(t);
		taskCommentList.setListData(t.getComments().toArray(new Comment[0]));
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
		saveCommentButton.setEnabled(false);
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

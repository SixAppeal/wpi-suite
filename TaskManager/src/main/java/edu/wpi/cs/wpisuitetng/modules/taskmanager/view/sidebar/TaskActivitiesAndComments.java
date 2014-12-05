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
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;

import edu.wpi.cs.wpisuitetng.janeway.config.ConfigManager;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.model.Activity;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.model.Comment;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.model.StageList;
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
//	JScrollPane activitiesScrollPane;
	
	JComponent commentPanel;
	JComponent commentLabel;
	JList<Comment> taskCommentList;	//Displays the Task's comment history
//	JScrollPane commentsScrollPane;
	JTextArea taskCommentArea;
	
	JButton saveCommentButton; //Saves the comment
	
	JScrollPane scrollPane;
	/**
	 * Constructor
	 */
	public TaskActivitiesAndComments() {
		Color labelColor = new Color(160, 160, 160);
		
		activitiesAndComments = new JTabbedPane();
		this.activitiesAndComments.setBackground(new Color(220, 220, 220));
		this.activitiesAndComments.setMinimumSize(new Dimension(260, 0));
		this.activitiesAndComments.setMaximumSize(new Dimension(260, Integer.MAX_VALUE));
		this.activitiesAndComments.setPreferredSize(new Dimension(260, 400));
		
		this.scrollPane = new JScrollPane(activitiesAndComments);		
		this.scrollPane.setOpaque(false);
		this.scrollPane.getViewport().setOpaque(false);
		this.scrollPane.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		this.scrollPane.setHorizontalScrollBarPolicy(
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		
		activitiesPanel = new JPanel();
		activitiesLabel = new JLabel("Task History");
		activitiesLabel.setForeground(labelColor);
		taskActivitiesList = new JList<Activity>();
//		activitiesScrollPane = new JScrollPane(taskActivitiesList);

		
		commentPanel = new JPanel();
		commentLabel = new JLabel ("Comments");
		commentLabel.setForeground(labelColor);
		taskCommentList = new JList<Comment>();
//		commentsScrollPane = new JScrollPane(taskCommentList);
		
		taskCommentArea = new JTextArea();
		taskCommentArea.setBorder(BorderFactory.createEmptyBorder());
		taskCommentArea.setLineWrap(true);
		taskCommentArea.setWrapStyleWord(true);
		
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
		activitiesgbc.weighty = 1.0;
		activitiesgbc.weightx = 0.0;
		activitiesgbc.insets.top = 5;
		activitiesgbc.gridy = 1;
//		activitiesPanel.add(activitiesScrollPane, activitiesgbc);
		activitiesPanel.add(taskActivitiesList, activitiesgbc);
		
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
//		commentPanel.add(commentsScrollPane,commentsgbc);
		commentPanel.add(taskCommentList, commentsgbc);
		commentsgbc.weighty = 0.;
		commentsgbc.weightx = 1.;
		commentsgbc.gridy = 2;
		commentPanel.add(taskCommentArea, commentsgbc);
		
		commentsgbc.gridx = 1;
		commentPanel.add(saveCommentButton, commentsgbc);
		
		activitiesAndComments.addTab("History", activitiesPanel);
		activitiesAndComments.addTab("Comments", commentPanel);
		
		this.setLayout(new BorderLayout());
		this.add(scrollPane, BorderLayout.CENTER);
	}
	
	/**
	 * Update the view to display the info for a different task
	 * @param t The new task to display.
	 */
	public void updateView( Task t ) {
		
		this.t = t;
		MyCellRenderer cellRenderer = new MyCellRenderer(this.activitiesPanel.getWidth());
		System.out.println("********this.activitiespanel.getwidth = " + this.activitiesPanel.getWidth());

		taskActivitiesList.setListData(t.getActivities().toArray(new Activity[0]));
		taskActivitiesList.setCellRenderer(cellRenderer);
		taskCommentList.setListData(t.getComments().toArray(new Comment[0]));
		taskCommentList.setCellRenderer(cellRenderer);
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

	@Override
	public void setStages(StageList sl) {
		// no purpose here
	}
	
	class MyCellRenderer extends DefaultListCellRenderer {
		   public static final String HTML_1 = "<html><body style='width: ";
		   public static final String HTML_2 = "px'>";
		   public static final String HTML_3 = "</html>";
		   private int width;

		   public MyCellRenderer(int width) {
		      this.width = width;
		   }

		   @Override
		   public Component getListCellRendererComponent(JList list, Object value,
		         int index, boolean isSelected, boolean cellHasFocus) {
		      String text = HTML_1 + String.valueOf(width) + HTML_2 + value.toString()
		            + HTML_3;
		      return super.getListCellRendererComponent(list, text, index, isSelected,
		            cellHasFocus);
		   }
	}
	
}

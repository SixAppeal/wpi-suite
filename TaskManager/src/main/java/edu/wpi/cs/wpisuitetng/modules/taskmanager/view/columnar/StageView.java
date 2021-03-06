/*******************************************************************************
 * Copyright (c) 2014 -- WPI Suite
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors: Will Rensselaer, Thomas Meehan, Ryan Orlando
 ******************************************************************************/

package edu.wpi.cs.wpisuitetng.modules.taskmanager.view.columnar;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

import edu.wpi.cs.wpisuitetng.modules.taskmanager.model.Stage;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.model.Task;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.presenter.Gateway;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.view.IView;

/**
 * View for an individual column in the columnal layout
 * @author wavanrensselaer
 * @author rnorlando
 * @author tmeehan
 */
public class StageView extends JPanel implements IView {
	private static final long serialVersionUID = 2174190454852340046L;
	
	private Gateway gateway;
	
	// State-related fields
	private Stage stage;
	private Task[] tasks;
	private GridBagLayout layout;
	
	// Components
	private JLabel nameLabel;
	private JPanel container;
	private JScrollPane scrollPane;
	
	/**
	 * Constructs a <code>ColumnView</code> which has a name and an
	 * <code>ArrayList</code> of the tasks to display.
	 * @param title The title of the column
	 */
	public StageView(Stage stage, Task[] tasks) {
		this.nameLabel = new JLabel("", JLabel.CENTER);
		this.container = new StageDragDropPanel(this);
		this.scrollPane = new JScrollPane(this.container);
		this.layout = new GridBagLayout();
		
		this.container.setLayout(this.layout);
		this.container.setOpaque(false);
		
		this.scrollPane.setOpaque(false);
		this.scrollPane.getViewport().setOpaque(false);
		this.scrollPane.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		this.scrollPane.setHorizontalScrollBarPolicy(
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

		this.setBackground(new Color(255, 255, 255));
		this.setMinimumSize(new Dimension(260, 0));
		this.setMaximumSize(new Dimension(260, Integer.MAX_VALUE));
		this.setPreferredSize(new Dimension(260, 400));
		this.setLayout(new GridBagLayout());
		
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.anchor = GridBagConstraints.PAGE_START;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.insets = new Insets(10, 20, 10, 20);
		gbc.weightx = 1.0;
		gbc.gridx = 0;
		gbc.gridy = 0;
		this.add(this.nameLabel, gbc);
		
		gbc.fill = GridBagConstraints.BOTH;
		gbc.insets = new Insets(0, 0, 10, 0);
		gbc.weighty = 1.0;
		gbc.gridy = 1;
		this.add(this.scrollPane, gbc);
		
		this.setState(stage, tasks);
	}
	
	/**
	 * Sets the stage of the view
	 * @param stage The new stage
	 */
	public void setStage(Stage stage) {
		this.setState(stage, this.tasks);
	}
	
	/**
	 * Gets the view's stage
	 * @return The stage of the stage
	 */
	public Stage getStage() {
		return this.stage;
	}
	
	/**
	 * Sets the state of tasks within this stage.
	 * @param tasks The new task array
	 */
	public void setTasks(Task[] tasks) {
		this.setState(this.stage, tasks);
	}
	
	/**
	 * Gets the state of the tasks within this view
	 * @return An array of tasks within this view
	 */
	public Task[] getTasks() {
		return this.tasks;
	}
	
	/**
	 * Sets the state of this view, the name and the tasks within this stage.
	 * @param tasks The new task array
	 * @param stage The new stage
	 * 
	 */
	public void setState(Stage stage, Task[] tasks) {
		this.stage = stage;
		this.tasks = tasks == null ? new Task[0] : tasks;
		this.reflow();
	}
	
	/**
	 * Reflows this views when it's state changes.
	 */
	public void reflow() {
		this.nameLabel.setText(this.stage == null ? "" : this.stage.getName());
		
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.anchor = GridBagConstraints.PAGE_START;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.weightx = 1.0;
		gbc.gridx = 0;
		
		TaskView taskView;
		int i;
		for (i = 0; i < this.container.getComponentCount(); i++) {
			taskView = (TaskView) this.container.getComponent(i);
			if (i >= this.tasks.length) {
				this.container.remove(i--);
			} else if (!taskView.getTask().equals(this.tasks[i])) {
				taskView.setState(this.tasks[i]);
			}
			this.updateConstraints(taskView, i);
		}
		for (; i < this.tasks.length; i++) {
			gbc.gridy = i;
			taskView = new TaskView(this.tasks[i]);
			taskView.setGateway(this.gateway);
			this.container.add(taskView, gbc);
			this.updateConstraints(taskView, i);
		}
		
		this.scrollPane.revalidate();
		this.scrollPane.repaint();
	}
	
	/**
	 * Updates the constraints on a component in the container.
	 * This is a helper method to reflow.
	 * @param view The component to update
	 * @param i The index of this component
	 */
	private void updateConstraints(TaskView view, int i) {
		GridBagConstraints gbc = this.layout.getConstraints(view);
		gbc.weighty = i == this.tasks.length - 1 ? 1.0 : 0.0;
		this.layout.setConstraints(view, gbc);
	}
	
	@Override
	public void paintComponent(Graphics g) {
		Graphics2D graphics = (Graphics2D) g;
		graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, 
				RenderingHints.VALUE_ANTIALIAS_ON);
		graphics.setColor(getBackground());
		graphics.fillRoundRect(0, 0, getWidth(), getHeight(), 6, 6);
	}
	
	/**
	 * @see IView.setGateway
	 */
	@Override
	public void setGateway(Gateway gateway) {
		this.gateway = gateway;
		for (Component c : this.container.getComponents()) {
			if (c instanceof IView) {
				((IView) c).setGateway(this.gateway);
			}
		}
	}
	
	/**
	 * gets the task views
	 * @return the returned array of task views
	 */
	public ArrayList<TaskView> getTaskViews()
	{
		ArrayList<TaskView> allTaskViews = new ArrayList<TaskView>();
		for (int i = 0; i < this.container.getComponentCount(); i++) {			
			allTaskViews.add((TaskView) this.container.getComponent(i));
		}
		return allTaskViews;
	}
	
	/**
	 * returns the the gateway for this stage
	 * @return the gateway
	 */
	public Gateway getGateway()
	{
		return this.gateway;
	}
}

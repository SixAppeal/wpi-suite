/*******************************************************************************
 * Copyright (c) 2014 -- WPI Suite
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors: Will Rensselaer, Alex Shoop, Thomas Meehan, Ryan Orlando
 ******************************************************************************/

package edu.wpi.cs.wpisuitetng.modules.taskmanager.view.columnar;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JLabel;
import javax.swing.JPanel;

import edu.wpi.cs.wpisuitetng.modules.taskmanager.model.StageList;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.draganddrop.DragAndDropTransferHandler;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.draganddrop.TaskDraggableMouseListener;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.model.Stage;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.model.Task;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.presenter.Gateway;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.view.IView;

/**
 * View for a task that display in the ColumnView
 * @author wavanrensselaer
 * @author akshoop
 * @author tmeehan
 * @author rnorlando
 */
public class TaskView extends JPanel implements  IView {
	private static final long serialVersionUID = 6255679649898290535L;
	
	/**
	 * The cutoff point for the title string in the task
	 */
	public static final int MAX_TITLE_LENGTH = 20;

	/**
	 * Background color of the TaskView
	 */
	public static final Color BACKGROUND_COLOR = new Color(255, 255, 255);
	
	/**
	 * Background color when the mouse is over the TaskView
	 */
	public static final Color HOVER_COLOR = new Color(245, 245, 245);
	
	private Gateway gateway;
	
	// State-related fields
	private Task task;
	
	// Components
	private JLabel titleLabel;
	private int id;
	

	/**
	 * Constructs a <code>TaskView</code>
	 * @param name The name of the task
	 */
	public TaskView(Task task) {
		this.titleLabel = new JLabel("", JLabel.LEFT);

		this.setBackground(TaskView.BACKGROUND_COLOR);
		this.setLayout(new GridBagLayout());
		this.addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent e) {
				//gateway.toPresenter("TaskPresenter", "editTask", task);
			}

			@Override
			public void mousePressed(MouseEvent e) {
			}

			@Override
			public void mouseReleased(MouseEvent e) {
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				setBackground(TaskView.HOVER_COLOR);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				setBackground(TaskView.BACKGROUND_COLOR);
			}
		});
		
		this.titleLabel = new JLabel("", JLabel.LEFT);

		this.setBackground(TaskView.BACKGROUND_COLOR);
		this.setLayout(new GridBagLayout());		
		
		this.addMouseListener(new TaskDraggableMouseListener(this));
		this.setTransferHandler(new DragAndDropTransferHandler());
		
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.insets = new Insets(10, 10, 10, 10);
		gbc.weightx = 1.0;
		gbc.gridx = 0;
		gbc.gridy = 0;
		this.add(this.titleLabel, gbc);
		
		this.setState(task);
	}
	
	/**
	 * Gets the task associated with this view
	 * @return The task associated with this view
	 */
	public Task getTask() {
		return this.task;
	}
	
	/**
	 * sets the stage for the task in the task view
	 * @param s stage that is being set to
	 */
	public void setStage(Stage s)
	{
		this.task.setStage(s);
	}
	
	/**
	 * Sets the state of this view, currently the task it represents.
	 * @param task The new task
	 */
	public void setState(Task task) {
		this.task = task == null ? new Task() : task;
		this.reflow();
	}
	
	/**
	 * Reflows this view when it's state changes
	 */
	public void reflow() {
		this.titleLabel.setText(this.task.getTitle());
		this.titleLabel.revalidate();
		this.revalidate();
	}
	
	/**
	 * get task ID
	 * @return the task ID
	 */
	public int getTaskID() {
		return this.task.getId();
	}

	/**
	 * @see edu.wpi.cs.wpisuitetng.modules.taskmanager.view.IView.setGateway
	 */
	@Override
	public void setGateway(Gateway gateway) {
		this.gateway = gateway;
	}

	@Override
	public void setStages(StageList sl) {
		//No purpose here.
  }

	/**
	 * gets the gateway
	 * @return gateway gotten
	 */
	public Gateway getGateway()
	{
		return gateway;
	}
}

/*******************************************************************************
 * Copyright (c) 2014 -- WPI Suite
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors: Will Van Rensselaer, Alex Shoop, Thomas Meehan, Ryan Orlando
 ******************************************************************************/

package edu.wpi.cs.wpisuitetng.modules.taskmanager.view.columnar;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

import edu.wpi.cs.wpisuitetng.modules.taskmanager.model.StageList;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.draganddrop.DragAndDropTransferHandler;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.draganddrop.TaskDraggableMouseListener;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.model.Stage;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.model.Task;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.presenter.Gateway;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.util.TaskManagerUtil;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.view.IView;

/**
 * View for a task that display in the ColumnView
 * @author wavanrensselaer
 * @author akshoop
 * @author tmeehan
 * @author rnorlando
 * @author srojas
 */
public class TaskView extends JPanel implements  IView {
	private static final long serialVersionUID = 6255679649898290535L;
	
	/**
	 * The cutoff point for the title string in the task
	 */
	public static final int MAX_TITLE_LENGTH = 150;

	/**
	 * Background color of the TaskView
	 */
	public static final Color BACKGROUND_COLOR = new Color(255, 255, 255);
	
	/**
	 * Background color when the mouse is over the TaskView
	 */
	public static final Color HOVER_COLOR = new Color(245, 245, 245);
	
	/**
	 * Background color when selected
	 */
	public static final Color SELECTED_COLOR = new Color(190,210,255);
	
	private Gateway gateway;
	
	// State-related fields
	private Task task;
	
	// Components
	private JLabel titleLabel;
	private JLabel dateLabel;
	private JPanel container;

	private boolean selected;
	
	public TaskView(Task task, boolean ForSearch) {
		this.titleLabel = new JLabel("", JLabel.LEFT);
		this.dateLabel = new JLabel("", JLabel.RIGHT);
		this.container = new JPanel();
		
		this.container.setOpaque(false);
		this.container.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, HOVER_COLOR));
		this.container.setLayout(new GridBagLayout());

		Font titleFont = this.titleLabel.getFont();
		this.titleLabel.setFont(titleFont.deriveFont(titleFont.getStyle() & ~Font.BOLD));
		
		this.dateLabel.setForeground(new Color(180, 180, 180));
		Font dateFont = this.dateLabel.getFont();
		this.dateLabel.setFont(dateFont.deriveFont(dateFont.getStyle() & ~Font.BOLD));
		
		this.selected = false;
		
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.insets = new Insets(10, 0, 10, 10);
		gbc.weightx = 1.0;
		gbc.gridx = 0;
		gbc.gridy = 0;
		this.container.add(this.titleLabel, gbc);
		
		gbc.fill = GridBagConstraints.NONE;
		gbc.insets.right = 20;
		gbc.weightx = 0;
		gbc.gridx = 1;
		this.container.add(this.dateLabel, gbc);
		
		this.setBackground(TaskView.BACKGROUND_COLOR);
		this.setLayout(new GridBagLayout());
		this.addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent e) {
				 if(e.getClickCount()==2){
					 gateway.toPresenter("TaskPresenter", "editTask", task);
			        }
				
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

		this.setBackground(TaskView.BACKGROUND_COLOR);
		this.setLayout(new GridBagLayout());		

		gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.insets = new Insets(0, 20, 0, 0);
		gbc.weightx = 1.0;
		gbc.gridx = 0;
		gbc.gridy = 0;
		this.add(this.container, gbc);
		
		this.setState(task);
	}
	/**
	 * Constructs a <code>TaskView</code>
	 * @param name The name of the task
	 */
	public TaskView(Task task) {
		this.titleLabel = new JLabel("", JLabel.LEFT);
		this.dateLabel = new JLabel("", JLabel.RIGHT);
		this.container = new JPanel();
		
		this.container.setOpaque(false);
		this.container.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, HOVER_COLOR));
		this.container.setLayout(new GridBagLayout());

		Font titleFont = this.titleLabel.getFont();
		this.titleLabel.setFont(titleFont.deriveFont(titleFont.getStyle() & ~Font.BOLD));
		
		boolean urgent = task.getDueDate().after(new Date(System.currentTimeMillis() - 86400000)); //urgent if late
		
		this.dateLabel.setForeground(urgent?new Color(180, 180, 180):new Color(255, 102, 0));
		Font dateFont = this.dateLabel.getFont();
		this.dateLabel.setFont(dateFont.deriveFont(dateFont.getStyle() & ~Font.BOLD));
		
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.insets = new Insets(10, 0, 10, 10);
		gbc.weightx = 1.0;
		gbc.gridx = 0;
		gbc.gridy = 0;
		this.container.add(this.titleLabel, gbc);
		
		gbc.fill = GridBagConstraints.NONE;
		gbc.insets.right = 20;
		gbc.weightx = 0;
		gbc.gridx = 1;
		this.container.add(this.dateLabel, gbc);
		
		this.setBackground(TaskView.BACKGROUND_COLOR);
		this.setLayout(new GridBagLayout());
		this.addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent e) {
//				 if(e.getClickCount()==2){
//					 gateway.toPresenter("TaskPresenter", "editTask", task);
//			        }
				 gateway.toPresenter("TaskPresenter", "editTask", task);
				
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

		this.setBackground(TaskView.BACKGROUND_COLOR);
		this.setLayout(new GridBagLayout());		
		
		this.addMouseListener(new TaskDraggableMouseListener(this));
		this.setTransferHandler(new DragAndDropTransferHandler());
		
		gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.insets = new Insets(0, 20, 0, 0);
		gbc.weightx = 1.0;
		gbc.gridx = 0;
		gbc.gridy = 0;
		this.add(this.container, gbc);
		
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
		String text = TaskManagerUtil.reduceString(this.task.getTitle(), MAX_TITLE_LENGTH,
				this.titleLabel.getFontMetrics(this.titleLabel.getFont()));
		this.titleLabel.setText(text);
		this.dateLabel.setText(new SimpleDateFormat("MM/dd/yy").format(this.task.getDueDate()));
		
		this.titleLabel.revalidate();
		this.dateLabel.revalidate();
		this.revalidate();
		this.repaint();
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
	
	/**
	 * gets the gateway
	 * @return gateway gotten
	 */
	public Gateway getGateway()
	{
		return gateway;
	}
	
	/**
	 * Changes the look of this TaskView to appear deselected.
	 */
	public void select() {
		this.selected = true;
		this.setBackground(TaskView.SELECTED_COLOR);
	}
	
	/**
	 * Changes the look of this TaskView to appear selected.
	 */
	public void deselect() {
		this.selected = false;
		this.setBackground(TaskView.BACKGROUND_COLOR);
	}
	
	/**
	 * Changes the look of this TaskView to appear unhighlighted
	 */
	public void unhighlight() {
		if ( !selected ) this.setBackground(TaskView.BACKGROUND_COLOR);
	}
	
	/**
	 * Changes the look of this TaskView to appear highlighted
	 */
	public void highlight() {
		if ( !selected ) this.setBackground(TaskView.HOVER_COLOR);
	}
}

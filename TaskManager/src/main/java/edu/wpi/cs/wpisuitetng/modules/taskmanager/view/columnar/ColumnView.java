/*******************************************************************************
 * Copyright (c) 2014 -- WPI Suite
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors: Will Rensselaer, Alex Shoop, Ryan Orlando, Thomas Meehan
 ******************************************************************************/

/**
 * Issues:
 * 	1) The only available space to drag is where tasks aren't (need to make task view drop targets as well
 *  5) database clears drag and drop order
 * Order of things I'm going to take a look at if you guys don't get to them:
 *  1) the available space thing.  I have a workaround, I think.
 *  2) the gateway stuff.  TaskView cannot contain anything with Task or Gateway in it. Probably need a global mouse listener or something. Other 
 *  option is the gateway, task and everything else becomes a JPanel (which is a pretty bad option.
 *  3) database stuff
 *  I'm figuring that you guys can definitely handle graphics / animation / everything else.  
 */

package edu.wpi.cs.wpisuitetng.modules.taskmanager.view.columnar;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.List;

import java.awt.datatransfer.DataFlavor;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

import edu.wpi.cs.wpisuitetng.modules.taskmanager.model.Stage;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.model.StageList;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.model.Task;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.presenter.Gateway;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.view.IView;

/**
 * View for displaying multiple horizontal columns with associated tasks
 * @author wavanrensselaer
 * @author akshoop
 * @author rnorlando
 * @author tmeehan
 */
public class ColumnView extends JPanel implements IView {
	private static final long serialVersionUID = 7965275386426411767L;

	private Gateway gateway;
	
	// State-related fields
	private StageList stages;
	private Task[] tasks;
	
	// Components
	private JPanel container;
	private JScrollPane scrollPane;
	
	private static DataFlavor dragAndDropPanelDataFlavor = null;
	
	/**
	 * Constructs a <code>ColumnView</code> which holds an <code>ArrayList</code>
	 * of <code>StageView</code>s.
	 */
	public ColumnView() {
		this.container = new JPanel();
		this.scrollPane = new JScrollPane(this.container);

		this.container.setLayout(new GridBagLayout());

		this.scrollPane.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		this.scrollPane.setVerticalScrollBarPolicy(
				ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
		
		this.setLayout(new GridBagLayout());
		
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.anchor = GridBagConstraints.PAGE_START;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		gbc.gridx = 0;
		gbc.gridy = 0;
		this.add(this.scrollPane, gbc);
		
		this.setState(new Task[0], new StageList());
	}
	
	/**
	 * Sets the state of tasks within the view
	 * @param tasks The new task array
	 */
	public void setTasks(Task... tasks) {
		this.setState(tasks, this.stages);
	}

	/**
	 * adds a Tasks to the COlumn View
	 * @param task
	 */
	public void addTask(Task task) 
	{
		//This seems ineffecnsent
		
		Task[] oldTasks = this.getTasks();
		Task[] tempTaskList = new Task[oldTasks.length +1];
		for(int i = 0; i< oldTasks.length; i++ )
		{
			tempTaskList[i] = oldTasks[i];
		}
		tempTaskList[oldTasks.length] = task;
		
		this.setTasks(tempTaskList);
	}
		
	
	/**
	 * Gets the state of tasks within this view
	 * @return The tasks within this view
	 */
	public Task[] getTasks() {
		return this.tasks;
	}
	
	/**
	 * Sets the state of stages within the view
	 * @param stages The new stages array
	 */
	public void setStages(StageList stages) {
		//System.out.println("setState: new Stages are " + stages.toString());
		if( ! stages.equals(this.stages) ) this.setState(this.tasks, stages);
	}
	
	/**
	 * Gets the state of stages within this view
	 * @return The stages within this view
	 */
	public StageList getStages() {
		return this.stages;
	}
	
	
	/**
	 * Sets the state (both tasks and stages) of this view.
	 * @param tasks The new task array
	 * @param stages The new stages array
	 */
	public void setState(Task[] tasks, StageList stages) {
		this.tasks = tasks == null ? new Task[0] : tasks;
		this.stages = stages == null ? new StageList() : stages;
		//this.reflow();
	}
	
	/**
	 * Reflows the view when it's state changes.
	 */
	public void reflow() {
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.anchor = GridBagConstraints.FIRST_LINE_START;
		gbc.fill = GridBagConstraints.VERTICAL;
		gbc.weighty = 1.0;
		gbc.insets = new Insets(20, 0, 20, 20);
		gbc.gridy = 0;
		
		StageView stageView;
		int i;
		//System.out.println("Component count is " + this.container.getComponentCount());
		for (i = 0; i < this.container.getComponentCount(); i++) {
			stageView = (StageView) this.container.getComponent(i);
			if (i >= this.stages.size()) {
				this.container.remove(i--);
			} else if (!stageView.getStage().equals(this.stages.get(i))) {
				stageView.setState(this.stages.get(i),
						this.getTasksForStage(this.stages.get(i)));
			} else {
				stageView.setTasks(this.getTasksForStage(this.stages.get(i)));
			}
		}
		for (; i < this.stages.size(); i++) {
			//System.out.println("Reflowing stage (II) " + this.stages.get(i).toString());
			gbc.insets.left = i == 0 ? 20 : 0;
			gbc.gridx = i;
			stageView = new StageView(this.stages.get(i),
					this.getTasksForStage(this.stages.get(i)));
			stageView.setGateway(this.gateway);
			this.container.add(stageView, gbc);
		}
		
		this.scrollPane.revalidate();
	}

	/**
	 * Gets the array of tasks that are associated with a stage
	 * @param stage The stage to which the tasks should belong
	 * @return An array of <code>Task</code>s
	 */
	private Task[] getTasksForStage(Stage stage) {
		List<Task> tasks = new ArrayList<Task>();
		for (int i = 0; i < this.tasks.length; i++) {
			if (this.tasks[i].getStage().equals(stage)) {
				tasks.add(this.tasks[i]);
			}
		}
		
		return tasks.toArray(new Task[0]);
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
	 * Creates a descriptive DataFlavor for the task that is to be moved so that the data can be packaged for drag and drop 
	 * and recognized as the type of data that it is by the drop target. 
	 * @return returns a DataFlavor for the task being moved
	 * @throws Exception if the data attempting to be dragged is not draggable 
	 */
	public static DataFlavor getTaskDataFlavor() throws Exception {
        if (dragAndDropPanelDataFlavor == null) {
            dragAndDropPanelDataFlavor = new DataFlavor(TaskView.class, "TaskView");// new DataFlavor(DataFlavor.javaJVMLocalObjectMimeType + ";class=TaskView");
        }
        return dragAndDropPanelDataFlavor;
    }
}

/*******************************************************************************
 * Copyright (c) 2014 -- WPI Suite
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors: Team Six-Appeal
 ******************************************************************************/

package edu.wpi.cs.wpisuitetng.modules.taskmanager.view.columnar;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.Collections;
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
 * @author thhughes
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
	private GridBagLayout layout;
	
	private static DataFlavor dragAndDropPanelDataFlavor = null;
	
	/**
	 * Constructs a <code>ColumnView</code> which holds an <code>ArrayList</code>
	 * of <code>StageView</code>s.
	 */
	public ColumnView() {
		this.container = new JPanel();
		this.scrollPane = new JScrollPane(this.container);
		this.layout = new GridBagLayout();

		this.container.setOpaque(false);
		this.container.setLayout(this.layout);

		this.scrollPane.setOpaque(false);
		this.scrollPane.getViewport().setOpaque(false);
		this.scrollPane.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		
		
		this.scrollPane.setVerticalScrollBarPolicy(
				ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
		
		this.scrollPane.setHorizontalScrollBarPolicy(
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		
		
		this.setOpaque(false);
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
		if (!stages.equals(this.stages)) this.setState(this.tasks, stages);

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
		this.reflow();
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
			this.updateConstraints(stageView, i);
		}
		for (; i < this.stages.size(); i++) {
			gbc.insets.left = i == 0 ? 20 : 0;
			gbc.gridx = i;
			stageView = new StageView(this.stages.get(i),
					this.getTasksForStage(this.stages.get(i)));
			stageView.setGateway(this.gateway);
			this.container.add(stageView, gbc);
			this.updateConstraints(stageView, i);
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
	private void updateConstraints(StageView view, int i) {
		GridBagConstraints gbc = this.layout.getConstraints(view);
		if (i == this.stages.size() - 1) {
			gbc.weightx = 1.0;
			gbc.insets.right = 20;
		} else {
			gbc.weightx = 0;
			gbc.insets.right = 20;
		}
		this.layout.setConstraints(view, gbc);
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
		
		Collections.sort(tasks, Task.PRIORITY_COMPARATOR);
		
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
	
	/**
	 * Scroll to the specified area
	 * @param viewStage
	 */
	public void scrollToPlace(Stage viewStage){
		int location = 0;
		int division = 0;
		
		if(this.stages.contains(viewStage)){
			for(int i = 0; i < stages.size(); i++){
				if(stages.get(i).equals(viewStage)){
					location = i; 
					break;
				}
			}
			if(this.scrollPane.getHorizontalScrollBar().getVisibleAmount() != this.scrollPane.getHorizontalScrollBar().getMaximum()){
				division = this.scrollPane.getHorizontalScrollBar().getMaximum();
				division = (division/stages.size())*location;
				
				this.scrollPane.getHorizontalScrollBar().setValue(division);
			
			}
		}
	}
}

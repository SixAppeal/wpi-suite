/*******************************************************************************
 * Copyright (c) 2014 -- WPI Suite
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors: Nathan Hughes
 ******************************************************************************/

package edu.wpi.cs.wpisuitetng.modules.taskmanager.presenter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

import edu.wpi.cs.wpisuitetng.modules.taskmanager.localcache.Cache;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.model.Stage;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.model.StageList;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.model.Task;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.view.columnar.TaskView;


/**
 * A presenter that handles updating of task models and task-related views
 *
 * @author wavanrensselaer
 * @author dpseaman
 * @author nhhughes
 * @author akshoop
 */
public class TaskPresenter implements IPresenter{

	private Gateway gateway;
	private Cache cache;
	private LinkedList<TaskView> selectedTasks;
	
	public TaskPresenter(Cache cache) {
		super();
		this.cache = cache;
		this.selectedTasks = new LinkedList<TaskView>();
	}
	
	@Override
	public void setGateway(Gateway gateway) {
		this.gateway = gateway;
	}
	
	public void toolbarCreate() {
		this.gateway.toView("SidebarView", "addCreatePanel");
	}
	
	public void toolbarToggleSidebar() {
		this.gateway.toView("SidebarView", "toggle");
	}
	
	public void editTask(Task task) {
		this.gateway.toView("SidebarView", "addEditPanel", task);
	}
	
	public void addAllToView( Task[] tasks ) {
		this.gateway.toView("ColumnView", "setTasks", new Object[] { tasks }); 
	}
	
	public void notifyMemberHandler() {
		this.gateway.toView("MemberListHandler", "updateAll", cache);
	};
	
	public void updateTasks() {
		Task[] tasks_from_cache =  (Task[]) cache.retrieve("task");
		this.gateway.toView("ColumnView", "setTasks", new Object[] {tasks_from_cache});
	}

	/**
	 * Tells the SidebarView to show the search box
	 */
	public void showSearch() {
		this.gateway.toView("SidebarView", "showSearchBox");
		//this.gateway.toView("ColumnView", "reflow");
	}

	public void setStages() {
		StageList newStages = new StageList( Arrays.asList((Stage[]) cache.retrieve("stages")) );
		this.gateway.toView("ColumnView", "setStages", newStages);
		this.gateway.toView("SidebarView", "setStages", newStages);
	}
	
	public void publishChanges(StageList sl) {
		this.gateway.toPresenter("LocalCache", "update", "stages:testing", sl);
	}
	
	/**
	 * Updates local cache for search engine to search through.
	 */
	public void updateSearch() {
		Task[] tasks_from_cache = (Task[]) cache.retrieve("task");
		Task[] archived_tasks = (Task[]) cache.retrieve("archive");
		ArrayList<Task> all_tasks = concat(tasks_from_cache, archived_tasks);
		this.gateway.toView("SidebarView", "updateSearchBox", all_tasks);
	}
	
	/**
	 * Concatenates two task index arrays.
	 * @param t1 First task array
	 * @param t2 Second task array
	 * @return all_tasks All tasks in a list<task>
	 */
	public ArrayList<Task> concat(Task[] t1, Task[] t2) {
		ArrayList<Task> all_tasks = new ArrayList<Task>(Arrays.asList(t1));
		all_tasks.addAll(Arrays.asList(t2));
		return all_tasks;
	}
	 
	/**
	 * Selects a task view
	 * @param add if true, add the taskview to the list of selections, if false deselect all other tasks
	 * @param taskView the task view to select
	 */
	public void selectTask( Boolean add, TaskView taskView) {
		
		if ( !add ) deselectAllTasks();
		
		taskView.select();
		selectedTasks.add( taskView );
		
	}
	
	/**
	 * Deselects all tasks.
	 */
	public void deselectAllTasks() {
		for (TaskView tv : selectedTasks ) tv.deselect();
		selectedTasks.clear();
	}
}


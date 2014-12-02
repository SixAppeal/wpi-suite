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

import java.util.Arrays;
import java.util.Collection;

import edu.wpi.cs.wpisuitetng.modules.taskmanager.localcache.Cache;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.model.Stage;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.model.StageList;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.model.Task;

public class TaskPresenter implements IPresenter{

	Gateway gateway;
	Cache cache;
	
	public TaskPresenter(Cache cache) {
		super();
		this.cache = cache;
	}
	
	@Override
	public void setGateway(Gateway gateway) {
		this.gateway = gateway;
	}
	
	public void toolbarDefault() {
		this.gateway.toView("SidebarView", "showDefaultPanel");
	}
	
	public void toolbarCreate() {
		this.gateway.toView("SidebarView", "showCreatePanel");
	}
	
	public void viewTask(Task task) {
		this.gateway.toView("SidebarView", "showDetailPanel", task);
	}
	
	public void editTask(Task task) {
		this.gateway.toView("SidebarView", "showEditPanel", task);
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
		this.gateway.toView("ColumnView", "reflow");
	}

	public void updateStages() {
		StageList newStages = new StageList( Arrays.asList((Stage[]) cache.retrieve("stages")) );
		this.gateway.toView("ColumnView", "setStages", newStages);
		//this.gateway.toView("ColumnView", "reflow");
		this.gateway.toView("SidebarView", "updateStages", newStages);
	}
	
	public void publishChanges(StageList sl) {
		this.gateway.toPresenter("LocalCache", "update", "stages", sl);
	}
	
}


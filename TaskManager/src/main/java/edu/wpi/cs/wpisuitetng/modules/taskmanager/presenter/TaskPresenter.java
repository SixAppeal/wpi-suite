package edu.wpi.cs.wpisuitetng.modules.taskmanager.presenter;

import edu.wpi.cs.wpisuitetng.modules.taskmanager.localcache.Cache;
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
	
	public void toolbarCreate() {
		this.gateway.toView("SidebarView", "addCreatePanel");
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
	
	public void updateStages() {
		Task[] tasks_from_cache =  (Task[]) cache.retrieve("task");
		this.gateway.toView("ColumnView", "setTasks", new Object[] {tasks_from_cache});
	}	
}

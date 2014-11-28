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
	
	public void toolbarDefault() {
		this.gateway.toView("SidebarView", "addDefaultPanel");
	}
	
	public void toolbarCreate() {
		this.gateway.toView("SidebarView", "addCreatePanel");
	}
	
	public void viewTask(Task task) {
		this.gateway.toView("SidebarView", "addDetailPanel", task);
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
	
<<<<<<< HEAD
	/**
	 * Archives a task, updating its status in the database
	 * @param t the task to archive
	 */
	public void archiveTask( Task t ) {
		
		gateway.toView("SidebarView", "showDefaultPanel");
		t.archive();
		updateTaskForArchival(t);
		
=======
	public void updateStages() {
		Task[] tasks_from_cache =  (Task[]) cache.retrieve("task");
		this.gateway.toView("ColumnView", "setTasks", new Object[] {tasks_from_cache});
		this.gateway.toView("ColumnView", "reflow");
>>>>>>> start-of-refactor
	}

	
}

//	/**
//	 * Makes a request to the servers to get all members
//	 */
//	public void getMembers() {
//		final Request request = Network.getInstance().makeRequest("core/user", HttpMethod.GET);
//		request.addObserver(new GetMembersObserver(this));
//		request.send();
//	}
//
//	/**
//	 * Caches members retrieved from the server locally and tells the sidebar view that there are new members to incorporate
//	 * @param to_submit
//	 */
//	public void updateMembers(String[] to_submit) {
//		this.members = to_submit;
//		this.gateway.toView("SidebarView", "clearMembers");
//		for (String member : to_submit) {
//			this.gateway.toView("SidebarView", "updateMembers", member); 
//		}
//	}
//
//}

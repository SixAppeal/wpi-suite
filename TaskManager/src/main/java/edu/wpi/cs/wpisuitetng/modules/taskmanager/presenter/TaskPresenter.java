package edu.wpi.cs.wpisuitetng.modules.taskmanager.presenter;

import edu.wpi.cs.wpisuitetng.modules.taskmanager.localcache.Cache;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.model.Task;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.RequestObserver;
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;
import edu.wpi.cs.wpisuitetng.network.models.IRequest;


/**
 * A presenter that handles updating of task models and task-related views
 *
 * @author wavanrensselaer
 * @author dpseaman
 * @author nhhughes
 * @author akshoop
 */

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
	
	public void updateStages() {
		Task[] tasks_from_cache =  (Task[]) cache.retrieve("task");
		this.gateway.toView("ColumnView", "setTasks", new Object[] {tasks_from_cache});
		this.gateway.toView("ColumnView", "reflow");
	}

	/**
	 * Tells the SidebarView to show the search box
	 */
	public void showSearch() {
		this.gateway.toView("SidebarView", "showSearchBox");
	}
}

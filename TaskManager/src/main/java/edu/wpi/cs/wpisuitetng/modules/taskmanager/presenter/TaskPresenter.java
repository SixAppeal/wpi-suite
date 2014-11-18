package edu.wpi.cs.wpisuitetng.modules.taskmanager.presenter;

import java.util.List;

import edu.wpi.cs.wpisuitetng.modules.taskmanager.model.Task;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.model.TaskModel;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;


/**
 * A presenter that handles updating of task models and task-related views
 *
 * @author wavanrensselaer
 * @author dpseaman
 * @author nhhughes
 */

public class TaskPresenter implements IPresenter{

	Gateway gateway;
	Task[] tasks;
	String[] members;
	TaskModel tm;
	
	/**
	 * @see IPresenter.setGateway
	 */
	@Override
	public void setGateway(Gateway gateway) {
		this.gateway = gateway;
	}
	
	/**
	 * Tells the SidebarView to show default form, which is empty
	 */
	public void toolbarDefault() {
		this.gateway.toView("SidebarView", "showDefaultPanel");
	}
	
	/**
	 * Tells the SidebarView to show the form for creating a task
	 */
	public void toolbarCreate() {
		this.gateway.toView("SidebarView", "showCreatePanel");
	}
	
	/**
	 * Creates a new task in the database 
	 * @param task Task to create
	 */
	public void createTask(Task task) {
		
		if( tm == null ) tm = new TaskModel();
		
		task.setId(tm.getNextID());
		
		final Request request = Network.getInstance().makeRequest("taskmanager/task", HttpMethod.PUT);
		request.setBody(task.toJson());
		request.send();
		this.gateway.toView("ColumnView", "addTask", task);
		this.gateway.toView("SidebarView", "showDefaultPanel");
	}
	
	/**
	 * Shows the details of a specific task
	 * @param task Task to view
	 */
	public void viewTask(Task task) {
		this.gateway.toView("SidebarView", "showDetailPanel", task);
	}
	
	/**
	 * Updates a task in the database
	 * @param task Task to update
	 */
	public void updateTask(Task task) {
		final Request request = Network.getInstance().makeRequest("taskmanager/task", HttpMethod.POST);
		request.setBody(task.toJson());
		request.send();
		this.gateway.toView("ColumnView", "removeTask", task);
		this.gateway.toView("ColumnView", "addTask", task);
	}
	
	/**
	 * Opens a task for editing
	 * @param task Task to edit
	 */
	public void editTask(Task task) {
		this.gateway.toView("SidebarView", "showEditPanel", task);
	}
	
	/**
	 * Retrieves all tasks from the database and caches the results and sends them to the task view
	 * @return 
	 */
	public void getAllTasks() {
		final Request request = Network.getInstance().makeRequest("taskmanager/task", HttpMethod.GET);
		request.addObserver(new GetTasksObserver(this));
		request.send();
	}
	
	/**
	 * Retrieves one task from the database
	 * @param id ID of the task to retrieve
	 */
	public void getTask(int id){
		if (tasks == null){
			final Request request = Network.getInstance().makeRequest("taskmanager/task", HttpMethod.GET);
			request.addObserver(new GetTasksObserver(this, id));
			request.send();
		} else {
			for (int i = 0; i < tasks.length; i++) {
				if (tasks[i].getId() == id){
					gateway.toView("DetailView", "taskInfo", tasks[i]);
					return;
				}
			}
			System.err.println("TaskPresenter: Error getting task " + id);
		}
	}

	/**
	 * Makes a request to the servers to get all members
	 */
	public void getMembers() {
		final Request request = Network.getInstance().makeRequest("core/user", HttpMethod.GET);
		request.addObserver(new GetMembersObserver(this));
		request.send();
	}

	/**
	 * Caches members retrieved from the server locally and tells the sidebar view that there are new members to incorporate
	 * @param to_submit
	 */
	public void updateMembers(String[] to_submit) {
		this.members = to_submit;
		this.gateway.toView("SidebarView", "UpdateMembers", (Object []) to_submit); 
		
	}

}
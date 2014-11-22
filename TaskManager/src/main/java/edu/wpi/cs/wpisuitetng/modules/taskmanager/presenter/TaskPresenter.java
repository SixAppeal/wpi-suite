package edu.wpi.cs.wpisuitetng.modules.taskmanager.presenter;

import java.util.List;

import edu.wpi.cs.wpisuitetng.modules.taskmanager.model.Task;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.model.TaskModel;
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
		
		final Request request = Network.getInstance().makeRequest("taskmanager/task", HttpMethod.PUT);
		request.setBody(task.toJson());
		request.addObserver(new RequestObserver() {
			
			@Override
			public void responseSuccess(IRequest iReq) {
				gateway.toView("ColumnView", "addTask", Task.fromJson(iReq.getResponse().getBody()));
			}

			/**
			 * @see RequestObserver.responseError
			 */
			@Override
			public void responseError(IRequest iReq) {
				System.err.println("createTask: Error retrieving all tasks");
				// TODO: send message to view saying there was an error so the view can display it to the user
			}

			/**
			 * @see RequestObserver.fail
			 */
			@Override
			public void fail(IRequest iReq, Exception exception) {
				System.err.println("createTask: Error retrieving all tasks");
				// TODO: send message to view saying there was an error so the view can display it to the user
			}
		});
		request.send();
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
		request.addObserver(new RequestObserver() {
			
			@Override
			public void responseSuccess(IRequest iReq) {
				gateway.toView("ColumnView", "addTask", Task.fromJson(iReq.getResponse().getBody()));
			}

			/**
			 * @see RequestObserver.responseError
			 */
			@Override
			public void responseError(IRequest iReq) {
				System.err.println("updateTask: Error updating task");
				// TODO: send message to view saying there was an error so the view can display it to the user
			}

			/**
			 * @see RequestObserver.fail
			 */
			@Override
			public void fail(IRequest iReq, Exception exception) {
				System.err.println("updateTask: Error updating task");
				// TODO: send message to view saying there was an error so the view can display it to the user
			}
		});
		request.send();
		this.gateway.toView("ColumnView", "removeTask", task);
		
	}
	
	/**
	 * Updates a task in the database without readding it to the view
	 * @param task Task to update
	 */
	public void updateTaskForArchival(Task task) {
		final Request request = Network.getInstance().makeRequest("taskmanager/task", HttpMethod.POST);
		request.setBody(task.toJson());
		request.addObserver(new RequestObserver() {
			
			@Override
			public void responseSuccess(IRequest iReq) {
				gateway.toView("ColumnView", "removeTask", Task.fromJson(iReq.getResponse().getBody()));
			}

			/**
			 * @see RequestObserver.responseError
			 */
			@Override
			public void responseError(IRequest iReq) {
				System.err.println("updateTask: Error updating task");
				// TODO: send message to view saying there was an error so the view can display it to the user
			}

			/**
			 * @see RequestObserver.fail
			 */
			@Override
			public void fail(IRequest iReq, Exception exception) {
				System.err.println("updateTask: Error updating task");
				// TODO: send message to view saying there was an error so the view can display it to the user
			}
		});
		request.send();
		this.gateway.toView("ColumnView", "removeTask", task);
		
	}
	
	/**
	 * Opens a task for editing
	 * @param task Task to edit
	 */
	public void editTask(Task task) {
		this.gateway.toView("SidebarView", "showEditPanel", task);
	}
	/**
	 * Removes all tasks from the view. This can never be called to archive all tasks, so it need not
	 * communicate with the database.
	 */
	public void removeAllTasks() {
		this.gateway.toView("ColumnView", "removeAllTasks");
		//this.gateway.toView("ColumnView", "refreshView");
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
	 * Adds every task in the array to the view.
	 * @param tasks An array of tasks to be added
	 */
	public void addAllToView( Task[] tasks ) {
		for( Task t : tasks) addToView(t);
		this.gateway.toView("ColumnView", "refreshView");
	}
	
	/**
	 * Adds a task to the view.
	 * @param t the task to add
	 */
	public void addToView( Task t ) {
		if( !t.isArchived() ) this.gateway.toView("ColumnView", "addTask", t);
	}
	
	/**
	 * Archives a task, updating its status in the database
	 * @param t the task to archive
	 */
	public void archiveTask( Task t ) {
		
		gateway.toView("SidebarView", "showDefaultPanel");
		t.archive();
		updateTaskForArchival(t);
		
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
		this.gateway.toView("SidebarView", "clearMembers");
		for (String member : to_submit) {
			this.gateway.toView("SidebarView", "updateMembers", member); 
		}
	}

}

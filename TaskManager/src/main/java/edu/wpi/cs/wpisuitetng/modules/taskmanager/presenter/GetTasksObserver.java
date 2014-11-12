package edu.wpi.cs.wpisuitetng.modules.taskmanager.presenter;

import edu.wpi.cs.wpisuitetng.network.RequestObserver;
import edu.wpi.cs.wpisuitetng.network.models.IRequest;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.model.Task;
import com.google.gson.Gson;

/**
 * An observer for a request made to get all the tasks over the network
 * 
 * @author wavanrensselaer
 * @author dpseaman
 */
public class GetTasksObserver implements RequestObserver {
	
	private int id;

	private TaskPresenter presenter;
	
	/**
	 * Constructs an observer for a request that gets all the tasks
	 * @param presenter The presenter that's sending the request 
	 */
	public GetTasksObserver(TaskPresenter presenter) {
		this.presenter = presenter;
		id = -1;
	}
	
	/**
	 * Constructs an observer for a request that gets a specific task
	 * @param presenter The presenter that's sending the request
	 * @param id ID of the specific task
	 */
	public GetTasksObserver(TaskPresenter presenter, int id) {
		this.presenter = presenter;
		this.id = id;
	}
	
	/**
	 * Cache the tasks returned from the network
	 * @see RequestObserver.responseSuccess
	 */
	@Override
	public void responseSuccess(IRequest iReq) {
		Task[] tasks = new Gson().fromJson(iReq.getResponse().getBody(), Task[].class);
		presenter.tasks = tasks;
		if (id >= 0) {
			presenter.getTask(id);
		}
	}

	/**
	 * @see RequestObserver.responseError
	 */
	@Override
	public void responseError(IRequest iReq) {
		System.err.println("GetTaskObserver: Error retrieving all tasks");
		// TODO: send message to view saying there was an error so the view can display it to the user
	}

	/**
	 * @see RequestObserver.fail
	 */
	@Override
	public void fail(IRequest iReq, Exception exception) {
		System.err.println("GetTaskObserver: Error retrieving all tasks");
		// TODO: send message to view saying there was an error so the view can display it to the user
	}

}

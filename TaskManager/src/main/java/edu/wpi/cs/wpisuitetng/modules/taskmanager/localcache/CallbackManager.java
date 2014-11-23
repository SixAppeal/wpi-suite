package edu.wpi.cs.wpisuitetng.modules.taskmanager.localcache;

import edu.wpi.cs.wpisuitetng.modules.taskmanager.presenter.Gateway;
import edu.wpi.cs.wpisuitetng.network.RequestObserver;
import edu.wpi.cs.wpisuitetng.network.models.IRequest;

public class CallbackManager implements RequestObserver {

	private Gateway gateway;
	private String callback;
	
	public CallbackManager(Gateway gateway, String callback) {
		this.gateway = gateway;
		this.callback = callback;
	}
	
	@Override
	public void responseSuccess(IRequest iReq) {
		//do stuff involving callbacks
	}

	/**
	 * @see RequestObserver.responseError
	 */
	@Override
	public void responseError(IRequest iReq) {
		System.err.println("updateTask: Error updating task");
		//invalidate caching operation
	}

	/**
	 * @see RequestObserver.fail
	 */
	@Override
	public void fail(IRequest iReq, Exception exception) {
		System.err.println("updateTask: Error updating task");
		//invalidate caching operation
	}
}
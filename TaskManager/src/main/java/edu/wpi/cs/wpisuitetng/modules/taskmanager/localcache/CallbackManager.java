package edu.wpi.cs.wpisuitetng.modules.taskmanager.localcache;

import java.util.List;

import edu.wpi.cs.wpisuitetng.modules.taskmanager.presenter.Gateway;
import edu.wpi.cs.wpisuitetng.network.RequestObserver;
import edu.wpi.cs.wpisuitetng.network.models.IRequest;

public class CallbackManager implements RequestObserver {

	private Gateway gateway;
	private List<String> callbacks;
	
	public CallbackManager(Gateway gateway, List<String> callbacks) {
		this.gateway = gateway;
		this.callbacks = callbacks;
	}
	
	/**
	 * @see RequestObserver.responseSuccess
	 */
	@Override
	public void responseSuccess(IRequest iReq) {
		for (String s: callbacks) {
			gateway.toView(s.split(":")[0], s.split(":")[1]);
		}
	}

	/**
	 * @see RequestObserver.responseError
	 */
	@Override
	public void responseError(IRequest iReq) {
		System.err.println("updateTask: Error updating task");
	}

	/**
	 * @see RequestObserver.fail
	 */
	@Override
	public void fail(IRequest iReq, Exception exception) {
		System.err.println("updateTask: Error updating task");
	}
}
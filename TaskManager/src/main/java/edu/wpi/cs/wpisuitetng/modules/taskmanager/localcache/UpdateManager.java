package edu.wpi.cs.wpisuitetng.modules.taskmanager.localcache;

import java.util.List;

import edu.wpi.cs.wpisuitetng.modules.taskmanager.presenter.Gateway;
import edu.wpi.cs.wpisuitetng.network.RequestObserver;
import edu.wpi.cs.wpisuitetng.network.models.IRequest;

public class UpdateManager implements RequestObserver {

	private Cache localCache;
	private String toUpdate;
	private Gateway gateway;
	private List<String> callbacks;
	
	public UpdateManager(Cache localCache, String toUpdate, Gateway gateway, List<String> callbacks) {
		this.localCache = localCache;
		this.toUpdate = toUpdate;
		this.gateway = gateway;
		this.callbacks = callbacks;
	}

	@Override
	public void responseSuccess(IRequest iReq) {
		localCache.updateVerified(toUpdate, iReq.getBody());
		for (String s: callbacks) {
			gateway.toPresenter(s.split(":")[0], s.split(":")[1]);
		}
	}

	/**
	 * @see RequestObserver.responseError
	 */
	@Override
	public void responseError(IRequest iReq) {
		System.err.println("Error Refreshing Cache for" + toUpdate);
	}

	/**
	 * @see RequestObserver.fail
	 */
	@Override
	public void fail(IRequest iReq, Exception exception) {
		System.err.println("Error Refreshing Cache for" + toUpdate);
	}

}
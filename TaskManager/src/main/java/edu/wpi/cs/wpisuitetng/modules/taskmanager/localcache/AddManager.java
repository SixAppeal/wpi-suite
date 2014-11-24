package edu.wpi.cs.wpisuitetng.modules.taskmanager.localcache;

import edu.wpi.cs.wpisuitetng.network.RequestObserver;
import edu.wpi.cs.wpisuitetng.network.models.IRequest;

public class AddManager implements RequestObserver {

	private Cache localCache;
	private String toUpdate;

	public AddManager(Cache localCache, String toUpdate) {
		this.localCache = localCache;
		this.toUpdate = toUpdate;
	}

	
	@Override
	public void responseSuccess(IRequest iReq) {
		localCache.addVerified(toUpdate, iReq.getBody());
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

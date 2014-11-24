package edu.wpi.cs.wpisuitetng.modules.taskmanager.localcache;

import edu.wpi.cs.wpisuitetng.network.RequestObserver;
import edu.wpi.cs.wpisuitetng.network.models.IRequest;

public class UpdateManager implements RequestObserver {

	private Cache localCache;
	private String toUpdate;
	private Object oldObject;

	public UpdateManager(Cache localCache, String toUpdate, Object oldObject) {
		this.localCache = localCache;
		this.toUpdate = toUpdate;
		this.oldObject = oldObject;
	}

	@Override
	public void responseSuccess(IRequest iReq) {
		localCache.updateVerified(toUpdate, iReq.getBody(), oldObject);
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

/**
 * 
 */
package edu.wpi.cs.wpisuitetng.modules.taskmanager.localcache;

import java.util.List;

import edu.wpi.cs.wpisuitetng.modules.taskmanager.presenter.Gateway;
import edu.wpi.cs.wpisuitetng.network.RequestObserver;
import edu.wpi.cs.wpisuitetng.network.models.IRequest;

/**
 * @author nathan
 *
 */
public class SyncManager implements RequestObserver {
	
	private Cache localCache;
	private String toUpdate;
	private List<String> callbacks;
	private Gateway gateway;
	
	public SyncManager(Cache localCache, String toUpdate, List<String> callbacks, Gateway gateway) {
		super();
		this.localCache = localCache;
		this.toUpdate = toUpdate;
		this.callbacks = callbacks;
		this.gateway = gateway;
	}
	
	@Override
	public void responseSuccess(IRequest iReq) {
		localCache.set(toUpdate, iReq.getResponse().getBody());
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

package edu.wpi.cs.wpisuitetng.modules.taskmanager.localcache;

import java.util.ArrayList;
import java.util.List;

import edu.wpi.cs.wpisuitetng.modules.taskmanager.presenter.Gateway;
import edu.wpi.cs.wpisuitetng.network.RequestObserver;
import edu.wpi.cs.wpisuitetng.network.models.IRequest;

public class AddManager implements RequestObserver {

	//private Cache localCache;
	private String toUpdate;
	private Gateway gateway;
	private List<String> callbacks;
	
	public AddManager(Cache localCache, String toUpdate, Gateway gateway, List<String> callbacks) {
		//this.localCache = localCache;
		this.toUpdate = toUpdate;
		this.callbacks = callbacks;
		this.gateway = gateway;

	}

	public AddManager(Cache localCache, String toUpdate, Gateway gateway, String RevocationCallback) {
		//this.localCache = localCache;
		this.toUpdate = toUpdate;
		this.callbacks = new ArrayList<String>();
		//this.callbacks.add(RevocationCallback);
		this.gateway = gateway; 
		//TODO 
	}
	
	@Override
	public void responseSuccess(IRequest iReq) {
		for (String s: callbacks) {
			String temp1 = s.split(":")[0];
			String temp2 = s.split(":")[1];
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

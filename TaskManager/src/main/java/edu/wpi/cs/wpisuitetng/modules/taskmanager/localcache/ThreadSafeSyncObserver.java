package edu.wpi.cs.wpisuitetng.modules.taskmanager.localcache;

import edu.wpi.cs.wpisuitetng.modules.taskmanager.presenter.Gateway;
import edu.wpi.cs.wpisuitetng.network.RequestObserver;
import edu.wpi.cs.wpisuitetng.network.models.IRequest;

public class ThreadSafeSyncObserver implements RequestObserver {

	private Gateway gateway;
	
	public ThreadSafeSyncObserver (Gateway gateway) {
		this.gateway = gateway;
	}
		
	@Override
	public void responseSuccess(IRequest iReq) {
		String[] splitUrl = iReq.getUrl().toString().split("/");
		String type = splitUrl[splitUrl.length - 1];
		if (type.equals("task")) {
			this.gateway.toPresenter("LocalCache", "updateTasks", iReq.getResponse().getBody());
		}
		if (type.equals("user")) {
			this.gateway.toPresenter("LocalCache", "updateMembers", iReq.getResponse().getBody());
		}
		if (type.equals("stages")) {
			this.gateway.toPresenter("LocalCache", "updateStages", iReq.getResponse().getBody());
		}
		if (type.equals("requirement")) {
			this.gateway.toView("SidebarView", "passInRequirements", iReq.getResponse().getBody());
		}
	}

	/**
	 * @see RequestObserver.responseError
	 */
	@Override
	public void responseError(IRequest iReq) {
		System.err.println("Error syncing with cache");
	}

	/**
	 * @see RequestObserver.fail
	 */
	@Override
	public void fail(IRequest iReq, Exception exception) {
		System.err.println("Error syncing with cache");
	}
}

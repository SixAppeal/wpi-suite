package edu.wpi.cs.wpisuitetng.modules.taskmanager.localcache;

import edu.wpi.cs.wpisuitetng.modules.taskmanager.presenter.Gateway;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.RequestObserver;
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;
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
		if (type.equals("task2")) {
			System.out.println("Long Pull Request Began!");
			ThreadSafeSyncObserver syncer = new ThreadSafeSyncObserver(this.gateway);
			final Request networkRequest = Network.getInstance().makeRequest("Advanced/taskmanager/task2", HttpMethod.GET);
			networkRequest.addObserver(syncer);
			networkRequest.setReadTimeout(50000);
			networkRequest.send();
			this.gateway.toPresenter("LocalCache", "updateTasks", iReq.getResponse().getBody());
			System.out.println("Long Pull Request Completed!");
			
		}
		if (type.equals("user")) {
			this.gateway.toPresenter("LocalCache", "updateMembers", iReq.getResponse().getBody());
		}
		if (type.equals("stages")) {
			this.gateway.toPresenter("LocalCache", "updateStages", iReq.getResponse().getBody());
		}
	}

	/**
	 * @see RequestObserver.responseError
	 */
	@Override
	public void responseError(IRequest iReq) {
		System.err.println(iReq.getUrl().getPath());
		//System.err.println("Error syncing with cache");
	}

	/**
	 * @see RequestObserver.fail
	 */
	@Override
	public void fail(IRequest iReq, Exception exception) {
		System.out.println(iReq.getUrl().getPath());
		exception.printStackTrace();
		//System.err.println("Error syncing with cache");
	}
}

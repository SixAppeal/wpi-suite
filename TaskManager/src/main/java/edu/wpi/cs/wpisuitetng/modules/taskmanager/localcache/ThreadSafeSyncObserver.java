/*******************************************************************************
 * Copyright (c) 2014 -- WPI Suite
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors: Team Six-Appeal
 ******************************************************************************/

package edu.wpi.cs.wpisuitetng.modules.taskmanager.localcache;

import edu.wpi.cs.wpisuitetng.modules.taskmanager.presenter.Gateway;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.RequestObserver;
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;
import edu.wpi.cs.wpisuitetng.network.models.IRequest;

/**
 * Request observer to deal with long polling / that syncs with everything in a more intelligent manager
 * @author nhhughes
 *
 */
public class ThreadSafeSyncObserver implements RequestObserver {

	private Gateway gateway;

	/**
	 * Construct a sync observer with a copy of the gateway
	 * @param gateway copy of the gateway
	 */
	public ThreadSafeSyncObserver (Gateway gateway) {
		this.gateway = gateway;
	}

	
	/**
	 * On success of a long poll request, initiate a new request.
	 * On every success, notify the presenter accordingly 
	 */
	@Override
	public void responseSuccess(IRequest iReq) {
		String[] splitUrl = iReq.getUrl().toString().split("/");
		String type = splitUrl[splitUrl.length - 1];
		if (type.equals("task2")) {
			ThreadSafeSyncObserver syncer = new ThreadSafeSyncObserver(this.gateway);
			final Request networkRequest = Network.getInstance().makeRequest("Advanced/taskmanager/task2", HttpMethod.GET);
			networkRequest.addObserver(syncer);
			networkRequest.setReadTimeout(0);
			networkRequest.send();
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
		System.err.println(iReq.getUrl().getPath());
	}

	/**
	 * @see RequestObserver.fail
	 */
	@Override
	public void fail(IRequest iReq, Exception exception) {
		exception.printStackTrace();
	}
}

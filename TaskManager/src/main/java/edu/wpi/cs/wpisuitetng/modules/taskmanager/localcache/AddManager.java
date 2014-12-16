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

import java.util.ArrayList;
import java.util.List;

import edu.wpi.cs.wpisuitetng.modules.taskmanager.presenter.Gateway;
import edu.wpi.cs.wpisuitetng.network.RequestObserver;
import edu.wpi.cs.wpisuitetng.network.models.IRequest;

/**
 * Request observer to track an add operation for the cache
 * @author nhhughes
 *
 */
public class AddManager implements RequestObserver {

	//private Cache localCache;
	private String toUpdate;
	private Gateway gateway;
	private List<String> callbacks;
	
	/**
	 * Initialize the observer with a copy of the cache and the gateway 
	 * @param localCache cache to use
	 * @param toUpdate type of object being added
	 * @param gateway gateway to use
	 * @param callbacks methods to be called upon success
	 */
	public AddManager(Cache localCache, String toUpdate, Gateway gateway, List<String> callbacks) {
		//this.localCache = localCache;
		this.toUpdate = toUpdate;
		this.callbacks = callbacks;
		this.gateway = gateway;

	}

	/**
	 * Initialize the observer with a copy of the cache and the gateway plus a revocation callback
	 * @param localCache cache to use
	 * @param toUpdate type of object being added
	 * @param gateway gateway to use
	 * @param RevocationCallback callback method when something goes wrong
	 */
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

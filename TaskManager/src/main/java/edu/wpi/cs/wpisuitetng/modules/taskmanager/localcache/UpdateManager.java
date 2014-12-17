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
 * A request observer to deal with updates to an object request by the view
 * @author nhhughes
 *
 */
public class UpdateManager implements RequestObserver {
	private String toUpdate;
	private Gateway gateway;
	private List<String> callbacks;
	
	/**
	 * Makes an update manager with copies of the gateway, the local cache, and the type of object it's updating 
	 * @param toUpdate type to update (task, member, etc..)
	 * @param gateway gateway to use
	 * @param callbacks strings representing classes to notify via. the gateway
	 */
	public UpdateManager(String toUpdate, Gateway gateway, List<String> callbacks) {
		this.toUpdate = toUpdate;
		this.gateway = gateway;
		this.callbacks = callbacks;
	}

	/**
	 * Makes an update manager with copies of the gateway and the local cache
	 * @param toUpdate type to update (task, member, etc...)
	 * @param gateway gateway to use
	 * @param revocationCallback method to call in the presenter
	 */
	public UpdateManager(String toUpdate, Gateway gateway, String revocationCallback) {
		this.toUpdate = toUpdate;
		this.gateway = gateway;
		this.callbacks = new ArrayList<String>();
	}
	
	/**
	 * Run all callbacks associated with the update manager 
	 */
	@Override
	public void responseSuccess(IRequest iReq) {
		for (String s: callbacks) {
			gateway.toPresenter(s.split(":")[0], s.split(":")[1]);
		}
	}

	/**
	 * @see RequestObserver.responseError
	 */
	@Override
	public void responseError(IRequest iReq) {
		System.err.println("Error Refreshing Cache for " + toUpdate);
	}

	/**
	 * @see RequestObserver.fail
	 */
	@Override
	public void fail(IRequest iReq, Exception exception) {
		System.err.println("Error Refreshing Cache for " + toUpdate);
	}

}

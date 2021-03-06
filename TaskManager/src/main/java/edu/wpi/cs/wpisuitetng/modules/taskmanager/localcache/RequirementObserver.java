/*******************************************************************************
 * Copyright (c) 2014 -- WPI Suite
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors: 
 * Alexander Shoop
 ******************************************************************************/

package edu.wpi.cs.wpisuitetng.modules.taskmanager.localcache;

import java.util.List;

import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.Requirement;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.presenter.Gateway;
import edu.wpi.cs.wpisuitetng.network.RequestObserver;
import edu.wpi.cs.wpisuitetng.network.models.IRequest;

/**
 * This class only retrieves the Requirement array from the Requirements manager
 * 
 * @author akshoop
 */
public class RequirementObserver implements RequestObserver {

	private Gateway gateway;

	public RequirementObserver(Gateway gateway) {
		this.gateway = gateway;
	}

	@Override
	public void responseSuccess(IRequest iReq) {
		String requirements = iReq.getResponse().getBody();

		gateway.toView("SidebarView", "passInRequirements", requirements);
	}

	/**
	 * @see RequestObserver.responseError
	 */
	@Override
	public void responseError(IRequest iReq) {
		// Auto generated
	}

	/**
	 * @see RequestObserver.fail
	 */
	@Override
	public void fail(IRequest iReq, Exception exception) {
		// Auto generated
	}
}

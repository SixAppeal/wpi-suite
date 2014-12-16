/*******************************************************************************
 * Copyright (c) 2014 -- WPI Suite
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors: William Van Rensselaer Dan Seaman
 ******************************************************************************/
package edu.wpi.cs.wpisuitetng.modules.taskmanager.presenter;


/**
 * This class exists for the sole purpose of testing the Gateway.
 * 
 * @author wavanrensselaer
 * @author dpseaman
 */
public class TestPresenter implements IPresenter {
	int number;
	boolean bool;
	Gateway gateway;
	
	/**
	 * Constructs a TestPresenter
	 */
	public TestPresenter() {
		this.number = 0;
		this.bool = true;
	}
	
	/**
	 * @see IPresenter.setGateway
	 */
	@Override
	public void setGateway(Gateway gateway) {
		this.gateway = gateway;
	}
	
	/**
	 * A test method for a view to call through the gateway
	 * @param data Data passed to the presenter
	 */
	public void testMethod(Integer data, Boolean bool) {
		this.number = data.intValue();
		this.bool = bool;
	}
	
	/**
	 * Gets the number passed to this presenter from the gateway.
	 * @return A number
	 */
	public int getNumber() {
		return this.number;
	}
	
	/**
	 * Gets the boolean passed to this presenter from the gateway.
	 * @return A boolean
	 */
	public boolean getBool() {
		return this.bool;
	}
}

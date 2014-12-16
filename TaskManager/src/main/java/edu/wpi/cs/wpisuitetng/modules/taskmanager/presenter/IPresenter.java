/*******************************************************************************
 * Copyright (c) 2014 -- WPI Suite
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors: William Van Rensselaer, Dan Seaman
 ******************************************************************************/
package edu.wpi.cs.wpisuitetng.modules.taskmanager.presenter;


/**
 * Presenter interface that all presenters implement
 * 
 * @author wavanrensselaer
 * @author dpseaman
 */
public interface IPresenter {
	/**
	 * This should set the gateway to be used by the presenter
	 * @param gateway A Gateway object
	 */
	public void setGateway(Gateway gateway);
}

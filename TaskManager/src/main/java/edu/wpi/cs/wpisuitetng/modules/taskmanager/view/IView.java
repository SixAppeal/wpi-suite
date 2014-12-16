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
package edu.wpi.cs.wpisuitetng.modules.taskmanager.view;

import edu.wpi.cs.wpisuitetng.modules.taskmanager.presenter.Gateway;

/**
 * View interface that all views implement
 * 
 * @author wavanrensselaer
 * @author dpseaman
 */
public interface IView {
	/**
	 * This method should set the gateway to be used by the view
	 * @param gateway A Gateway object
	 */
	public void setGateway(Gateway gateway);
}

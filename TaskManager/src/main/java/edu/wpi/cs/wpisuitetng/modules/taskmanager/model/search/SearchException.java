/*******************************************************************************
 * 
 * Copyright (c) 2014 -- WPI Suite
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors: Nathan Hughes, Alexander Shoop
 ******************************************************************************/

package edu.wpi.cs.wpisuitetng.modules.taskmanager.model.search;

/**
 * Exception for Search class
 * 
 * @author akshoop
 * @author nhhughes
 */
public class SearchException extends Exception {
	/**
	 * Generated serial number
	 */
	private static final long serialVersionUID = 7079667172386275927L;

	/**
	 * The only constructor. There must be a message.
	 * @param message the reason for throwing the exception
	 */
	public SearchException(String message)
	{
		super(message);
	}
}

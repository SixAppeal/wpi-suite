/*******************************************************************************
 * Copyright (c) 2014 -- WPI Suite
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors: Six-Appeal
 ******************************************************************************/
package edu.wpi.cs.wpisuitetng.modules.taskmanager.trello;

/**
 * Trello board for importing
 * @author wavanrensselaer
 */
public class TrelloBoard {
	private String id;
	private String name;
	
	/**
	 * Constructor
	 */
	public TrelloBoard() {
		id = "";
		name = "";
	}
	
	/**
	 * Gets the ID of the Trello board
	 * @return
	 */
	public String getId() {
		return id;
	}
	
	/**
	 * Gets the name of the Trello board
	 * @return
	 */
	public String getName() {
		return name;
	}
	
	@Override
	public String toString() {
		return name;
	}
}

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
package edu.wpi.cs.wpisuitetng.modules.taskmanager.trello;

/**
 * Trello list for importing
 * @author wavanrensselaer
 */
public class TrelloList {
	String id;
	String name;
	
	/**
	 * Creates a Trello list
	 */
	public TrelloList() {
		id = "";
		name = "";
	}
	
	/**
	 * Gets the ID of the Trello list
	 * @return The ID
	 */
	public String getId() {
		return id;
	}
	
	/**
	 * Gets the name of the Trello list
	 * @return
	 */
	public String getName() {
		return name;
	}
}

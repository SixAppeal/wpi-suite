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
 * Trello card for importing
 * @author wavanrensselaer
 */
public class TrelloCard {
	private String name;
	private String desc;
	private String due;
	private String idList;
	
	/**
	 * Creates a Trello card
	 */
	public TrelloCard() {
		name = "";
		desc = "";
		due = "";
		idList = "";
	}
	
	/**
	 * Gets the name of the Trello card
	 * @return The name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Gets the description of the Trello card
	 * @return The description
	 */
	public String getDesc() {
		return desc;
	}
	
	/**
	 * Gets the due date of the Trello card
	 * @return The due date
	 */
	public String getDue() {
		return due;
	}
	
	/**
	 * Gets the list ID of the card
	 * @return The list ID of the card
	 */
	public String getIdList() {
		return idList;
	}
}

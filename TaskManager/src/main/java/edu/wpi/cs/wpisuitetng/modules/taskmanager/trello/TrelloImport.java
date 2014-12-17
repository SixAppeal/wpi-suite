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

import java.util.ArrayList;
import java.util.List;

/**
 * Trello import of cards and lists
 * @author wavanrensselaer
 */
public class TrelloImport {
	private List<TrelloCard> cards;
	private List<TrelloList> lists;
	
	/**
	 * Creates a Trello import
	 */
	public TrelloImport() {
		cards = new ArrayList<TrelloCard>();
		lists = new ArrayList<TrelloList>();
	}
	
	/**
	 * Gets the cards from the import
	 * @return A list of Trello cards
	 */
	public List<TrelloCard> getCards() {
		return cards;
	}
	
	/**
	 * Gets the lists from the import
	 * @return A list of Trello lists
	 */
	public List<TrelloList> getLists() {
		return lists;
	}
}

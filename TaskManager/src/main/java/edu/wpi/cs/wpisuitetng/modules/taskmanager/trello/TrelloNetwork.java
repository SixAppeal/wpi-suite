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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import javax.swing.SwingUtilities;

import com.google.gson.Gson;

import edu.wpi.cs.wpisuitetng.modules.taskmanager.presenter.Gateway;
import edu.wpi.cs.wpisuitetng.network.Network;

/**
 * Class for importing from Trello
 * @author wavanrensselaer
 */
public class TrelloNetwork extends Network {
	private static TrelloNetwork instance = null;
	
	protected TrelloNetwork() {
	}
	
	/**
	 * Begins the import from Trello
	 */
	public void beginImport(Gateway gateway) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				TrelloBrowser browser = new TrelloBrowser();
				browser.setCallback(new Runnable() {
					@Override
					public void run() {
						String token = browser.getToken();
						chooseBoard(token, gateway);
						browser.dispose();
					}
				});
			}
		});
	}
	
	/**
	 * Gets the Trello boards associated with a user's account and prompts
	 * for them to pick one to import from.
	 * @param token
	 */
	private void chooseBoard(String token, Gateway gateway) {
		String boardsRes = makeRequest("https://api.trello.com/1/members/my/boards?key=" + TrelloBrowser.KEY + "&token=" + token);
		if (boardsRes != null) {
			TrelloBoard[] boards = new Gson().fromJson(boardsRes, TrelloBoard[].class);
			gateway.toView("ToolbarView", "chooseTrelloBoard", boards, token);
		}
	}

	/**
	 * Imports the cards from a trello board
	 * @param board The board to import from
	 * @param token The user authorized token
	 * @param gateway The task manager gateway
	 */
	public void importCards(TrelloBoard board, String token, Gateway gateway) {
		String cardsRes = makeRequest("https://api.trello.com/1/board/" + board.getId() + "?key=" + TrelloBrowser.KEY + "&token=" + token + "&cards=open&lists=open");
		if (cardsRes != null) {
			TrelloImport trelloImport = new Gson().fromJson(cardsRes, TrelloImport.class);
			gateway.toPresenter("LocalCache", "trelloImport", trelloImport);
		}
	}
	
	/**
	 * Makes a request and returns the result of that request in a string.
	 * @param path The url to send the request to
	 * @return The response from the server or null if something went wrong
	 */
	public static String makeRequest(String path) {
		try {
			URL url = new URL(path);
			URLConnection connection = url.openConnection();
			BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			
			String result = "";
			String input;
			
			while ((input = br.readLine()) != null) {
				result += input;
			}
			
			br.close();
			return result;
			
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static TrelloNetwork getInstance() {
		if (instance == null) {
			instance = new TrelloNetwork();
		}
		return instance;
	}
}
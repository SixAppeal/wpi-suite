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
package edu.wpi.cs.wpisuitetng.network;

import javax.swing.SwingUtilities;

import edu.wpi.cs.wpisuitetng.modules.taskmanager.view.components.WebBrowser;

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
	public void beginImport() {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				WebBrowser browser = new WebBrowser();
				browser.setVisible(true);
				browser.load("http://google.com/");
			}
		});
	}

	/**
	 * Returns the singleton instance of TrelloNetwork.
	 * 
	 * @return The singleton instance of TrelloNetwork.
	 */
	public static TrelloNetwork getInstance() {
		if (instance == null) {
			instance = new TrelloNetwork();
		}
		return instance;
	}
}
/*******************************************************************************
 * Copyright (c) 2014 -- WPI Suite
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors: SixAppeal
 ******************************************************************************/
package edu.wpi.cs.wpisuitetng.network;

import javax.swing.SwingUtilities;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

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
				browser.load("https://trello.com/1/authorize?key=418e452053382c690ed313f528950133&name=WPI+Suite+Task+Manager&expiration=30days&response_type=token");
			}
		});
	}
	
	/**
	 * Listener to find out when the user has recieved their token
	 * @author wavanrensselaer
	 * @param <String>
	 */
	private class TrelloChangeListener implements ChangeListener<String> {
		@Override
		public void changed(ObservableValue<? extends String> ov, String oldValue, final String newValue) {
			
		}
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
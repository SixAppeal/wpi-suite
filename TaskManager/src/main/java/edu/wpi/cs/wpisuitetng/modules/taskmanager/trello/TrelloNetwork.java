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

import javax.swing.SwingUtilities;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker.State;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.view.components.WebBrowser;
import edu.wpi.cs.wpisuitetng.network.Network;

/**
 * Class for importing from Trello
 * @author wavanrensselaer
 */
public class TrelloNetwork extends Network {
	private static TrelloNetwork instance = null;
	
	private WebBrowser browser;
	
	protected TrelloNetwork() {
	}
	
	/**
	 * Begins the import from Trello
	 */
	public void beginImport() {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				browser = new WebBrowser();
				
				browser.setVisible(true);
				browser.load("https://trello.com/1/authorize?key=418e452053382c690ed313f528950133&name=WPI+Suite+Task+Manager&expiration=30days&response_type=token");
			}
		});
	}
	
	public static TrelloNetwork getInstance() {
		if (instance == null) {
			instance = new TrelloNetwork();
		}
		return instance;
	}
}
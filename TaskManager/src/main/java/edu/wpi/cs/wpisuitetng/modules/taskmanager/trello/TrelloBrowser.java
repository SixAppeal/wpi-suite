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
package edu.wpi.cs.wpisuitetng.modules.taskmanager.trello;

import javax.swing.SwingUtilities;

import org.w3c.dom.NodeList;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker.State;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.view.components.WebBrowser;

/**
 * A browser for interacting with Trello OAuth
 * @author wavanrensselaer
 */
public class TrelloBrowser extends WebBrowser implements ChangeListener<State> {
	private static final long serialVersionUID = 118075172830828523L;

	public static final String KEY = "418e452053382c690ed313f528950133";
	
	private String token;
	private Runnable callback;
	
	/**
	 * Creates a Trello browser
	 */
	public TrelloBrowser() {
		super();
		
		this.callback = null;
		this.token = null;

		setVisible(true);
		
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				getEngine().getLoadWorker().stateProperty().addListener(TrelloBrowser.this);
				
				load("https://trello.com/1/authorize?key=" + KEY
						+ "&name=WPI+Suite+Task+Manager&expiration=30days&response_type=token");
			}
		});
	}
	
	/**
	 * Sets the thread to be run when the token has been extracted.
	 * @param callback A runnable thread
	 */
	public void setCallback(Runnable callback) {
		this.callback = callback;
	}
	
	/**
	 * Gets the token extracted from the OAuth process
	 * @return A token
	 */
	public String getToken() {
		return token;
	}

	@Override
	public void changed(ObservableValue<? extends State> value, State oldState,
			State newState) {
		if (newState == State.SUCCEEDED) {
			if (getEngine().getLocation().equals("https://trello.com/1/token/approve")) {
				NodeList nodes = getEngine().getDocument().getElementsByTagName("pre");
				if (nodes.getLength() > 0) {
					token = nodes.item(0).getTextContent().trim();
					SwingUtilities.invokeLater(callback);
				}
			}
		}
	}
}

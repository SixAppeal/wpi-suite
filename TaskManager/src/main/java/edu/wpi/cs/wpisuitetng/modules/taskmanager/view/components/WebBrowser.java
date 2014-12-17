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
package edu.wpi.cs.wpisuitetng.modules.taskmanager.view.components;

import java.awt.Dimension;
import java.net.MalformedURLException;
import java.net.URL;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

import javax.swing.JFrame;

/**
 * An embedable browser frame
 * @author wavanrensselaer
 */
public class WebBrowser extends JFrame {
	private static final long serialVersionUID = 6976797986355517950L;
	
	private JFXPanel window;
	private WebView view;
	private WebEngine engine;
	
	/**
	 * Creates a Browser
	 */
	public WebBrowser() {
		window = new JFXPanel();
		createScene();
		
		getContentPane().add(window);
		
		setPreferredSize(new Dimension(800, 600));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		pack();
	}
	
	/**
	 * Creates the scene for the browser
	 */
	private void createScene() {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				view = new WebView();
				engine = view.getEngine();
				window.setScene(new Scene(view));
			}
		});
	}
	
	/**
	 * Loads a URL in the browser
	 * @param url The URL to load
	 */
	public void load(final String url) {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				String tmpUrl = toURL(url);
				
				if (tmpUrl == null) {
					tmpUrl = toURL("http://" + url);
				}
				
				engine.load(tmpUrl);
			}
		});
	}
	
	/**
	 * Sanitizes a URL string
	 * @param url A URL string
	 * @return The sanitized URL
	 */
	private static String toURL(String url) {
		try {
			return new URL(url).toExternalForm();
		} catch (MalformedURLException e) {
			return null;
		}
	}
	
	/**
	 * Gets the WebView associated with this browser
	 * @return A WebView
	 */
	public WebView getBrowser() {
		return view;
	}
	
	/**
	 * Gets the WebEngine associated with the browser
	 * @return A WebEngine
	 */
	public WebEngine getEngine() {
		return engine;
	}
}

/*******************************************************************************
 * 
 * Copyright (c) 2014 -- WPI Suite
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors: Nathan Hughes, Alexander Shoop
 ******************************************************************************/

package edu.wpi.cs.wpisuitetng.modules.taskmanager.view.sidebar;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;

import javax.swing.JTextField;

import org.apache.lucene.queryparser.classic.ParseException;

import edu.wpi.cs.wpisuitetng.modules.taskmanager.model.search.Search;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.model.search.SearchException;

/**
 * This key listener parses user input and passes it to the search engine
 * @author nhhughes
 * @author akshoop
 */
public class SearchUserInput implements KeyListener {
	JTextField searchBox;
	Search toSearch;
	SearchBox searchPanel;
	String fullString;

	/**
	 * Constructor for SearchUserInput
	 * @param searchBox JTextField parameter
	 * @param toSearch Search parameter
	 * @param searchPanel SearchBox parameter
	 */
	public SearchUserInput(JTextField searchBox, Search toSearch,
			SearchBox searchPanel) {
		this.searchBox = searchBox;
		this.toSearch = toSearch;
		this.searchPanel = searchPanel;
		this.fullString = "";
	}

	/**
	 * Main method to search what the user typed
	 */
	public void keyReleased(KeyEvent e) {
		int quotationCount = 0;

		// This is to wipe the results panel when user backspaces all that they typed
		if (e.getKeyChar() == '\b') {
			if (searchBox.getText().length() == 0) {
				fullString = "";	
			}
			else {
				fullString = fullString.substring(0, fullString.length() - 1);
			}
			System.out.println("gettext length is " + searchBox.getText().length());
			try {
				if (searchBox.getText().length() >= 1) {
					System.out.println("length of backspace1 results is: "
							+ toSearch.searchFor(searchBox.getText() + "*")
									.size());
					searchPanel.displayResults(toSearch.searchFor(searchBox
							.getText() + "*"));
				} else if (searchBox.getText().length() == 0) {
					searchPanel.resultsBox.removeAll();
					searchPanel.revalidate();
					searchPanel.repaint();
				}
			} catch (SearchException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (ParseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			return;
		}

		// For case where user starts quotation marks
		if ((e.getKeyCode() == 222 || e.getKeyChar() == '\"')
				&& quotationCount == 0) {
			quotationCount = 1;
			fullString += "\"";
			return;
		}

		// For case where user is typing within quotation marks
		if ((e.getKeyCode() != 16 || e.getKeyCode() != 222)
				&& quotationCount == 1) {
			fullString += e.getKeyChar();
		}

		// For case where user is typing regular text
		if ((e.getKeyCode() != 16 || e.getKeyCode() != 222)
				&& quotationCount == 0) {
			fullString += e.getKeyChar();
		}

		// For case where user finishes quotation marks
		if ((e.getKeyCode() == 222 || e.getKeyChar() == '\"')
				&& quotationCount == 1) {
			quotationCount = 0;
		}

		try {
			fullString = fullString.replaceAll("\uFFFF", "");

			// Check if full string contains the quotation mark
			if (fullString.indexOf("\"") != -1) {
				System.out.println("length of full quote results is: "
						+ toSearch.searchFor(fullString).size());
				searchPanel.displayResults(toSearch.searchFor(fullString));
			} else {
				System.out.println("full string for wild is " + fullString);
				System.out.println("string of gettext is "
						+ searchBox.getText());
				System.out.println("length of full wild results is: "
						+ toSearch.searchFor(fullString + "*").size());
				searchPanel
						.displayResults(toSearch.searchFor(fullString + "*"));
			}
		} catch (SearchException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	/**
	 * Generated method for KeyEvent
	 */
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	/**
	 * Generated method for KeyEvent
	 */
	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub

	}
}

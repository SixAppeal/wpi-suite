package edu.wpi.cs.wpisuitetng.modules.taskmanager.view.sidebar;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;

import javax.swing.JTextField;

import org.apache.lucene.queryparser.classic.ParseException;

import edu.wpi.cs.wpisuitetng.modules.taskmanager.model.search.Search;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.model.search.SearchException;

/**
 * This key listener parses user input and passes it to the search engine.
 * 
 * @author nhhughes
 * @author akshoop
 */
public class SearchUserInput implements KeyListener {
	JTextField searchBox;
	Search toSearch;
	SearchBox searchPanel;
	
	public SearchUserInput(JTextField searchBox, Search toSearch, SearchBox searchPanel) {
		this.searchBox = searchBox;
		this.toSearch = toSearch;
		this.searchPanel = searchPanel;
	}
	
	public void keyReleased(KeyEvent e) {
		String fullString = "";
		int quotationCount = 0;
		// System.out.println("e keycode is " + e.getKeyCode());
		// System.out.println("e char is " + e.getKeyChar());


		// This is to wipe the results panel when user backspaces all that they
		// typed
		if (e.getKeyChar() == '\b') {
			fullString = "";
			try {
				if (searchBox.getText().length() > 1) {
					System.out.println("length of backspace results is: "
							+ toSearch.searchFor(searchBox.getText() + "*")
									.size());
					searchPanel.displayResults(toSearch
							.searchFor(searchBox.getText() + "*"));
				} else if (searchBox.getText().length() == 1) {
					searchPanel.displayResults(toSearch.searchFor(searchBox.getText()));
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
			// System.out.println("first quote, quotCount is" + quotationCount +
			// " and fullString is " + fullString);
			quotationCount = 1;
			fullString += "\"";
			return;
		}

		// For case where user is typing within quotation marks
		if ((e.getKeyCode() != 16 || e.getKeyCode() != 222)
				&& quotationCount == 1) {
			// System.out.println("get here, quotcount is" + quotationCount);
			fullString += e.getKeyChar();
			// System.out.println("fullString doing quotes is "+ fullString);
		}

		// For case where user is typing regular text
		if ((e.getKeyCode() != 16 || e.getKeyCode() != 222)
				&& quotationCount == 0) {
			fullString += e.getKeyChar();
			// System.out.println("fullString is " + fullString);
		}

		// For case where user finishes quotation marks
		if ((e.getKeyCode() == 222 || e.getKeyChar() == '\"')
				&& quotationCount == 1) {
			// System.out.println("second quote, quotCount is" + quotationCount
			// + " and fullString is " + fullString);
			quotationCount = 0;
		}

		try {
			fullString = fullString.replaceAll("\uFFFF", "");

			// Check if full string contains the quotation mark
			if (fullString.indexOf("\"") != -1) {
				// System.out.println("full string for quote is " + fullString);
				System.out.println("length of full quote results is: "
						+ toSearch.searchFor(fullString).size());
				searchPanel.displayResults(toSearch.searchFor(fullString));
			} else {
				System.out.println("full string for wild is " + fullString);
				System.out.println("string of gettext is "
						+ searchBox.getText());
				System.out.println("length of full wild results is: "
						+ toSearch.searchFor(fullString + "*").size());
				searchPanel.displayResults(toSearch.searchFor(fullString + "*"));
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

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub

	}
}

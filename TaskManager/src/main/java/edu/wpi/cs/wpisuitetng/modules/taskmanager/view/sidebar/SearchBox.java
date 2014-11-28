package edu.wpi.cs.wpisuitetng.modules.taskmanager.view.sidebar;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.apache.lucene.queryparser.classic.ParseException;

import edu.wpi.cs.wpisuitetng.modules.taskmanager.model.Task;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.model.search.Search;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.model.search.SearchException;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.presenter.Gateway;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.view.IView;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.view.components.Form;

/**
 * The GUI Search Box for the Sidebar
 * 
 * @author akshoop
 * @author nhhughes
 */
public class SearchBox extends JPanel implements IView {

	/**
	 * Generated serial number
	 */
	private static final long serialVersionUID = 2745494576347107370L;

	private Gateway gateway;
	
	Search toSearch;
	JTextField searchBox;
	JPanel resultsBox;
	List<Task> taskList;
	Form form;
	GridBagConstraints gbc;
	int quotationCount;
	String fullString;
	
	/**
	 * General constructor
	 * @throws IOException 
	 */
	public SearchBox() throws IOException {
		toSearch = new Search();
		toSearch.initialize();
		resultsBox = new JPanel();
		taskList = new ArrayList<Task>();
		quotationCount = 0;
		fullString = "";
		
		// testing purposes
		Task task1 = new Task();
		task1.setTitle("someTitle bunch");
		task1.setDescription("someTitle bunch of words HERE ARE CAPITAL lettersWith, commas's yeah!?");
		task1.setId(1);
		
		Task task2 = new Task();
		task2.setTitle("another title testing");
		task2.setDescription("sometitle bunch of yeah!?");
		task2.setId(2);
		
		Task task3 = new Task();
		task3.setTitle("testing bunch");
		task3.setDescription("other description bunch");
		task3.setId(3);
		
		taskList.add(task1);
		taskList.add(task2);
		taskList.add(task3);
		
		toSearch.createIndex(taskList);
		
		resultsBox.setLayout(new GridBagLayout());
		resultsBox.setOpaque(false);;
		
		searchBox = new JTextField();
		searchBox.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent e) {
//				System.out.println("e keycode is " + e.getKeyCode());
//				System.out.println("e char is " + e.getKeyChar());
				
				// This is to wipe the results panel when user backspaces all that they typed
				if (e.getKeyChar() == '\b') {
					fullString = "";
					try {
						if (searchBox.getText().length() > 1) {
							System.out.println("length of backspace results is: " + toSearch.searchFor(searchBox.getText() + "*").size());
							displayResults(toSearch.searchFor(searchBox.getText() + "*"));
						}
						else if (searchBox.getText().length() == 1) {
							displayResults(toSearch.searchFor(searchBox.getText()));
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
				if ((e.getKeyCode() == 222 || e.getKeyChar() == '\"') && quotationCount == 0) {
//					System.out.println("first quote, quotCount is" + quotationCount + " and fullString is " + fullString);
					quotationCount = 1;
					fullString += "\"";
					return;
				}
				
				// For case where user is typing within quotation marks
				if ((e.getKeyCode() != 16 || e.getKeyCode() != 222) && quotationCount == 1) {
//					System.out.println("get here, quotcount is" + quotationCount);
					fullString += e.getKeyChar();
//					System.out.println("fullString doing quotes is "+ fullString);
				}
				
				// For case where user is typing regular text
				if ((e.getKeyCode() != 16 || e.getKeyCode() != 222) && quotationCount == 0) {
					fullString += e.getKeyChar();
//					System.out.println("fullString is " + fullString);
				}
				
				// For case where user finishes quotation marks
				if ((e.getKeyCode() == 222 || e.getKeyChar() == '\"') && quotationCount == 1) {
//					System.out.println("second quote, quotCount is" + quotationCount + " and fullString is " + fullString);
					quotationCount = 0;
				}
				
				try {
					fullString = fullString.replaceAll("\uFFFF", "");
					
					// Check if full string contains the quotation mark
					if (fullString.indexOf("\"") != -1) {
//						System.out.println("full string for quote is " + fullString);
						System.out.println("length of full quote results is: " + toSearch.searchFor(fullString).size());
						displayResults(toSearch.searchFor(fullString));
					}
					else {
						System.out.println("full string for wild is " + fullString);
						System.out.println("string of gettext is " + searchBox.getText());
						System.out.println("length of full wild results is: " + toSearch.searchFor(fullString + "*").size());
						displayResults(toSearch.searchFor(fullString + "*"));
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
		});
		
		
		this.setLayout(new GridBagLayout());
		this.setOpaque(false);
		
		gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.anchor = GridBagConstraints.PAGE_START;
		gbc.weightx = 1.0;
		gbc.gridwidth = 2;
		
		gbc.gridx = 0;
		gbc.gridy = 0;
		
		gbc.insets = new Insets(20, 20, 0, 20);
		
		this.add(new JLabel("Search", JLabel.CENTER), gbc);
		gbc.gridy = 1;
		this.add(searchBox, gbc);
		
		
		gbc.weighty = 1.0;
		gbc.gridy = 2;
		this.add(resultsBox, gbc);
		gbc.weighty = 0.0;
	}
	
	/**
	 * Display the results
	 * @param results The list of task IDs to print 
	 */
	public void displayResults(List<Integer> results) {
		this.resultsBox.removeAll();
		
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		
		
		int count = 0;
		
		for (Integer r: results) {
			gbc.fill = GridBagConstraints.HORIZONTAL;
			Task t = getTask(r);
			JLabel content = new JLabel("<html><i>Title:</i><br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" 
										+ t.getTitle() 
										+ "<br>"
										+ "<i>Description:</i><br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" 
										+ t.getDescription() 
										+"<br></html>");
			JButton viewButton = new JButton("View");
			viewButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					viewTask(t);
				}
			});
			gbc.gridy = count;
			this.resultsBox.add(content, gbc);
			gbc.gridy = count + 1;
			gbc.fill = GridBagConstraints.NONE;
			gbc.anchor = GridBagConstraints.FIRST_LINE_START;
			this.resultsBox.add(viewButton, gbc);
			count += 2;
		}
		gbc.weighty = 1.0;
		gbc.gridy = count + 1;
		JPanel filler = new JPanel();
		filler.setOpaque(false);
		
		this.resultsBox.add(filler, gbc);
		gbc.weighty = 0.0;
		this.revalidate();
		this.repaint();
	}
	
	/**
	 * This gets a task from the given ID
	 * @param id The specific task ID
	 * @return Task Return the task
	 */
	public Task getTask(int id) {
		for (Task t : taskList) {
			if (t.getId() == id) {
				return t;
			}
		}
		return null;
	}
	
	/**
	 * Views a task from inputed task
	 * @param t Task to view
	 */
	public void viewTask(Task t) {
		this.gateway.toPresenter("TaskPresenter", "viewTask", t);
	}
	
	/**
	 * @see IView.setGateway
	 * @param gateway
	 */
	@Override
	public void setGateway(Gateway gateway) {
		this.gateway = gateway;
	}

}

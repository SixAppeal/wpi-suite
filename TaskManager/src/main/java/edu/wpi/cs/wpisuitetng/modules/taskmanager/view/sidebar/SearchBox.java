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

import javafx.scene.input.KeyCode;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.apache.lucene.queryparser.classic.ParseException;
import org.jdesktop.swingx.JXSearchPanel;

import edu.wpi.cs.wpisuitetng.modules.taskmanager.model.Task;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.model.search.Search;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.model.search.SearchException;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.presenter.Gateway;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.view.IView;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.view.components.Form;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.view.components.FormField;

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
	
	/**
	 * General constructor
	 * @throws IOException 
	 */
	public SearchBox() throws IOException {
		toSearch = new Search();
		toSearch.initialize();
		resultsBox = new JPanel();
		taskList = new ArrayList<Task>();
		
		// testing purposes
		Task task1 = new Task();
		task1.setTitle("someTitle bunch");
		task1.setDescription("someTitle bunch of words HERE ARE CAPITAL lettersWith, commas's yeah!?");
		task1.setId(1);
		
		Task task2 = new Task();
		task2.setTitle("another title testing");
		task2.setDescription("sometitle bunch of's yeah!?");
		task2.setId(2);
		
		Task task3 = new Task();
		task3.setTitle("testing bunch");
		task3.setDescription("other description bunch");
		task3.setId(3);
		
		taskList.add(task1);
		taskList.add(task2);
		taskList.add(task3);
		
		toSearch.createIndex(taskList);
		
		searchBox = new JTextField();
		searchBox.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent e) {
				try {
					System.out.println("length of results is: " + toSearch.searchFor(searchBox.getText()).size());
					displayResults(toSearch.searchFor(searchBox.getText() /*+"*"*/));
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
			public void keyPressed(KeyEvent e)
			{
				if (e.getKeyChar() == '\n') {
					// THIS IS FINE-ish
					try {
						System.out.println("length of results is: " + toSearch.searchFor(searchBox.getText()).size());
						displayResults(toSearch.searchFor(searchBox.getText()));
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
		this.resultsBox.setLayout(new GridBagLayout());
		this.resultsBox.setOpaque(false);;
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

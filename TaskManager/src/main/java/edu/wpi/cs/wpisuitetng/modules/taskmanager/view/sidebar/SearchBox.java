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

import edu.wpi.cs.wpisuitetng.modules.taskmanager.localcache.LocalCache;
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
	Form form;
	GridBagConstraints gbc;
	List<Task> taskList;
	
	/**
	 * General constructor
	 * @throws IOException 
	 */
	public SearchBox() throws IOException {
		toSearch = new Search();
		toSearch.initialize();
		resultsBox = new JPanel();
		
		resultsBox.setLayout(new GridBagLayout());
		resultsBox.setOpaque(false);
		
		searchBox = new JTextField();
		searchBox.addKeyListener(new SearchUserInput(this.searchBox, this.toSearch, this));
		
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
	 * Retrieves tasks from the local cache
	 * @return tasks_from_cache An array of tasks retrieved
	 * @throws IOException 
	 */
	public void updateIndex(ArrayList<Task> all_tasks) throws IOException {
		this.taskList = all_tasks;
		
		if (this.toSearch.isInitialized()) {
			this.toSearch.updateIndex(all_tasks);
		}
		else {
			this.toSearch.initialize();
			this.toSearch.createIndex(all_tasks);
		}
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

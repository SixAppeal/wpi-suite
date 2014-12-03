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

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import net.miginfocom.swing.MigLayout;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.model.Task;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.model.search.Search;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.presenter.Gateway;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.view.IView;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.view.components.Form;

/**
 * The GUI Search Box for the Sidebar
 * @author akshoop
 * @author nhhughes
 * @author wavanrensselaer
 */
public class SearchBox extends JPanel implements IView {

	/**
	 * Generated serial number
	 */
	private static final long serialVersionUID = 2745494576347107370L;

	private Gateway gateway;
	
	private JPanel container;
	private JScrollPane scrollPane;
	
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
		this.container = new JPanel();
		this.scrollPane = new JScrollPane(this.container);
		
		toSearch = new Search();
		toSearch.initialize();
		resultsBox = new JPanel();
		
		resultsBox.setLayout(new GridBagLayout());
		resultsBox.setOpaque(false);
		
		searchBox = new JTextField();
		searchBox.addKeyListener(new SearchUserInput(this.searchBox, this.toSearch, this));

		this.container.setOpaque(false);
		this.container.setLayout(new MigLayout("fillx, wrap 1, ins 20", "[260]"));
		
		this.container.add(searchBox, "growx");
		this.container.add(resultsBox, "grow");
		
		this.setBackground(new Color(230, 230, 230));
		this.setLayout(new BorderLayout());
		this.add(this.scrollPane, BorderLayout.CENTER);
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
			
			// The purpose of the html tags is to properly display the results
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

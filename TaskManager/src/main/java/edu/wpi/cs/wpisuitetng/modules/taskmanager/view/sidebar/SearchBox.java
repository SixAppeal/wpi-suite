/*******************************************************************************
 * 
 * Copyright (c) 2014 -- WPI Suite
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors: Team Six-Appeal
 ******************************************************************************/

package edu.wpi.cs.wpisuitetng.modules.taskmanager.view.sidebar;

import java.awt.*;
import java.awt.event.*;

import java.awt.event.ItemEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import net.miginfocom.swing.MigLayout;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.model.Task;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.model.search.Search;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.presenter.Gateway;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.view.IView;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.view.columnar.TaskView;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.view.components.Form;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.view.components.FormField;

/**
 * The GUI Search Box for the Sidebar
 * @author akshoop
 * @author nhhughes
 * @author wavanrensselaer
 * @author srojas
 */
public class SearchBox extends JPanel implements IView {

	/**
	 * Generated serial number
	 */
	private static final long serialVersionUID = 2745494576347107370L;

	private Gateway gateway;

	private JPanel container;
	private JScrollPane scrollPane;
	private List<Integer> resultsG;
	Search toSearch;
	JTextField searchBox;
	JCheckBox searchCheckBox;
	JPanel resultsBox; //keeps the previous list of searched results 
	Form form;
	GridBagConstraints gbc;
	List<Task> taskList;
	JLabel archiveLabel;
	boolean archiveModeOn;



	/**
	 * General constructor
	 * @throws IOException 
	 */
	public SearchBox() throws IOException {
		this.container = new JPanel();
		this.scrollPane = new JScrollPane(this.container);
		this.archiveModeOn = false;
		this.archiveLabel = new JLabel("Archived Tasks");

		this.scrollPane.setBorder(BorderFactory.createEmptyBorder());
		this.scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

		toSearch = new Search();
		toSearch.initialize();
		resultsBox = new JPanel();
		this.resultsG = new ArrayList<Integer>();

		resultsBox.setLayout(new GridBagLayout());
		resultsBox.setOpaque(false);

		this.searchCheckBox = new JCheckBox();
		this.searchCheckBox.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent arg0) {
				if (archiveModeOn){
					//check box unchecked -> archive mode off
					archiveModeOn = false;
					resultsBox.removeAll();
					if(isSearchBoxEmpty()){
						displayResults(new ArrayList<Integer>());
					}
					else{ 
						displayResults(resultsG);
					}
				}
				else{
					//checked
					archiveModeOn = true; 
					if (!isSearchBoxEmpty()){
						resultsBox.removeAll();
					}
					displayResults(resultsG);
				}

			}
		});


		searchBox = new JTextField();
		searchBox.setBorder(FormField.BORDER_NORMAL);
		searchBox.addKeyListener(new SearchUserInput(this.searchBox, this.toSearch, this));
		/**
		 * Key listener for the searchBox
		 */
		searchBox.addKeyListener( new KeyListener() {
			@Override
			public void keyPressed(KeyEvent e) {
				if((searchBox.getText().length() == 1) && e.getKeyCode() == KeyEvent.VK_BACK_SPACE){
					searchBox.setText("");
				}
				if(archiveModeOn && isSearchBoxEmpty()){
					searchBox.removeAll();
					displayResults(new ArrayList<Integer>());

				}
				else {
					resultsG.clear();
					displayResults(resultsG);
				}
			}

			@Override
			public void keyReleased(KeyEvent e) {

			}

			@Override
			public void keyTyped(KeyEvent e) {

			}

		});

		this.container.setLayout(new GridBagLayout());
		this.container.setBackground(SidebarView.SIDEBAR_COLOR);

		gbc = new GridBagConstraints();
		gbc.anchor = GridBagConstraints.PAGE_START;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.insets = new Insets(20, 20, 20, 20);
		gbc.weightx = 1.0;
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 2;
		this.container.add(searchBox, gbc);

		gbc.insets = new Insets(10, 20, 10, 10);
		gbc.weightx = 0;
		gbc.weighty = 0;
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.gridwidth = 1;
		this.container.add(searchCheckBox, gbc);

		gbc.weightx = 1;
		gbc.weighty = 0;
		gbc.gridx = 1;
		gbc.gridy = 1;
		this.container.add(archiveLabel, gbc);

		gbc.insets = new Insets(10, 10, 10, 10);
		//gbc.insets.top = 0;
		gbc.gridwidth = 2; 
		gbc.gridx = 0;
		gbc.weightx = 1;
		gbc.weighty = 1;
		gbc.gridy = 2;
		this.container.add(resultsBox, gbc);

		this.scrollPane.setMinimumSize(new Dimension(300, 0));
		this.setLayout(new MigLayout("fill, ins 0", "[300]"));
		this.add(this.scrollPane, "grow");
	}

	/**
	 * Display the results, if archive mode: only show archived. if not: show the not archived. 
	 * I would advise people not to try to change this :P 
	 * @param results The list of task IDs to print 
	 */
	public void displayResults(List<Integer> results) {
		this.resultsBox.removeAll();
		resultsG =results;
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.fill = GridBagConstraints.HORIZONTAL;

		int count = 0;
		if (archiveModeOn && (isSearchBoxEmpty())){
			for (Task t : taskList){
				if (t.isArchived()){
					TaskView content = new TaskView(t, true);
					content.setGateway(this.gateway);
					gbc.gridy = count;
					this.resultsBox.add(content, gbc);
					count++;
				}
			}
		}
		else {
			for (Integer r: results) {
				gbc.fill = GridBagConstraints.HORIZONTAL;
				Task t = getTask(r);
				if (archiveModeOn && t.isArchived()){
					TaskView content = new TaskView(t, true);
					content.setGateway(this.gateway);
					gbc.gridy = count;
					this.resultsBox.add(content, gbc);
					count++;
				}
				else if ((!archiveModeOn && (!t.isArchived()))) {
					TaskView content = new TaskView(t, true);
					content.setGateway(this.gateway);
					gbc.gridy = count;
					this.resultsBox.add(content, gbc);
					count++;
				}

			}
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
		this.gateway.toPresenter("TaskPresenter", "editTask", t);
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
			this.displayResults(resultsG);
		}
		else {
			this.toSearch.initialize();
			this.toSearch.createIndex(all_tasks);
			this.displayResults(resultsG);
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
	
	/**
	 * returns true if the search box is empty
	 * @return boolean if true
	 */
	public boolean isSearchBoxEmpty(){
		return searchBox.getText().isEmpty();
	}
}

/*******************************************************************************
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

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import net.miginfocom.swing.MigLayout;

import org.jdesktop.swingx.JXDatePicker;

import edu.wpi.cs.wpisuitetng.modules.taskmanager.localcache.ThreadSafeLocalCache;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.presenter.Gateway;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.reports.ReportGenerator;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.view.IView;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.view.components.ButtonGroup;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.view.components.Form;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.view.components.FormField;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.view.components.FormFieldValidator;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.view.components.HorizontalForm;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.view.components.MemberButtonGroup;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.view.sidebar.JListMouseHandler;

/**
 *  A view for creating a PDF of statistics
 * 
 * @author dpseaman
 * @author thhughes
 * @author nhhughes
 */

public class StatisticsView extends JPanel implements IView{

	private static final long serialVersionUID = -7282532926324062923L;

	private Gateway gateway;
	private ThreadSafeLocalCache cache;
	private JXDatePicker startDate;
	private JXDatePicker endDate;
	private JButton createButton;

	private JList<String> members;
	private JScrollPane membersScrollPane;
	private JList<String> selectedMembers;
	private JScrollPane selectedMembersScrollPane;
	private JButton addMemberButton;
	private JButton removeMemberButton;
	private JButton pickFileButton;
	private JTextField dirPath;
	private List<String> usernames = new ArrayList<String>();
	private List<String> assigned = new ArrayList<String>();
	private List<String> unassigned = new ArrayList<String>();
	private boolean flag = false;

	JListMouseHandler allMembersMouseHandler;
	JListMouseHandler selectedMembersMouseHandler;

	private Form form;

	/**
	 * Constructor
	 */
	public StatisticsView () {

		this.startDate = new JXDatePicker();
		this.endDate = new JXDatePicker();
		this.createButton = new JButton ("Create");

		this.createButton.setEnabled(false);
		this.pickFileButton = new JButton("Choose File Path");
		this.dirPath = new JTextField("");
		this.dirPath.setEditable(false);
		
		this.members = new JList<String>();
		this.membersScrollPane = new JScrollPane(this.members);

		this.selectedMembers = new JList<String>();
		this.selectedMembersScrollPane = new JScrollPane(this.selectedMembers);

		this.members.setVisibleRowCount(4);				
		this.members.setLayoutOrientation(JList.VERTICAL);

		this.selectedMembers.setVisibleRowCount(4);				
		this.selectedMembers.setLayoutOrientation(JList.VERTICAL);



		// Buttons below
		addMemberButton = new JButton(">>");
		addMemberButton.setSize(40, 60);
		addMemberButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				moveMembersToSelected();
			}
		});

		removeMemberButton = new JButton("<<");
		removeMemberButton.setSize(40, 60);
		removeMemberButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				moveMembersToAll();
			}
		});


		// Member mouse handlers
		allMembersMouseHandler = new JListMouseHandler(members);
		selectedMembersMouseHandler = new JListMouseHandler(selectedMembers);


		// Member JList Action Listeners
		members.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				notifyAllMembersMouseHandler();
			}
		});

		selectedMembers.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				notifySelectedMembersMouseHandler();
			}
		});

		selectedMembersScrollPane.setMinimumSize(new Dimension(selectedMembers.getMinimumSize().width, 150));
		membersScrollPane.setMinimumSize(new Dimension(members.getMinimumSize().width, 150));
		selectedMembersScrollPane.setMaximumSize(new Dimension(selectedMembers.getMinimumSize().width, 150));
		membersScrollPane.setMaximumSize(new Dimension(members.getMinimumSize().width, 150));
		selectedMembersScrollPane.setPreferredSize(new Dimension(selectedMembers.getMinimumSize().width, 150));
		membersScrollPane.setPreferredSize(new Dimension(members.getMinimumSize().width, 150));

		FormField dirPathForm = new FormField("Directory to Create", this.dirPath, new FormFieldValidator() {
			@Override
			public boolean validate(JComponent component) {
				if (dirPath.getText().length() == 0) {
					return false;
				}
				if (Files.notExists(Paths.get(dirPath.getText()))) {
					return true;
				}
				else {
					File file = new File(dirPath.getText());
					if (file.isDirectory() && file.list().length == 0) {
						return true;
					}
				}
				return false;
			}
			
			@Override
			public String getMessage() {
				if (dirPath.getText().length() == 0) {
					return "Please enter a directory";
				}
				return "Please enter a non-existing or empty directory";
			}
			
		});

		// form field for start date
		FormField startDateForm = new FormField("Start Date", this.startDate, new FormFieldValidator() {
			@Override
			public boolean validate(JComponent component) {
				return !(startDate.getDate() == null);
			}

			@Override
			public String getMessage() {
				return "Please select a start date";
			}	
		});

		// validator for start date
		this.startDate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (startDate != null) { 
					startDateForm.validateInput();
				}
				validateForm();
			}
		});

		// form field for end date
		FormField endDateForm = new FormField("End Date", this.endDate, new FormFieldValidator() {
			@Override
			public boolean validate(JComponent component) {
				return endDate.getDate() != null && endDate.getDate().compareTo(startDate.getDate()) > 0;
			}

			@Override
			public String getMessage() {
				return "Please select an end date after the start date";
			}
		});


		// validator for end date
		this.endDate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (endDate != null) { 
					endDateForm.validateInput();
				}
				validateForm();
			}
		});

		this.pickFileButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser saveFile = new JFileChooser();
				saveFile.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				int returnVal = saveFile.showDialog(StatisticsView.this, "Create");
				
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					File file = saveFile.getSelectedFile();
					dirPath.setText(file.getAbsolutePath());
				}
				
				else {
					
				}
				validateForm();
			}
		});

		this.createButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String path = dirPath.getText();
				File file = new File(path);
				if (!file.exists()) {
					file.mkdir();
				}
				path = path + "/index.html";
				file = new File(path);
				if (cache != null) {
					ReportGenerator generator = new ReportGenerator(file, startDate.getDate(), endDate.getDate(), cache, assigned);
				}
				else {
					System.out.println("Problem Occured!");
				}

			}
		});
		
		this.form = new Form(
				startDateForm,
				endDateForm,
				new HorizontalForm(
						new FormField("Members", this.membersScrollPane),
						new Form(
								new MemberButtonGroup(addMemberButton, removeMemberButton)
								),
								new FormField("Selected", this.selectedMembersScrollPane)
						),
						new ButtonGroup(
								this.pickFileButton
								),
						dirPathForm,
						new ButtonGroup(
								this.createButton
								)
				);


		this.setBackground(SidebarView.SIDEBAR_COLOR);
		this.setLayout(new MigLayout("fill, ins 20", "[260]"));
		this.add(this.form, "grow");
		
		this.setMinimumSize(new Dimension(300, 0));
		this.updateMembers();

	}

	/**
	 * Sets the cache of the current StatisticsView
	 * @param cache
	 */
	public void setCache(ThreadSafeLocalCache cache) {
		this.cache = cache;
	}
	
	/**
	 * 
	 * @param selected
	 * @param all
	 * 
	 *  Update Panels is used to redraw the lists once something is changed
	 */
	public void updateMembers() {
		this.members.setListData(unassigned.toArray(new String[0]));
		this.selectedMembers.setListData(assigned.toArray(new String[0]));
		this.revalidate();
		this.repaint();
	}

	/**
	 * Takes the members that the user has selected and moves them to the list of members selected to a task
	 */
	public void moveMembersToSelected() {
		assigned.addAll(members.getSelectedValuesList());
		unassigned.removeAll(members.getSelectedValuesList());
		updateMembers();


		this.allMembersMouseHandler.clear();
		this.selectedMembersMouseHandler.clear();

	}

	/**
	 * Take the members that are selected in the selected Members list and moves them back to the All Members list
	 */
	public void moveMembersToAll() {
		unassigned.addAll(selectedMembers.getSelectedValuesList());
		assigned.removeAll(selectedMembers.getSelectedValuesList());
		updateMembers();

		this.allMembersMouseHandler.clear();
		this.selectedMembersMouseHandler.clear();
	}

	public void notifyAllMembersMouseHandler() {
		this.allMembersMouseHandler.just_changed = true;
	}

	public void notifySelectedMembersMouseHandler() {
		this.selectedMembersMouseHandler.just_changed = true;
	}


	/**
	 *  Validates the form
	 */
	public void validateForm() {
		this.createButton.setEnabled(this.form.hasValidInput());
	}




	@Override
	public void setGateway(Gateway gateway) {
		this.gateway = gateway;
	}		

	/**
	 * Updates all members of StatisticsView
	 * @param localCache
	 */
	public void updateAll(ThreadSafeLocalCache localCache){
		if (flag == false){
			usernames = MemberListHandler.getInstance().getGlobal();
			assigned = usernames;
			flag = true;
			this.updateMembers();
		}
		else {
			List<String> newList = new ArrayList<String>(usernames);
			newList.removeAll(unassigned);
			if (!(newList.equals(assigned))) {
				this.selectedMembers.setListData(MemberListHandler.getInstance().getGlobal().toArray(new String [0]));
				revalidate();
				repaint();
			}
		}
	}
}

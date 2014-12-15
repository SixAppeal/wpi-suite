/*******************************************************************************
 * Copyright (c) 2014 -- WPI Suite
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors: Nathan Hughes, Troy Hughes
 ******************************************************************************/


package edu.wpi.cs.wpisuitetng.modules.taskmanager.view.sidebar;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import net.miginfocom.swing.MigLayout;

import org.jdesktop.swingx.JXDatePicker;

import edu.wpi.cs.wpisuitetng.modules.core.models.User;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.localcache.Cache;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.localcache.ThreadSafeLocalCache;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.model.Task;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.presenter.Gateway;
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
 *
 */

public class StatisticsView extends JPanel implements IView{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7282532926324062923L;

	private Gateway gateway;

	private JXDatePicker startDate;
	private JXDatePicker endDate;
	private JButton createButton;
	private Task memberTask;

	private JList<String> members;
	private JScrollPane membersScrollPane;
	private JList<String> selectedMembers;
	private JScrollPane selectedMembersScrollPane;
	private JButton addMemberButton;
	private JButton removeMemberButton;
	private ArrayList<String> allMembers;
	
	JListMouseHandler allMembersMouseHandler;
	JListMouseHandler selectedMembersMouseHandler;

	private Form form;

	public StatisticsView () {

		this.startDate = new JXDatePicker();
		this.endDate = new JXDatePicker();
		this.createButton = new JButton ("Create");
		
		this.createButton.setEnabled(false);


		this.members = new JList<String>();
		this.membersScrollPane = new JScrollPane(this.members);

		this.selectedMembers = new JList<String>();
		this.selectedMembersScrollPane = new JScrollPane(this.selectedMembers);

		this.members.setVisibleRowCount(4);				
		this.members.setLayoutOrientation(JList.VERTICAL);

		this.selectedMembers.setVisibleRowCount(4);				
		this.selectedMembers.setLayoutOrientation(JList.VERTICAL);
	
		this.allMembers = new ArrayList<String>();
		
		
		

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
				return !(endDate.getDate() == null);
			}

			@Override
			public String getMessage() {
				return "Please select an end date";
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

		this.createButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser saveFile = new JFileChooser();
				saveFile.showSaveDialog(null);
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
								this.createButton
								)
				);


		this.setBackground(SidebarView.SIDEBAR_COLOR);
		this.setLayout(new MigLayout("fill, ins 20", "[260]"));
		this.add(this.form, "grow");
		this.updateMembers();

	}
	
	/**
	 * 
	 * @param selected
	 * @param all
	 * 
	 *  Update Panels is used to redraw the lists once something is changed
	 */
	public void updateMembers() {
		this.members.setListData(this.allMembers.toArray(new String[0]));
		this.selectedMembers.setListData(new ArrayList<String>().toArray(new String[0]));
		this.revalidate();
		this.repaint();
	}

	/**
	 * Takes the members that the user has selected and moves them to the list of members selected to a task
	 */
	public void moveMembersToSelected() {	
		MemberListHandler.getInstance().assignMember(members.getSelectedValuesList());
		updateMembers();

		this.allMembersMouseHandler.clear();
		this.selectedMembersMouseHandler.clear();
		
	}

	/**
	 * Take the members that are selected in the selected Members list and moves them back to the All Members list
	 */
	public void moveMembersToAll() {
		MemberListHandler.getInstance().unAssignMember(selectedMembers.getSelectedValuesList());
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
		System.out.println("Fuck this shit!");
		this.gateway = gateway;
	}		
	
	public void updateAll(ThreadSafeLocalCache localCache){
		setGlobal(localCache);
		updateMembers();
	}
	
	/**
	 * 
	 * @param localCache
	 *            local cache of all the data
	 * 
	 *            Converts the array of Objects from the Loal Cache to an array
	 *            of Users Then converts the array of users to a list<String> of
	 *            the usernames
	 */
	private void setGlobal(Cache localCache) {
		Object[] output = localCache.retrieve("member");
		User[] memberArray = (User[]) output;
		this.allMembers = new ArrayList<String>(usersToUsernames(memberArray));
	}

	/**
	 * Converts an array of users to a list of usernames
	 * 
	 * @param userList
	 *            list of users
	 * @return List<String> that are the usernames of the users
	 */
	private List<String> usersToUsernames(User[] userList) {
		List<String> output = new ArrayList<String>();
		for (int i = 0; i < userList.length; i++) {
			output.add(userList[i].getUsername());
		}
		return output;
	}

}

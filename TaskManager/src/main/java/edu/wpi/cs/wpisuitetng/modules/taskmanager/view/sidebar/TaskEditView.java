/*******************************************************************************
 * Copyright (c) 2014 -- WPI Suite
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors: Nathan Hughes
 ******************************************************************************/


package edu.wpi.cs.wpisuitetng.modules.taskmanager.view.sidebar;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTabbedPane;
import javax.swing.SpinnerNumberModel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import net.miginfocom.swing.MigLayout;

import org.jdesktop.swingx.JXDatePicker;

import edu.wpi.cs.wpisuitetng.modules.requirementmanager.RequirementManager;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.Requirement;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.RequirementModel;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.view.ViewEventController;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.model.Stage;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.model.StageList;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.model.Task;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.presenter.Gateway;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.util.TaskManagerUtil;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.view.IView;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.view.components.ButtonGroup;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.view.components.Form;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.view.components.FormField;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.view.components.FormFieldValidator;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.view.components.HorizontalForm;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.view.components.MemberButtonGroup;

/**
 * Displays and allows editing of task properties.
 * 
 * @author tmeehan
 * @author wmtemple
 * @author krpeffer
 * @author wavanrensselaer
 * @author srojas
 * @author akshoop
 * @author dpseaman
 * @author thhughes
 */
public class TaskEditView extends JPanel implements IView {
	private static final long serialVersionUID = -8972626054612267276L;

	private Gateway gateway;

	private Task task;
	private StageList stages;
	private Requirement[] requirements;

	// Components
	private JPanel container;
	private JScrollPane scrollPane;
	private TaskActivitiesAndComments commentPanel;

	private JTextField titleInput;
	private JTextArea titleLabel;
	private JTextArea descInput;
	@SuppressWarnings("unused")
	private JTextArea descLabel;
	private JXDatePicker dateInput;
	@SuppressWarnings("unused")
	private JLabel dateLabel;
	private JSpinner estEffortInput;
	private JSpinner actEffortInput;

	private JList<String> members;
	private JScrollPane membersScrollPane;
	private JList<String> assignedMembers;
	private JScrollPane assignedMembersScrollPane;
	private JButton addMemberButton;
	private JButton removeMemberButton;
	
	private JComboBox<Stage> stageInput;
	private List<String> requirementTitles;
	private JButton viewRequirement;
	private JButton attachRequirement;
	private JComboBox<String> requirementsComboBox;
	private JButton archiveButton;
	private JButton closeButton;
	private Form form;

	private ActionListener stageBoxListener;

	@SuppressWarnings("unused")
	private List<String> stringArray;

	private TaskEditView tev;

	JListMouseHandler allMembersMouseHandler;
	JListMouseHandler assignedMembersMouseHandler;


	/**
	 * Constructor
	 */
	public TaskEditView(Task iTask, StageList stages) {
		this.task = iTask;
		this.stages = stages;
		this.requirements = new Requirement[0];
		this.tev = this;
		// Populates the member list handler with the assigned members
		
		
		// UI stuff
		this.container = new JPanel();
		this.scrollPane = new JScrollPane(this.container);
		this.commentPanel = new TaskActivitiesAndComments();
		this.titleInput = new JTextField(this.task.getTitle());
		this.titleLabel = new JTextArea(this.task.getTitle());
		this.descInput = new JTextArea(this.task.getDescription(), 5, 0);
		this.descLabel = new JTextArea(this.task.getDescription());
		this.dateInput = new JXDatePicker(this.task.getDueDate());
		this.dateLabel = new JLabel();
		this.estEffortInput = new JSpinner(new SpinnerNumberModel(1, null, null, 1));
		this.actEffortInput = new JSpinner(new SpinnerNumberModel(1, null, null, 1));
		this.requirementTitles = new ArrayList<String>();

		this.commentPanel.updateView(this.task);

		this.members = new JList<String>();
		this.membersScrollPane = new JScrollPane(this.members);

		this.assignedMembers = new JList<String>();
		this.assignedMembersScrollPane = new JScrollPane(this.assignedMembers);

		MemberListHandler.getInstance().populateMembers(this.task.getAssignedTo());
		this.updateMembers();
		
		this.stageInput = new JComboBox<Stage>();
		this.archiveButton = new JButton("Archive");
		this.closeButton = new JButton("Close");
		TaskEditView that = this;			// What the actual fuck... 
		
		this.titleLabel.setOpaque(false);
		this.titleLabel.setBorder(BorderFactory.createEmptyBorder());
		this.titleLabel.setLineWrap(true);
		this.titleLabel.setWrapStyleWord(true);

		this.scrollPane.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		this.scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		
		this.descInput.setLineWrap(true);
		this.descInput.setWrapStyleWord(true);
		
		this.estEffortInput.setValue(this.task.getEstimatedEffort());
		this.actEffortInput.setValue(this.task.getActualEffort());

		for( Stage s : this.stages )this.stageInput.addItem(s);
		this.stageInput.setSelectedItem(task.getStage());
	
		
		this.members.setVisibleRowCount(4);				
		this.members.setLayoutOrientation(JList.VERTICAL);

		this.assignedMembers.setVisibleRowCount(4);				
		this.assignedMembers.setLayoutOrientation(JList.VERTICAL);

		this.requirementTitles = getRequirementTitles();
		this.requirementsComboBox = new JComboBox<String>();
		for (String s: this.requirementTitles) this.requirementsComboBox.addItem(s);
		this.requirementsComboBox.setSelectedIndex(-1);
		for (int i = 0; i < this.requirementTitles.size(); i++) {
			if (this.task.getRequirement().getName() == this.requirementTitles.get(i)) {
				this.requirementsComboBox.setSelectedIndex(i);
			}
		}

		this.viewRequirement = new JButton("View");
		this.attachRequirement = new JButton("Associate");

		
		// Buttons below
		addMemberButton = new JButton(">>");
		addMemberButton.setSize(40, 60);
		addMemberButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				moveMembersToAssigned();
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
		assignedMembersMouseHandler = new JListMouseHandler(assignedMembers);
		// Member JList Action Listeners
		members.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				notifyAllMembersMouseHandler();
			}
		});

		assignedMembers.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				notifyAssignedMembersMouseHandler();
			}
		});
		
		assignedMembersScrollPane.setMinimumSize(new Dimension(assignedMembers.getMinimumSize().width, 150));
		membersScrollPane.setMinimumSize(new Dimension(members.getMinimumSize().width, 150));
		assignedMembersScrollPane.setMaximumSize(new Dimension(assignedMembers.getMinimumSize().width, 150));
		membersScrollPane.setMaximumSize(new Dimension(members.getMinimumSize().width, 150));
		assignedMembersScrollPane.setPreferredSize(new Dimension(assignedMembers.getMinimumSize().width, 150));
		membersScrollPane.setPreferredSize(new Dimension(members.getMinimumSize().width, 150));
		
		
		this.archiveButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				task.archive();
				gateway.toPresenter("LocalCache", "update", "archive:testing", task);
				gateway.toView("SidebarView", "removeEditPanel", that);
			}
		});
		
		this.closeButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				gateway.toView("SidebarView", "removeEditPanel", that);
			}
		});

		// Predefine title field hook up listener
		FormField titleField = new FormField("Title", this.titleInput, new FormFieldValidator() {
			@Override
			public boolean validate(JComponent component) {
				return !((JTextField) component).getText().trim().equals("");
			}

			@Override
			public String getMessage() {
				return "Please enter a title.";
			}
		});
		this.titleInput.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent e) {
				if (e.getKeyCode() != 9) { // tab key
					titleField.validateInput();
				}
			}
		});
		this.titleInput.addFocusListener(new FocusListener() {
			@Override
			public void focusGained(FocusEvent e) {
			}

			@Override
			public void focusLost(FocusEvent e) {
				if (titleField.hasValidInput()) {
					task.setTitle(titleInput.getText());
					saveTask();
				}
			}
		});

		// Predefine description field to hook up listener
		FormField descField = new FormField("Description", this.descInput, new FormFieldValidator() {
			@Override
			public boolean validate(JComponent component) {
				return !descInput.getText().trim().equals("");
			}

			@Override
			public String getMessage() {
				return "Please enter a description.";
			}
		});

		this.descInput.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent e) {
				if (e.getKeyCode() != 9) { // tab key
					descField.validateInput();
				}
			}
		});

		this.descInput.addFocusListener(new FocusListener() {
			@Override
			public void focusGained(FocusEvent e) {
			}

			@Override
			public void focusLost(FocusEvent e) {
				if (descField.hasValidInput()) {
					task.setDescription(descInput.getText());
					saveTask();
				}
			}
		});

		this.dateInput.addPropertyChangeListener("date", new PropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				task.setDueDate(dateInput.getDate());
				saveTask();
			}
		});

		// Predefine effort fields to hook up listeners
		FormField estEffortField = new FormField("Est. Effort", this.estEffortInput, new FormFieldValidator() {
			@Override
			public boolean validate(JComponent component) {
				return ((Integer) ((JSpinner) component).getValue()).intValue() > 0;
			}

			@Override
			public String getMessage() {
				return "Effort must be greater than zero.";
			}
		});
		this.estEffortInput.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				estEffortField.validateInput();
				if (estEffortField.hasValidInput()) {
					task.setEstimatedEffort((Integer) estEffortInput.getValue());
					saveTask();
				}
			}
		});

		FormField actEffortField = new FormField("Act. Effort", this.actEffortInput, new FormFieldValidator() {
			@Override
			public boolean validate(JComponent component) {
				return ((Integer) ((JSpinner) component).getValue()).intValue() > 0;
			}

			@Override
			public String getMessage() {
				return "Effort must be greater than zero.";
			}
		});
		this.actEffortInput.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				actEffortField.validateInput();
				if (actEffortField.hasValidInput()) {
					task.setActualEffort((Integer) actEffortInput.getValue());
					saveTask();
				}
			}
		});
		
		stageBoxListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("Fire in the hole!");
				if( stageInput.getSelectedIndex() > -1) {
					task.setStage((Stage) stageInput.getSelectedItem());
					saveTask();
				}
			}
		};

		// Associated a requirement to a task
		this.attachRequirement.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String reqName = (String) requirementsComboBox.getSelectedItem();
				for (Requirement r : requirements) {
					if (r.getName().equals(reqName)) {
						task.setRequirement(r);
						saveTask();
					}
				}
			}
		});

		// View button for associated requirement
		// Parts of code is borrowed from No Comment's EditTaskController.java
		this.viewRequirement.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String reqName = (String) requirementsComboBox
						.getSelectedItem();
				Container c = tev;
				c = c.getParent().getParent().getParent().getParent();
				JTabbedPane tabPane = (JTabbedPane) c;
				tabPane.setSelectedIndex(tabPane.indexOfTab(RequirementManager
						.staticGetName()));
				ViewEventController.getInstance().editRequirement(
						RequirementModel.getInstance().getRequirementByName(
								reqName));
			}
		});

		this.stageInput.addActionListener(stageBoxListener);

		this.form = new Form(
				titleField,
				descField,
				new FormField("Due Date", this.dateInput),
				new HorizontalForm(
						estEffortField,
						actEffortField
						),
						new HorizontalForm(
								new FormField("Members", this.membersScrollPane),
								new Form(
//										new ButtonGroup(addMemberButton),
//										new ButtonGroup(removeMemberButton)
										new MemberButtonGroup(addMemberButton, removeMemberButton)
									),
								new FormField("Assigned", this.assignedMembersScrollPane)
								),
								new FormField("Associated Requirement", this.requirementsComboBox),
								new ButtonGroup(
										this.viewRequirement,
										this.attachRequirement
										),
										//new FormField("Stage", this.stageInput),
										new ButtonGroup(
												this.archiveButton,
												this.closeButton
												)
				);
		

		this.container.setBackground(SidebarView.SIDEBAR_COLOR);
		this.container.setLayout(new MigLayout("fill, ins 20", "[260]"));
		this.container.add(this.form, "grow");

		this.scrollPane.setMinimumSize(new Dimension(300, 0));

		this.setLayout(new MigLayout("fill, ins 0", "[300][300]"));
		this.add(this.scrollPane, "grow");
		this.add(this.commentPanel, "grow");
		
	}

	/**
	 * @return the task
	 */
	public Task getTask() {
		return task;
	}

	/**
	 * Saves the task currently being edited
	 */
	private void saveTask() {
		this.gateway.toPresenter("LocalCache", "update", "task:testing", this.task);
	}

	@Override
	public void setGateway(Gateway gateway) {
		this.gateway = gateway;
		this.commentPanel.setGateway(this.gateway);
	}

	public void updateEverything(Task t) {
		this.task.setStage(t.getStage());
		this.stageInput.setSelectedItem(t.getStage());
	}

	public void setStages( StageList sl ) {
		if(!stages.equals(sl)) {
			Object pSelected = stageInput.getSelectedItem();
			this.stages = sl;
			stageInput.removeActionListener(stageBoxListener);
			this.stageInput.removeAllItems();
			for (Stage s : stages) stageInput.addItem(s);
			if(pSelected != null && stages.contains(pSelected)) stageInput.setSelectedItem(pSelected);
			stageInput.addActionListener(stageBoxListener);
		}
	}
	
	
	
	public void notifyAllMembersMouseHandler() {
		this.allMembersMouseHandler.just_changed = true;
	}

	public void notifyAssignedMembersMouseHandler() {
		this.assignedMembersMouseHandler.just_changed = true;
	}
	
	/**
	 * 
	 * @param assigned
	 * @param all
	 * 
	 *  Update Panels is used to redraw the lists once something is changed
	 */
	public void updateMembers() {
		this.members.setListData(MemberListHandler.getInstance().getUnassigned().toArray(new String[0]));
		this.assignedMembers.setListData(MemberListHandler.getInstance().getAssigned().toArray(new String[0]));
		this.revalidate();
		this.repaint();
	}

	/**
	 * Takes the members that the user has selected and moves them to the list of members assigned to a task
	 */
	public void moveMembersToAssigned() {	
		System.out.println("Moving to assigned!");
		MemberListHandler.getInstance().assignMember(members.getSelectedValuesList());
		updateMembers();

		System.out.println(MemberListHandler.getInstance().getAssigned().size());
		this.task.setAssignedTo(MemberListHandler.getInstance().getAssigned());
		saveTask();

		this.allMembersMouseHandler.clear();
		this.assignedMembersMouseHandler.clear();
		
	}

	/**
	 * Take the members that are selected in the Assigned Members list and moves them back to the All Members list
	 */
	public void moveMembersToAll() {
		MemberListHandler.getInstance().unAssignMember(assignedMembers.getSelectedValuesList());
		updateMembers();
		this.task.setAssignedTo(MemberListHandler.getInstance().getAssigned());
		System.out.println(MemberListHandler.getInstance().getAssigned().size());
		saveTask();
		this.allMembersMouseHandler.clear();
		this.assignedMembersMouseHandler.clear();
	}
	
	
	/**
	 * This takes in a task and updates the task linked to this panel. This is used in the scenario where a task 
	 * is mutated outside of the task edit view. Most likely only by Drag and Drop - possible to be changed by 
	 * other implementations. 
	 * 
	 * @param updatedTask is the task that is set to the new task in the edit view. 
	 */
	public void updateEVTask(Task updatedTask){
		this.task = updatedTask;
		
	}

	/**
	 * Place the requirement titles into the dropdown menu
	 * @param requirementsArray Passed in list of requirements from Requirements Manager
	 */
	public void getRequirements(Requirement[] requirementsArray) {
		this.requirements = requirementsArray;
		this.requirementTitles = getRequirementTitles();
		ComboBoxModel<String> model = this.requirementsComboBox.getModel();
		int size = model.getSize();
		boolean foundRequirement = false;
		if (!task.getRequirement().getName().isEmpty()) {
			this.requirementsComboBox.setSelectedItem(task.getRequirement().getName());
		}
		if (size < requirementTitles.size()) {
			for (String s : this.requirementTitles) {
				foundRequirement = false;
				for (int i = 0; i < size; i++) {
					Object element = model.getElementAt(i);
					if (s == element) {
						foundRequirement = true;
					}
				}
				if (!foundRequirement) {
					FontMetrics fm = this.requirementsComboBox.getFontMetrics(this.requirementsComboBox.getFont());
					String reducedString = TaskManagerUtil.reduceString(s, 220, fm);
					this.requirementsComboBox.addItem(reducedString);
				}
			}	
			if (!task.getRequirement().getName().isEmpty()) {
				this.requirementsComboBox.setSelectedItem(task.getRequirement().getName());
			}
			else {
				this.requirementsComboBox.setSelectedIndex(-1);
			}
		}
	}

	/**
	 * Return the titles of the requirements
	 */
	public List<String> getRequirementTitles() {
		this.requirementTitles.clear();
		for (Requirement r : this.requirements) {
			requirementTitles.add(r.getName());
		}
		return requirementTitles;
	}

	private class JListMouseHandler implements MouseListener {

		JList<String> list;
		Boolean just_changed;
		int[] previous_indexes;
		int keyboard_event_count;

		public JListMouseHandler (JList<String> list) {
			this.list = list;
			just_changed = false;
			previous_indexes = list.getSelectedIndices();
		}

		public void mousePressed(MouseEvent e) {
			int clicked_index = this.list.locationToIndex(e.getPoint());
			if (this.just_changed) {
				this.just_changed = false;

				for (int i : previous_indexes) {
					if (!this.inArray(i, this.list.getSelectedIndices())) {
						this.list.addSelectionInterval(i, i);
					}
				}
				if (this.inArray(clicked_index, this.list.getSelectedIndices()) && this.inArray(clicked_index, previous_indexes)) {
					this.list.removeSelectionInterval(clicked_index, clicked_index);
				}
			}
			else {
				list.removeSelectionInterval(clicked_index, clicked_index);
			}
			this.previous_indexes = this.list.getSelectedIndices();

		}

		public void mouseReleased(MouseEvent e) {}

		public void mouseEntered(MouseEvent e) {}

		public void mouseExited(MouseEvent e) {}

		public void mouseClicked(MouseEvent e) {}

		@SuppressWarnings("unused")
		public void update_selected() {
			if (this.keyboard_event_count == 0) {
				this.previous_indexes = this.list.getSelectedIndices();
				this.keyboard_event_count++;
			}
		}

		public void clear() {
			this.list.clearSelection();
			this.previous_indexes = this.list.getSelectedIndices();
		}

		private Boolean inArray(int to_check, int[] array) {
			for (int i : array) {
				if (i == to_check) {
					return true;
				}
			}
			return false;
		}
	}

	
	
	
	
	
	
}


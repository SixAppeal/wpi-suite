package edu.wpi.cs.wpisuitetng.modules.taskmanager.view.sidebar;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListSelectionModel;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.ListSelectionModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.jdesktop.swingx.JXDatePicker;

import edu.wpi.cs.wpisuitetng.modules.taskmanager.model.Stage;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.model.Task;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.presenter.Gateway;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.view.IView;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.view.components.Form;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.view.components.FormField;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.view.components.FormGroup;

/**
 * 
 * @author wmtemple
 * @author akshoop
 * @author rnorlando
 * @author wavanrensselaer
 * @author jrhennessy
 * @author nhhughes
 * @author rwang3
 * @author akshoop
 * @author rnorlando
 * @author Thhughes
 *
 * A view to be displayed when creating or modifying a task object in the GUI.
 *
 */
public class TaskEditView extends JPanel implements IView {

	/**
	 * Eclipse-generated Serial Version UID	compile 'com.miglayout:miglayout-swing:4.1'
	 */
	private static final long serialVersionUID = -8331650108561001757L;

	protected Gateway gateway;
	protected Task t;

	Form form;
	JTextField titleEntry;
	JTextArea descEntry;
	JScrollPane descEntryScroller;
	JComboBox<Stage> statusBox;
	JSpinner estEffortSpinner;
	JSpinner actEffortSpinner;
	SpinnerNumberModel estEffortSpinnerModel;
	SpinnerNumberModel actEffortSpinnerModel;
	JXDatePicker dueDatePicker;
	JButton saveButton;
	JButton cancelButton;
	JLabel errorText;	
	HashMap<String, Boolean> requirderFieldFlags = new HashMap<String,Boolean>();
	

	JScrollPane allMembersListScroller;
	JScrollPane assignedMembersListScroller;
	//Member stuff
	JList<String> allMembers;
	JList<String> assignedMembers;
	JButton addMemberButton;
	JButton removeMemberButton;
	
	MemberListHandler EditViewMemberHandler;
	
	// Being replaced by MemberListHandler, uncommented to not throw errors. 
	List<String> allMembersList;
	List<String> assignedMembersList;
	List<String> globalMembersList;
	
	JListMouseHandler allMembersMouseHandler;
	JListMouseHandler assignedMembersMouseHandler;


	/**
	 * Create a new TaskEditView
	 */
	public TaskEditView () {
		
		t = new Task();		// Internal Task

		this.setOpaque(false);
		this.setLayout(new GridBagLayout());

		// Gui Setup
		titleEntry = new JTextField();
		titleEntry.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent e)
			{
				checkValidField(titleEntry, titleEntry.getText(), "titleEntry");
			}
		});

		descEntry = new JTextArea(5,0);
		descEntry.setLineWrap(true);
		descEntry.setWrapStyleWord(true);
		descEntry.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent e)
			{
				checkValidField(descEntryScroller, descEntry.getText(), "descEntry");
			}
		});

		dueDatePicker = new JXDatePicker( new Date(System.currentTimeMillis()));

		estEffortSpinnerModel = new SpinnerNumberModel(0, null, null, 1);
		estEffortSpinner = new JSpinner( estEffortSpinnerModel );

		estEffortSpinner.addChangeListener(new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent e)
			{
				checkValidityOfSpinner(estEffortSpinner, "Est. Effort");
			}
		});
		
		actEffortSpinnerModel = new SpinnerNumberModel(1, null, null, 1);
		actEffortSpinner = new JSpinner( actEffortSpinnerModel );
		actEffortSpinner.setEnabled(false);
		actEffortSpinner.addChangeListener(new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent e)
			{
				checkValidityOfSpinner(actEffortSpinner, "Act. Effort");
			}
		});
		
		statusBox = new JComboBox<Stage>();
		statusBox.addItem(new Stage("New"));
		statusBox.addItem(new Stage("Scheduled"));
		statusBox.addItem(new Stage("In Progress"));
		statusBox.addItem(new Stage("Complete"));

		saveButton = new JButton("Save");
		saveButton.setEnabled(false);
		saveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				processTask();
			}
		});

		cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				clearForm();
				gateway.toPresenter("TaskPresenter", "toolbarDefault");
			}
		});

		this.descEntryScroller = new JScrollPane(descEntry);

		//Member Stuff
		EditViewMemberHandler = new MemberListHandler();
		
		// TODO: Delete below lists after cache information is working. 
//		allMembersList = new ArrayList<String>();
//		assignedMembersList = new ArrayList<String>();
//		globalMembersList = new ArrayList<String>();
		
		// Set UI for members
		allMembers = new JList<String>();
		allMembers.setVisibleRowCount(4);
		allMembers.setLayoutOrientation(JList.VERTICAL);
		this.allMembersMouseHandler = new JListMouseHandler(allMembers);
		allMembers.addMouseListener(allMembersMouseHandler);
		allMembers.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				notifyAllMembersMouseHandler();
			}
		});
		allMembersListScroller = new JScrollPane(allMembers);
		
		assignedMembers = new JList<String>();
		assignedMembers.setVisibleRowCount(4);
		assignedMembers.setLayoutOrientation(JList.VERTICAL);
		this.assignedMembersMouseHandler = new JListMouseHandler(assignedMembers);
		assignedMembers.addMouseListener(assignedMembersMouseHandler);
		assignedMembers.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				notifyAssignedMembersMouseHandler();
			}
		});
		assignedMembersListScroller = new JScrollPane(assignedMembers);
		
		addMemberButton = new JButton(">");
		addMemberButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				moveMembersToAssigned();
			}
		});
		
		removeMemberButton = new JButton("<");
		removeMemberButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				moveMembersToAll();
			}
		});

		Box buttonBox = new Box(BoxLayout.Y_AXIS);
		buttonBox.add(addMemberButton);
		buttonBox.add(new JLabel(""));
		buttonBox.add(removeMemberButton);
		
		errorText = new JLabel("Invalid Input", JLabel.CENTER);
		errorText.setForeground(Color.RED);
		errorText.setBorder(FormField.BORDER_ERROR);
		
		// GUI Layout Stuff
		checkValidityOfSpinner(estEffortSpinner , "Est. Effort");
		
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.anchor = GridBagConstraints.PAGE_START;
		gbc.weightx = 1.0;
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.insets = new Insets(20, 0, 0, 0);
		this.add(new JLabel(this.getTitle(), JLabel.CENTER), gbc);

		gbc.insets.top = 0;
		gbc.weighty = 1.0;
		gbc.gridy = 1;
		this.form = new Form(
			new FormField("Title", titleEntry),
			new FormField("Description", descEntryScroller),
			new FormField("Due Date", dueDatePicker),
			new FormGroup(
				new FormField("Est. Effort", estEffortSpinner),
				new FormField("Act. Effort", actEffortSpinner)
			),
			new FormGroup(
				new FormField("Members", allMembersListScroller),
				buttonBox,
				new FormField("Assigned", assignedMembersListScroller)
			),
			new FormField("Status", statusBox),
			new FormGroup(saveButton, cancelButton),
			new FormField(null, errorText)
		);
		
		this.add(this.form, gbc);
	}

	public void notifyAllMembersMouseHandler() {
		this.allMembersMouseHandler.just_changed = true;
	}

	public void notifyAssignedMembersMouseHandler() {
		this.assignedMembersMouseHandler.just_changed = true;
	}
	


	/*
	 * TODO: setAllMembers should be removed. 
	 * Currently is not removed because the system throws 
	 * errors because it expects it
	 */
	
	/**
	 * Allows a request from the server to add to the list of all members available to assign to a task
	 * @param to_add Members from the server that are going to be added to the All Members list
	 */
	public void setAllMembers(String [] to_add) {
		for (int i = 0; i < to_add.length; i++) {
			if (!this.allMembersList.contains(to_add[i])) {
				allMembersList.add(to_add[i]);
			}
		}
		allMembers.setListData(allMembersList.toArray(new String[allMembersList.size()]));
	}

	/**
	 * 
	 * @param assigned
	 * @param all
	 * 
	 *  Update Panels is used to redraw the lists once something is changed
	 */
	public void updatePanels(List<String> assigned, List<String> all) {
		allMembers.setListData(all.toArray(new String[all.size()]));
		assignedMembers.setListData(assigned.toArray(new String[assigned.size()]));
		fixPanel();
	}
	
	/**
	 * Fixes the display of the panel containing the member information
	 * 
	 */
	public void fixPanel() {
		if(EditViewMemberHandler.getAssigned().size() == 0){
			String [] empty_string_list = new String[1];
			empty_string_list[0] = "";
			this.assignedMembers.setListData(empty_string_list);	
		}
		if (EditViewMemberHandler.getUnassigned().size() == 0) {
			String [] empty_string_list = new String[1];
			empty_string_list[0] = "";
			this.allMembers.setListData(empty_string_list);
		}
		this.allMembers.revalidate();
		this.allMembersListScroller.revalidate();
		this.allMembers.repaint();
		this.allMembersListScroller.repaint();
		this.assignedMembers.revalidate();
		this.assignedMembersListScroller.revalidate();
		this.assignedMembers.repaint();
		this.assignedMembersListScroller.repaint();
		this.revalidate();
		this.repaint();
	}
	
	/*
	 * TODO: Remove addMember(String ...)
	 * 
	 * Currently in the system to avoid errors due to refactoring
	 */
	public void addMember(String member_to_add) {
		
		this.globalMembersList.add(member_to_add);
		this.allMembersList = new ArrayList<String>(globalMembersList);
		String to_print = "";
		for (String s: allMembersList) {
			to_print = to_print + ", " + s;
		}
		System.out.println(to_print);
		allMembers.setListData(allMembersList.toArray(new String[allMembersList.size()]));
		fixPanel();
	}
	
	/**
	 * Takes the members that the user has selected and moves them to the list of members assigned to a task
	 */
	public void moveMembersToAssigned() {	
		// Add selected members
		EditViewMemberHandler.assignMember(allMembers.getSelectedValuesList());
		
		
		// Set the unsassigned/assigned JList
		allMembers.setListData(EditViewMemberHandler.getUnassigned().toArray(new String[EditViewMemberHandler.getNumUnAssigned()]));
		allMembers.setListData(EditViewMemberHandler.getAssigned().toArray(new String[EditViewMemberHandler.getNumAssigned()]));
		
		this.allMembersMouseHandler.clear();
		this.assignedMembersMouseHandler.clear();
		fixPanel();
	}
	
	/**
	 * Take the members that are selected in the Assigned Members list and moves them back to the All Members list
	 */
	public void moveMembersToAll() {
		// Add selected members
		EditViewMemberHandler.unAssignMember(allMembers.getSelectedValuesList());
		
		
		// Set the unsassigned/assigned JList
		allMembers.setListData(EditViewMemberHandler.getUnassigned().toArray(new String[EditViewMemberHandler.getNumUnAssigned()]));
		allMembers.setListData(EditViewMemberHandler.getAssigned().toArray(new String[EditViewMemberHandler.getNumAssigned()]));
		
		this.allMembersMouseHandler.clear();
		this.assignedMembersMouseHandler.clear();
		fixPanel();
	}

	/**
	 * @see edu.wpi.cs.wpisuitetng.modules.taskmanager.view.IView#setGateway()
	 */
	@Override
	public void setGateway(Gateway gateway) {
		this.gateway = gateway;
	}


	/**
	 * Updates the view with a new task.
	 * @param t The task with which to populate the view.
	 */
	public void updateView( Task t ) {

		this.t = t;
		populate();
		updateBorder(titleEntry, titleEntry.getText());
		updateBorder(descEntryScroller, descEntry.getText());
		saveButton.setEnabled(true);

	}

	/**
	 * Populates all entries with contents from the task model t
	 */
	private void populate() {

		Date d = t.getDueDate();

		titleEntry.setText(t.getTitle());
		descEntry.setText(t.getDescription());
		estEffortSpinnerModel.setValue(t.getEstimatedEffort());
		if (t.getActualEffort() <= 0) {
			actEffortSpinnerModel.setValue(1);
		}
		else {
			actEffortSpinnerModel.setValue(t.getActualEffort());
		}
		dueDatePicker.setDate( d );
		
		statusBox.setSelectedItem(t.getStage());

		String stat = t.getStage().toString();
	
		//This is hard coded and should be fixed at some point in the future
		//I agree
		actEffortSpinner.setEnabled( (stat.equals("In Progress")) || (stat.equals("Complete")) );
		
		
		EditViewMemberHandler.populateMembers(t.getAssignedTo());
		updatePanels(EditViewMemberHandler.getAssigned(), EditViewMemberHandler.getUnassigned());
	}
	
	/**
	 * grabs all the data from the form
	 */
	private void processTask() {

		String title = titleEntry.getText();
		String desc = descEntry.getText();
		Stage st = (Stage)statusBox.getSelectedItem();
		int est = (Integer)estEffortSpinnerModel.getValue();
		int act = (Integer)actEffortSpinnerModel.getValue();

		if(title.isEmpty() || desc.isEmpty())
		{
			return;
		}

		try {

			t.setTitle(title);
			t.setDescription(desc);
			t.setStage(st);
			t.setEstimatedEffort(est);
			t.setActualEffort(act);
			t.setDueDate( dueDatePicker.getDate() );
			//t.setColumn(statusBox.getSelectedIndex());  //TODO change this to new status implementtation
			t.setAssignedTo(new ArrayList<String>(this.EditViewMemberHandler.getAssigned()));
			System.out.println(t.toJson());
		} catch (IllegalArgumentException ex) {
			System.err.println(ex.toString());
			return;
		}
		
		// Why do we do this?? 
//		this.assignedMembersList = new ArrayList<String> ();
//		this.allMembersList = new ArrayList<String> ();
		taskOut();
		
	}

	/**
	 * Gets the title for this view
	 * @return A title
	 */
	protected String getTitle() {
		return "Edit Task";
	}
	
	/**
	 * sends the task to the presenter
	 */
	protected void taskOut() {
		gateway.toPresenter("TaskPresenter", "updateTask", t);
	}

	/**
	 * Updates the board of the JTextComponents, 
	 * if it is empty it will indicate that it is a required field.
	 * updates the borders based of whether the text is empty or has something
	 * @param someComponent Text area that gets updated.
	 * @param text The text relating to the field.
	 */
	protected void updateBorder(JComponent someComponent, String text)
	{
		if(text.isEmpty())
		{
			someComponent.setBorder(FormField.BORDER_ERROR);
		}
		else
		{
			someComponent.setBorder(FormField.BORDER_NORMAL);
		}
	}

	/**
	 * Check to see if the field is valid, (set flags for save button)
	 * update border, whether it is valid or not,.
	 * @param someComponent The JComponent the field is held in.
	 * @param data The data we are check if valid.
	 * @param field The required field we are checking.
	 */
	protected void checkValidField(JComponent someComponent, String data, String field)
	{
		updateBorder(someComponent, data);
		this.requirderFieldFlags.put(field, !data.isEmpty());
		
		update();
	}
	
	/**
	 *  update any field for changeable information
	 */
	public void update()
	{
		updateErrorMessage();
		attemptToEnableSaveButton();
	}

	/**
	 * reveals the error message if a single field is not valid
	 */
	private void updateErrorMessage()
	{
		boolean vis = true;
		for(String key: this.requirderFieldFlags.keySet())
		{
			vis = vis && this.requirderFieldFlags.get(key);
		}
		errorText.setVisible(!vis);
	}
	
	/**
	 * Check the flags of all required field to see if they're all true.
	 * If yes, then enable save button.
	 * If no, disable save button.
	 */
	protected void attemptToEnableSaveButton()
	{
		boolean enable = true;
		for(String key: this.requirderFieldFlags.keySet())
		{
			enable = enable && this.requirderFieldFlags.get(key);
		}
		saveButton.setEnabled(enable);
	}

	/**
	 * Clears the creation form
	 */
	public void clearForm() {
		this.titleEntry.setText("");
		this.descEntry.setText("");
		this.estEffortSpinner.setValue(1);
		this.actEffortSpinner.setValue(0);
		this.titleEntry.setBorder((new JTextField()).getBorder());
		this.descEntry.setBorder(null);
		this.descEntryScroller.setBorder((new JScrollPane()).getBorder());

		for(String key: this.requirderFieldFlags.keySet())
		{
			this.requirderFieldFlags.put(key, false);
		}
		saveButton.setEnabled(false);

		updateBorder(titleEntry, titleEntry.getText());
		updateBorder(descEntryScroller, descEntry.getText());
	}
	
	/**
	 * Checks if the spinner has a valid value
	 * creates an error if the value is <=0
	 * @param spinner
	 * @param names
	 */
	public void checkValidityOfSpinner(JSpinner spinner, String names)
	{
		if((int)spinner.getValue() <= 0)
		{
			requirderFieldFlags.put(names, false);
			spinner.setBorder(FormField.BORDER_ERROR);
		}
		else
		{
			requirderFieldFlags.put(names, true);
			spinner.setBorder(FormField.BORDER_NORMAL);
		}
		update();
	
		
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

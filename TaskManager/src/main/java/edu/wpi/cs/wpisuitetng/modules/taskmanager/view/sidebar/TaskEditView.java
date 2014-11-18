package edu.wpi.cs.wpisuitetng.modules.taskmanager.view.sidebar;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
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
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.jdesktop.swingx.JXDatePicker;

import edu.wpi.cs.wpisuitetng.modules.taskmanager.model.Task;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.model.TaskStatus;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.presenter.Gateway;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.presenter.TaskPresenter;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.view.IView;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.view.components.Form;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.view.components.FormField;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.view.components.FormGroup;

/**
 * 
 * @author wmtemple
<<<<<<< HEAD
 * @author akshoop
 * @author rnorlando
 * @author wavanrensselaer
=======
 * @author jrhennessy
 * @author nhhughes
 * @author rwang3
 * @author akshoop
 * @author rnorlando
>>>>>>> origin/devel_member
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
	private TaskPresenter presenter;
	protected Task t;

	Form form;
	JTextField titleEntry;
	JTextArea descEntry;
	JScrollPane descEntryScroller;
	JComboBox<TaskStatus> statusBox;
	JSpinner estEffortSpinner;
	JSpinner actEffortSpinner;
	SpinnerNumberModel estEffortSpinnerModel;
	SpinnerNumberModel actEffortSpinnerModel;
	JXDatePicker dueDatePicker;
	JButton saveButton;
	JButton cancelButton;	
	HashMap<String, Boolean> requirderFieldFlags = new HashMap<String,Boolean>();

	//Member stuff
	JList<String> allMembers;
	JList<String> assignedMembers;
	JButton addMemberButton;
	JButton removeMemberButton;
	String[] membersTest1 = {"user1", "user2", "user3","user1", "user2", "user3","user1", "user2", "user3","user1", "user2", "user3"};
	String[] membersTest2 = {"user4", "user5", "user6"};
	List<String> allMembersList;
	List<String> assignedMembersList;
	JListMouseHandler mouse;


	/**
	 * Create a new TaskEditView
	 */
	public TaskEditView () {
		this.setOpaque(false);
		this.setLayout(new GridBagLayout());

		titleEntry = new JTextField();
		titleEntry.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent e)
			{
				checkValidField(titleEntry, titleEntry.getText(), "titleEntry");
			}
		}
				);

		descEntry = new JTextArea(5,0);
		descEntry.setLineWrap(true);
		descEntry.setWrapStyleWord(true);
		descEntry.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent e)
			{
				checkValidField(descEntryScroller, descEntry.getText(), "descEntry");
			}
		}
				);

		dueDatePicker = new JXDatePicker( new Date(System.currentTimeMillis()));

		estEffortSpinnerModel = new SpinnerNumberModel(1, 1, null, 1);
		estEffortSpinner = new JSpinner( estEffortSpinnerModel );

		actEffortSpinnerModel = new SpinnerNumberModel(0, 0, null, 1);
		actEffortSpinner = new JSpinner( actEffortSpinnerModel );
		actEffortSpinner.setEnabled(false);

		statusBox = new JComboBox<TaskStatus>();
		statusBox.addItem(new TaskStatus("New"));
		statusBox.addItem(new TaskStatus("Scheduled"));
		statusBox.addItem(new TaskStatus("In Progress"));
		statusBox.addItem(new TaskStatus("Complete"));

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
		allMembersList = new ArrayList<String>(Arrays.asList(membersTest1));
		assignedMembersList = new ArrayList<String>(Arrays.asList(membersTest2));
		allMembers = new JList<String>(membersTest1);
		allMembers.setVisibleRowCount(4);
		assignedMembers = new JList<String>(membersTest2);
		assignedMembers.setVisibleRowCount(4);
		addMemberButton = new JButton(">");
		addMemberButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				moveMembersToAssigned();
			}
		});
		this.mouse = new JListMouseHandler(allMembers);
		allMembers.addMouseListener(mouse);
		allMembers.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				notifyMouseHandler();
			}
		});


		removeMemberButton = new JButton("<");
		removeMemberButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				moveMembersToAll();
			}
		});

		//		ListSelectionModel allMembersSelectionManger = new MartyListSelectionModel();
		//		allMembers.setSelectionModel(allMembersSelectionManger);

		allMembers.setLayoutOrientation(JList.VERTICAL);
		assignedMembers.setLayoutOrientation(JList.VERTICAL);
		JScrollPane allMembersListScroller = new JScrollPane(allMembers);
		JScrollPane assignedMembersListScroller = new JScrollPane(assignedMembers);

		Box buttonBox = new Box(BoxLayout.Y_AXIS);
		buttonBox.add(addMemberButton);
		buttonBox.add(new JLabel(""));
		buttonBox.add(removeMemberButton);

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
								new FormGroup(saveButton, cancelButton)
				);

		this.add(this.form, gbc);
	}

	public void notifyMouseHandler() {
		this.mouse.just_changed = true;
	}

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
	}

	/**
	 * Takes the members that the user has selected and moves them to the list of members assigned to a task
	 */
	public void moveMembersToAssigned() {
		List<String> selected = allMembers.getSelectedValuesList();
		List<String> updatedAll = new ArrayList<String>();
		for (String s : allMembersList) {
			if (!selected.contains(s)) {
				updatedAll.add(s);
			}
		}
		for (String s: selected) {
			assignedMembersList.add(s);
		}
		allMembersList = updatedAll;
		allMembers.setListData(updatedAll.toArray(new String[updatedAll.size()]));
		assignedMembers.setListData(assignedMembersList.toArray(new String[assignedMembersList.size()]));
	}

	/**
	 * Take the members that are selected in the Assigned Members list and moves them back to the All Members list
	 */
	public void moveMembersToAll() {
		List<String> selected = assignedMembers.getSelectedValuesList();
		List<String> updatedAssigned = new ArrayList<String>();
		for (String s : assignedMembersList) {
			if (!selected.contains(s)) {
				updatedAssigned.add(s);
			}
		}
		for (String s: selected) {
			allMembersList.add(s);
		}
		assignedMembersList = updatedAssigned;
		allMembers.setListData(allMembersList.toArray(new String[allMembersList.size()]));
		assignedMembers.setListData(updatedAssigned.toArray(new String[updatedAssigned.size()]));
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
		actEffortSpinnerModel.setValue(t.getActualEffort());
		dueDatePicker.setDate( d );
		statusBox.setSelectedItem(t.getStatus());

		String stat = t.getStatus().toString();

		//This is hard coded and should be fixed at some point in the future
		actEffortSpinner.setEnabled( (stat.equals("In Progress")) || (stat.equals("Complete")) );
	}


	private void processTask() {
		t = new Task();

		String title = titleEntry.getText();
		String desc = descEntry.getText();
		TaskStatus st = (TaskStatus)statusBox.getSelectedItem();
		int est = (Integer)estEffortSpinnerModel.getValue();
		int act = (Integer)actEffortSpinnerModel.getValue();

		if(title.isEmpty() || desc.isEmpty())
		{
			return;
		}

		try {

			t.setTitle(title);
			t.setDescription(desc);
			t.setStatus(st);
			t.setEstimatedEffort(est);
			t.setActualEffort(act);
			t.setDueDate( dueDatePicker.getDate() );
			t.setColumn(statusBox.getSelectedIndex());
		} catch (IllegalArgumentException ex) {
			System.out.println(ex.getMessage());
			return;
		}

		taskOut();

	}

	/**
	 * Gets the title for this view
	 * @return A title
	 */
	protected String getTitle() {
		return "Edit Task";
	}

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

		attemptToEnableSaveButton();
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

	private class JListEmptySelectionModel implements ListSelectionModel {

		@Override
		public void setSelectionInterval(int index0, int index1) {
			// TODO Auto-generated method stub

		}

		@Override
		public void addSelectionInterval(int index0, int index1) {
			// TODO Auto-generated method stub

		}

		@Override
		public void removeSelectionInterval(int index0, int index1) {
			// TODO Auto-generated method stub

		}

		@Override
		public int getMinSelectionIndex() {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public int getMaxSelectionIndex() {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public boolean isSelectedIndex(int index) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public int getAnchorSelectionIndex() {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public void setAnchorSelectionIndex(int index) {
			// TODO Auto-generated method stub

		}

		@Override
		public int getLeadSelectionIndex() {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public void setLeadSelectionIndex(int index) {
			// TODO Auto-generated method stub

		}

		@Override
		public void clearSelection() {
			// TODO Auto-generated method stub

		}

		@Override
		public boolean isSelectionEmpty() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public void insertIndexInterval(int index, int length, boolean before) {
			// TODO Auto-generated method stub

		}

		@Override
		public void removeIndexInterval(int index0, int index1) {
			// TODO Auto-generated method stub

		}

		@Override
		public void setValueIsAdjusting(boolean valueIsAdjusting) {
			// TODO Auto-generated method stub

		}

		@Override
		public boolean getValueIsAdjusting() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public void setSelectionMode(int selectionMode) {
			// TODO Auto-generated method stub

		}

		@Override
		public int getSelectionMode() {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public void addListSelectionListener(ListSelectionListener x) {
			// TODO Auto-generated method stub

		}

		@Override
		public void removeListSelectionListener(ListSelectionListener x) {
			// TODO Auto-generated method stub

		}

	}

	private class JListMouseHandler implements MouseListener {

		JList<String> list;
		Boolean just_changed;
		int[] previous_indexes;

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

		public void mouseClicked(MouseEvent e) {

//			int clicked_index = this.list.locationToIndex(e.getPoint());
//			if (this.just_changed) {
//				this.just_changed = false;
//				
//				for (int i : previous_indexes) {
//					if (!this.inArray(i, this.list.getSelectedIndices())) {
//						this.list.addSelectionInterval(i, i);
//					}
//				}
//				if (this.inArray(clicked_index, this.list.getSelectedIndices()) && this.inArray(clicked_index, previous_indexes)) {
//					this.list.removeSelectionInterval(clicked_index, clicked_index);
//				}
//			}
//			else {
//				list.removeSelectionInterval(clicked_index, clicked_index);
//			}
//			this.previous_indexes = this.list.getSelectedIndices();

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

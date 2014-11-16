package edu.wpi.cs.wpisuitetng.modules.taskmanager.view.sidebar;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.BorderFactory;

import org.jdesktop.swingx.JXDatePicker;

import net.miginfocom.swing.MigLayout;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.model.Task;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.model.TaskStatus;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.presenter.Gateway;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.presenter.TaskPresenter;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.view.IView;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.view.columnar.ColumnView;

/**
 * 
 * @author wmtemple
 * @author jrhennessy
 * @author nhhughes
 * @author rwang3
 * @author akshoop
 * @author rnorlando
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
	
	JTextField titleEntry;
	JTextArea descEntry;
	JComboBox<TaskStatus> statusBox;
	JSpinner estEffortSpinner;
	JSpinner actEffortSpinner;
	SpinnerNumberModel estEffortSpinnerModel;
	SpinnerNumberModel actEffortSpinnerModel;
	JXDatePicker dueDatePicker;
	JButton saveButton;
	//Member stuff
	JList<String> allMembers;
	JList<String> assignedMembers;
	JButton addMemberButton;
	JButton removeMemberButton;
	String[] membersTest1 = {"user1", "user2", "user3","user1", "user2", "user3","user1", "user2", "user3","user1", "user2", "user3"};
	String[] membersTest2 = {"user4", "user5", "user6"};
	List<String> allMembersList;
	List<String> assignedMembersList;
	
	
	/**
	 * Create a new TaskEditView
	 */
	public TaskEditView () {
		
		//If this is a task creation panel, use a different title text
		String paneTitle = getTitle();
		
		// MigLayout gives us the easiest layout with best flexibility
		MigLayout layout = new MigLayout(
				"wrap 3",						//Layout constraints
				//changed wrap to 3 cells
				"[right][][left, 100::, grow]", 	//Column constraints
				//add the empty brackets to create a third cell
				"");							//Row Constraints
		
		this.setOpaque(false);
		this.setBorder(BorderFactory.createTitledBorder(paneTitle));
		this.setLayout(layout);
		
		titleEntry = new JTextField();
		
		descEntry = new JTextArea(5,0);
		descEntry.setLineWrap(true);
		descEntry.setWrapStyleWord(true);
		
		dueDatePicker = new JXDatePicker( new Date(System.currentTimeMillis()));

		estEffortSpinnerModel = new SpinnerNumberModel(1, 1, null, 1);
		estEffortSpinner = new JSpinner( estEffortSpinnerModel );
		
		actEffortSpinnerModel = new SpinnerNumberModel(0, 0, null, 1);
		actEffortSpinner = new JSpinner( actEffortSpinnerModel );
		actEffortSpinner.setEnabled(false);
		
		statusBox = new JComboBox<TaskStatus>();
		statusBox.addItem(new TaskStatus("Backlog"));
		statusBox.addItem(new TaskStatus("Development"));
		statusBox.addItem(new TaskStatus("Testing"));
		statusBox.addItem(new TaskStatus("Live"));
		
		saveButton = new JButton("Save");
		saveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				processTask();
			}
		});
		
		this.add( new JLabel("Title :"), "top" );
		this.add( titleEntry, "wrap, width 100:150, growx, span 2" );
		
		this.add( new JLabel("Description :"), "top" );
		this.add( new JScrollPane(descEntry), "wrap, grow, span 2");
		
		this.add( new JLabel("Due Date :") );
		this.add( dueDatePicker, "wrap, width 120:120:200, growx, span 2" );
		
		this.add( new JLabel("Est. Effort :") );
		this.add( estEffortSpinner, "wrap, width 50:120:150, span 2" );
		
		this.add( new JLabel("Act. Effort :") );
		this.add( actEffortSpinner, "wrap, width 50:120:150, span 2" );
		
		this.add( new JLabel("Task Status :") );
		this.add( statusBox, "wrap, width 50:120:150, span 2" );
		
		this.add( saveButton, "span 3, wrap, right" );
		
		//Member Stuff
		allMembersList = new ArrayList<String>(Arrays.asList(membersTest1));
		assignedMembersList = new ArrayList<String>(Arrays.asList(membersTest2));
		allMembers = new JList<String>(membersTest1);
		assignedMembers = new JList<String>(membersTest2);
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
		
		
		
		
		allMembers.setLayoutOrientation(JList.VERTICAL);
		assignedMembers.setLayoutOrientation(JList.VERTICAL);
		JScrollPane allMembersListScroller = new JScrollPane(allMembers);
		JScrollPane assignedMembersListScroller = new JScrollPane(assignedMembers);
		
		Box buttonBox = new Box(BoxLayout.PAGE_AXIS);
		buttonBox.add(addMemberButton);
		buttonBox.add(new JLabel(""));
		buttonBox.add(removeMemberButton);
		this.add( new JLabel("Members Available"), "width 150:150:200, growx" );
		this.add( new JLabel("Members Assigned    "), "width 150:150:200, growx, right, cell 2 7" );
		this.add(allMembersListScroller, "width 50:50:100, growx, left" );
		this.add( buttonBox, "width 50:50:100, center"  );
		this.add(assignedMembersListScroller, "width 50:50:100, growx, right" );
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
		
		actEffortSpinner.setEnabled( (stat == "Testing") || (stat == "Live") );
		
	}
	
	
	private void processTask() {
		t = new Task();
		
		String title = titleEntry.getText();
		String desc = descEntry.getText();
		TaskStatus st = (TaskStatus)statusBox.getSelectedItem();
		int est = (Integer)estEffortSpinnerModel.getValue();
		int act = (Integer)actEffortSpinnerModel.getValue();
		
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
	
	
}

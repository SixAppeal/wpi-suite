package edu.wpi.cs.wpisuitetng.modules.taskmanager.view.sidebar;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.BorderFactory;
import javax.swing.text.JTextComponent;

import org.jdesktop.swingx.JXDatePicker;

import net.miginfocom.swing.MigLayout;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.model.Task;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.model.TaskStatus;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.presenter.Gateway;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.view.IView;

/**
 * 
 * @author wmtemple
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
	protected Task t;
	
	JTextField titleEntry;
	JTextArea descEntry;
	JScrollPane descEntryScoller;
	JComboBox<TaskStatus> statusBox;
	JSpinner estEffortSpinner;
	JSpinner actEffortSpinner;
	SpinnerNumberModel estEffortSpinnerModel;
	SpinnerNumberModel actEffortSpinnerModel;
	JXDatePicker dueDatePicker;
	JTextArea membersTextArea;
	JTextField newMemberField;
	JButton addNewMemberButton;
	JButton saveButton;
	// TODO: There is a better datastucture out there to do this
	boolean[] requiredFieldFlags = {false , false};
	String[] requiredFieldNames = {"titleEntry", "descEntry"};
	
	
	/**
	 * Create a new TaskEditView
	 */
	public TaskEditView () {
		
		//If this is a task creation panel, use a different title text
		String paneTitle = getTitle();
		
		// MigLayout gives us the easiest layout with best flexibility
		MigLayout layout = new MigLayout(
				"wrap 2",						//Layout constraints
				"[right][left, 100::, grow]", 	//Column constraints
				"");							//Row Constraints
		
		this.setOpaque(false);
		this.setBorder(BorderFactory.createTitledBorder(paneTitle));
		this.setLayout(layout);
		
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
				checkValidField(descEntryScoller, descEntry.getText(), "descEntry");
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
		statusBox.addItem(new TaskStatus("Backlog"));
		statusBox.addItem(new TaskStatus("Development"));
		statusBox.addItem(new TaskStatus("Testing"));
		statusBox.addItem(new TaskStatus("Live"));
		
		membersTextArea = new JTextArea(5,0);
		membersTextArea.setLineWrap(true);
		membersTextArea.setWrapStyleWord(true);
		
		saveButton = new JButton("Save");
		saveButton.setEnabled(false);
		saveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				processTask();
			}
		});
		
		this.descEntryScoller = new JScrollPane(descEntry);
		
		this.add( new JLabel("Title :"), "top" );
		this.add( titleEntry, "wrap, width 100:150, growx" );
		
		this.add( new JLabel("Description :"), "top" );
		this.add( descEntryScoller, "wrap, grow");
		
		this.add( new JLabel("Due Date :") );
		this.add( dueDatePicker, "wrap, width 120:120:200, growx" );
		
		this.add( new JLabel("Est. Effort :") );
		this.add( estEffortSpinner, "wrap, width 50:120:150" );
		
		this.add( new JLabel("Act. Effort :") );
		this.add( actEffortSpinner, "wrap, width 50:120:150" );
		
		this.add( new JLabel("Task Status :") );
		this.add( statusBox, "wrap, width 50:120:150" );
		
		this.add( saveButton, "span 2, wrap, right" );
		
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
		
		updateBorder(titleEntry, titleEntry.getText());
		updateBorder(descEntryScoller, descEntry.getText());
		
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
	 * Highlights the text field if attribute is required
	 * to show the user that they did not fill out the text field.
	 * @param someComponent Text area they did not fill out.
	 */
	protected void indicateRequiredField(JComponent someComponent)
	{
		someComponent.setBorder(BorderFactory.createLineBorder(Color.RED));
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
			indicateRequiredField(someComponent);
		}
		else
		{	
			if(someComponent instanceof JTextField)
			{
				someComponent.setBorder((new JTextField()).getBorder());
			}
			else if(someComponent instanceof JScrollPane)
			{
				someComponent.setBorder((new JScrollPane()).getBorder());
			}
			else
			{
				someComponent.setBorder(null);
			}
		}
	}
	
	/**
	 * Check to see if the field is valid, (set flags for save button)
	 * update boarder, whether it is valid or not,.
	 * @param someComponent The JComponent the field is held in.
	 * @param data The data we are check if valid.
	 * @param field The required field we are checking.
	 */
	protected void checkValidField(JComponent someComponent, String data, String field)
	{
		updateBorder(someComponent, data);
		int index = -1;
		for (int i = 0; i < requiredFieldNames.length; i++)
		{
			if (requiredFieldNames[i] == field)
			{
				index = i;
				break;
			}
		}
		if (index == -1)
		{
			System.out.println("What the fuck did you do!?");
		}
		else 
		{
			requiredFieldFlags[index] = !data.isEmpty();
		}
		
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
		for (int i = 0; i < requiredFieldFlags.length; i++)
		{
			enable = enable && requiredFieldFlags[i];
		}
		saveButton.setEnabled(enable);
	}
}

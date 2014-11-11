package edu.wpi.cs.wpisuitetng.modules.taskmanager.view;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.BorderFactory;

import org.jdatepicker.JDateComponentFactory;
import org.jdatepicker.impl.JDatePickerImpl;

import net.miginfocom.swing.MigLayout;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.model.Task;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.model.TaskStatus;

/**
 * 
 * @author wmtemple
 *
 * A view to be displayed when creating or modifying a task object in the GUI.
 *
 */
public class TaskEditView extends JPanel {

	/**
	 * Eclipse-generated Serial Version UID
	 */
	private static final long serialVersionUID = -8331650108561001757L;
	
	JTextField titleEntry;
	JTextArea descEntry;
	JComboBox<TaskStatus> statusBox;
	JSpinner estEffortSpinner;
	JDatePickerImpl dueDatePicker;
	JTextArea membersTextArea;
	JTextField newMemberField;
	JButton addNewMemberButton;
	JButton saveButton;
	
	
	/**
	 * Create a new TaskEditView
	 */
	protected TaskEditView (boolean isCreatePanel) {
		
		//If this is a task creation panel, use a different title text
		String paneTitle = isCreatePanel?"New Task":"Edit Task";
		
		// MigLayout gives us the easiest layout with best flexibility
		MigLayout layout = new MigLayout(
				"wrap 2",						//Layout constraints
				"[right][left, 100::, grow]", 	//Column constraints
				"");							//Row Constraints
		
		this.setBorder(BorderFactory.createTitledBorder(paneTitle));
		this.setLayout(layout);
		
		titleEntry = new JTextField();
		
		descEntry = new JTextArea(5,0);
		descEntry.setLineWrap(true);
		descEntry.setWrapStyleWord(true);
		
		dueDatePicker = (JDatePickerImpl) new JDateComponentFactory().createJDatePicker();

		estEffortSpinner = new JSpinner( new SpinnerNumberModel(1, 1, null, 1) );
		
		statusBox = new JComboBox<TaskStatus>();
		statusBox.addItem(new TaskStatus("Backlog"));
		statusBox.addItem(new TaskStatus("Development"));
		statusBox.addItem(new TaskStatus("Testing"));
		statusBox.addItem(new TaskStatus("Live"));
		
		membersTextArea = new JTextArea(5,0);
		membersTextArea.setLineWrap(true);
		membersTextArea.setWrapStyleWord(true);
		
		saveButton = new JButton("Save");
		
		this.add( new JLabel("Title :"), "top" );
		this.add( titleEntry, "wrap, width 100:150, growx" );
		
		this.add( new JLabel("Description :"), "top" );
		this.add( new JScrollPane(descEntry), "wrap, grow");
		
		this.add( new JLabel("Due Date :") );
		this.add( dueDatePicker, "wrap, width 120:120:200, growx" );
		
		this.add( new JLabel("Est. Effort :") );
		this.add( estEffortSpinner, "wrap, width 50:120:150" );
		
		this.add( new JLabel("Task Status :") );
		this.add( statusBox, "wrap, width 50:120:150" );
		
		this.add( saveButton, "span 2, wrap, right" );
		
	}
	
	/**
	 * Create a new TaskEditView, editing the given task
	 * 
	 * @param task the task object with which to populate the field
	 */
	public TaskEditView (Task task) {
		
		this(false);
		
	}
	
	/**
	 * Create a new TaskEditView, assuming that it is for a New task.
	 */
	public TaskEditView() {
		
		this(true);
		
	}
	
	
}

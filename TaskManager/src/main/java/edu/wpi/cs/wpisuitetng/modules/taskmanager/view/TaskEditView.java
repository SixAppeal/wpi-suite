package edu.wpi.cs.wpisuitetng.modules.taskmanager.view;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.BorderFactory;
import javax.swing.text.DateFormatter;

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

		this.add( new JLabel("Title :"), "top" );
		this.add( titleEntry, "wrap, width 150:200:300, growx" );
		
		this.add( new JLabel("Description :"), "top" );
		this.add( new JScrollPane(descEntry), "wrap, grow");
		
		this.add( new JLabel("Due Date :") );
		this.add( dueDatePicker, "wrap" );
		
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

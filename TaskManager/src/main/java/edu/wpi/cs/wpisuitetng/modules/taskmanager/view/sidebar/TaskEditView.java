package edu.wpi.cs.wpisuitetng.modules.taskmanager.view.sidebar;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
import edu.wpi.cs.wpisuitetng.modules.taskmanager.presenter.Gateway;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.view.IView;

/**
 * 
 * @author wmtemple
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
	JComboBox<TaskStatus> statusBox;
	JSpinner estEffortSpinner;
	JSpinner actEffortSpinner;
	SpinnerNumberModel estEffortSpinnerModel;
	SpinnerNumberModel actEffortSpinnerModel;
	JDatePickerImpl dueDatePicker;
	JTextArea membersTextArea;
	JTextField newMemberField;
	JButton addNewMemberButton;
	JButton saveButton;
	
	
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
		
		this.setBorder(BorderFactory.createTitledBorder(paneTitle));
		this.setLayout(layout);
		
		titleEntry = new JTextField();
		
		descEntry = new JTextArea(5,0);
		descEntry.setLineWrap(true);
		descEntry.setWrapStyleWord(true);
		
		dueDatePicker = (JDatePickerImpl) new JDateComponentFactory().createJDatePicker();

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
		saveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				processTask();
			}
		});
		
		this.add( new JLabel("Title :"), "top" );
		this.add( titleEntry, "wrap, width 100:150, growx" );
		
		this.add( new JLabel("Description :"), "top" );
		this.add( new JScrollPane(descEntry), "wrap, grow");
		
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
		dueDatePicker.getJDateInstantPanel().getModel().setDate(d.getYear(),
				d.getMonth(), d.getDay());
		statusBox.setSelectedItem(t.getStatus());
		
		String stat = t.getStatus().toString();
		
		actEffortSpinner.setEnabled( (stat == "Testing") || (stat == "Live") );
		
	}
	
	private void processTask() {
		System.out.println("processTask");
		if( t == null) t = new Task();
		
		String title = titleEntry.getText();
		String desc = descEntry.getText();
		TaskStatus st = (TaskStatus)statusBox.getSelectedItem();
		int est = (Integer)estEffortSpinnerModel.getValue();
		int act = (Integer)actEffortSpinnerModel.getValue();
		int year = dueDatePicker.getJDateInstantPanel().getModel().getYear();
		int month = dueDatePicker.getJDateInstantPanel().getModel().getMonth();
		int day = dueDatePicker.getJDateInstantPanel().getModel().getDay();
		
		try {
			
			t.setTitle(title);
			t.setDescription(desc);
			t.setStatus(st);
			t.setEstimatedEffort(est);
			t.setActualEffort(act);
			t.setDueDate(new Date(year, month, day));
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
		System.out.println("EditView taskOut");
		gateway.toPresenter("TaskPresenter", "updateTask", t);
	}
	
	
}

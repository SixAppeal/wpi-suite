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
public class TaskCreateView extends TaskEditView implements IView {
	
	@Override
	protected String getTitle() {
		return "New Task";
	}
	
	@Override
	protected void taskOut() {
		gateway.toPresenter("TaskPresenter", "createTask", t);
	}
	
}

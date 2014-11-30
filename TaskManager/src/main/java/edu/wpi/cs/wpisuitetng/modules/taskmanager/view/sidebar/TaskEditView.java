package edu.wpi.cs.wpisuitetng.modules.taskmanager.view.sidebar;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.LinkedList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.jdesktop.swingx.JXDatePicker;

import edu.wpi.cs.wpisuitetng.modules.taskmanager.model.Stage;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.model.Task;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.presenter.Gateway;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.view.IView;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.view.components.Form;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.view.components.FormField;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.view.components.FormFieldValidator;

/**
 * Displays and allows editing of task properties.
 * 
 * @author tmeehan
 * @author wmtemple
 * @author krpeffer
 * @author wavanrensselaer
 */
public class TaskEditView extends JPanel implements IView {
	private static final long serialVersionUID = -8972626054612267276L;

	private Gateway gateway;
	
	private Task task;
	
	// Components
	private JPanel container;
	private JScrollPane scrollPane;

	private JTextField titleInput;
	private JTextArea titleLabel;
	private JTextArea descInput;
	private JTextArea descLabel;
	private JScrollPane descScrollPane;
	private JXDatePicker dateInput;
	private JLabel dateLabel;
	private JSpinner estEffortInput;
	private JSpinner actEffortInput;
	private JList<String> members;
	private JScrollPane membersScrollPane;
	private JList<String> assignedMembers;
	private JScrollPane assignedMembersScrollPane;
	private JComboBox<Stage> stageInput;
	private JButton archiveButton;
	private JButton closeButton;
	private Form form;
	
	/**
	 * Constructor
	 */
	public TaskEditView(Task task) {
		this.task = task;
		this.container = new JPanel();
		this.scrollPane = new JScrollPane(this.container);
		this.titleInput = new JTextField(this.task.getTitle());
		this.titleLabel = new JTextArea(this.task.getTitle());
		this.descInput = new JTextArea(this.task.getDescription(), 5, 0);
		this.descLabel = new JTextArea(this.task.getDescription());
		this.descScrollPane = new JScrollPane(this.descInput);
		this.dateInput = new JXDatePicker(this.task.getDueDate());
		this.dateLabel = new JLabel();
		this.estEffortInput = new JSpinner(new SpinnerNumberModel(1, null, null, 1));
		this.actEffortInput = new JSpinner(new SpinnerNumberModel(1, null, null, 1));
		this.members = new JList<String>();
		this.membersScrollPane = new JScrollPane(this.members);
		this.assignedMembers = new JList<String>();
		this.assignedMembersScrollPane = new JScrollPane(this.assignedMembers);
		this.stageInput = new JComboBox<Stage>();
		this.archiveButton = new JButton("Archive");
		this.closeButton = new JButton("Close");
		
		this.container.setOpaque(false);
		
		this.titleLabel.setOpaque(false);
		this.titleLabel.setBorder(BorderFactory.createEmptyBorder());
		this.titleLabel.setLineWrap(true);
		this.titleLabel.setWrapStyleWord(true);

		this.scrollPane.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		this.scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		
		this.descInput.setLineWrap(true);
		this.descInput.setWrapStyleWord(true);
		
		if (!this.task.getStage().getName().equals("Complete")) {
			this.actEffortInput.setEnabled(false);
		}
		
		this.estEffortInput.setValue(this.task.getEstimatedEffort());
		this.actEffortInput.setValue(this.task.getActualEffort());
		
		this.members.setVisibleRowCount(4);
		this.assignedMembers.setVisibleRowCount(4);
		
		this.stageInput.addItem(new Stage("New"));
		this.stageInput.addItem(new Stage("Scheduled"));
		this.stageInput.addItem(new Stage("In Progress"));
		this.stageInput.addItem(new Stage("Complete"));
		this.stageInput.setSelectedItem(this.task.getStage());
		
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
		
		// Predefine description field to hook up listener
		FormField descField = new FormField("Description", this.descScrollPane, new FormFieldValidator() {
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
			}
		});
		
		FormField actEffortField = new FormField("Act. Effort", this.actEffortInput, new FormFieldValidator() {
			@Override
			public boolean validate(JComponent component) {
				return ((Integer) ((JSpinner) component).getValue()).intValue() >= 0;
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
			}
		});
		
		this.form = new Form(
			titleField,
			descField,
			new FormField("Due Date", this.dateInput),
			new Form(
				Form.HORIZONTAL,
				estEffortField,
				actEffortField
			),
			new Form(
				Form.HORIZONTAL,
				new FormField("Members", this.membersScrollPane),
				new FormField("Assigned", this.assignedMembersScrollPane)
			),
			new FormField("Stage", this.stageInput),
			new Form(
				Form.HORIZONTAL,
				this.archiveButton,
				this.closeButton
			)
		);
		
		this.container.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		this.container.setMinimumSize(new Dimension(300, 0));
		this.container.setMaximumSize(new Dimension(300, Integer.MAX_VALUE));
		this.container.setLayout(new BorderLayout());
		this.container.add(this.form, BorderLayout.CENTER);
		
		this.setLayout(new BorderLayout());
		this.add(this.scrollPane, BorderLayout.CENTER);
	}
	
	@Override
	public void setGateway(Gateway gateway) {
		this.gateway = gateway;
	}
}

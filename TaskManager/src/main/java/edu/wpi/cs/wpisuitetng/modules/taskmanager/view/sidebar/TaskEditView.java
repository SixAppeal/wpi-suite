package edu.wpi.cs.wpisuitetng.modules.taskmanager.view.sidebar;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

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

import net.miginfocom.swing.MigLayout;

import org.jdesktop.swingx.JXDatePicker;

import edu.wpi.cs.wpisuitetng.modules.taskmanager.model.Stage;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.model.Task;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.presenter.Gateway;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.view.IView;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.view.components.ButtonGroup;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.view.components.Form;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.view.components.FormField;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.view.components.FormFieldValidator;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.view.components.HorizontalForm;

/**
 * Displays and allows editing of task properties.
 * 
 * @author tmeehan
 * @author wmtemple
 * @author krpeffer
 * @author wavanrensselaer
 * @author srojas
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
		TaskEditView that = this;
		
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
		
		this.archiveButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				gateway.toPresenter("Localcache", "update", "archive", task);
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
				if (actEffortField.hasValidInput()) {
					task.setActualEffort((Integer) actEffortInput.getValue());
					saveTask();
				}
			}
		});
		
		this.stageInput.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				task.setStage((Stage) stageInput.getSelectedItem());
				saveTask();
			}
		});
		
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
				new FormField("Assigned", this.assignedMembersScrollPane)
			),
			new FormField("Stage", this.stageInput),
			new ButtonGroup(
				this.archiveButton,
				this.closeButton
			)
		);
		
		this.container.setBackground(new Color(230, 230, 230));
		this.container.setLayout(new MigLayout("fill, ins 20", "[260]"));
		this.container.add(this.form, "grow");
		
		this.setLayout(new BorderLayout());
		this.add(this.scrollPane, BorderLayout.CENTER);
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
		this.gateway.toPresenter("LocalCache", "update", "task", this.task);
	}
	
	@Override
	public void setGateway(Gateway gateway) {
		this.gateway = gateway;
	}
	
	
	
}

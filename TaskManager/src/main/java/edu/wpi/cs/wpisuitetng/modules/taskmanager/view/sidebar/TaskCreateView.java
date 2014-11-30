package edu.wpi.cs.wpisuitetng.modules.taskmanager.view.sidebar;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JComboBox;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import edu.wpi.cs.wpisuitetng.modules.taskmanager.model.Stage;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.model.Task;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.presenter.Gateway;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.view.IView;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.view.components.Form;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.view.components.FormField;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.view.components.FormFieldValidator;

/**
 * A view to be displayed when creating or modifying a task object in the GUI.
 *
 * @author wavanrensselaer
 * @author wmtemple
 * @author akshoop
 * @author rnorlando
 */
public class TaskCreateView extends JPanel implements IView {
	private static final long serialVersionUID = -1055431537990755671L;
	
	private Gateway gateway;

	// Components
	private JPanel container;
	private JScrollPane scrollPane;
	private JTextField title;
	private JTextArea description;
	private JScrollPane descScrollPane;
	private JSpinner estimatedEffort;
	private JComboBox<Stage> stage;
	private JButton createButton;
	private JButton cancelButton;
	private Form form;
	
	/**
	 * Constructs a <code>TaskCreateView</code>
	 */
	public TaskCreateView() {
		this.container = new JPanel();
		this.scrollPane = new JScrollPane(this.container);
		this.title = new JTextField();
		this.description = new JTextArea(5, 0);
		this.descScrollPane = new JScrollPane(this.description);
		this.estimatedEffort = new JSpinner(
				new SpinnerNumberModel(1, null, null, 1));
		this.stage = new JComboBox<Stage>();
		this.createButton = new JButton("Create");
		this.cancelButton = new JButton("Cancel");
		TaskCreateView that = this;
		
		this.container.setOpaque(false);
		
		this.scrollPane.setBorder(BorderFactory.createEmptyBorder());
		this.scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		
		this.description.setLineWrap(true);
		this.description.setWrapStyleWord(true);
		
		this.stage.addItem(new Stage("New"));
		this.stage.addItem(new Stage("Scheduled"));
		this.stage.addItem(new Stage("In Progress"));
		this.stage.addItem(new Stage("Complete"));
		
		this.createButton.setEnabled(false);
		this.createButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Task task = new Task();
				task.setTitle(title.getText());
				task.setDescription(description.getText());
				task.setEstimatedEffort((Integer) estimatedEffort.getValue());
				task.setStage((Stage) stage.getSelectedItem());
				gateway.toPresenter("LocalCache", "store", "task", task);
				gateway.toView("SidebarView", "removeCreatePanel", that);
			}
		});
		
		this.cancelButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				gateway.toView("SidebarView", "removeCreatePanel", that);
			}
		});
		
		FormField titleField = new FormField("Title", this.title, new FormFieldValidator() {
			@Override
			public boolean validate(JComponent component) {
				return !((JTextField) component).getText().trim().equals("");
			}
			
			@Override
			public String getMessage() {
				return "Please enter a title.";
			}
		});
		this.title.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent e) {
				if (e.getKeyCode() != 9) { // tab key
					titleField.validateInput();
				}
				validateForm();
			}
		});
		
		FormField descriptionField = new FormField("Description", this.descScrollPane, new FormFieldValidator() {
			@Override
			public boolean validate(JComponent component) {
				return !description.getText().trim().equals("");
			}
			
			@Override
			public String getMessage() {
				return "Please enter a description.";
			}
		});
		this.description.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent e) {
				if (e.getKeyCode() != 9) { // tab key
					descriptionField.validateInput();
				}
				validateForm();
			}
		});
		
		FormField effortField = new FormField("Estimated Effort", this.estimatedEffort, new FormFieldValidator() {
			@Override
			public boolean validate(JComponent component) {
				return ((Integer) ((JSpinner) component).getValue()).intValue() > 0;
			}
			
			@Override
			public String getMessage() {
				return "Effort must be greater than zero.";
			}
		});
		this.estimatedEffort.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				effortField.validateInput();
				validateForm();
			}
		});
		
		this.form = new Form(
			titleField,
			descriptionField,
			effortField,
			new FormField("Stage", stage),
			new Form(
				Form.HORIZONTAL,
				this.createButton,
				this.cancelButton
			)
		);
		
		this.container.setMinimumSize(new Dimension(300, 0));
		this.container.setLayout(new BorderLayout());
		this.container.add(this.form, BorderLayout.CENTER);
		
		this.setLayout(new BorderLayout());
		this.add(this.scrollPane, BorderLayout.CENTER);
	}
	
	/**
	 * Validates the form
	 */
	public void validateForm() {
		this.createButton.setEnabled(this.form.hasValidFields());
	}

	@Override
	public void setGateway(Gateway gateway) {
		this.gateway = gateway;
	}
}

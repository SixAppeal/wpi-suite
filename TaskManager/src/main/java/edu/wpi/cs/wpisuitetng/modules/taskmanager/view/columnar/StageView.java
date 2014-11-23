package edu.wpi.cs.wpisuitetng.modules.taskmanager.view.columnar;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import edu.wpi.cs.wpisuitetng.modules.taskmanager.model.Task;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.presenter.Gateway;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.view.IView;

/**
 * View for an individual column in the columnal layout
 * @author wavanrensselaer
 * @author rnorlando
 */
public class StageView extends JPanel implements IView {
	private static final long serialVersionUID = 2174190454852340046L;
	
	private Gateway gateway;
	
	// State-related fields
	private Task[] tasks;
	
	// Components
	private String name;
	private JLabel nameLabel;
	private JPanel container;
	private JScrollPane scrollPane;
	
	/**
	 * Constructs a <code>ColumnView</code> which has a name and an
	 * <code>ArrayList</code> of the tasks to display.
	 * @param title The title of the column
	 */
	public StageView(String name) {
		this.nameLabel = new JLabel("", JLabel.CENTER);
		this.container = new JPanel();
		this.scrollPane = new JScrollPane(this.container);
		
		this.container.setLayout(new GridBagLayout());

		this.setBackground(new Color(220, 220, 220));
		this.setMinimumSize(new Dimension(260, 0));
		this.setMaximumSize(new Dimension(260, Integer.MAX_VALUE));
		this.setPreferredSize(new Dimension(260, 0));
		this.setLayout(new GridBagLayout());
		this.setOpaque(false);
		
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.anchor = GridBagConstraints.PAGE_START;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.insets = new Insets(10, 20, 10, 20);
		gbc.weightx = 1.0;
		gbc.gridx = 0;
		gbc.gridy = 0;
		this.add(this.nameLabel);
		
		gbc.fill = GridBagConstraints.BOTH;
		gbc.insets = new Insets(0, 0, 0, 0);
		gbc.weighty = 1.0;
		gbc.gridy = 1;
		this.add(this.scrollPane);
		
		this.setState(this.name, null);
	}
	
	/**
	 * Sets the name of the stage.
	 * @param name The new name of the stage
	 */
	public void setName(String name) {
		this.setState(name, this.tasks);
	}
	
	/**
	 * Gets the name of the stage
	 * @return The name of the stage
	 */
	public String getName() {
		return this.name;
	}
	
	/**
	 * Sets the state of tasks within this stage.
	 * @param tasks The new task array
	 */
	public void setTasks(Task[] tasks) {
		this.setState(this.name, tasks);
	}
	
	/**
	 * Gets the state of the tasks within this view
	 * @return An array of tasks within this view
	 */
	public Task[] getTasks() {
		return this.tasks;
	}
	
	/**
	 * Sets the state of this view, the name and the tasks within this stage.
	 * @param tasks The new task array
	 */
	public void setState(String name, Task[] tasks) {
		this.name = name == null ? "" : name;
		this.tasks = tasks == null ? new Task[0] : tasks;
		this.reflow();
	}
	
	/**
	 * Reflows this views when it's state changes.
	 */
	public void reflow() {
		TaskView[] taskViews = (TaskView[]) this.container.getComponents();
		for (int i = 0; i < taskViews.length; i++) {
			
		}
	}
	
	/**
	 * @see IView.setGateway
	 */
	@Override
	public void setGateway(Gateway gateway) {
		this.gateway = gateway;
	}
}

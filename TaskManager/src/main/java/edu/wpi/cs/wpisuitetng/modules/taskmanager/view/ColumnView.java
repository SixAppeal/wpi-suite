package edu.wpi.cs.wpisuitetng.modules.taskmanager.view;

import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import edu.wpi.cs.wpisuitetng.modules.taskmanager.model.Task;

import edu.wpi.cs.wpisuitetng.modules.taskmanager.presenter.Gateway;

/**
 * View for an individual column in the columnal layout
 * @author wavanrensselaer
 */
public class ColumnView extends JPanel implements IView {
	private static final long serialVersionUID = 2174190454852340046L;
	
	private Gateway gateway;
	private String title;
	private JPanel columnPanel;
	private JLabel titleLabel;
	private ArrayList<TaskView> tasks;
	
	/**
	 * Constructs a <code>ColumnView</code> which has a title and an
	 * <code>ArrayList</code> of the tasks to display.
	 * @param title The title of the column
	 */
	public ColumnView(String title) {
		this.title = title;
		this.columnPanel = new JPanel();
		this.titleLabel = new JLabel(this.title, JLabel.LEFT);
		this.tasks = new ArrayList<TaskView>();
		
		this.tasks.add(new TaskView("Task 1"));
		this.tasks.add(new TaskView("Task 2"));
		this.tasks.add(new TaskView("Task 3"));
		
		this.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 20));
		this.setOpaque(false);
		this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		this.setPreferredSize(new Dimension(260, 500));
		this.setMaximumSize(new Dimension(260, Integer.MAX_VALUE));
		
		this.titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		
		this.columnPanel.setBackground(new Color(200, 200, 200));
		this.columnPanel.setLayout(new BoxLayout(this.columnPanel, BoxLayout.Y_AXIS));
		
		this.columnPanel.add(this.titleLabel);
		for (TaskView task : this.tasks) {
			this.columnPanel.add(task);
		}
		this.add(this.columnPanel);
	}
	
	/**
	 * Adds a task to the column
	 * @param task A task model
	 */
	public void addTask(Task task) {
		TaskView taskView = new TaskView(task.getTitle());
		tasks.add(taskView);
		this.columnPanel.add(taskView);
	}
	
	/**
	 * @see IView.setGateway
	 */
	@Override
	public void setGateway(Gateway gateway) {
		this.gateway = gateway;
	}

}

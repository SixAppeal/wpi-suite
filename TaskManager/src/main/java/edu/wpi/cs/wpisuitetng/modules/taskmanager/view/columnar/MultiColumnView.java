package edu.wpi.cs.wpisuitetng.modules.taskmanager.view.columnar;

import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import edu.wpi.cs.wpisuitetng.modules.taskmanager.model.Task;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.presenter.Gateway;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.presenter.TaskPresenter;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.view.IView;

/**
 * View for displaying multiple horizontal columns
 * @author wavanrensselaer
 * @author akshoop
 * @author rnorlando
 */
public class MultiColumnView extends JPanel implements IView {
	private static final long serialVersionUID = 7965275386426411767L;

	private Gateway gateway;
	private TaskPresenter presenter;
	JScrollPane scrollPane;
	JPanel container;
	JPanel multiColumnPanel;
	
	ArrayList<ColumnView> columns;
	
	/**
	 * Constructs a <code>MultiViewColumn</code> which holds an <code>ArrayList</code>
	 * of <code>ColumnView</code>s.
	 */
	public MultiColumnView() {
		this.columns = new ArrayList<ColumnView>();
		this.multiColumnPanel = new JPanel();
		this.container = new JPanel();
		this.scrollPane = new JScrollPane(this.container);
		
		this.container.setLayout(new BoxLayout(this.container, BoxLayout.X_AXIS));
		this.container.add(this.multiColumnPanel);
		
		this.columns.add(new ColumnView("New"));
		this.columns.add(new ColumnView("Scheduled"));
		this.columns.add(new ColumnView("In Progress"));
		this.columns.add(new ColumnView("Complete"));
		
		this.scrollPane.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		
		this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		this.setOpaque(false);
		
		this.multiColumnPanel.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 0));
		this.multiColumnPanel.setLayout(new BoxLayout(this.multiColumnPanel, BoxLayout.X_AXIS));
		
		for (ColumnView column : this.columns) {
			this.multiColumnPanel.add(column);
		}
		this.add(this.scrollPane);
	}
	
	public void updateFromOldTasks() {
	}
	
	/**
	 * Adds an array of tasks to their respective columns
	 * @param tasks An array of tasks to add
	 */
	public void addAllTasks(Task[] tasks) {
		for (int i = 0; i < tasks.length; i++) {
			if (tasks[i].getColumn() < this.columns.size() && tasks[i].getColumn() >= 0) {
				this.columns.get(tasks[i].getColumn()).addTask(tasks[i]);
			}
		}
	}
	
	public void removeTask(Task task) {
		/*if (task.getColumn() < this.columns.size() && task.getColumn() >= 0) {
			this.columns.get(task.getColumn()).removeTask(task);
			this.scrollPane.revalidate();
		}*/
		
		for( ColumnView c : columns ) {
			c.removeTask(task);
			c.revalidate();
		}
		
		this.scrollPane.revalidate();
	}
	
	/**
	 * Adds a single task to it's column
	 * @param task The task to add
	 */
	public void addTask(Task task) {
		if (task.getColumn() < this.columns.size() && task.getColumn() >= 0) {
			this.columns.get(task.getColumn()).addTask(task);
		}
		this.scrollPane.revalidate();
	}
	
	/**
	 * @see IView.setGateway
	 */
	@Override
	public void setGateway(Gateway gateway) {
		this.gateway = gateway;
		for (ColumnView column : this.columns) {
			column.setGateway(gateway);;
		}
	}
}

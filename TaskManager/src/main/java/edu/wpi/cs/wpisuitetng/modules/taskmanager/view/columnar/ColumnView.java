package edu.wpi.cs.wpisuitetng.modules.taskmanager.view.columnar;

import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import edu.wpi.cs.wpisuitetng.modules.taskmanager.model.Task;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.presenter.Gateway;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.view.IView;

/**
 * View for displaying multiple horizontal columns with associated tasks
 * @author wavanrensselaer
 * @author akshoop
 * @author rnorlando
 */
public class ColumnView extends JPanel implements IView {
	private static final long serialVersionUID = 7965275386426411767L;

	private Gateway gateway;
	
	private Task[] tasks;
	
	JScrollPane scrollPane;
	JPanel container;
	
	List<StageView> columns;
	
	/**
	 * Constructs a <code>ColumnView</code> which holds an <code>ArrayList</code>
	 * of <code>StageView</code>s.
	 */
	public ColumnView() {
		this.columns = new ArrayList<StageView>();
		this.container = new JPanel();
		this.scrollPane = new JScrollPane(this.container);
		
		this.container.setLayout(new BoxLayout(this.container, BoxLayout.X_AXIS));
		
		this.columns.add(new StageView("New"));
		this.columns.add(new StageView("Scheduled"));
		this.columns.add(new StageView("In Progress"));
		this.columns.add(new StageView("Complete"));
		
		this.scrollPane.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		
		this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		this.setOpaque(false);
		
		this.container.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 0));
		
		for (StageView column : this.columns) {
			this.container.add(column);
		}
		this.add(this.scrollPane);
	}
	
	/**
	 * Reflows the view when it's state changes.
	 */
	public void reflow() {
		
	}
	
	/**
	 * Revalidates the contents of the View
	 */
	public void refreshView() {
		for (StageView c : columns) {
			c.refreshView();
			c.repaint();
		}
		this.scrollPane.revalidate();
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
	
	/**
	 * removes a task from this columnar view
	 * @param task the task to be removed
	 */
	public void removeTask(Task task) {
		/*if (task.getColumn() < this.columns.size() && task.getColumn() >= 0) {
			this.columns.get(task.getColumn()).removeTask(task);
			this.scrollPane.revalidate();
		}*/
		
		for( StageView c : columns ) {
			c.removeTask(task);
			c.revalidate();
		}
		
		this.scrollPane.revalidate();
	}
	
	/**
	 * removes all tasks from this columnar view
	 */
	public void removeAllTasks() {
		for (StageView c : columns) {
			//System.out.println("Removing all tasks from column " + c.getTitle());
			c.removeAllTasks();
			//System.out.println("Finished removing!");
		}
		
	}
	
	/**
	 * Adds a single task to it's column
	 * @param task The task to add
	 */
	public void addTask(Task task) {
		if ( task.isArchived() ) System.err.println("View trying to add Archived Task!");
		if (task.getColumn() < this.columns.size() && task.getColumn() >= 0) {
			this.columns.get(task.getColumn()).addTask(task);
		}
		//this.scrollPane.revalidate();
	}
	
	/**
	 * @see IView.setGateway
	 */
	@Override
	public void setGateway(Gateway gateway) {
		this.gateway = gateway;
		for (StageView column : this.columns) {
			column.setGateway(gateway);;
		}
	}
}

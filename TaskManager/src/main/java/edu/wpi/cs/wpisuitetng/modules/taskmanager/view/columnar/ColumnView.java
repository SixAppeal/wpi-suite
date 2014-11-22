package edu.wpi.cs.wpisuitetng.modules.taskmanager.view.columnar;

import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import edu.wpi.cs.wpisuitetng.modules.taskmanager.model.Stage;
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
	
	// State-related fields
	private Stage[] stages;
	private Task[] tasks;
	
	// Components
	private JPanel container;
	private JScrollPane scrollPane;
	private List<StageView> stageViews;
	
	/**
	 * Constructs a <code>ColumnView</code> which holds an <code>ArrayList</code>
	 * of <code>StageView</code>s.
	 */
	public ColumnView() {
		this.stageViews = new ArrayList<StageView>();
		this.container = new JPanel();
		this.scrollPane = new JScrollPane(this.container);
		
		this.container.setLayout(new BoxLayout(this.container, BoxLayout.X_AXIS));
		this.container.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 0));
		
		this.scrollPane.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		
		this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		this.setOpaque(false);
		this.add(this.scrollPane);
		
		this.setState(null, new Stage[] {
			new Stage("New"),
			new Stage("Scheduled"),
			new Stage("In Progress"),
			new Stage("Complete")
		});
	}
	
	/**
	 * Sets the state of tasks within the view
	 * @param tasks The new task array
	 */
	public void setTasks(Task[] tasks) {
		this.setState(tasks, this.stages);
	}
	
	/**
	 * Gets the state of tasks within this view
	 * @return The tasks within this view
	 */
	public Task[] getTasks() {
		return this.tasks;
	}
	
	/**
	 * Sets the state of stages within the view
	 * @param stages The new stages array
	 */
	public void setStages(Stage[] stages) {
		this.setState(this.tasks, stages);
	}
	
	/**
	 * Gets the state of stages within this view
	 * @return The stages within this view
	 */
	public Stage[] getStages() {
		return this.stages;
	}
	
	/**
	 * Sets the state (both tasks and stages) of this view.
	 * @param tasks The new task array
	 * @param stages The new stages array
	 */
	public void setState(Task[] tasks, Stage[] stages) {
		this.tasks = tasks == null ? new Task[0] : tasks;
		this.stages = stages == null ? new Stage[0] : stages;
		this.reflow();
	}
	
	/**
	 * Reflows the view when it's state changes.
	 */
	public void reflow() {
		// TODO
	}
	
	/**
	 * @see IView.setGateway
	 */
	@Override
	public void setGateway(Gateway gateway) {
		this.gateway = gateway;
		for (StageView column : this.stageViews) {
			column.setGateway(gateway);;
		}
	}
}

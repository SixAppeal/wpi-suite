package edu.wpi.cs.wpisuitetng.modules.taskmanager.view.columnar;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

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
	
	/**
	 * Constructs a <code>ColumnView</code> which holds an <code>ArrayList</code>
	 * of <code>StageView</code>s.
	 */
	public ColumnView() {
		this.container = new JPanel();
		this.scrollPane = new JScrollPane(this.container);
		
		this.container.setLayout(new GridBagLayout());

		this.scrollPane.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		this.scrollPane.setVerticalScrollBarPolicy(
				ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
		
		this.setLayout(new GridBagLayout());
		this.setOpaque(false);
		
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.anchor = GridBagConstraints.PAGE_START;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		gbc.gridx = 0;
		gbc.gridy = 0;
		this.add(this.scrollPane, gbc);
		
		this.setState(new Task[0], new Stage[] {
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
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.anchor = GridBagConstraints.FIRST_LINE_START;
		gbc.fill = GridBagConstraints.VERTICAL;
		gbc.weighty = 1.0;
		gbc.insets = new Insets(20, 0, 20, 20);
		gbc.gridy = 0;
		
		StageView stageView;
		int i;
		for (i = 0; i < this.container.getComponentCount(); i++) {
			stageView = (StageView) this.container.getComponent(i);
			if (i >= this.stages.length) {
				this.container.remove(i--);
			} else if (!stageView.getStage().equals(this.stages[i])) {
				stageView.setState(this.stages[i],
						this.getTasksForStage(this.stages[i]));
			}
		}
		for (; i < this.stages.length; i++) {
			gbc.insets.left = i == 0 ? 20 : 0;
			gbc.gridx = i;
			this.container.add(new StageView(this.stages[i],
					this.getTasksForStage(this.stages[i])), gbc);
		}
		
		this.scrollPane.revalidate();
	}
	
	/**
	 * Gets the array of tasks that are associated with a stage
	 * @param stage The stage to which the tasks should belong
	 * @return An array of <code>Task</code>s
	 */
	private Task[] getTasksForStage(Stage stage) {
		List<Task> tasks = new ArrayList<Task>();
		
		for (int i = 0; i < this.tasks.length; i++) {
			if (this.tasks[i].getStage().equals(stage.getName())) {
				tasks.add(this.tasks[i]);
			}
		}
		
		return tasks.toArray(new Task[0]);
	}
	
	/**
	 * @see IView.setGateway
	 */
	@Override
	public void setGateway(Gateway gateway) {
		this.gateway = gateway;
	}
}

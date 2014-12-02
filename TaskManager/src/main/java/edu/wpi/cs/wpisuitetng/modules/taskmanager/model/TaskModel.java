package edu.wpi.cs.wpisuitetng.modules.taskmanager.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Used to "latch" the current state of the database so the view knows what objects to use 
 * This serves / handles all workspace operations as well
 * 
 * @author nhhughes
 * @author srojas
 * @author jrhennessy
 */
public class TaskModel {
	//TODO Figure out how to calculate this.
	static final long serialVersionUID = 8609340016893431330L;

	/**
	 * The list in which all the Tasks for a single project are contained
	 */
	private List<Task> Tasks;
	private int nextID; // the next available ID number for the Tasks that are added.

	//the static object to allow the Task model to be 
	private static TaskModel instance;

	/**
	 * Constructs an empty list of Tasks for the project
	 */
	public TaskModel() {
		Tasks = new ArrayList<Task>();
		nextID = 0;
	}

	/**
	 * @return the instance of the Task model singleton.
	 */
	public static TaskModel getInstance()
	{
		if (instance == null)
		{
			instance = new TaskModel();
		}

		return instance;
	}

	/**
	 * 
	 * Adds new task to the model and updates ID
	 * 
	 * @param newTask Task to add
	 */
	public void addTask(Task newTask) {
		this.Tasks.add(newTask);
	}

	/**
	 * Returns the Task with the given ID
	 * 
	 * @param id The ID number of the Task to be returned @return the
	 *            Task for the id or null if the Task is not
	 *            found
	 */
	public Task getTask(int id)
	{
		Task t = null;
		// iterate through list of Tasks until id is found
		for (int i = 0; i < this.Tasks.size(); i++) {
			t = Tasks.get(i);
			if (t.getId() == id) {
				break;
			}
		}
		return t;
	}

	/**
	 * Provides the number of elements in the list of Tasks for the
	 * project. This
	 * function is called internally by the JList in NewTaskPanel.
	 * Returns elements
	 * in reverse order, so the newest Task is returned first.
	 * 
	 * @return the number of Tasks in the project
	 * @see javax.swing.ListModel#getSize()
	 * @see javax.swing.ListModel#getSize()
	 * @see javax.swing.ListModel#getSize()
	 */
	public int getSize() {
		return Tasks.size();
	}

	/**
	 * Provides the next ID number that should be used for a new Task
	 * that is created.
	 * 
	 * @return the next open id number
	 */
	public int getNextID()
	{
		return this.nextID++;
	}

	/**
	 * This function takes an index and finds the Task in the list of
	 * Tasks
	 * for the project. Used internally by the JList in NewTaskModel.
	 * 
	 * @param index The index of the Task to be returned @return the
	 *            Task associated with the provided index * @see
	 *            javax.swing.ListModel#getElementAt(int) * @see
	 *            javax.swing.ListModel#getElementAt(int) * @see
	 *            javax.swing.ListModel#getElementAt(int)
	 */
	public Task getElementAt(int index) {
		return Tasks.get(Tasks.size() - 1 - index);
	}

	/**
	 * Returns the list of the Tasks
	 * 
	 * @return the Tasks held within the Taskmodel.
	 */
	public List<Task> getTasks() {
		return Tasks;
	}

	/**
	 * Sets the instance of TaskModel
	 * @param instance 
	 */
	public static void setInstance(TaskModel instance) {
		TaskModel.instance = instance;
	}

	/**
	 * Tells you which tasks are in what column
	 * @param columnID Number column that the task is in
	 * @return all the tasks in the given column
	 */
	public List<Task> getTasksFromColumn(Integer columnID){
		List<Task> tasksInColumn = new ArrayList<Task>();
		for(Task t : Tasks){
			if (t.getColumn() == columnID){
				tasksInColumn.add(t);
			}
		}
		return tasksInColumn;
	}

	/**
	 * Allows a tasks to be put in a different column
	 * @param taskID The ID number of the Task to be returned
	 * @param columnID Number of the column you want to put the task in
	 */
	public void changeColumn(int taskID, int columnID){
		for(Task t : Tasks){
			if (t.getId() == taskID){
				t.setColumn(columnID);
				break;
			}
		}
	}
}
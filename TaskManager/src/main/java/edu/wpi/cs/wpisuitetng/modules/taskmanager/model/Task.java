package edu.wpi.cs.wpisuitetng.modules.taskmanager.model;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import com.google.gson.Gson;

import edu.wpi.cs.wpisuitetng.modules.AbstractModel;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.util.TaskUtil;

/**
 * 
 * The model we will be using to store in the database.  Contains all necessary information about a task
 * 
 * @author nhhughes
 * @author srojas
 * @author jrhennessy
 * @author Thhughes
 */
public class Task extends AbstractModel {

	private int id;
	private boolean archived;
	private String title;
	private String description;
	private TaskStatus status;
	private List<String> assignedTo;
	private Integer estimatedEffort; 
	private Integer actualEffort;
	private Date dueDate;
	private List<Activity> activities;
	//Requirement associatedRequirement;

	/**
	 * Default constructor (dummy task for initialization)
	 */
	public Task() {
		this("Dummy", "Dummy", new TaskStatus("New"), new LinkedList<String>(), 1, 1,
				new Date(), new LinkedList<Activity>());
	}

	/**
	 * Constructor for a task that has all of the fields
	 * 
	 * @param title name for the task
	 * @param description explanation of the task
	 * @param status what point the task is at (in progress, not started, etc.)
	 * @param assignedTo list of members that are assigned to the task
	 * @param estimatedEffort number that represents how much effort (units of work)
	 * @param actualEffort number that represents the actual effort
	 * @param dueDate when the task is due
	 * @param activities list of activities (comments that members can put) for the task
	 * @throws IllegalArgumentException
	 */
	public Task(String title, String description, TaskStatus status,
			List<String> assignedTo, Integer estimatedEffort,
			Integer actualEffort, Date dueDate, List<Activity> activities) throws IllegalArgumentException {
		super();
		this.title = TaskUtil.validateTitle(title);
		this.description = TaskUtil.validateDescription(description);
		this.status = TaskUtil.validateStage(status);
		this.assignedTo = assignedTo;
		this.estimatedEffort = TaskUtil.validateEffort(estimatedEffort);
		this.actualEffort = TaskUtil.validateEffort(actualEffort);
		this.dueDate = TaskUtil.validateDueDate(dueDate);
		this.activities = activities;
		this.archived = false;
	}
	
	/**
	 * Copy-Constructor
	 * @param t Task to copy from.
	 */
	public Task(Task t) {
		this.id = t.getId();
		this.title = new String(t.getTitle());
		this.description = new String(t.getDescription());
		this.status = new TaskStatus(t.getStatus());
		this.assignedTo = new LinkedList<String>(t.getAssignedTo());
		this.estimatedEffort = new Integer(t.getEstimatedEffort());
		this.actualEffort = new Integer(t.getActualEffort());
		this.dueDate = new Date(t.getDueDate().getTime());
		this.activities = new LinkedList<Activity>(t.getActivities());
	}
	
	/**
	 * Checks to see if the objects are equal according to reference then id number
	 * @param obj:  Object to compare against
	 */
	public boolean equals(Object obj) {
		//If obj is an instance of Task then return true if there's a reference math OR an ID match
		return ((obj instanceof Task) && ((this == obj) || (((Task)obj).getId() == this.id)));
	}
	
	/**
	 * Determines the hashCode of the task to be its ID
	 */
	public int hashCode() { return this.id; }
	
	/**
	 * @return a JSON text representation of this task
	 */
	public String toJson() {
		return new Gson().toJson(this, Task.class);
	}

	/**
	 * Necessary Method Implementation: our task model does not use this.
	 */
	@Override
	public void save() {
		throw new RuntimeException("Someone called save() on a task. This violates our methodology.");
	}

	/**
	 * Necessary Method Implementation: our task model does not use this.
	 */
	@Override
	public void delete() {
		throw new RuntimeException("Someone called delete() on a task. This violates our methodology.");
	}

	/**
	 * Necessary Method Implementation: our task model does not use this.
	 */
	@Override
	public Boolean identify(Object o) {
		throw new RuntimeException("Someone called identify() on a task. Please use equals() instead.");
	}

	/**
	 * @return Title of task
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Setter for title.  If title is too long, throws an exception
	 * @param title
	 * @throws IllegalArgumentException
	 */
	public void setTitle(String title) throws IllegalArgumentException {
		this.title = title;
	}

	/**
	 * @return Description of task
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * 
	 * @param description Description of task
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * 
	 * @return status of task
	 */
	public TaskStatus getStatus() {
		return status;
	}

	/**
	 * 
	 * @param status status of task
	 */
	public void setStatus(TaskStatus status) {
		this.status = status;
	}

	/**
	 * 
	 * @return members associated with task
	 */
	public List<String> getAssignedTo() {
		return assignedTo;
	}

	/**
	 * 
	 * @param assignedTo members associated with task
	 */
	public void setAssignedTo(List<String> assignedTo) {
		this.assignedTo = assignedTo;
	}

	/**
	 * 
	 * @return Task ID (used to discriminate between different tasks)
	 */
	public int getId() {
		return id;
	}

	/**
	 * 
	 * @param id Task ID (used to discriminate between different tasks)
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * 
	 * @return estimated effort for the task
	 */
	public Integer getEstimatedEffort() {
		return estimatedEffort;
	}

	/**
	 * This setter checks to make sure that the estimated effort is a positive integer and throws an error otherwise
	 * @param estimatedEffort estimated effort for the task
	 * @throws IllegalArgumentException
	 */
	public void setEstimatedEffort(Integer estimatedEffort) throws IllegalArgumentException {
		//checks that estimatedEffort is positive
		if (estimatedEffort > 0){
			this.estimatedEffort = estimatedEffort;
		}
		else {
			throw new IllegalArgumentException("Estimated Effort Must Be Greater Than Zero!");
		}
	}

	/**
	 * 
	 * @return actual effort for the task
	 */
	public Integer getActualEffort() {
		return actualEffort;
	}

	/**
	 * This setter checks to make sure that the actual effort is a positive integer and throws an error otherwise
	 * @param actualEffort actual effort for task
	 * @throws IllegalArgumentException
	 */
	public void setActualEffort(Integer actualEffort) throws IllegalArgumentException {
		// making sure that the inputted value is positive
		if (actualEffort >= 0){
			this.actualEffort = actualEffort;
		}
		else {
			throw new IllegalArgumentException("Actual Effort Must Be Greater Than Or Equal To Zero!");
		}
	}

	/**
	 * 
	 * @return due date for the task
	 */
	public Date getDueDate() {
		return dueDate;
	}

	/**
	 * 
	 * @param dueDate due date for the task
	 */
	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

	/**
	 * 
	 * @return list of comments on the task
	 */
	public List<Activity> getActivities() {
		return activities;
	}

	/**
	 * 
	 * @param activities list of comments on the task
	 */
	public void setActivities(List<Activity> activities) {
		this.activities = activities;
	}
	
	/**
	 * @return whether or not this task is archived
	 */
	public boolean isArchived() {
		return archived;
	}
	
	/**
	 * Set archival status to false
	 */
	public void archive() {
		this.archived = true;
	}
	
	/**
	 * Set archival status to true
	 */
	public void unarchive() {
		this.archived = false;
	}
	
}

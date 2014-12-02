<<<<<<< HEAD
package edu.wpi.cs.wpisuitetng.modules.taskmanager.model;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import com.google.gson.Gson;

import edu.wpi.cs.wpisuitetng.modules.AbstractModel;

/**
 * 
 * The model we will be using to store in the database.  Contains all necessary information about a task
 * 
 * @author nhhughes
 * @author srojas
 * @author jrhennessy
 * @author krpeffer
 * @author rwang3
 */
public class Task extends AbstractModel {

	int id;
	private boolean archived;
	String title;
	String description;
	TaskStatus status;
	List<String> assignedTo;
	Integer estimatedEffort; 
	Integer actualEffort;
	Date dueDate;
	List<Activity> activities;
	Integer column;
	//List<Requirement> associatedRequirement;

	/**
	 * Empty constructor for the Task class
	 */
	public Task() {
		super();
		this.title = "";
		this.description = "";
		this.status = new TaskStatus("New");
		this.assignedTo = new LinkedList<String>();
		this.estimatedEffort = -1;
		this.actualEffort = 1;
		this.dueDate = new Date();
		this.activities = new LinkedList<Activity>();
		this.activities.add(new Activity("Task created, bro"));
		this.column = 0;
		this.archived = false;
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
		this.title = title;
		this.description = description;
		this.status = status;
		this.assignedTo = assignedTo;

		//check that estimatedEffort is positive
		if (estimatedEffort > 0){
			this.estimatedEffort = estimatedEffort;
		}
		else {
			throw new IllegalArgumentException("Estimated Effort Must Be Greater Than Zero!");
		}
		
		if (actualEffort >= 0){
			this.actualEffort = actualEffort;
		}
		else {
			throw new IllegalArgumentException("Actual Effort Must Be Greater Than Or Equal To Zero!");
		}

		this.dueDate = dueDate;
		this.activities = activities;
		this.column = 0;
		this.archived = false;
	}

	/**
	 * Constructor for a task that does not have list of members assigned to it or a list of activities
	 * 
	 * 
	 * @param title name for the task
	 * @param description explanation of the task
	 * @param status what point the task is at (in progress, not started, etc.)
	 * @param estimatedEffort number that represents how much effort (units of work)
	 * @param actualEffort number that represents the actual effort
	 * @param dueDate when the task is due
	 * @throws IllegalArgumentException
	 */

	public Task(String title, String description, TaskStatus status, Integer estimatedEffort,
			Integer actualEffort, Date dueDate) throws IllegalArgumentException {
		super();

		this.title = title;
		this.description = description;
		this.status = status;
		this.assignedTo = new LinkedList<String>();
		//checks that estimatedEffort is positive
		if (estimatedEffort > 0){
			this.estimatedEffort = estimatedEffort;
		}
		else {
			throw new IllegalArgumentException("Estimated Effort Must Be Greater Than Zero!");
		}
		//checks that actualEffort is positive
		if (actualEffort > 0){
			this.actualEffort = actualEffort;
		}
		else {
			throw new IllegalArgumentException("Actual Effort Must Be Greater Than Zero!");
		}
		this.dueDate = dueDate;
		this.activities = new LinkedList<Activity>();
		this.column = 0;
		this.archived = false;
	}

	/**
	 * Constructor for a task with only a title
	 * @param title name for the task
	 * @throws IllegalArgumentException
	 */
	public Task(String title) throws IllegalArgumentException {
		super();
		this.title = title;
		this.description = "";
		this.status = null;
		this.assignedTo = new LinkedList<String>();
		this.estimatedEffort = -1;
		this.actualEffort = -1;
		this.dueDate = null;
		this.activities = new LinkedList<Activity>();
		this.column = 0;
		this.archived = false;
	}


	/**
	 * Returns an instance of Requirement constructed using the given
	 * Requirement encoded as a JSON string.
	 * 
	 * @param json
	 *            JSON-encoded Requirement to deserialize @return the
	 *            Requirement contained in the given JSON
	 */
	public static Task fromJson(String json) {
		return new Gson().fromJson(json, Task.class);

	}

	/**
	 * Method toJSON. @return String * @see
	 * edu.wpi.cs.wpisuitetng.modules.Model#toJSON() * @see
	 * edu.wpi.cs.wpisuitetng.modules.Model#toJSON()
	 */
	@Override
	/**This returns a Json encoded String representation of this requirement object.
	 * 
	 * @return a Json encoded String representation of this requirement
	 * 
	 */
	public String toJson() {
		return new Gson().toJson(this, Task.class);
	}

	/**
	 * Copies all of the values from the given requirement to this requirement.
	 * 
	 * @param toCopyFrom
	 *            the requirement to copy from.
	 */
	public void copyFrom(Task toCopyFrom) {
		//
		//If not it can be with Requirement.fromJson(requirement.toJson)
		this.description = toCopyFrom.description;
		this.title = toCopyFrom.title;
		this.status = toCopyFrom.status;
		this.assignedTo = toCopyFrom.assignedTo;
		this.id = toCopyFrom.id;
		this.estimatedEffort = toCopyFrom.estimatedEffort;
		this.actualEffort = toCopyFrom.actualEffort;
		this.status = toCopyFrom.status;
		this.dueDate = toCopyFrom.dueDate;
		this.activities = toCopyFrom.activities;
		this.column = toCopyFrom.column;
		this.archived = toCopyFrom.archived;
	}

	/**
	 * Checks to see if the objects are equal by first checking the Task id then the title.
	 * 
	 * @param obj:  Object to compare against
	 */
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;

		Task other = (Task) obj;

		if (this.id != other.id) {
			return false;
		}
		if (this.title.compareTo(other.title) != 0) {
			return false;
		}

		return true;
	}
	/**
	 * Checks to see if anything new has been set in the task, and if so
	 * will add what happened to the list of Activities.
	 * 
	 * @param oldObj
	 * @param newObj
	 * @param objType
	 */
	public void addToActivitiesList(Object oldObj, Object newObj, String objType){
		if (!oldObj.equals(newObj)){
			activities.add(new Activity("Set " + objType + " to " + newObj.toString()));
		}
	}

	/**
	 * Necessary Method Implementation
	 */
	@Override
	public void save() {
		// TODO Auto-generated method stub

	}

	/**
	 * Necessary Method Implementation
	 */
	@Override
	public void delete() {
		// TODO Auto-generated method stub
	}

	/**
	 * Necessary Method Implementation
	 * 
	 */
	@Override
	public Boolean identify(Object o) {
		// TODO Auto-generated method stub
		return null;
	}


	/**
	 * @return Title of task
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Setter for title.  If title is too long, throws an exception
	 * adds any changes to the Activities List
	 * @param title
	 * @throws IllegalArgumentException
	 */
	public void setTitle(String title) throws IllegalArgumentException {
		this.addToActivitiesList(this.getTitle(), title, "Title");
		this.title = title;
	}

	/**
	 * @return Description of task
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Setter of the Description
	 * adds any changes to the Activities List
	 * @param description Description of task
	 */
	public void setDescription(String description) {
		this.addToActivitiesList(this.getDescription(), description, "Description");
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
	 * Setter for the Status of the task
	 * adds any changes to the Activities List
	 * @param status status of task
	 */
	public void setStatus(TaskStatus status) {
		this.addToActivitiesList(this.getStatus(), status, "Status");
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
	 * Sets members to the task
	 * adds any changes to the Activities List
	 * @param assignedTo members associated with task
	 */
	public void setAssignedTo(List<String> assignedTo) {
		this.addMembersToActivities(this.getAssignedTo(), assignedTo);
		this.assignedTo = assignedTo;
		
	}
	
	/**
	 * Checks to see if there are any changes to the members on the task, and if so
	 * adds it to the list of Activities
	 * @param oldMembers
	 * @param newMembers
	 */
	public void addMembersToActivities(List<String> oldMembers, List<String> newMembers){
		System.out.println("addMembersToActivities: " + oldMembers + " " + newMembers);
		for(String member : oldMembers){
			if(!newMembers.contains(member)){
				this.activities.add(new Activity (member + " was removed"));
			}
		}
		for(String member : newMembers){
			if(!oldMembers.contains(member)){
				this.activities.add(new Activity (member + " was added"));
			}
		}
	}

	/**
	 * 
	 * @return Task ID (used to discriminate between different tasks)
	 */
	public int getId() {
		return id;
	}

	/**
	 * Sets the ID for the task
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
	 * adds any changes to the Activities List
	 * @param estimatedEffort estimated effort for the task
	 * @throws IllegalArgumentException
	 */
	public void setEstimatedEffort(Integer estimatedEffort) throws IllegalArgumentException {
		//checks that estimatedEffort is positive
		if (estimatedEffort > 0){
			this.addToActivitiesList(this.getEstimatedEffort(), estimatedEffort, "Estimated Effort");
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
	 * adds any changes to the Activities List
	 * 
	 * @param actualEffort actual effort for task
	 * @throws IllegalArgumentException
	 */
	public void setActualEffort(Integer actualEffort) throws IllegalArgumentException {
		// making sure that the inputted value is positive
		if (actualEffort >= 0){
			this.addToActivitiesList(this.getActualEffort(), actualEffort, "Actual Effort");
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
	 * Sets the due date of the task
	 * adds any changes to the Activities List
	 * @param dueDate due date for the task
	 */
	public void setDueDate(Date dueDate) {
		this.addToActivitiesList(this.getDueDate(), dueDate, "Due Date");
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
	 * 
	 * @return column task is associated with
	 */
	public Integer getColumn() {
		return column;
	}

	/**
	 * Sets the column location of the task
	 * adds any changes to the Activities List
	 * @param column column task is associated with
	 */
	public void setColumn(Integer column) {
		this.addToActivitiesList(this.getColumn(), column, "Column");
		this.column = column;
	}
	
	/**
	 * @return whether or not this task is archived
	 */
	public boolean isArchived() {
		return archived;
	}
	
	/**
	 * Set archival status to true and adds it to the Activities List
	 */
	public void archive() {
		this.archived = true;
		this.activities.add(new Activity("Task has been archived"));
	}
	
	/**
	 * Set archival status to false and adds it to the Activities List
	 */
	public void unarchive() {
		this.archived = false;
		this.activities.add(new Activity("Task has been unarchived"));
	}
	
}
=======
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
	private Stage stage;
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
		this("A New Task", "A New Task", new Stage("New"), new LinkedList<String>(), 1, 1,
				new Date(), new LinkedList<Activity>());
	}

	/**
	 * Constructor for a task that has all of the fields
	 * 
	 * @param title name for the task
	 * @param description explanation of the task
	 * @param assignedTo list of members that are assigned to the task
	 * @param estimatedEffort number that represents how much effort (units of work)
	 * @param actualEffort number that represents the actual effort
	 * @param dueDate when the task is due
	 * @param activities list of activities (comments that members can put) for the task
	 * @throws IllegalArgumentException
	 */
	public Task(String title, String description, Stage stage,
			List<String> assignedTo, Integer estimatedEffort,
			Integer actualEffort, Date dueDate, List<Activity> activities) throws IllegalArgumentException {
		super();
		this.title = TaskUtil.validateTitle(title);
		this.description = TaskUtil.validateDescription(description);
		this.stage = TaskUtil.validateStage(stage);
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
		this.stage = t.getStage();
		this.assignedTo = new LinkedList<String>(t.getAssignedTo());
		this.estimatedEffort = new Integer(t.getEstimatedEffort());
		this.actualEffort = new Integer(t.getActualEffort());
		this.dueDate = new Date(t.getDueDate().getTime());
		this.activities = new LinkedList<Activity>(t.getActivities());
	}
	
	/**
	 * Checks to see if the objects are equal
	 * @param o Object to compare against
	 */
	@Override
	public boolean equals(Object o) {
		if (o instanceof Task) {
			Task task = (Task) o;
			return this.id == task.getId()
				&& this.title.equals(task.getTitle())
				&& this.description.equals(task.getDescription())
				&& this.estimatedEffort.equals(task.getEstimatedEffort())
				&& this.actualEffort.equals(task.getActualEffort())
				&& this.dueDate.equals(task.getDueDate());
		}
		return false;
	}
	
	/**
	 * Determines the hashCode of the task to be its ID
	 */
	@Override
	public int hashCode() { return this.id; }
	
	/**
	 * A simple toString
	 */
	public String toString() {
		return "Task[" + this.id + "][" + this.title + "](" + this.stage + ")";
	}
	
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
		throw new RuntimeException("Called save() on a task. This violates our methodology.");
	}

	/**
	 * Necessary Method Implementation: our task model does not use this.
	 */
	@Override
	public void delete() {
		throw new RuntimeException("Called delete() on a task. This violates our methodology.");
	}

	/**
	 * Necessary Method Implementation: our task model does not use this.
	 */
	@Override
	public Boolean identify(Object o) {
		throw new RuntimeException("Called identify() on a task. Please use equals() instead.");
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
		this.title = TaskUtil.validateTitle(title);
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
	public void setDescription(String description) throws IllegalArgumentException  {
		this.description = TaskUtil.validateDescription(description);
	}
	
	/**
	 * Gets the stage of this task
	 * @return The stage that this task belongs to
	 */
	public Stage getStage() {
		return this.stage;
	}
	
	/**
	 * Sets the stage of this task
	 * @param stage A stage
	 */
	public void setStage(Stage stage) throws IllegalArgumentException {
		this.stage = TaskUtil.validateStage(stage);
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
		this.estimatedEffort = TaskUtil.validateEffort(estimatedEffort);
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
		// making sure that the input value is positive
		this.actualEffort = TaskUtil.validateEffort(actualEffort);
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
	public void setDueDate(Date dueDate) throws IllegalArgumentException  {
		this.dueDate = TaskUtil.validateDueDate(dueDate);
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
	public void setActivities(List<Activity> activities) throws IllegalArgumentException  {
		this.activities = activities;
	}
	
	/**
	 * @return whether or not this task is archived
	 */
	public boolean isArchived() {
		return archived;
	}
	
	/**
	 * Set archival status to true
	 */
	public void archive() {
		this.archived = true;
	}
	
	/**
	 * Set archival status to false
	 */
	public void unarchive() {
		this.archived = false;
	}

	/**
	 * Master setter which updates this task according to a new task without changing its id.
	 * @param updatedTask
	 * @throws IllegalArgumentException
	 */
	public void updateFrom(Task updatedTask) throws IllegalArgumentException {
		this.title = new String(TaskUtil.validateTitle(updatedTask.getTitle()));
		this.description = new String(TaskUtil.validateDescription(updatedTask.getDescription()));
		this.stage = TaskUtil.validateStage(updatedTask.getStage());
		this.assignedTo = new LinkedList<String>(updatedTask.getAssignedTo());
		this.estimatedEffort = TaskUtil.validateEffort(new Integer(updatedTask.getEstimatedEffort()));
		this.actualEffort = TaskUtil.validateEffort(new Integer(updatedTask.getActualEffort()));
		this.dueDate = TaskUtil.validateDueDate(new Date(updatedTask.getDueDate().getTime()));
		this.activities = new LinkedList<Activity>(updatedTask.getActivities());
		this.archived = updatedTask.archived;
	}
}
>>>>>>> start-of-refactor

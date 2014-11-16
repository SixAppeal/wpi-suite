package edu.wpi.cs.wpisuitetng.modules.taskmanager.model;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import com.google.gson.Gson;

import edu.wpi.cs.wpisuitetng.modules.AbstractModel;

//TODO Fix import error
//import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.Requirement;

/**
 * 
 * The model we will be using to store in the database.  Contains all necessary information about a task
 * 
 * @author nhhughes
 * @author srojas
 * @author jrhennessy
 */
public class Task extends AbstractModel {

	String title;
	String description;
	TaskStatus status;
	List<String> assignedTo;
	int id;
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
		this.status = null;
		this.assignedTo = new LinkedList<String>();
		this.estimatedEffort = -1;
		this.actualEffort = -1;
		this.dueDate = null;
		this.activities = new LinkedList<Activity>();
		this.column = 0;
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
		if (title.length() > 100 ){
			throw new IllegalArgumentException("Title Too Long!");
		}
		else {
			this.title = title;
		}
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

		if (title.length() > 100 ){
			throw new IllegalArgumentException("Title Too Long!");
		}
		else {
			this.title = title;
		}
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
	}

	/**
	 * Constructor for a task with only a title
	 * @param title name for the task
	 * @throws IllegalArgumentException
	 */
	public Task(String title) throws IllegalArgumentException {
		super();
		if (title.length() > 100 ){
			throw new IllegalArgumentException("Title Too Long!");
		} 
		else {
			this.title = title;
		}
		this.description = "";
		this.status = null;
		this.assignedTo = new LinkedList<String>();
		this.estimatedEffort = -1;
		this.actualEffort = -1;
		this.dueDate = null;
		this.activities = new LinkedList<Activity>();
		this.column = 0;
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
	 * Returns a Concantenated String of all the members in the assignedTo List
	 * String memberList
	 * @return memberList
	 */
	public String getMemberList() {
		StringBuilder memberList = new StringBuilder();
		for (String m: this.assignedTo){
			memberList.append(m).append(", ");
		}
		return memberList.toString();
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
		if (title.length() > 100 ){
			throw new IllegalArgumentException("Title Too Long!");
		}
		else {
			this.title = title;
		}
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
	 * 
	 * @return column task is associated with
	 */
	public Integer getColumn() {
		return column;
	}

	/**
	 * 
	 * @param column column task is associated with
	 */
	public void setColumn(Integer column) {
		this.column = column;
	}
}

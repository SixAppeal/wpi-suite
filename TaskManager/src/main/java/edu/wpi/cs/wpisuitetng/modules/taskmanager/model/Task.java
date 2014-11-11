/**
 * 
 */
package edu.wpi.cs.wpisuitetng.modules.taskmanager.model;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import com.google.gson.Gson;

import edu.wpi.cs.wpisuitetng.modules.AbstractModel;

//For some reason can't import Requirement class that is necessary for associated requirement feature

//TODO Fix import error
//import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.Requirement; // ???? is happening here


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
	List<Member> assignedTo;
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

	Task() {
		super();
		this.title = "";
		this.description = "";
		this.status = null;
		this.assignedTo = new LinkedList<Member>();
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
	 */
	Task(String title, String description, TaskStatus status,
			List<Member> assignedTo, Integer estimatedEffort,
			Integer actualEffort, Date dueDate, List<Activity> activities) {
		super();
		//TODO Srojas will check that substring works (Santiago will use JUnit test)
		if (title.length() > 100 ){
			this.title = title.substring(0, 100);
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
			this.estimatedEffort = -1;
		}
		//if negative,  -1
		if (actualEffort > 0){
			this.actualEffort = actualEffort;
		}
		else {
			this.actualEffort = -1;
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
	 */

	Task(String title, String description, TaskStatus status, Integer estimatedEffort,
			Integer actualEffort, Date dueDate) {
		super();
		
		if (title.length() > 100 ){
			this.title = title.substring(0, 99);
		}
		else {
			this.title = title;
		}
		this.description = description;
		this.status = status;
		this.assignedTo = new LinkedList<Member>();
		//checks that estimatedEffort is positive
		if (estimatedEffort > 0){
			this.estimatedEffort = estimatedEffort;
		}
		else {
			this.estimatedEffort = -1;
		}
		//checks that actualEffort is positive
		if (actualEffort > 0){
			this.actualEffort = actualEffort;
		}
		else {
			this.actualEffort = -1;
		}
		this.dueDate = dueDate;
		this.activities = new LinkedList<Activity>();
		this.column = 0;
	}

	/**
	 * Constructor for a task with only a title
	 * @param title name for the task
	 */
	Task(String title) {
		super();
		//TODO Srojas will check that substring works (Santiago will use JUnit test)
		if (title.length() > 100 ){
			this.title = title.substring(0, 99);
		}
		else {
			this.title = title;
		}
		this.description = "";
		this.status = null;
		this.assignedTo = new LinkedList<Member>();
		this.estimatedEffort = -1;
		this.actualEffort = -1;
		this.dueDate = null;
		this.activities = new LinkedList<Activity>();
		this.column = 0;
	}



	//database methods
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

	//ignore this, don't delete 
	@Override
	public void save() {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete() {
		// TODO Auto-generated method stub

	}


	@Override
	public Boolean identify(Object o) {
		// TODO Auto-generated method stub
		return null;
	}



	//GETTERS AND SETTERS 
	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		//TODO Srojas will check that substring works (Santiago will use JUnit test)
		if (title.length() > 100 ){
			this.title = title.substring(0, 99);
		}
		else {
			this.title = title;
		}
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public TaskStatus getStatus() {
		return status;
	}


	public void setStatus(TaskStatus status) {
		this.status = status;
	}


	public List<Member> getAssignedTo() {
		return assignedTo;
	}


	public void setAssignedTo(List<Member> assignedTo) {
		this.assignedTo = assignedTo;
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public Integer getEstimatedEffort() {
		return estimatedEffort;
	}


	public void setEstimatedEffort(Integer estimatedEffort) {
		//checks that estimatedEffort is positive
		if (estimatedEffort > 0){
			this.estimatedEffort = estimatedEffort;
		}
		else {
			this.estimatedEffort = -1;
		}
	}


	public Integer getActualEffort() {
		return actualEffort;
	}


	public void setActualEffort(Integer actualEffort) {
		// making sure that the inputted value is positive
		if (actualEffort > 0){
			this.actualEffort = actualEffort;
		}
		else {
			this.actualEffort = -1;
		}
	}


	public Date getDueDate() {
		return dueDate;
	}


	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}


	public List<Activity> getActivities() {
		return activities;
	}


	public void setActivities(List<Activity> activities) {
		this.activities = activities;
	}

	public Integer getColumn() {
		return column;
	}


	public void setColumn(Integer column) {
		this.column = column;
	}


}

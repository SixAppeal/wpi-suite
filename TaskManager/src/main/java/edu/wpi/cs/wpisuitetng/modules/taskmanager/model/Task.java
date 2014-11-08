/**
 * 
 */
package edu.wpi.cs.wpisuitetng.modules.taskmanager.model;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import edu.wpi.cs.wpisuitetng.modules.AbstractModel;
//import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.Requirement; // ???? is happening here

/**
 * @author nathan
 * @author santiago
 * @author jill
 */
public class Task extends AbstractModel {
	
	String title;
	String description;
	TaskStatus status;
	List<Member> assignedTo;
	
	Integer estimatedEffort; 
	Integer actualEffort;
	Date dueDate;
	List<Activity> activities;
	//List<Requirement> associatedRequirement;
	
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
		this.title = title;
		this.description = description;
		this.status = status;
		this.assignedTo = assignedTo;
		this.estimatedEffort = estimatedEffort;
		this.actualEffort = actualEffort;
		this.dueDate = dueDate;
		this.activities = activities;
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
		this.title = title;
		this.description = description;
		this.status = status;
		this.assignedTo = new LinkedList<Member>();
		this.estimatedEffort = estimatedEffort;
		this.actualEffort = actualEffort;
		this.dueDate = dueDate;
		this.activities = new LinkedList<Activity>();
	}
	
	/**
	 * Constructor for a task with only a title
	 * @param title name for the task
	 */
	Task(String title) {
		super();
		this.title = title;
		this.description = "";
		this.status = null;
		this.assignedTo = new LinkedList<Member>();
		this.estimatedEffort = -1;
		this.actualEffort = -1;
		this.dueDate = null;
		this.activities = new LinkedList<Activity>();
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
	public String toJson() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean identify(Object o) {
		// TODO Auto-generated method stub
		return null;
	}

}

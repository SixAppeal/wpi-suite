/**
 * 
 */
package edu.wpi.cs.wpisuitetng.modules.taskmanager.model;

/**
 * @author nathan
 * @author santiago
 * @author jill
 */
public class TaskStatus {


	String status;
	
	/**
	 * Constructor for the task status class. 
	 * @param status what point the task is at (in progress, not started, etc.)
	 */
	public TaskStatus (String status) {
		this.status = status;
	}
	
	@Override
	public String toString() {
		return status;
	}
	
}

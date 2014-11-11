/**
 * 
 */
package edu.wpi.cs.wpisuitetng.modules.taskmanager.model;

/**
 * @author nhhughes
 * @author srojas
 * @author jrhennessy
 */
public class TaskStatus {


	String status;
	
	/**
	 * Constructor for the task status class. 
	 * @param status what point the task is at (in progress, not started, etc.)
	 */
	TaskStatus (String status) {
		this.status = status;
	}
	
	
	
	//Getters and Setters
	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	
}

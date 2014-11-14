package edu.wpi.cs.wpisuitetng.modules.taskmanager.model;

/**
 * Class to capture the status of a task
 * 
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
	public TaskStatus (String status) {
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
	
	@Override
	public String toString() {
		return status;
	}
	
}

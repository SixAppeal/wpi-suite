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
	
	public TaskStatus(TaskStatus ts) {
		this.status = new String(ts.getStatus());
	}

	/**
	 * TaskStatus equality is based on the string which identifies them.
	 */
	public boolean equals(Object o) {
		return (o instanceof TaskStatus) && ((TaskStatus)o).getStatus().equals(this.status);
	}
	/**
	 * 
	 */
	public int hashCode() {
		return status.hashCode();
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

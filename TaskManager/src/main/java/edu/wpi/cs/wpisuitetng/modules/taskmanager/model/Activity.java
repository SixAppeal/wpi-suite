package edu.wpi.cs.wpisuitetng.modules.taskmanager.model;

/**
 * Class to capture an activity, which is a comment on a task
 * 
 * @author nhhughes
 * @author srojas
 * @author jrhennessy
 * 
 */
public class Activity {
	String comment;
	
	/**
	 *  Constructor for an activity
	 * @param member member that made the comment
	 * @param comment comment that the member made
	 */
	public Activity(String comment){
		this.comment = comment;
	}

	/**
	 * @return the comment
	 */
	public String getComment() {
		return comment;
	}

	/**
	 * @param comment the comment to set
	 */
	public void setComment(String comment) {
		this.comment = comment;
	}
	
	public String toString(){
		return this.comment;
	}
}

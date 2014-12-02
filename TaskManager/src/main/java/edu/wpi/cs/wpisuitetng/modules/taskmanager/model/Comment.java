package edu.wpi.cs.wpisuitetng.modules.taskmanager.model;

/**
 * Class to capture comments made on a task by a user
 * 
 * @author krpeffer
 * @author rwang3
 *
 */

public class Comment {
	String user;
	String comment;
	
	/**
	 * Constructor for the comment object
	 * @param user
	 * @param comment
	 */
	public Comment(String user, String comment){
		this.user = user;
		this.comment = comment;
	}
	
	/**
	 * 
	 * @return comment
	 */
	public String getComment(){
		return this.comment;
	}
	
	/**
	 * 
	 * @return user
	 */
	public String getUser(){
		return this.user;
	}
	
	/**
	 * sets the comment to a new comment.
	 * @param comment
	 */
	public void setComment(String comment){
		this.comment = comment;
	}
	
	@Override
	public String toString(){
		return (this.user + ": " + this.comment);
	}
	
	/**
	 * sets the user to a new user
	 * @param user
	 */
	public void setUser(String user){
		this.user = user;
	}
}
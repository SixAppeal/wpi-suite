package edu.wpi.cs.wpisuitetng.modules.taskmanager.model;

import java.io.Serializable;

import java.util.Calendar;
import java.text.SimpleDateFormat;

import com.google.gson.Gson;

/**
 * Class to capture comments made on a task by a user
 * 
 * @author krpeffer
 * @author rwang3
 *
 */

public class Comment implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7751779367913054594L;
	String user;
	String comment;
	Calendar dateAndTime;
	SimpleDateFormat sdf = new SimpleDateFormat("h:mm a, MMM d, yyyy");
	
	/**
	 * Default Constructor for comments
	 */
	public Comment(){
		dateAndTime = Calendar.getInstance();
		this.user = "";
		this.comment = "";
	}
	
	/**
	 * Constructor for the comment object
	 * @param user
	 * @param comment
	 */
	public Comment(String user, String comment){
		dateAndTime = Calendar.getInstance();
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
	
	public Calendar getTime(){
		return this.dateAndTime;
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
		return (sdf.format(this.dateAndTime.getTime()) + " -- " + this.user + ": " + this.comment);
	}
	
	/**
	 * sets the user to a new user
	 * @param user
	 */
	public void setUser(String user){
		this.user = user;
	}
	
	
	public String toJson() {
		return new Gson().toJson(this, Comment.class);
	}
}

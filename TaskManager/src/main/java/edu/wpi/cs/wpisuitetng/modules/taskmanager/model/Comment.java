/*******************************************************************************
 * Copyright (c) 2014 -- WPI Suite
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors: Team Six-Appeal
 ******************************************************************************/
package edu.wpi.cs.wpisuitetng.modules.taskmanager.model;

import java.io.Serializable;

import java.util.Date;
import java.text.SimpleDateFormat;

import com.google.gson.Gson;

/**
 * Class to capture comments made on a task by a user
 * 
 * @author krpeffer
 * @author rwang3
 * @author thhughes
 * @author tmeehan
 *
 */
public class Comment implements Serializable {
	
	private static final long serialVersionUID = -7751779367913054594L;
	
	private static final int MAX_LENGTH = 20;
	String user;
	String comment;
	Date date;
	SimpleDateFormat sdf = new SimpleDateFormat("h:mm a, MM/dd/yyyy");
	
	/**
	 * Default Constructor for comments
	 */
	public Comment(){
		this.user = "";
		this.comment = "";
		this.date = new Date();
	}
	
	/**
	 * Constructor for the comment object
	 * @param user
	 * @param comment
	 */
	public Comment(String user, String comment){
		this.user = user;
		this.comment = comment;
		this.date = new Date();
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
	 * Checks to see if two comments are equal. 
	 */
	@Override 
	public boolean equals(Object other){
		if (this==other) {
			return true;
		}
		boolean result = false;
		if(other instanceof Comment){
			Comment that = (Comment) other;
			result = this.getUser().equals(that.getUser()) && this.getComment().equals(that.getComment());
		}
		return result;
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
		return (sdf.format(date) + "> " + this.user + ": " + this.comment);
	}
	
	/**
	 * sets the user to a new user
	 * @param user
	 */
	public void setUser(String user){
		this.user = user;
	}
	
	/**
	 * Converts the comments to Json
	 * @return string representation of Json
	 */
	public String toJson() {
		return new Gson().toJson(this, Comment.class);
	}
	


	/**
	 * This returns a displayable comment in a JList<String>. It is used in the TaskEditView mainly, but could be applied elsewhere. 
	 * 
	 * @return an HTML code parsed String for JList<String> of length MAX_VALUE
	 */
	public String viewableComment(){
		// Initialize
		String returnString = new String("<html>");
		int numCuts = this.toString().length() / MAX_LENGTH;
		
		// If the string needs to be parsed, begin parcing
		if (this.toString().length() > MAX_LENGTH){
			// add 1 to numCuts in order to ensure you get the entire comment
			if ((this.toString().length() % MAX_LENGTH) > 0){
				numCuts++;
			}
			
			// Iterate through the comment inserting breaks in where necessary. << This stops 1 iteration short>> 
			for (int i = 0; i < (numCuts - 1); i++){
				if(i == 0){			// If this is the first time iterating, return below << this eliminates the break for the first part of the string >> 
					returnString = returnString + this.toString().substring(i*MAX_LENGTH, ((i+1)*MAX_LENGTH));
				}
				else{				// Otherwise add this break in at the appropriate places. 
					returnString = returnString + "<br>" + this.toString().substring(i*MAX_LENGTH, ((i+1)*MAX_LENGTH));
				}
			}
			// Add the final part of the string to the return value, add the HTML tag so that it parses properly 
			returnString = returnString + this.toString().substring((numCuts - 1)*MAX_LENGTH) + "</html>";
			return returnString;
			
		}else{
			// Otherwise return the string
			return this.toString();
		}
	}
}

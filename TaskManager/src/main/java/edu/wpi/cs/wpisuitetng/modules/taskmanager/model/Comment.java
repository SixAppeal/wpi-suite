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
import java.util.Calendar;
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
	String user;
	String comment;
	Date dateAndTime;
	
	/**
	 * Default Constructor for comments
	 */
	public Comment(){
		dateAndTime = new Date(); 
		this.user = "";
		this.comment = "";
		
	}
	
	/**
	 * Constructor for the comment object
	 * @param user
	 * @param comment
	 */
	public Comment(String user, String comment){
		dateAndTime = new Date();
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
	
	public Date getTime(){
		return this.dateAndTime;
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
		return DateFormatPrinter.getInstance().getString(this.dateAndTime, this.user, this.comment);
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
}

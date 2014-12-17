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

import java.util.Calendar;
import java.util.Date;
import java.text.SimpleDateFormat;

import com.google.gson.Gson;

import edu.wpi.cs.wpisuitetng.janeway.config.ConfigManager;
/**
 * Class to capture an activity, which stores information based on changes made to a task
 * 
 * @author nhhughes
 * @author srojas
 * @author jrhennessy
 * @author krpeffer
 * @author thhughes
 * @author tmeehan
 * 
 */
public class Activity {
	String user;
	String activity;
	Date dateAndTime;
	
	
	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public Date getDateAndTime() {
		return dateAndTime;
	}

	public void setDateAndTime(Date dateAndTime) {
		this.dateAndTime = dateAndTime;
	}

	public Activity(){
		this.user = "";
		this.activity = "";
		this.dateAndTime = new Date();
	}
	
	/**
	 * Constructor for an activity
	 * @param member member that made the comment
	 * @param comment comment that the member made
	 */
	public Activity(String comment){
		this.user = ConfigManager.getConfig().getUserName();
		this.activity = comment;
	}

	/**
	 * @return the comment
	 */
	public String getActivity() {
		return activity;
	}

	/**
	 * @param comment the comment to set
	 */
	public void setActivity(String comment) {
		this.activity = comment;
	}
	
	
	@Override
	public String toString(){
		return DateFormatPrinter.getInstance().getString(this.dateAndTime, this.user, this.activity);
	}
	
	
	/**
	 * Checks to see if two activities are equal
	 */
	public boolean equals(Object other){
		if (this==other) {
			return true;
		}
		boolean result = false;
		if(other instanceof Activity){
			Activity that = (Activity) other;
			result = this.getActivity().equals(that.getActivity());
		}
		return result;
	}
	
	/**
	 * Converts the comments to Json
	 * @return string representation of Json
	 */
	public String toJson() {
		return new Gson().toJson(this, Comment.class);
	}
	
}

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
import java.text.SimpleDateFormat;

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
	Calendar dateAndTime;
	SimpleDateFormat sdf = new SimpleDateFormat("h:mm a, MMM d, yyyy");
	
	public Activity(){
		this.user = "";
		this.activity = "";
		this.dateAndTime = Calendar.getInstance();
	}
	
	/**
	 * Constructor for an activity
	 * @param member member that made the comment
	 * @param comment comment that the member made
	 */
	public Activity(String comment){
		this.user = ConfigManager.getConfig().getUserName();
		this.activity = comment;
		this.dateAndTime = Calendar.getInstance();
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
	
	public Calendar getTime() {
		return this.dateAndTime;
	}
	
	@Override
	public String toString(){
		return (sdf.format(this.dateAndTime.getTime()) + ": " + this.user + " " + this.activity);
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
	
	
}

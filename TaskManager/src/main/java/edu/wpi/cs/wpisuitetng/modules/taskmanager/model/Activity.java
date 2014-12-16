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
	String activity;
	
	public Activity(){
		this.activity = "";
	}
	
	/**
	 * Constructor for an activity
	 * @param member member that made the comment
	 * @param comment comment that the member made
	 */
	public Activity(String comment){
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
		return this.activity;
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

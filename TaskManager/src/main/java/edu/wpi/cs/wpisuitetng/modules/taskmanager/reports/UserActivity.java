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

package edu.wpi.cs.wpisuitetng.modules.taskmanager.reports;

/**
 * Class to represent the amount of work a user has done during the week
 * @author nhhughes
 *
 */
public class UserActivity {
	
	private int importance;
	private String name;
	
	/**
	 * Makes a UserActivity object associated with the user provided by username
	 * @param username username of user associated with object
	 */
	public UserActivity(String username) {
		this.importance = 0;
		this.name = username;
	}
	
	/**
	 * Get the importance of the user in question
	 * @return an integer score of the importance of the user
	 */
	public int getImportance() {
		return this.importance;
	}
	
	/**
	 * Set the importance of the user
	 * @param importance importance of the user
	 */
	public void setImportance(int importance) {
		this.importance = importance;
	}
	
	/**
	 * Get the name of the user
	 * @return the name of the user
	 */
	public String getName() {
		return this.name;
	}
	
	/**
	 * Set the name of the user
	 * @param username the name of the user
	 */
	public void setName(String username) {
		this.name = username;
	}
	
	/**
	 * Return a string representation for debugging purposes
	 */
	@Override
	public String toString() {
		return (this.name + ": " + this.importance);
	}
	
	/**
	 * Two UserActivities are equal if the names are equal
	 */
	@Override 
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o instanceof UserActivity) {
			if (this.name.equals(((UserActivity)o).getName())) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * The hashcode of a user activity is based on the name of the user activity
	 */
	@Override
	public int hashCode() {
		return this.name.hashCode();
	}
	
	/**
	 * updates a userActivity with the importance of the old user activity
	 * @param user
	 */
	public void copyFrom(UserActivity user) {
		this.importance = user.importance;
	}
	
}

/*******************************************************************************
 * Copyright (c) 2014 -- WPI Suite
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors: Will Temple, Troy Hughes
 ******************************************************************************/
package edu.wpi.cs.wpisuitetng.modules.taskmanager.util;

import java.util.Date;

import com.google.gson.Gson;

import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.Requirement;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.model.Stage;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.model.Task;

/**
 * A utility class for managing Tasks.
 * @author wmtemple
 * @author Thhughes
 *
 */
public class TaskUtil {
	
	/**
	 * Deserializes a task from JSON
	 * 
	 * @param json JSON representation of task
	 * @return the Task represented by the given JSON
	 */
	public static Task fromJson(String json) {
		return new Gson().fromJson(json, Task.class);
	}
	
	/**
	 * Checks a String against the ruleset for valid task titles.
	 * @param input Raw input from the user
	 * @return The sanitized input, if valid.
	 * @throws IllegalArgumentException Title is blank or otherwise invalid.
	 */
	public static String validateTitle(String input) throws IllegalArgumentException {
		input = sanitizeInput(input);
		if( input.equals("") ) throw new IllegalArgumentException("Title must not be blank!");
		return input;
	}
	
	/**
	 * Checks a String against the ruleset for valid task descriptions.
	 * @param input Raw input from the user
	 * @return The sanitized input, if valid.
	 * @throws IllegalArgumentException Title is blank or otherwise invalid.
	 */
	public static String validateDescription(String input) throws IllegalArgumentException {
		input = sanitizeInput(input);
		if( input.equals("") ) throw new IllegalArgumentException("Description must not be blank!");
		return input;
	}
	
	/**
	 * Checks a Date against the ruleset for valid task due dates.
	 * @param input The date chosen by the user
	 * @return The same date, provided that it is valid.
	 * @throws IllegalArgumentException Date is somehow invalid.
	 */
	public static Date validateDueDate(Date input) throws IllegalArgumentException {
		//No rules!
		return input;
	}
	
	/**
	 * Checks an Integer against the ruleset for valid task efforts
	 * @param input The integer entered by the user
	 * @return The same Integer, provided that it is valid.
	 * @throws IllegalArgumentException
	 */
	public static Integer validateEffort(Integer input) throws IllegalArgumentException {
		if ( input <= 0 ) throw new IllegalArgumentException("Effort values must be greater than zero.");
		return input;
	}
	
	/**
	 * Checks an associated requirement against the ruleset for valid associated requirements
	 * @param aReq The string input from user
	 * @return The same input provided that it's valid.
	 * @throws IllegalArgumentException
	 */
	public static Requirement validateRequirement(Requirement aReq) throws IllegalArgumentException {
		// No rules
		return aReq;
	}
	
	/**
	 * 
	 * @param stage
	 * @return
	 * @throws IllegalArgumentException
	 */
	public static Stage validateStage(Stage stage) throws IllegalArgumentException {
		//TODO: Define implementation of validation
		return stage;
	}
	
	/**
	 * @param input Raw input from user.
	 * @return A "safe" version of the string, which has removed all leading or trailing whitespace.
	 */
	public static String sanitizeInput(String input) {
		return input.trim();
	}

}

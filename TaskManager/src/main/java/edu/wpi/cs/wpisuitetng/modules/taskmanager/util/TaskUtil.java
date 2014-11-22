package edu.wpi.cs.wpisuitetng.modules.taskmanager.util;

import java.util.Date;

import edu.wpi.cs.wpisuitetng.modules.taskmanager.model.TaskStatus;

public class TaskUtil {
	
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
	 * 
	 * @param input
	 * @return
	 * @throws IllegalArgumentException
	 */
	public static Integer validateEstEffort(Integer input) throws IllegalArgumentException {
		//TODO: Define implementation of validation
		return input;
	}
	
	/**
	 * 
	 * @param input
	 * @return
	 * @throws IllegalArgumentException
	 */
	public static Integer validateActEffort(Integer input) throws IllegalArgumentException {
		//TODO: Define implementation of validation
		return input;
	}
	
	/**
	 * 
	 * @param input
	 * @return
	 * @throws IllegalArgumentException
	 */
	public static TaskStatus validateStage(TaskStatus input) throws IllegalArgumentException {
		//TODO: Define implementation of validation
		return input;
	}
	
	/**
	 * @param input Raw input from user.
	 * @return A "safe" version of the string, which has removed all leading or trailing whitespace.
	 */
	public static String sanitizeInput(String input) {
		return input.trim();
	}

}

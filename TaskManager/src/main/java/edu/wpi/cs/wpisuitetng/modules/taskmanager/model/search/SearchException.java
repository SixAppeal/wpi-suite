package edu.wpi.cs.wpisuitetng.modules.taskmanager.model.search;

/**
 * Exception for Search class
 * 
 * @author akshoop
 * @author nhhughes
 */
public class SearchException extends Exception {
	/**
	 * The only constructor. There must be a message.
	 * @param message the reason for throwing the exception
	 */
	public SearchException(String message)
	{
		super(message);
	}
}

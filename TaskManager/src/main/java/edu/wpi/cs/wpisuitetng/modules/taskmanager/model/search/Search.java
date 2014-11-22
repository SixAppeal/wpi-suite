package edu.wpi.cs.wpisuitetng.modules.taskmanager.model.search;

/**
 * Search functionality to find tasks within cache
 * 
 * @author akshoop
 * @author nhhughes
 */
public class Search {
	private boolean isInit;
	
	/**
	 * General constructor for Search class
	 */
	public Search() {
		isInit = false;
	}
	
	/**
	 * Initialize function
	 */
	public void initialize() {
		isInit = true;
	}
	
	/**
	 * 
	 */
	public void searchFor(String input) throws SearchException {
		if (!isInit)
			throw new SearchException("Search is not initialized.");
	}
}

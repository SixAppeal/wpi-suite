package edu.wpi.cs.wpisuitetng.modules.taskmanager.model.search;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import edu.wpi.cs.wpisuitetng.modules.taskmanager.model.Task;

/**
 * Unit tests for search feature
 * 
 * @author akshoop
 * @author nhhughes
 */
public class TestSearch {
	List<Task> testList;
	
	/**
	 * Constructor for test search
	 */
	public void testConstructor() {
		Search someSearch = new Search();
		assertNotNull(someSearch);
	}
	
	/**
	 * Test for initialize
	 * @throws SearchException 
	 */
	@Test
	public void testInitialize() throws SearchException {
		Search someSearch = new Search();
		someSearch.initialize();
		someSearch.searchFor("blah");
	}
	
	/**
	 * Test when not initializing Search
	 * @throws SearchException 
	 */
	@Test(expected=SearchException.class)
	public void testNotInitializ() throws SearchException {
		Search someSearch = new Search();
		someSearch.searchFor("example");
	}
}

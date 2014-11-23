package edu.wpi.cs.wpisuitetng.modules.taskmanager.model.search;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.queryparser.classic.ParseException;
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
	 * @throws ParseException 
	 * @throws IOException 
	 */
	@Test
	public void testInitialize() throws SearchException, IOException, ParseException {
		Search someSearch = new Search();
		someSearch.initialize();
		//someSearch.searchFor("blah");
	}
	
	/**
	 * Test when not initializing Search
	 * @throws SearchException 
	 * @throws ParseException 
	 * @throws IOException 
	 */
	@Test(expected=SearchException.class)
	public void testNotInitializ() throws SearchException, IOException, ParseException {
		Search someSearch = new Search();
		someSearch.searchFor("example");
	}
	
	/**
	 * 
	 * @throws SearchException 
	 * @throws ParseException 
	 * @throws IOException 
	 */
	@Test
	public void testSearchForTitle() throws SearchException, IOException, ParseException {
		Search someSearch = new Search();
		testList = new ArrayList<Task>();
		
		someSearch.initialize();
		testList.add(new Task("sometitle"));
		testList.add(new Task("Another title"));
		testList.add(new Task("HERE'S ANOTHER TITLE"));
		someSearch.createIndex(testList);
		String result = someSearch.searchFor("sometitle");
		System.out.println("result should be " + result);
		assertEquals(result.compareTo("sometitle"), 0);
	}
	
	
}

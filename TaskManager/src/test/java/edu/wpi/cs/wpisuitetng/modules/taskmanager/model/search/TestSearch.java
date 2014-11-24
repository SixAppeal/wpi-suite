package edu.wpi.cs.wpisuitetng.modules.taskmanager.model.search;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
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
		List<String> testAssignedTo = new LinkedList<String>();
		testAssignedTo.add("akshoop");
		testAssignedTo.add("bunch");
		
		someSearch.initialize();
		Task task1 = new Task("sometitle bunch");
		task1.setDescription("sometitle bunch of words HERE ARE CAPITAL lettersWith, commas's yeah!?");
		task1.setAssignedTo(testAssignedTo);
		task1.setId(3);
		
		Task task2 = new Task("another title bunch");
		task2.setDescription("sometitle bunch of's yeah!?");
		task2.setAssignedTo(testAssignedTo);
		task2.setId(4);
		
		Task task3 = new Task("testing bunch");
		task3.setDescription(" other description bunch");
		task3.setAssignedTo(testAssignedTo);
		task3.setId(5);
		
		
		testList.add(task1);
		testList.add(task2);
		testList.add(task3);
		
		
		
		someSearch.createIndex(testList);
//		String result1 = someSearch.searchFor("sometitle");
//		System.out.println("result1 should be " + result1);
//		
//		String result2 = someSearch.searchFor("capital");
//		System.out.println("result2 should be " + result2);
//		
//		String result3 = someSearch.searchFor("nhhughes");
//		System.out.println("result3 should be " + result3);
//		
		System.out.println("******************************************************");
		System.out.println("Searching for: sometitle");
		System.out.println("******************************************************");
		
		List<Integer> result = someSearch.searchFor("sometitle");
		
		System.out.println("******************************************************");
		System.out.println("Searching for: nhhughes");
		System.out.println("******************************************************");
		
		result = someSearch.searchFor("akshoop");
		
		System.out.println("******************************************************");
		System.out.println("Searching for: bunch");
		System.out.println("******************************************************");
		
		
		result = someSearch.searchFor("bunch");
		//assertEquals(result.compareTo("sometitle"), 0);
	}
	
	
}

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
		Task task1 = new Task("someTitle bunch");
		task1.setDescription("someTitle bunch of words HERE ARE CAPITAL lettersWith, commas's yeah!?");
		task1.setAssignedTo(testAssignedTo);
		task1.setId(3);
		
		Task task2 = new Task("another title testing");
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
		
		System.out.println("******************************************************");
		System.out.println("Searching for: sometitle");
		System.out.println("******************************************************");
		
		List<Integer> result = someSearch.searchFor("sometitle");
		int count = 1;
		for (Integer i: result) {
			System.out.println(count + ": " + i);
			count++;
		}
		
		System.out.println("******************************************************");
		System.out.println("Searching for: akshoop");
		System.out.println("******************************************************");
		
		result = someSearch.searchFor("akshoop");
		
		count = 1;
		for (Integer i: result) {
			System.out.println(count + ": " + i);
			count++;
		}
		
		System.out.println("******************************************************");
		System.out.println("Searching for: testing AND bunch");
		System.out.println("******************************************************");
		
		
		result = someSearch.searchFor("testing AND bunch");
		
		
		count = 1;
		for (Integer i: result) {
			System.out.println(count + ": " + i);
			count++;
		}
		
		System.out.println("******************************************************");
		System.out.println("Searching for: another");
		System.out.println("******************************************************");
		
		
		result = someSearch.searchFor("another");
		
		
		count = 1;
		for (Integer i: result) {
			System.out.println(count + ": " + i);
			count++;
		}
		//assertEquals(result.compareTo("sometitle"), 0);
	}
	
	
}

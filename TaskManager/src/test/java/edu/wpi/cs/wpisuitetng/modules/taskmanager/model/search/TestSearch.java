/*******************************************************************************
 * 
 * Copyright (c) 2014 -- WPI Suite
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors: Nathan Hughes, Alexander Shoop
 ******************************************************************************/

package edu.wpi.cs.wpisuitetng.modules.taskmanager.model.search;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

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
		assertTrue(someSearch.isInitialized());
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
	 * Test for createIndex and overall searching
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
		Task task1 = new Task();
		task1.setTitle("someTitle bunch");
		task1.setDescription("someTitle bunch of words HERE ARE CAPITAL lettersWith, commas's yeah!?");
		task1.setAssignedTo(testAssignedTo);
		task1.setId(3);
		
		Task task2 = new Task();
		task2.setTitle("another title testing");
		task2.setDescription("sometitle bunch of's yeah!?");
		task2.setAssignedTo(testAssignedTo);
		task2.setId(4);
		
		Task task3 = new Task();
		task3.setTitle("testing bunch");
		task3.setDescription(" other description bunch");
		task3.setAssignedTo(testAssignedTo);
		task3.setId(5);
		
		testList.add(task1);
		testList.add(task2);
		testList.add(task3);
		
		someSearch.createIndex(testList);
		
		List<Integer> result = someSearch.searchFor("sometitle");
		
		result = someSearch.searchFor("akshoop");
		
		result = someSearch.searchFor("testing AND bunch");
		
		result = someSearch.searchFor("another");
	}
	
	/**
	 * Test for updateIndex
	 * @throws IOException 
	 * @throws ParseException 
	 * @throws SearchException 
	 */
	@Test
	public void testUpdateIndex() throws IOException, SearchException, ParseException {
		Search someSearch = new Search();
		someSearch.initialize();
		testList = new ArrayList<Task>();
		List<String> testAssignedTo = new LinkedList<String>();
		testAssignedTo.add("title");
		
		Task task1 = new Task();
		task1.setTitle("a title");
		task1.setDescription("a random title");
		task1.setAssignedTo(testAssignedTo);
		task1.setId(2);
		
		Task task2 = new Task();
		task2.setTitle("another title");
		task2.setDescription("gotta do tests");
		task2.setId(3);
		
		testList.add(task1);
		testList.add(task2);
		
		someSearch.updateIndex(testList);
		
		List<Integer> result = someSearch.searchFor("title");
		
		result = someSearch.searchFor("description");
	}
}

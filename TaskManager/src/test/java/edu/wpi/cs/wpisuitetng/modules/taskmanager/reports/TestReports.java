/*******************************************************************************
 * Copyright (c) 2014 -- WPI Suite
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors: Nathan Hughes
 ******************************************************************************/

package edu.wpi.cs.wpisuitetng.modules.taskmanager.reports;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class TestReports {

	private ActivityGraph graph;
	private UserActivity activityTest;
	
	@Before
	public void setup() {
		graph = new ActivityGraph();
		activityTest = new UserActivity("Bob");
		graph.addNode(new UserActivity("Bob"));
		graph.addNode(new UserActivity("Fred"));
		graph.addNode(new UserActivity("George"));
	}
	
	@Test
	public void testConstructor() {
		assertNotNull(graph);
		assertNotNull(activityTest);
	}

	@Test
	public void testAddNode() {
		assertFalse(graph.addNode(new UserActivity("Bob")));
		assertTrue(graph.addNode(new UserActivity("Nick")));
	}
	
	@Test
	public void testAddEdge() {
		assertTrue(graph.addEdge(new UserActivity("Bob"), new UserActivity("George")));
		assertFalse(graph.addEdge(new UserActivity("Bob"), new UserActivity("George")));
	}
	
	@Test
	public void testAdjacencyMatrix() {
		graph.calcAdjacencyMatrix();
		System.out.println(graph.getAdjacencyMatrix());
		UserActivity toUpdate = new UserActivity("Bob");
		toUpdate.setImportance(1);
		UserActivity toUpdate2 = new UserActivity("George");
		
		toUpdate2.setImportance(1);
		graph.updateNode(toUpdate);
		graph.updateNode(toUpdate2);
		graph.addEdge(toUpdate, toUpdate2);
		graph.calcAdjacencyMatrix();
		System.out.println(graph.getAdjacencyMatrix());
		
	}
	
}

/*******************************************************************************
 * Copyright (c) 2014 -- WPI Suite
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors: SixAppeal
 ******************************************************************************/

package edu.wpi.cs.wpisuitetng.modules.taskmanager.model;

import static org.junit.Assert.*;

import java.util.Collection;
import java.util.LinkedList;

import org.junit.Before;
import org.junit.Test;

/**
 * Test cases for the StageList Class
 * @author krpeffer
 *
 */

public class TestStageList {
	
	LinkedList<Stage> stageList;
	
	/**
	 * set up the list for testing
	 */
	@Before
	public void setup(){
		this.stageList = new LinkedList<Stage>();
	}
	
	/**
	 * test that the constructor works properly
	 */
	@Test
	public void testConstructor(){
		StageList testList = new StageList();
		assertNotNull(testList);
	}
	
	/**
	 * tests that the constructor with params works properly
	 */
	@Test
	public void testParamdConstructor(){
		Collection<Stage> stageCollection = new LinkedList<Stage>();
		stageCollection.add(new Stage("Stage 1"));
		stageCollection.add(new Stage("Stage 2"));
		StageList testList = new StageList(stageCollection);
		assertEquals(testList.getLL(), stageCollection);
	}
	
	/**
	 * test the toString method for correct output
	 */
	@Test
	public void testToString(){
		StageList emptyListTest = new StageList();
		assertEquals(emptyListTest.toString(), "[]");
		Collection<Stage> stageCollection = new LinkedList<Stage>();
		stageCollection.add(new Stage("Stage 1"));
		stageCollection.add(new Stage("Stage 2"));
		StageList testList = new StageList(stageCollection);
		assertEquals(testList.toString(), "[{Stage 1}, {Stage 2}, ]");
	}
}

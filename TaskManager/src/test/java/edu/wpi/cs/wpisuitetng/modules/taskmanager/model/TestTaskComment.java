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

import java.text.SimpleDateFormat;

import org.junit.Before;
import org.junit.Test;

/**
 * Test cases for the Comment Class
 * @author krpeffer
 *
 */
public class TestTaskComment {
	
	Comment comment;
	
	/**
	 * Sets up a comment to test
	 */
	@Before
	public void setup(){
		this.comment = new Comment();
	}
	
	/**
	 * Tests the constructor
	 */
	@Test
	public void testConstructor(){
		Comment newComment = new Comment();
		newComment.setUser("User");
		newComment.setComment("This is a comment");
		assertNotNull(newComment);
		assertEquals(newComment.getComment(), "This is a comment");
		assertEquals(newComment.getUser(), "User");
	}
	
	/**
	 * Test to make sure comments are received correctly
	 */
	@Test
	public void testGetComment(){
		Comment newComment = new Comment();
		newComment.setComment("Test String!");
		assertEquals(newComment.getComment(), "Test String!");
	}
	
	/**
	 * Test to make sure users are received correctly
	 */
	@Test
	public void testGetUser(){
		Comment newComment = new Comment();
		newComment.setUser("User Person");
		assertEquals(newComment.getUser(), "User Person");
	}
	
	/**
	 * Test to make sure comments are set correctly
	 */
	@Test
	public void testSetComment(){
		Comment newComment = new Comment();
		newComment.setComment("Test String!");
		assertEquals(newComment.getComment(), "Test String!");
		newComment.setComment("New Test String");
		assertEquals(newComment.getComment(), "New Test String");
	}
	
	/**
	 * Test to make sure users are set correctly
	 */
	@Test
	public void testSetUser(){
		Comment newComment = new Comment();
		newComment.setUser("New User");
		assertEquals(newComment.getUser(), "New User");
		newComment.setUser("Newer User");
		assertEquals(newComment.getUser(), "Newer User");
	}
	
	/**
	 * test to make sure the toString method creates the correct String for output
	 */
	@Test
	public void testCommentToString(){
		Comment newComment = new Comment();
		String date = new SimpleDateFormat("h:mm a, MM/dd/yyyy").format(newComment.getDate());
		assertEquals(newComment.toString(), date + "> : ");
		newComment.setUser("New User");
		assertEquals(newComment.toString(), date + "> New User: ");
		newComment.setComment("This is a Comment!");
		assertEquals(newComment.toString(), date + "> New User: This is a Comment!");
	}
}

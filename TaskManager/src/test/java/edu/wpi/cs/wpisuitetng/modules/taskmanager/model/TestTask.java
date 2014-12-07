package edu.wpi.cs.wpisuitetng.modules.taskmanager.model;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

/**
 * Test Suite to test Task Class
 * 
 * @author nhhughes
 * @author srojas
 * @author jrhennessy
 * @author Thhughes
 * 
 *
 */
public class TestTask {

	Task task;

	/**
	 * set up creating a task
	 */
	@Before
	public void setup() {
		this.task = new Task();
		/*
		 * Default: 
		 * Title 		= Dummy
		 * Description	= Dummy
		 * Efforts 		= 1
		 * Date 		= new Date()
		 * Stage		= New
		 * 
		 */
	}
	
	/**
	 * Tests that the constructor works properly
	 */
	@Test
	public void testConstructor() {
		Task newTask = new Task();
		assertNotNull(newTask);
		assertEquals(newTask.getTitle(), "A New Task");
		assertEquals(newTask.getActivities().size(),0);
		assertEquals(newTask.getActualEffort(), new Integer(1));
		assertEquals(newTask.getAssignedTo().size(), 0);
		assertEquals(newTask.getDescription(), "A New Task");
		assertEquals(newTask.getDueDate(), new Date());
		assertEquals(newTask.getEstimatedEffort(), new Integer(1));
		assertEquals(newTask.getId(), 0);
		assertEquals(newTask.getStage(), new Stage("New"));

	}

	/**
	 * Placeholder for future tests
	 */
	@Test
	public void testSetActivities() {
		//TODO Write Comprehensive Tests once we start using activities
		assertEquals(1, 1); 
	}
	
	
	/**
	 * Function to test that the actual effort changes with the set method and is NOT negative 
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testSetActualEffort() {

		Task newTask = new Task();
		assertEquals(newTask.getActualEffort(), new Integer(1));
		newTask.setActualEffort(10);
		assertEquals(newTask.getActualEffort(), new Integer(10));
		newTask.setActualEffort(-50);
		assertEquals(newTask.getActualEffort(), new Integer(1));

	}
	
	/**
	 * Function to test that the estimated effort changes with the set method and is not negative
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testSetEstimatedEffort() {
		//TODO Write Comprehensive Tests once we start using actual effort

		Task newTask = new Task();
		assertEquals(newTask.getEstimatedEffort(), new Integer(1));
		newTask.setEstimatedEffort(10);
		assertEquals(newTask.getEstimatedEffort(), new Integer(10));
		newTask.setEstimatedEffort(-50);
		assertEquals(newTask.getEstimatedEffort(), new Integer(1));
	}
	
	@Test
	public void testArchived(){
		assertFalse(task.isArchived());
		
		task.archive();
		assertTrue(task.isArchived());
		
		task.unarchive();
		assertFalse(task.isArchived());
		
	}
	
	@Test
	public void testComments(){
		Comment testComment = new Comment("Troy", "Wrote this comment");
		List<Comment> CommentList = new LinkedList<Comment>();
		CommentList.add(testComment);
		
		task.setComments(CommentList);
		CommentList.add(new Comment("Troy", "Second Comment"));
		
		task.addComment("Troy", "Second Comment");
		
		assertEquals(task.getComments(), CommentList);
		
		
	}
	
	@Test
	public void testAssignedTo(){
		Task task2 = new Task();
		task2.setId(10);
		List<String> assignedTo = new ArrayList<String>();
		assignedTo.add("Troy");
		assignedTo.add("Paul");
		task2.setAssignedTo(assignedTo);
		assertEquals(task2.getAssignedTo(), assignedTo);
		
	}
	
	@Test
	public void testUpdateFrom(){
		Task task2 = new Task();
		List<String> assignedTo = new ArrayList<String>();
		assignedTo.add("Troy");
		assignedTo.add("Paul");
		task2.setAssignedTo(assignedTo);
		task.updateFrom(task2);
		

		assertEquals(task.getAssignedTo(), task2.getAssignedTo());
		assertEquals(task.getTitle(), task2.getTitle());
		assertEquals(task.getDescription(), task2.getDescription());
		assertEquals(task.getEstimatedEffort(), task2.getEstimatedEffort());
		assertEquals(task.getActualEffort(), task2.getActualEffort());
		assertEquals(task.getDueDate(), task2.getDueDate());
		
	}
	
	@Test
	public void testEqual_true(){
		Task task2 = new Task();
		List<String> assignedTo = new ArrayList<String>();
		assignedTo.add("Troy");
		assignedTo.add("Paul");
		task2.setAssignedTo(assignedTo);
		task2.addComment("Troy", "Second Comment");
		task.updateFrom(task2);
		task.setId(1);
		task2.setId(1);
		
		assertTrue(task.equals(task2));
		
		
	}
	public void testEqual_false(){
		Task task2 = new Task();
		List<String> assignedTo = new ArrayList<String>();
		assignedTo.add("Troy");
		assignedTo.add("Paul");
		task2.setAssignedTo(assignedTo);
		task2.addComment("Troy", "Second Comment");
		task.updateFrom(task2);
		task.setId(1);
		task2.setId(2);
		
		assertFalse(task.equals(task2));
		
		
	}
	
	
	
	
	
	
	
	
	
}

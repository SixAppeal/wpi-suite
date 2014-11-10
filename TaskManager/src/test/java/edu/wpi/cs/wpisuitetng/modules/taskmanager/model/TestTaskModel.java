/**
 * 
 */
package edu.wpi.cs.wpisuitetng.modules.taskmanager.model;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;


/**
 * Unit Tests for TaskModel
 * 
 * @author nhhughes
 *
 */
public class TestTaskModel {

	Task task1 = new Task("Task 1");
	Task task2 = new Task("Task 2");
	
	List<Task> tasks1 = new ArrayList<Task>();
	List<Task> tasks2 = new ArrayList<Task>();
	
	TaskModel model;
	@Before
	public void setup() {
		task1.id = 0;
		task2.id = 1;
		tasks1.add(task1);
		tasks2.add(task1);
		tasks2.add(task2);
		
		model = new TaskModel();
	}
	
	@Test
	public void testAddTask() {
		model.addTask(task1);
		assertEquals(model.getTasks(), tasks1);
		model.addTask(task2);
		assertEquals(model.getTasks(), tasks2);
	}
	
	@Test
	public void testChangeColumn() {
		model.addTask(task1);
		model.addTask(task2);
		model.changeColumn(0, 5);
		Task result = model.getElementAt(1);
		assertEquals(result.getColumn(), new Integer(5));
	}
	
	@Test
	public void testGetElementAt() {
		model.addTask(task1);
		model.addTask(task2);
		assertEquals(model.getElementAt(0), task2);
		assertEquals(model.getElementAt(1), task1);
	}
	
	@Test
	public void testGetNextID() {
		assertEquals(model.getNextID(), 0);
		model.addTask(task1);
		assertEquals(model.getNextID(), 1);
	}
	
	@Test
	public void testGetSize() {
		assertEquals(model.getSize(), 0);
		model.addTask(task1);
		assertEquals(model.getSize(), 1);
		model.addTask(task2);
		assertEquals(model.getSize(), 2);
	}
	
	@Test
	public void testGetTask() {
		model.addTask(task1);
		model.addTask(task2);
		Task result = model.getTask(0);
		assertEquals(task1, result);
	}
	
	@Test
	public void testGetTasksFromColumn() {
		model.addTask(task1);
		model.addTask(task2);
		List<Task> result = model.getTasksFromColumn(0);
		assertEquals(tasks2, result);
		model.changeColumn(0, 5);
		assertEquals(model.getTasksFromColumn(5).get(0),task1);
	}
	
	@Test
	public void getTasks() {
		model.addTask(task1);
		model.addTask(task2);
		assertEquals(model.getTasks(), tasks2);
	}
	
	
}

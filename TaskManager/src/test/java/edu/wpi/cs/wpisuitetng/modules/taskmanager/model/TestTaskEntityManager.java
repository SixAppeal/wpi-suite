/**
 * 
 */
package edu.wpi.cs.wpisuitetng.modules.taskmanager.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import edu.wpi.cs.wpisuitetng.Session;
import edu.wpi.cs.wpisuitetng.database.Data;
import edu.wpi.cs.wpisuitetng.exceptions.NotImplementedException;
import edu.wpi.cs.wpisuitetng.exceptions.WPISuiteException;
import edu.wpi.cs.wpisuitetng.modules.Model;
import edu.wpi.cs.wpisuitetng.modules.core.models.Project;

/**
 * 
 * Unit Tests for Task Entity Manager
 * 
 * @author nhhughes
 *
 */
public class TestTaskEntityManager {
	private Data mockDb = mock(Data.class); 
	private Session mockSession = mock(Session.class);
	private Project mockProject = mock(Project.class);

	private Task task1 = new Task("Task 1");
	private Task task2 = new Task("Task 2");

	List<Task> tasks = new ArrayList<Task>();

	private TaskEntityManager entityManager = new TaskEntityManager(mockDb);

	
	/**
	 * Checks to see if a task is in an array of tasks
	 * 
	 * @param container
	 * @param t
	 * @return
	 */
	public boolean contains(Task[] container, Task t) {
		for (Task q: container) {
			if (t == q) {
				return true;
			}
		}
		return false;
	}

	/**
	 * The setup method for all the tests
	 */
	@Before
	public void setup() {
		task1.id = 0;
		task2.id = 1;
		tasks.add(task1);
		tasks.add(task2);

		when(mockSession.getProject()).thenReturn(mockProject);
	}

	
	/**
	 * Make sure constructor works
	 */
	@Test
	public void testConstructor() {
		assertEquals(mockDb, entityManager.getDb());
	}

	/**
	 * Make sure database can create and store a task
	 * @throws WPISuiteException
	 */
	@Test
	public void testMakeEntity() throws WPISuiteException {
		ArgumentCaptor<Task> taskCaptor = ArgumentCaptor.forClass(Task.class);
		String taskJson = "{\"title\":\"Task 1\",\"description\":\"\",\"assignedTo\"" +
				":[],\"estimatedEffort\":-1,\"actualEffort\":-1," +
				"\"activities\":[],\"column\":0,\"id\":0,\"permissionMap\":{}}";

		when(mockDb.save(taskCaptor.capture(), eq(mockProject))).thenReturn(true);

		Task result = entityManager.makeEntity(mockSession, taskJson);

		assertEquals(task1, result); 
		assertEquals(task1, taskCaptor.getValue());

		verify(mockDb, times(1)).save(task1, mockProject);
	}

	/**
	 * Make an Invalid entity
	 * @throws WPISuiteException
	 */
	@Test(expected = WPISuiteException.class)
	public void testMakeEntity_Exception() throws WPISuiteException {
		ArgumentCaptor<Task> taskCaptor = ArgumentCaptor.forClass(Task.class);
		String taskJson = "{\"title\":\"Task 1\",\"description\":\"\",\"assignedTo\"" +
				":[],\"estimatedEffort\":-1,\"actualEffort\":-1," +
				"\"activities\":[],\"column\":0,\"permissionMap\":{}}";

		when(mockDb.save(taskCaptor.capture(), eq(mockProject))).thenReturn(false);

		Task result = entityManager.makeEntity(mockSession, taskJson);

		verify(mockDb, times(1)).save(task1, mockProject);

		assertNull(result);
	}

	/**
	 * Test what happens when a task isn't in the database
	 * @throws WPISuiteException
	 */
	@Test(expected = WPISuiteException.class)
	public void testGetEntity() throws WPISuiteException {
		entityManager.getEntity(mockSession, "2");
	}

	/**
	 * Test getting all the tasks from the database
	 * @throws WPISuiteException
	 */
	@Test
	public void testGetAll() throws WPISuiteException {
		List<Model> mockModelList = new ArrayList<Model>(tasks);

		when(mockDb.retrieveAll(any(Task.class), eq(mockProject))).thenReturn(mockModelList);

		Task[] result = entityManager.getAll(mockSession);

		assertEquals(2, result.length);
		assertTrue(contains(result, task1));
		assertTrue(contains(result, task2));
	}

	/**
	 * Test what happens when an update fails
	 * @throws WPISuiteException
	 */
	@Test(expected = WPISuiteException.class)
	public void testUpdate() throws WPISuiteException {
		String taskJson = "{\"title\":\"Task 1\",\"description\":\"\",\"assignedTo\"" +
				":[],\"estimatedEffort\":-1,\"actualEffort\":-1," +
				"\"activities\":[],\"column\":0,\"permissionMap\":{}}";
		entityManager.update(mockSession, taskJson);
	}

	/**
	 * Test saving a task in the database
	 * @throws WPISuiteException
	 */
	@Test
	public void testSave() throws WPISuiteException {
		entityManager.save(mockSession, task1);

		verify(mockDb, times(1)).save(task1);
	}


	/**
	 * Not Implemented yet
	 * @throws WPISuiteException
	 */
	@Test(expected = NotImplementedException.class)
	public void testDeleteEntity() throws WPISuiteException {
		entityManager.deleteEntity(mockSession, "2");
	}

	/**
	 * Not Implemented yet
	 * @throws WPISuiteException
	 */
	@Test(expected = NotImplementedException.class)
	public void testDeleteAll() throws WPISuiteException {
		entityManager.deleteAll(mockSession);
	}

	/**
	 * Test counting of database members
	 * @throws WPISuiteException
	 */
	@Test
	public void testCount() throws WPISuiteException {
		when(mockDb.retrieveAll(any(Task.class))).thenReturn(tasks);

		int count = entityManager.Count();

		assertEquals(2, count);

		verify(mockDb, times(1)).retrieveAll(any(Task.class));
	}

	/**
	 * Not implemented
	 * @throws WPISuiteException
	 */
	@Test(expected = NotImplementedException.class)
	public void testAdvancedGet() throws WPISuiteException {
		entityManager.advancedGet(mockSession, new String[] { "Test String 1", "Test String 2" });
	}

	/** 
	 * Not implemented
	 * @throws WPISuiteException
	 */
	@Test(expected = NotImplementedException.class)
	public void testAdvancedPut() throws WPISuiteException {
		entityManager.advancedPut(mockSession, new String[] { "Test Arg1", "Test Arg2" }, "Test Content");
	}

	/**
	 * Not implemented
	 * @throws WPISuiteException
	 */
	@Test(expected = NotImplementedException.class)
	public void testAdvancedPost() throws WPISuiteException {
		entityManager.advancedPost(mockSession, "Test String", "Test Content");
	}


}

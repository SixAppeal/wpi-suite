package edu.wpi.cs.wpisuitetng.modules.taskmanager.localcache;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

import org.junit.Before;
import org.junit.Test;

import edu.wpi.cs.wpisuitetng.Session;
import edu.wpi.cs.wpisuitetng.database.Data;
import edu.wpi.cs.wpisuitetng.exceptions.NotImplementedException;
import edu.wpi.cs.wpisuitetng.modules.core.models.Project;
import edu.wpi.cs.wpisuitetng.modules.core.models.User;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.TaskManager;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.model.Task;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.presenter.Gateway;

public class TestLocalCache {

	LocalCache localCache;
	private Data mockDb = mock(Data.class);
	private Session mockSession = mock(Session.class);
	private Project mockProject = mock(Project.class);
	private Gateway mockGateway = mock(Gateway.class);

	@Before
	public void setup() {
		localCache = new LocalCache(mockGateway);
	}

	@Test
	public void testConstructor() {
		assertNotNull(localCache);
	}

	@Test
	public void testSubscribe() {
		localCache.subscribe("task:TestClass:TestMethod");
		localCache.subscribe("archive:TestClass:TestMethod");
		localCache.subscribe("member:TestClass:TestMethod");
		localCache.subscribe("stage:TestClass:TestMethod");
		assertEquals(
				localCache.callbacks.get("task").contains(
						"TestClass:TestMethod"), true);
		assertEquals(
				localCache.callbacks.get("archive").contains(
						"TestClass:TestMethod"), true);
		assertEquals(
				localCache.callbacks.get("member").contains(
						"TestClass:TestMethod"), true);
		assertEquals(
				localCache.callbacks.get("stage").contains(
						"TestClass:TestMethod"), true);
	}

	@Test(expected = NotImplementedException.class)
	public void testRetrieveWithFilter() throws NotImplementedException {
		localCache.retrieve("", "");
	}

	@Test
	public void addVerified() {
		Task testTask = new Task();
		localCache.addVerified("task", testTask.toJson());
		localCache.addVerified("archive", testTask.toJson());
		assertEquals(localCache.tasks.get(0), testTask);
		assertEquals(localCache.archive.get(0), testTask);
		
	}

	@Test
	public void testOne() {
		assertEquals(0, 0);
	}

}

/**
 * 
 */
package edu.wpi.cs.wpisuitetng.modules.taskmanager.model;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import com.google.gson.Gson;

import edu.wpi.cs.wpisuitetng.Session;
import edu.wpi.cs.wpisuitetng.database.Data;
import edu.wpi.cs.wpisuitetng.exceptions.WPISuiteException;
import edu.wpi.cs.wpisuitetng.modules.core.models.Project;

/**
 * 
 * Unit Tests for Task Entity Manager
 * 
 * @author nathan
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
    
	@Before
	public void setup() {
		tasks.add(task1);
		tasks.add(task2);
		
        when(mockSession.getProject()).thenReturn(mockProject);
	}
	
	@Test
    public void testConstructor() {
        assertEquals(mockDb, entityManager.getDb());
    }
    
	@Test
    public void testMakeEntity() throws WPISuiteException {
        ArgumentCaptor<Task> taskCaptor = ArgumentCaptor.forClass(Task.class);
        String taskJson = "{\"title\":\"Task 1\",\"description\":\"\",\"assignedTo\"" +
        				  ":[],\"estimatedEffort\":-1,\"actualEffort\":-1," +
        				  "\"activities\":[],\"column\":0,\"permissionMap\":{}}";
       
        System.out.println(task1.toJson());
        when(mockDb.save(taskCaptor.capture(), eq(mockProject))).thenReturn(true);
        
        Task result = entityManager.makeEntity(mockSession, taskJson);
        assertEquals(task1, result);
        //assertEquals(task1, taskCaptor.getValue());
        
        //verify(mockDb, times(1)).save(task1, mockProject);
    }
   
	
}

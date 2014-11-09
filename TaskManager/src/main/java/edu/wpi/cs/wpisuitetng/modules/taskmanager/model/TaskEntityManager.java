/**
 * 
 */
package edu.wpi.cs.wpisuitetng.modules.taskmanager.model;

import java.util.List;

import edu.wpi.cs.wpisuitetng.Session;
import edu.wpi.cs.wpisuitetng.database.Data;
import edu.wpi.cs.wpisuitetng.exceptions.BadRequestException;
import edu.wpi.cs.wpisuitetng.exceptions.ConflictException;
import edu.wpi.cs.wpisuitetng.exceptions.NotFoundException;
import edu.wpi.cs.wpisuitetng.exceptions.NotImplementedException;
import edu.wpi.cs.wpisuitetng.exceptions.UnauthorizedException;
import edu.wpi.cs.wpisuitetng.exceptions.WPISuiteException;
import edu.wpi.cs.wpisuitetng.modules.EntityManager;
import edu.wpi.cs.wpisuitetng.modules.Model;
import edu.wpi.cs.wpisuitetng.modules.core.models.Role;
import edu.wpi.cs.wpisuitetng.modules.core.models.User;
//import edu.wpi.cs.wpisuitetng.modules.Taskmanager.models.Task;


/**
 * @author nathan
 * @author santiago
 * @author jill
 */
public class TaskEntityManager implements EntityManager<Task>{

	/** The database */
	Data db;
	
	/**
	 * Constructs the entity manager. This constructor is called by
	 * {@link edu.wpi.cs.wpisuitetng.ManagerLayer#ManagerLayer()}. To make sure
	 * this happens, be sure to place add this entity manager to the map in
	 * the ManagerLayer file.
	 * 
	 * @param db a reference to the persistent database
	 */
	public TaskEntityManager(Data db) {
		this.db = db; 
	}
	
	/**
	 * Saves a Task when it is received from a client
	 * 
	 * @see edu.wpi.cs.wpisuitetng.modules.EntityManager#makeEntity(edu.wpi.cs.wpisuitetng.Session, java.lang.String)
	 */
	@Override
	public Task makeEntity(Session s, String content) throws WPISuiteException {
		final Task newTask = Task.fromJson(content);
		if(!db.save(newTask, s.getProject())) {
			throw new WPISuiteException();
		}
		return newTask;
	}
	
	/**
	 * Retrieves a single Task from the database
	 * @param s the session
	 * @param id the id number of the Task to retrieve
	
	
	
	
	 * @return the Task matching the given id * @throws NotFoundException * @throws NotFoundException * @throws NotFoundException
	 * @see edu.wpi.cs.wpisuitetng.modules.EntityManager#getEntity(Session, String) */
	@Override
	public Task[] getEntity(Session s, String id) throws NotFoundException {
		final int intId = Integer.parseInt(id);
		if(intId < 1) {
			throw new NotFoundException();
		}
		Task[] Tasks = null;
		try {
			Tasks = db.retrieve(Task.class, "id", intId, s.getProject()).toArray(new Task[0]);
		} catch (WPISuiteException e) {
			e.printStackTrace();
		}
		if(Tasks.length < 1 || Tasks[0] == null) {
			throw new NotFoundException();
		}
		return Tasks;
	}

	/**
	 * Retrieves all Tasks from the database
	 * @param s the session
	
	
	
	 * @return array of all stored Tasks * @see edu.wpi.cs.wpisuitetng.modules.EntityManager#getAll(Session) * @see edu.wpi.cs.wpisuitetng.modules.EntityManager#getAll(Session) * @see edu.wpi.cs.wpisuitetng.modules.EntityManager#getAll(Session)
	 */
	@Override
	public Task[] getAll(Session s) {
		return db.retrieveAll(new Task(), s.getProject()).toArray(new Task[0]);
	}

	/**
	 * Saves a data model to the database
	 * @param s the session
	 * @param model the model to be saved
	 */
	@Override
	public void save(Session s, Task model) {
		db.save(model, s.getProject());
	}
	
	/**
	 * Ensures that a user is of the specified role
	 * @param session the session
	 * @param role the role being verified
	
	 * @throws WPISuiteException user isn't authorized for the given role */
	private void ensureRole(Session session, Role role) throws WPISuiteException {
		User user = (User) db.retrieve(User.class, "username", session.getUsername()).get(0);
		if(!user.getRole().equals(role)) {
			throw new UnauthorizedException();
		}
	}
	
	/**
	 * Deletes a Task from the database
	 * @param s the session
	 * @param id the id of the Task to delete
	
	
	
	public 
	 * @return true if the deletion was successful * @throws WPISuiteException * @throws WPISuiteException * @throws WPISuiteException
	 * @see edu.wpi.cs.wpisuitetng.modules.EntityManager#deleteEntity(Session, String) */
	@Override
	public boolean deleteEntity(Session s, String id) throws WPISuiteException {
		ensureRole(s, Role.ADMIN);
		return (db.delete(getEntity(s, id)[0]) != null) ? true : false;
	}
	
	/**
	 * Deletes all Tasks from the database
	 * @param s the session
	
	
	 * @throws WPISuiteException * @see edu.wpi.cs.wpisuitetng.modules.EntityManager#deleteAll(Session) * @see edu.wpi.cs.wpisuitetng.modules.EntityManager#deleteAll(Session)
	 */
	@Override
	public void deleteAll(Session s) throws WPISuiteException {
		ensureRole(s, Role.ADMIN);
		db.deleteAll(new Task(), s.getProject());
	}
	
	/**
	 * Returns the number of Tasks in the database
	
	
	
	
	 * @return number of Tasks stored * @throws WPISuiteException * @throws WPISuiteException * @throws WPISuiteException
	 * @see edu.wpi.cs.wpisuitetng.modules.EntityManager#Count() */
	@Override
	public int Count() throws WPISuiteException {
		return db.retrieveAll(new Task()).size();
	}

	/**
	 * Method update.
	 * @param session Session
	 * @param content String
	
	
	
	 * @return Task * @throws WPISuiteException * @see edu.wpi.cs.wpisuitetng.modules.EntityManager#update(Session, String) * @throws WPISuiteException
	 * @see edu.wpi.cs.wpisuitetng.modules.EntityManager#update(Session, String)
	 */
	@Override
	public Task update(Session session, String content) throws WPISuiteException {
		
		Task updatedTask = Task.fromJson(content);
		/*
		 * Because of the disconnected objects problem in db4o, we can't just save Tasks.
		 * We have to get the original defect from db4o, copy properties from updatedTask,
		 * then save the original Task again.
		 */
		List<Model> oldTasks = db.retrieve(Task.class, "id", updatedTask.getId(), session.getProject());
		if(oldTasks.size() < 1 || oldTasks.get(0) == null) {
			throw new BadRequestException("Task with ID does not exist.");
		}
				
		Task existingTask = (Task)oldTasks.get(0);		

		// copy values to old Task and fill in our changeset appropriately
		existingTask.copyFrom(updatedTask);
		
		if(!db.save(existingTask, session.getProject())) {
			throw new WPISuiteException();
		}
		
		return existingTask;
	}

	/**
	 * Method advancedGet.
	 * @param arg0 Session
	 * @param arg1 String[]
	
	
	
	 * @return String * @throws NotImplementedException * @see edu.wpi.cs.wpisuitetng.modules.EntityManager#advancedGet(Session, String[]) * @throws NotImplementedException
	 * @see edu.wpi.cs.wpisuitetng.modules.EntityManager#advancedGet(Session, String[])
	 */
	@Override
	public String advancedGet(Session arg0, String[] arg1) throws NotImplementedException {
		throw new NotImplementedException();
	}

	/**
	 * Method advancedPost.
	 * @param arg0 Session
	 * @param arg1 String
	 * @param arg2 String
	
	
	
	 * @return String * @throws NotImplementedException * @see edu.wpi.cs.wpisuitetng.modules.EntityManager#advancedPost(Session, String, String) * @throws NotImplementedException
	 * @see edu.wpi.cs.wpisuitetng.modules.EntityManager#advancedPost(Session, String, String)
	 */
	@Override
	public String advancedPost(Session arg0, String arg1, String arg2) throws NotImplementedException {
		throw new NotImplementedException();
	}

	/**
	 * Method advancedPut.
	 * @param arg0 Session
	 * @param arg1 String[]
	 * @param arg2 String
	
	
	
	 * @return String * @throws NotImplementedException * @see edu.wpi.cs.wpisuitetng.modules.EntityManager#advancedPut(Session, String[], String) * @throws NotImplementedException
	 * @see edu.wpi.cs.wpisuitetng.modules.EntityManager#advancedPut(Session, String[], String)
	 */
	@Override
	public String advancedPut(Session arg0, String[] arg1, String arg2) throws NotImplementedException {
		throw new NotImplementedException();
	}
	
}
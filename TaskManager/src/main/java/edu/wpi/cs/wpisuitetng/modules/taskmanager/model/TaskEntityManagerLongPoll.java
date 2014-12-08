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

package edu.wpi.cs.wpisuitetng.modules.taskmanager.model;


import java.util.List;
import java.util.concurrent.Semaphore;

import edu.wpi.cs.wpisuitetng.Session;
import edu.wpi.cs.wpisuitetng.database.Data;
import edu.wpi.cs.wpisuitetng.exceptions.BadRequestException;
import edu.wpi.cs.wpisuitetng.exceptions.NotFoundException;
import edu.wpi.cs.wpisuitetng.exceptions.NotImplementedException;
import edu.wpi.cs.wpisuitetng.exceptions.WPISuiteException;
import edu.wpi.cs.wpisuitetng.modules.EntityManager;
import edu.wpi.cs.wpisuitetng.modules.Model;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.model.Task;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.util.TaskUtil;

/**
 * Entity Manager for the Task Model.  This is responsible for storing and retrieving all data requests
 * from the client (i.e. Janeway). WPI Suite, when it receives a request, finds this entity manager and calls
 * the correct method.
 * 
 * @author nhhughes
 * @author srojas
 * @author jrhennessy
 * @author Thhughes
 * 
 */
public class TaskEntityManagerLongPoll implements EntityManager<Task>{

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
	public TaskEntityManagerLongPoll(Data db) {
		this.db = db; 
	}

	/**
	 * Gets the instance of the database that this EntityManager uses
	 * 
	 * @return Data db
	 */
	public Data getDb() {
		return this.db;
	}

	/**
	 * Saves a Task when it is received from a client
	 * 
	 * @see edu.wpi.cs.wpisuitetng.modules.EntityManager#makeEntity(edu.wpi.cs.wpisuitetng.Session, java.lang.String)
	 */
	@Override
	public Task makeEntity(Session s, String content) throws WPISuiteException {
		final Task newTask = TaskUtil.fromJson(content);
		newTask.setId(this.Count());
		if(!db.save(newTask, s.getProject())) {
			throw new WPISuiteException();
		}
		TaskPollTracker.getInstance().update();
		return newTask;
	}

	/**
	 * Retrieves a single Task from the database
	 * 
	 * @param s the session
	 * @param id the id number of the Task to retrieve
	 * @return the Task matching the given id * @throws NotFoundException * @throws NotFoundException * @throws NotFoundException
	 * @see edu.wpi.cs.wpisuitetng.modules.EntityManager#getEntity(Session, String) */
	@Override
	public Task[] getEntity(Session s, String id) throws NotFoundException {
		//should be not implemented eventually
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

	private Semaphore lock;
	
	public void fixLock() {
		lock.release();
	}
	
	/**
	 * Retrieves all Tasks from the database
	 * 
	 * @param s the session
	 * @return array of all stored Tasks * @see edu.wpi.cs.wpisuitetng.modules.EntityManager#getAll(Session) * @see edu.wpi.cs.wpisuitetng.modules.EntityManager#getAll(Session) * @see edu.wpi.cs.wpisuitetng.modules.EntityManager#getAll(Session)
	 */
	@Override
	public Task[] getAll(Session s) {
		
		TaskPollTracker.getInstance().register(s);
		
		//Semaphore lock = TaskPollTracker.getInstance().getLock();
		lock = new Semaphore(0, true);
		RequestTimeoutTracker timeout = new RequestTimeoutTracker(this);
		timeout.start();
		try {
			lock.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("Got Here!");
		//delete thread if still active
		return db.retrieveAll(new Task(), s.getProject()).toArray(new Task[0]);
	}

	/**
	 * Saves a data model to the database
	 * 
	 * @param s the session
	 * @param model the model to be saved
	 * @throws WPISuiteException 
	 */
	@Override
	public void save(Session s, Task model) throws WPISuiteException {
		db.save(model, s.getProject());
	}

	/**
	 * Deletes a Task from the database
	 * 
	 * @param s the session
	 * @param id the id of the Task to delete 
	 * @return true if the deletion was successful * @throws WPISuiteException * @throws WPISuiteException * @throws WPISuiteException
	 * @see edu.wpi.cs.wpisuitetng.modules.EntityManager#deleteEntity(Session, String) */
	@Override
	public boolean deleteEntity(Session s, String id) throws WPISuiteException {
		/*ensureRole(s, Role.ADMIN);
		return (db.delete(getEntity(s, id)[0]) != null) ? true : false;*/
		throw new NotImplementedException();
		//TODO Implement this
	}

	/**
	 * Deletes all Tasks from the database
	 * 
	 * @param s the session
	 * @throws WPISuiteException * @see edu.wpi.cs.wpisuitetng.modules.EntityManager#deleteAll(Session) * @see edu.wpi.cs.wpisuitetng.modules.EntityManager#deleteAll(Session)
	 */
	@Override
	public void deleteAll(Session s) throws WPISuiteException {
		/*ensureRole(s, Role.ADMIN);
		db.deleteAll(new Task(), s.getProject());*/
		throw new NotImplementedException();
		//TODO Implement this
	}

	/**
	 * Returns the number of Tasks in the database
	 * 
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
		Task updatedTask = TaskUtil.fromJson(content);

		//Gets old task, modifies it, and saves it again
		List<Model> oldTasks = db.retrieve(Task.class, "id", updatedTask.getId(), session.getProject());
		if(oldTasks.size() < 1 || oldTasks.get(0) == null) {
			throw new BadRequestException("Task with ID does not exist.");
		}

		Task existingTask = (Task)oldTasks.get(0);		

		// copy values to old Task and fill in our changeset appropriately
		existingTask.updateFrom(updatedTask);

		if(!db.save(existingTask, session.getProject())) {
			throw new WPISuiteException();
		}
		TaskPollTracker.getInstance().update();
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
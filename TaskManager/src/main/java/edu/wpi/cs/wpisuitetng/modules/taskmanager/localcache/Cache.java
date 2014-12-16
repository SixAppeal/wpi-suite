/*******************************************************************************
 * Copyright (c) 2014 -- WPI Suite
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors: Team Six-Appeal
 ******************************************************************************/
package edu.wpi.cs.wpisuitetng.modules.taskmanager.localcache;

import edu.wpi.cs.wpisuitetng.exceptions.NotImplementedException;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.model.StageList;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.model.Task;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.presenter.IPresenter;

/**
 * Interface to describe cache functionality 
 * 
 * @author nhhughes
 *
 */
public interface Cache extends IPresenter {

	/**
	 * Take a task and store it in the cache and database
	 * @param request type of object to store (i.e. task)
	 * @param taskToStore task to store
	 */
	public void store(String request, Task taskToStore);
	
	/**
	 * Take a task and update it in the cache and database
	 * @param request type of object to store (i.e. request)
	 * @param newTask task to store
	 */
	public void update(String request, Task newTask);
	
	/**
	 * Take a stage list and store it in the cache and database
	 * @param request type of object to store (i.e. stages)
	 * @param slToStore stage list to store
	 */
	public void store(String request, StageList slToStore);
	
	/**
	 * Take a stage list and update it in the cache and database
	 * @param request type of object to store (i.e. stages)
	 * @param newSL stage list to update
	 */
	public void update(String request, StageList newSL);
	
	/**
	 * Retrieve the cache's copy of the current state of the database for a type
	 * @param request the type of object to request (i.e. stages, task, archives, members)
	 * @return the copy of the database
	 */
	public Object[] retrieve(String request);
	
	/**
	 * Retrieve the cache's copy of the current state of the database for a type
	 * @param request the type of object to request (i.e. stages, task, archives, members)
	 * @return the copy of the database
	 * @throws NotImplementedException this isn't actually implemented yet
	 */
	public Object[] retrieve(String request, String filter) throws NotImplementedException;
	
	/**
	 * Clear all the contents of the cache for a specific type
	 * @param request type of object to clear (i.e. stages, task, archives, members)
	 */
	public void clearCache(String request);
	
	/**
	 * Subscribe to get notified when a request comes back successful
	 * @param request type of object to get notified about (i.e. stages, task, archives, members)
	 */
	public void subscribe(String request);

	/**
	 * Set the data for a specific object type
	 * @param request type of object (i.e. stages, task, archives...)
	 * @param data Json representation of the object array
	 */
	public void set(String request, String data);
	
	/**
	 * Update the cache with the database
	 * @param request type of object (i.e. stages, task, archives...)
	 */
	public void sync(String request);
	
	/**
	 * make a new copy of the stage list
	 */
	public void initStageList();

}



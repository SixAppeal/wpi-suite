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

package edu.wpi.cs.wpisuitetng.modules.taskmanager.localcache;

import java.util.ArrayList;
import java.util.List;

import edu.wpi.cs.wpisuitetng.exceptions.NotImplementedException;
import edu.wpi.cs.wpisuitetng.modules.core.models.User;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.model.StageList;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.model.Task;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.presenter.Gateway;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;

/**
 * This class is designed to replace our current implementation of the local cache and do two things:
 * 	1) move database verification after all the other CRUD operations
 *  2) interface with the server as to implement long pulling
 *  
 *  These modifications will mean that a view requesting a CRUD operation on the local cache will a 
 *  request in the form of object:verification callback
 *  The verification callback is used to revoke changes on the view that have already taken place in the case of a database problem
 *  These callbacks will and should be handled by the presenter first, as they will contain information on why the object was revoked.
 *  Reasons for revocation could include:
 *  	multiple user edit / conflicting options
 *  	unable to contact server
 *  	bad request (i.e. bug)
 *  	 
 * @author nhhughes
 *
 */
public class ThreadSafeLocalCache implements Cache {

	List<Task> tasks;
	List<Task> archives;
	List<User> members;
	StageList stages;
	Gateway gateway;

	/**
	 * Create a new instance of the cache
	 */
	public ThreadSafeLocalCache() {
		tasks = new ArrayList<Task>();
		archives = new ArrayList<Task>();
		members = new ArrayList<User>();
		stages = new StageList();
	}

	/**
	 * Set the gateway for this cache
	 */
	@Override
	public void setGateway(Gateway gateway) {
		this.gateway = gateway;
	}

	/**
	 * @see edu.wpi.cs.wpisuitetng.modules.taskmanager.localcache.ICache#store(java.lang.String,
	 *      java.lang.Object)
	 */
	@Override
	public void store(String request, Task taskToStore) {
		if (!(request.split(":")[0].equals("task") && request.split(":").length == 2)) {
			System.out.println("Bad Request!");
			//TODO get rid of print statement
			return;
		}
		final Request networkRequest = Network.getInstance().makeRequest(
				"taskmanager/task", HttpMethod.PUT);
		networkRequest.addObserver(new AddManager(this, request, gateway, request.split(":")[1]));
		networkRequest.setBody(taskToStore.toJson());
		networkRequest.send();
		this.tasks.add(taskToStore);
	}	

	/**
	 * @see edu.wpi.cs.wpisuitetng.modules.taskmanager.localcache.ICache#update(java.lang.String,
	 *      java.lang.Object, java.lang.Object)
	 */
	@Override
	public void update(String request, Task newTask) {
		//TODO add in updateVerified logic here	and index out of bounds protection
		final Request networkRequest = Network.getInstance().makeRequest(
				"taskmanager/task", HttpMethod.POST);
		networkRequest.setBody(newTask.toJson());
		networkRequest.addObserver(new UpdateManager(this, request, gateway, request.split(":")[1]));
		networkRequest.send();

	}

	/**
	 * @see edu.wpi.cs.wpisuitetng.modules.taskmanager.localcache.ICache#store(java.lang.String,
	 *      java.lang.Object)
	 */
	@Override
	public void store(String request, StageList slToStore) {
		//TODO add in array out of bounds protection
		if (request.equals("stages")) {
			this.stages = slToStore;
			final Request networkRequest = Network.getInstance().makeRequest(
					"taskmanager/stages", HttpMethod.PUT);
			networkRequest.addObserver(new AddManager(this, request, gateway, request.split(":")[1]));
			networkRequest.setBody(slToStore.toJson());
			networkRequest.send();
		}
		else {
			System.out.println("Invalid Request!");
			//TODO clean this up
		}
	}

	/**
	 * @see edu.wpi.cs.wpisuitetng.modules.taskmanager.localcache.ICache#update(java.lang.String,
	 *      java.lang.Object, java.lang.Object)
	 */
	@Override
	public void update(String request, StageList newSL) {
		//TODO add in array out of bounds protection
		if (request.equals("stages")) {
			System.out.println("The StageList is updating to " + newSL.toString());
			final Request networkRequest = Network.getInstance().makeRequest(
					"taskmanager/stages", HttpMethod.POST);
			networkRequest.addObserver(new UpdateManager(this, request, gateway, request.split(":")[1]));
			networkRequest.setBody(newSL.toJson());
			networkRequest.send();
		}
	}

	/**
	 * @see edu.wpi.cs.wpisuitetng.modules.taskmanager.localcache.ICache#retreive(java.lang.String)
	 */
	@Override
	public Object[] retrieve(String request) {
		if (request.equals("task")) {
			return tasks.toArray(new Task[0]);
		}
		if (request.equals("archive")) {
			return archives.toArray(new Task[0]);
		}
		if (request.equals("member")) {
			return members.toArray(new User[0]);
		}
		if (request.equals("stages")) {
			StageList[] to_return = new StageList[1];
			to_return[0] = stages;
			return to_return;
		}
		return null;
	}


	/**
	 * @throws NotImplementedException
	 * @see edu.wpi.cs.wpisuitetng.modules.taskmanager.localcache.ICache#retreive(java.lang.String,
	 *      java.lang.String)
	 */
	@Override
	public Object[] retrieve(String request, String filter)
			throws NotImplementedException {
		throw new NotImplementedException();
	}


	/**
	 * @see edu.wpi.cs.wpisuitetng.modules.taskmanager.localcache.ICache#clearCache(java.lang.String)
	 */
	@Override
	public void clearCache(String request) {
		if (request.equals("task")) {
			tasks = new ArrayList<Task>();
		}
		if (request.equals("archive")) {
			archives = new ArrayList<Task>();
		}
		if (request.equals("member")) {
			members = new ArrayList<User>();
		}
		if (request.equals("stages")) {
			stages = new StageList();
		}
	}


	/**
	 * @see edu.wpi.cs.wpisuitetng.modules.taskmanager.localcache.ICache#subscribe()
	 */
	@Override
	public void subscribe(String request) {
		try {
			throw new NotImplementedException();
		} catch (NotImplementedException e) {
			e.printStackTrace();
		}
	}


	/**
	 * @see edu.wpi.cs.wpisuitetng.modules.taskmanager.localcache.ICache#set(java.lang.String,
	 *      java.lang.String)
	 */
	@Override
	public void set(String request, String data) {
		try {
			throw new NotImplementedException();
		} catch (NotImplementedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @see edu.wpi.cs.wpisuitetng.modules.taskmanager.localcache.ICache#sync(java.lang.String)
	 */
	@Override
	public void sync(String request) {
		try {
			throw new NotImplementedException();
		} catch (NotImplementedException e) {
			e.printStackTrace();
		}

	}

	/**
	 * @see edu.wpi.cs.wpisuitetng.modules.taskmanager.localcache.ICache#addVerified(java.lang.String,
	 *      java.lang.String)
	 */
	@Override
	public void addVerified(String request, String data) {
		try {
			throw new NotImplementedException();
		} catch (NotImplementedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @see edu.wpi.cs.wpisuitetng.modules.taskmanager.localcache.ICache#updateVerified(java.lang.String,
	 *      java.lang.String, java.lang.Object)
	 */
	@Override
	public void updateVerified(String request, String data) {
		try {
			throw new NotImplementedException();
		} catch (NotImplementedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Makes inital stage list
	 */
	@Override
	public void initStageList() {
		// TODO Write a better implementation of this somewhere
	}

}

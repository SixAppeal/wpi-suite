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
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;

import edu.wpi.cs.wpisuitetng.exceptions.NotImplementedException;
import edu.wpi.cs.wpisuitetng.modules.AbstractModel;
import edu.wpi.cs.wpisuitetng.modules.core.models.User;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.model.Stage;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.model.StageList;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.model.Task;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.presenter.Gateway;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.presenter.IPresenter;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;

/**
 * This class is responsible for managing local copies of the database
 * information and for communicating directly with the server
 */
public class LocalCache implements Cache, IPresenter {

	List<Task> tasks;
	List<Task> archives;
	List<User> members;
	StageList stages;
	Gateway gateway;
	Map<String, List<String>> callbacks;

	/**
	 * Initializes the local cache with a lookup table and some cache data
	 * structures
	 */
	public LocalCache() {
		tasks = new ArrayList<Task>();
		archives = new ArrayList<Task>();
		members = new ArrayList<User>();
		stages = new StageList();
		callbacks = new HashMap<String, List<String>>();
		callbacks.put("task", new ArrayList<String>());
		callbacks.put("archive", new ArrayList<String>());
		callbacks.put("member", new ArrayList<String>());
		callbacks.put("stages", new ArrayList<String>());
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
		String topic = request.split(":")[0];
		String callback = request.split(":")[1] + ":" + request.split(":")[2];
		if (callbacks.containsKey(topic)) {
			List<String> currentCallbacks = callbacks.get(topic);
			currentCallbacks.add(callback);
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
			return stages.toArray(new Stage[0]);
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
	 * @see edu.wpi.cs.wpisuitetng.modules.taskmanager.localcache.ICache#store(java.lang.String,
	 *      java.lang.Object)
	 */
	@Override
	public void store(String request, Task taskToStore) {
		if (request.equals("task")) {
			final Request networkRequest = Network.getInstance().makeRequest(
					"taskmanager/task", HttpMethod.PUT);
			networkRequest.addObserver(new AddManager(this, request, gateway, callbacks.get("task")));
			networkRequest.setBody(taskToStore.toJson());
			networkRequest.send();
		}
		if (request.equals("archive")) {
			final Request networkRequest = Network.getInstance().makeRequest(
					"taskmanager/task", HttpMethod.PUT);
			networkRequest.addObserver(new AddManager(this, request, gateway, callbacks.get("task")));
			networkRequest.setBody(taskToStore.toJson());
			networkRequest.send();
		}
	}
	
	public void store(String request, StageList slToStore) {
		if (request.equals("stages")) {
			final Request networkRequest = Network.getInstance().makeRequest(
					"taskmanager/stages", HttpMethod.PUT);
			networkRequest.addObserver(new AddManager(this, request, gateway, callbacks.get("stages")));
			networkRequest.setBody(slToStore.toJson());
			networkRequest.send();
		}
	}

	/**
	 * @see edu.wpi.cs.wpisuitetng.modules.taskmanager.localcache.ICache#update(java.lang.String,
	 *      java.lang.Object, java.lang.Object)
	 */
	@Override
	public void update(String request, Task newTask) {
		if (request.equals("task")) {
			final Request networkRequest = Network.getInstance().makeRequest(
					"taskmanager/task", HttpMethod.POST);
			networkRequest.setBody(newTask.toJson());
			networkRequest.addObserver(new UpdateManager(this, request, gateway,
					callbacks.get("task")));
			networkRequest.send();
		}
		if (request.equals("archive")) {
			newTask.archive();
			final Request networkRequest = Network.getInstance().makeRequest(
					"taskmanager/task", HttpMethod.POST);
			networkRequest.setBody(newTask.toJson());
			networkRequest.addObserver(new UpdateManager(this, request, gateway,
					callbacks.get("archive")));
			networkRequest.send();
		}
	}
	
	public void update(String request, StageList newSL) {
		if (request.equals("stages")) {
			System.out.println("The StageList is updating to " + newSL.toString());
			final Request networkRequest = Network.getInstance().makeRequest(
					"taskmanager/stages", HttpMethod.POST);
			networkRequest.addObserver(new UpdateManager(this, request, gateway,
					callbacks.get("stages")));
			networkRequest.setBody(newSL.toJson());
			networkRequest.send();
		}
	}
	
	/**
	 * @see edu.wpi.cs.wpisuitetng.modules.taskmanager.localcache.ICache#sync(java.lang.String)
	 */
	@Override
	public void sync(String request) {
		if (request.equals("task")) {
			final Request networkRequest = Network.getInstance().makeRequest("taskmanager/task", HttpMethod.GET);
			networkRequest.addObserver(new SyncManager((Cache) this, "task", callbacks.get("task"), gateway));
			networkRequest.send();
		}
		if (request.equals("archive")) {
			final Request networkRequest = Network.getInstance().makeRequest(
					"taskmanager/task", HttpMethod.GET);
			networkRequest.addObserver(new SyncManager((Cache) this, "archive", callbacks.get("archive"), gateway));
			networkRequest.send();
		}
		if (request.equals("member")) {
			final Request networkRequest = Network.getInstance().makeRequest(
					"core/user", HttpMethod.GET);
			networkRequest.addObserver(new SyncManager((Cache) this, "member", callbacks.get("member"), gateway));
			networkRequest.send();
		}
		if (request.equals("stages")) {
			final Request networkRequest = Network.getInstance().makeRequest(
					"taskmanager/stages", HttpMethod.GET);
			networkRequest.addObserver(new SyncManager((Cache) this, request, callbacks.get("stages"), gateway));
			networkRequest.send();
		}
	}

	/**
	 * @see edu.wpi.cs.wpisuitetng.modules.taskmanager.localcache.ICache#addVerified(java.lang.String,
	 *      java.lang.String)
	 */
	@Override
	public void addVerified(String request, String updateValue) {
		if (request.equals("task")) {
			Task t = new Gson().fromJson(updateValue, Task.class);
			tasks.add(t);
		}
		if (request.equals("archive")) {
			Task t = new Gson().fromJson(updateValue, Task.class);
			archives.add(t);
		}
		if (request.equals("stages")) {
			stages = new Gson().fromJson(updateValue, StageList.class);
		}
	}

	/**
	 * @see edu.wpi.cs.wpisuitetng.modules.taskmanager.localcache.ICache#updateVerified(java.lang.String,
	 *      java.lang.String, java.lang.Object)
	 */
	@Override
	public void updateVerified(String request, String updateValue) {
		if (request.equals("task") || request.equals("archive")) {
			
			Task newValue = new Gson().fromJson(updateValue, Task.class);
			for (int i = 0; i < archives.size(); i++) {
				if (archives.get(i).getId() == newValue.getId()) {
					archives.remove(i);
					break;
				}
			}
			for (int i = 0; i < tasks.size(); i++) {
				if (tasks.get(i).getId() == newValue.getId()) {
					tasks.remove(i);
					break;
				}
			}
			if (newValue.isArchived()) {
				archives.add(newValue);
			} else {
				tasks.add(newValue);
			}
		}
		if (request.equals("stages")) {
			stages = new Gson().fromJson(updateValue, StageList.class);
		}

	}

	/**
	 * @see edu.wpi.cs.wpisuitetng.modules.taskmanager.localcache.ICache#set(java.lang.String,
	 *      java.lang.String)
	 */
	@Override
	public void set(String request, String updateValue) {
		if (request.equals("task")) {
			Task[] returned = new Gson().fromJson(updateValue, Task[].class);
			tasks = new ArrayList<Task>();
			for (Task t : returned) {
				if (!t.isArchived()) {
					tasks.add(t);
				}
			}
		}
		if (request.equals("archive")) {
			Task[] returned = new Gson().fromJson(updateValue, Task[].class);
			archives = new ArrayList<Task>();
			for (Task t : returned) {
				if (t.isArchived()) {
					archives.add(t);
				}
			}
		}
		if (request.equals("member")) {
			User[] returned = new Gson().fromJson(updateValue, User[].class);
			members = new ArrayList<User>(Arrays.asList(returned));
		}
		if (request.equals("stages")) {
			StageList[] returned = new Gson().fromJson(updateValue, StageList[].class);
			try {
				stages = returned[0];
			} catch (ArrayIndexOutOfBoundsException ex) {
				initStageList();
				//System.err.println(ex.getMessage());
			}
		}
	}
	
	@Override
	public void setGateway(Gateway gateway) {
		this.gateway = gateway;
	}

	@Override
	public void initStageList() {
		stages = new StageList();
		stages.add( new Stage("New") );
		stages.add( new Stage("Scheduled"));
		stages.add( new Stage("In Progress"));
		stages.add( new Stage("Completed"));
		this.store("stages", stages);
	}

}

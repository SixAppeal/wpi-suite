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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.JOptionPane;

import com.google.gson.Gson;

import edu.wpi.cs.wpisuitetng.exceptions.NotImplementedException;
import edu.wpi.cs.wpisuitetng.modules.core.models.User;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.Requirement;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.model.Stage;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.model.StageList;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.model.Task;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.presenter.Gateway;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.TrelloNetwork;
import edu.wpi.cs.wpisuitetng.network.configuration.NetworkConfiguration;
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
 * @author thhughes
 *
 */
public class ThreadSafeLocalCache implements Cache {

	List<Task> tasks;
	List<Task> archives;
	List<User> members;
	StageList stages;
	Requirement[] requirements;
	Gateway gateway;

	String secret_key = "";
	String application_key = "";
	String boardId = "";
	String token = "";
	
	/**
	 * Create a new instance of the cache
	 */
	public ThreadSafeLocalCache() {
		tasks = new ArrayList<Task>();
		archives = new ArrayList<Task>(); 
		members = new ArrayList<User>();
		stages = new StageList();

		TrelloNetwork networksetup = TrelloNetwork.getInstance(); 
		NetworkConfiguration config = new NetworkConfiguration("https://api.trello.com/1");
		networksetup.setDefaultNetworkConfiguration(config);

		requirements = new Requirement[0];

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
			return;
		}
		final Request networkRequest = Network.getInstance().makeRequest(
				"taskmanager/task2", HttpMethod.PUT);
		networkRequest.addObserver(new AddManager(this, request, gateway, request.split(":")[1]));
		networkRequest.setBody(taskToStore.toJson());
		networkRequest.send();
	}	

	/**
	 * Searches through the list of tasks to remove the task with the specified ID. 
	 * Necessary because of the way the task equals method is implemented
	 * 
	 * @param toRemove Task to get rid of
	 * @param tasks list of tasks to iterate through
	 */
	private void updateHelper(Task toRemove, List<Task> tasks) {
		for (int i = 0; i < tasks.size(); i++) {
			if (tasks.get(i).getId() == toRemove.getId()) {
				tasks.remove(i);
				break;
			}
		}
	}

	/**
	 * @see edu.wpi.cs.wpisuitetng.modules.taskmanager.localcache.ICache#update(java.lang.String,
	 *      java.lang.Object, java.lang.Object)
	 */
	@Override
	public void update(String request, Task newTask) {
		if (!((request.split(":")[0].equals("task") || request.split(":")[0].equals("archive")) && request.split(":").length == 2)) {
			System.out.println("Bad Request!");
			return;
		}
		if (archives.contains(newTask)) {
			int index = archives.indexOf(newTask);
			if (!archives.get(index).hasChanged(newTask)) {
				System.out.println("Trying to update task with no updates");
				return;
			}
		}
		if (tasks.contains(newTask)) {
			int index = tasks.indexOf(newTask);
			if (!tasks.get(index).hasChanged(newTask)) {
				System.out.println("Trying to update task with no updates");
				return;
			}
		}
		updateHelper(newTask, archives);
		updateHelper(newTask, tasks);
		if (newTask.isArchived()) {
			archives.add(newTask);
		} else {
			tasks.add(newTask);
		}
		final Request networkRequest = Network.getInstance().makeRequest(
				"taskmanager/task2", HttpMethod.POST);
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
		if (!(request.split(":").length == 2 && request.split(":")[0].equals("stages"))) {
			System.out.println("Bad Request");
		}
		this.stages = slToStore;
		final Request networkRequest = Network.getInstance().makeRequest(
				"taskmanager/stages", HttpMethod.PUT);
		networkRequest.addObserver(new AddManager(this, request, gateway, request.split(":")[1]));
		networkRequest.setBody(slToStore.toJson());
		networkRequest.send();
	}

	/**
	 * @see edu.wpi.cs.wpisuitetng.modules.taskmanager.localcache.ICache#update(java.lang.String,
	 *      java.lang.Object, java.lang.Object)
	 */
	@Override
	public void update(String request, StageList newSL) {
		if (!(request.split(":").length == 2 && request.split(":")[0].equals("stages"))) {
			System.out.println("Bad Request");
		}
		
		final Request networkRequest = Network.getInstance().makeRequest(
				"taskmanager/stages", HttpMethod.POST);
		networkRequest.addObserver(new UpdateManager(this, request, gateway, request.split(":")[1]));
		networkRequest.setBody(newSL.toJson());
		networkRequest.send();
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
		if (request.equals("requirements")) {
			return requirements;
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
	 * Takes in updates generated from a long pull request and updates the cache
	 * @param taskVal all the updates that have occured since the last sync
	 */
	public void updateTasks(String taskVal) {
		Task[] updatedTasks = new Gson().fromJson(taskVal, Task[].class);
		System.out.println("Tasks to update:");
		int count = 0;
		for (Task t : updatedTasks) {
			System.out.println(count + ") " + t);
			count++;
		}
		List<Task> updatedTaskList = Arrays.asList(updatedTasks);
		List<Task> unarchived = new ArrayList<Task>();
		List<Task> archived = new ArrayList<Task>();
		List<Task> used = new ArrayList<Task>();
		for (Task oldTask : this.tasks) {
			boolean changed = false;
			Task taskToInsert = oldTask; 
			for (Task newTask : updatedTaskList) {
				if (newTask.equals(oldTask)) {
					if (newTask.isArchived()) {
						taskToInsert = null;
					}
					else {
						changed = true;
						taskToInsert = newTask;
					}
					break;
				}
			}
			if (taskToInsert != null) {
				unarchived.add(taskToInsert);
				if (changed) {
					used.add(taskToInsert);
				}
			}
		}
		for (Task oldTask : this.archives) {
			boolean changed = false;
			Task taskToInsert = oldTask; 
			for (Task newTask : updatedTasks) {
				if (newTask.equals(oldTask)) {
					if (newTask.isArchived()) {
						taskToInsert = null;
					}
					else {
						taskToInsert = newTask;
						changed = true;
					}
					break;
				}
			}
			if (taskToInsert != null) {
				archived.add(taskToInsert);
				if (changed) {
					used.add(taskToInsert);
				}
			}
		}
		for (Task newTask : updatedTaskList) {
			if (!used.contains(newTask)) {
				if (newTask.isArchived()) {
					archived.add(newTask);
				}
				else {
					unarchived.add(newTask);
				}
			}
		}
		this.archives = archived;
		this.tasks = unarchived;
		this.gateway.toPresenter("TaskPresenter", "updateTasks");
		this.gateway.toPresenter("TaskPresenter", "updateSearch");
	}

	/**
	 * Update the members in the cache and reflow view accordingly
	 * @param userVal Json string of users
	 */
	public void updateMembers(String userVal) {
		User[] users = new Gson().fromJson(userVal, User[].class);
		this.members = Arrays.asList(users);
		this.gateway.toPresenter("TaskPresenter", "notifyMemberHandler");
	}

	/**
	 * Update the stages in the cache and reflow view accordingly
	 * @param stageVal Json string of stages
	 */
	public void updateStages(String stageVal) {
		StageList[] stages = new Gson().fromJson(stageVal, StageList[].class);
		this.stages = stages[0];
		this.gateway.toPresenter("TaskPresenter", "setStages");

	}
	
	/**
	 * Update the requirements in the cache and reflow accordingly
	 * @param reqVal Json string of requirements
	 */
	public void updateReqs(String reqVal) {
		Requirement[] reqs = new Gson().fromJson(reqVal, Requirement[].class);
		this.requirements = reqs;
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
		if (request.equals("tasks")) {
			ThreadSafeSyncObserver syncer = new ThreadSafeSyncObserver(this.gateway);
			final Request networkRequest = Network.getInstance().makeRequest("Advanced/taskmanager/task2", HttpMethod.GET);
			networkRequest.addObserver(syncer);
			networkRequest.setReadTimeout(70000);
			networkRequest.send();
		}
		if (request.equals("member")) {
			ThreadSafeSyncObserver syncer = new ThreadSafeSyncObserver(this.gateway);
			final Request networkRequest = Network.getInstance().makeRequest("core/user", HttpMethod.GET);
			networkRequest.addObserver(syncer);
			networkRequest.send();
		}
		if (request.equals("stages")) {
			ThreadSafeSyncObserver syncer = new ThreadSafeSyncObserver(this.gateway);
			final Request networkRequest = Network.getInstance().makeRequest("taskmanager/stages", HttpMethod.GET);
			networkRequest.addObserver(syncer);
			networkRequest.send();
		}
		if (request.equals("requirement")) {
			ThreadSafeSyncObserver syncer = new ThreadSafeSyncObserver(this.gateway);
			final Request networkRequest = Network.getInstance().makeRequest("requirementmanager/requirement", HttpMethod.GET);
			networkRequest.addObserver(syncer);
			networkRequest.send();
		}
		if (request.equals("auth")) {

			TrelloNetwork networksetup = TrelloNetwork.getInstance(); 
			NetworkConfiguration config = new NetworkConfiguration("https://trello.com/1/OAuthGetRequestToken?key=" + application_key + "&secret=" + secret_key);
			final Request networkRequest2 = TrelloNetwork.getInstance().makeRequest("", HttpMethod.GET);
			networkRequest2.addObserver(new TrelloManager(this));
			networkRequest2.send();
//          Oauth Reqest Sequence   					
//			https://trello.com/1/OAuthGetRequestToken
//			https://trello.com/1/OAuthAuthorizeToken
//			https://trello.com/1/OAuthGetAccessToken
		}
		if (request.equals("trello")) {
			final Request networkRequest2 = TrelloNetwork.getInstance().makeRequest("board/" + boardId + "/cards?key=" + application_key + "&token=" + token + "&fields=name", HttpMethod.GET);
			networkRequest2.addObserver(new TrelloManager(this));
			networkRequest2.send();
		}
	}

	/**
	 * Makes inital stage list
	 */
	@Override
	public void initStageList() {
		stages = new StageList();
		stages.add( new Stage("New") );
		stages.add( new Stage("Scheduled"));
		stages.add( new Stage("In Progress"));
		stages.add( new Stage("Completed"));
		this.store("stages:testing", stages);
	}

	/**
	 * Update name of stage in stage list
	 * @param oldName previous stage name
	 * @param newName new stage name
	 */
	public void renameStage(String oldName, String newName) {
		for (Task t : tasks) {
			if (t.getStage().toString().equals(oldName)) {
				t.setStage(new Stage(newName));
				update("task", t );
			}
		}
	}
	
	/**
	 * Archives all the tasks belonging to a stage. This should be called
	 * before deleting a stage.
	 * @param stage The stage with tasks to archive
	 */
	public void archiveTasksForStage(Stage stage) {
		boolean bInit = true;
		for (Task task : this.tasks) {
			if (task.getStage().equals(stage)) {
				task.setArchived(true);
				update("archive:testing", task);
			}
		}
	}
}


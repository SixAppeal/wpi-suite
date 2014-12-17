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

import com.google.gson.Gson;

import edu.wpi.cs.wpisuitetng.modules.taskmanager.model.Task;
import edu.wpi.cs.wpisuitetng.network.RequestObserver;
import edu.wpi.cs.wpisuitetng.network.models.IRequest;

/**
 * Trello Manager class
 * @author wavanrensselaer
 *
 */
public class TrelloManager implements RequestObserver {

	ThreadSafeLocalCache cache;
	
	/**
	 * Default constructor
	 * @param cache
	 */
	public TrelloManager(ThreadSafeLocalCache cache) {
		this.cache = cache;
	}
	
	/**
	 * A task for the Trello Manager
	 */
	private class TrelloTask {
		String name;
		String id;
		
		public TrelloTask() {
			name = "";
			id = "";
		}
		
		/**
		 * getter for Name
		 * @return name
		 */
		public String getTitle() {
			return this.name;
		}
		
		/**
		 * getter for ID
		 * @return id
		 */
		public String getId() {
			return this.id;
		}
	}
	
	@Override
	public void responseSuccess(IRequest iReq) {
		System.out.println(iReq.getResponse().getBody());
		TrelloTask[] tasks = new Gson().fromJson(iReq.getResponse().getBody(), TrelloTask[].class);
		for (TrelloTask t: tasks) {
			System.out.println(t.getTitle() + " " + t.getId());
		}
		Task[] currentTasks = (Task[])cache.retrieve("task");
		boolean not_found = true;
		for (TrelloTask tt : tasks) {
			for (Task t : currentTasks) {
				if (tt.getTitle().equals(t.getTitle())) {
					not_found = false;
					break;
				}
			}
			if (not_found) {
				System.out.println("Need to create a new task");
				Task toCreate = new Task();
				toCreate.setTitle(tt.getTitle());
				cache.store("task", toCreate);
			}
		}
	}

	@Override
	public void responseError(IRequest iReq) {
		System.out.println("Didn't Work!");
	}

	@Override
	public void fail(IRequest iReq, Exception exception) {
		System.out.println("Didn't Work for another reason!");
		exception.printStackTrace();
	}

	
}
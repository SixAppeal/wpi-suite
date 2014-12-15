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

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Semaphore;


/**
 * Task Poll tracker keeps track of the all clients that need to be notified via long pulling
 * @author nhhughes
 * @author srojas
 */
public class TaskPollTracker {

	private static TaskPollTracker instance;
	private List<Thread> toBeNotified;
	private Semaphore notificationLock;
	private Task task;
	
	/**
	 * Singleton constructor
	 */
	private TaskPollTracker() {
		toBeNotified = new LinkedList<Thread>();
		notificationLock = new Semaphore(0, true);
		this.task = new Task();
	}
	
	/**
	 * Get instance of the singleton
	 * @return the current instance of this class
	 */
	public static TaskPollTracker getInstance() {
		if (instance == null) {
			instance = new TaskPollTracker();
		}
		return instance;
	}
	
	/**
	 * Register a client with the poll tracker
	 * @param session client being registered
	 */
	public void register(Thread session) {
		toBeNotified.add(session);
	}
	
	/**
	 * Remove a client from the poll tracker
	 * @param session client being removed
	 */
	public void remove(Thread session) {
		toBeNotified.remove(session);
	}
	
	/**
	 * Get the semaphore that controls the update critical region
	 * @return the semaphore that controls the critical region
	 */
	public Semaphore getLock() {
		return notificationLock;
	}
	
	/**
	 * Get task that is being updated
	 * @return task to return to the client
	 */
	public Task getTask(){
		return this.task;
	}
	
	/**
	 * Notify clients of an update to the databse
	 * @param task task that was an update
	 */
	public void update(Task task) {
		this.task = task;
		System.out.println("Got Here! " + toBeNotified.size());
		System.out.flush();
		for (Thread entityToNotify : toBeNotified) {
			entityToNotify.interrupt();
		}
		int notifiesPending = toBeNotified.size();
		toBeNotified.clear();
		this.notificationLock.release(notifiesPending);
		System.out.println("ToBeNotified has been cleared! ");
	}
	
}

/**
 * 
 */

package edu.wpi.cs.wpisuitetng.modules.taskmanager.model;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Semaphore;


/**
 * @author nhhughes
 * @author srojas
 */

public class TaskPollTracker {

	private static TaskPollTracker instance;
	private List<Thread> toBeNotified;
	private Semaphore notificationLock;
	private Task task;
	
	private TaskPollTracker() {
		toBeNotified = new LinkedList<Thread>();
		notificationLock = new Semaphore(0, true);
		this.task = new Task();
	}
	
	public static TaskPollTracker getInstance() {
		if (instance == null) {
			instance = new TaskPollTracker();
		}
		return instance;
	}
	
	public void register(Thread session) {
		toBeNotified.add(session);
	}
	
	public void remove(Thread session) {
		toBeNotified.remove(session);
	}
	
	public Semaphore getLock() {
		return notificationLock;
	}
	public Task getTask(){
		return this.task;
	}
	
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

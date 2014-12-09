package edu.wpi.cs.wpisuitetng.modules.taskmanager.model;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Semaphore;


public class TaskPollTracker {

	private static TaskPollTracker instance;
	private List<TaskEntityManagerLongPoll> toBeNotified;
	private Semaphore notificationLock;
	
	private TaskPollTracker() {
		toBeNotified = new LinkedList<TaskEntityManagerLongPoll>();
		notificationLock = new Semaphore(-1, true);
	}
	
	public static TaskPollTracker getInstance() {
		if (instance == null) {
			instance = new TaskPollTracker();
		}
		return instance;
	}
	
	public void register(TaskEntityManagerLongPoll session) {
		toBeNotified.add(session);
	}
	
	public Semaphore getLock() {
		return notificationLock;
	}
	
	public void update() {
		System.out.println("Got Here! " + toBeNotified.size());
		for (TaskEntityManagerLongPoll entityToNotify : toBeNotified) {
			entityToNotify.lock.release();
		}
		toBeNotified.clear();
	}
	
}

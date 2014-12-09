package edu.wpi.cs.wpisuitetng.modules.taskmanager.model;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Semaphore;

import edu.wpi.cs.wpisuitetng.Session;

public class TaskPollTracker {

	private static TaskPollTracker instance;
	private List<Session> toBeNotified;
	private Semaphore notificationLock;
	
	private TaskPollTracker() {
		toBeNotified = new LinkedList<Session>();
		notificationLock = new Semaphore(-1, true);
	}
	
	public static TaskPollTracker getInstance() {
		if (instance == null) {
			instance = new TaskPollTracker();
		}
		return instance;
	}
	
	public void register(Session session) {
		toBeNotified.add(session);
	}
	
	public Semaphore getLock() {
		return notificationLock;
	}
	
	public void update() {
		notificationLock.release(toBeNotified.size());
		toBeNotified.clear();
	}
	
}
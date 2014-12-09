package edu.wpi.cs.wpisuitetng.modules.taskmanager.model;


public class RequestTimeoutTracker extends Thread {

	private TaskEntityManagerLongPoll entityManager;
	private Boolean timedOut;
	
	public RequestTimeoutTracker() {
		
	}
	
	public RequestTimeoutTracker(TaskEntityManagerLongPoll entityManager) {
		this.entityManager = entityManager;
		this.timedOut = false;
	}
	
	public Boolean isDone() {
		return this.timedOut;
	}
	
	public void run() {
		try {
			Thread.sleep(7000);
		} catch (InterruptedException e) {
			System.out.println("Good Stuff Happened");
			e.printStackTrace();
		}
		timedOut = true;
		this.entityManager.lock.release();
	}
	
}

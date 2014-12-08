package edu.wpi.cs.wpisuitetng.modules.taskmanager.model;


public class RequestTimeoutTracker extends Thread {

	private TaskEntityManagerLongPoll entityManager;
	
	public RequestTimeoutTracker() {
		
	}
	
	public RequestTimeoutTracker(TaskEntityManagerLongPoll entityManager) {
		this.entityManager = entityManager;
	}
	
	
	
	public void run() {
		System.out.println("doing stuff");
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			System.out.println("Good Stuff Happened");
			e.printStackTrace();
		}
		//TaskPollTracker.getInstance().update();
		System.out.println("finished sleeping");
		this.entityManager.fixLock();
	}
	
}

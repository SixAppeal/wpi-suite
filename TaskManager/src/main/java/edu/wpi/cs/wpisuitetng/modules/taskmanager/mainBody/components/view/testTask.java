package edu.wpi.cs.wpisuitetng.modules.taskmanager.mainBody.components.view;

public class testTask {
	String name = new String();
	String data = new String();
	
	public testTask(String name, String data){
		this.name = name;
		this.data = data;
	}
	
	public String getName(){
		return this.name;
	}
	
	
}

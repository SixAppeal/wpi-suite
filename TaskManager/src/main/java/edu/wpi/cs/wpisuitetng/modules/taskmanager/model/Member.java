/**
 * 
 */
package edu.wpi.cs.wpisuitetng.modules.taskmanager.model;

/**
 * @author nhhughes
 * @author srojas
 * @author jrhennessy
 */
public class Member {

	String name;
	/**
	 * Constructor for the member class
	 * @param name username or full name of the member
	 */
	Member (String name){
		this.name = name;
	}
	
	
	
	
	//Getters and Setters
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	
}

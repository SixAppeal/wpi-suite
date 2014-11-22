package edu.wpi.cs.wpisuitetng.modules.taskmanager.model;

import edu.wpi.cs.wpisuitetng.modules.AbstractModel;

/**
 * Basic stage model
 * 
 * @author wavanrensselaer
 */
public class Stage extends AbstractModel {
	String name;
	
	/**
	 * Constructs a stage with no name
	 */
	public Stage() {
		this("");
	}
	
	/**
	 * Constructs a stage with a name
	 * @param name The name of the stage
	 */
	public Stage(String name) {
		this.name = name;
	}

	@Override
	public void save() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String toJson() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean identify(Object o) {
		// TODO Auto-generated method stub
		return null;
	}

}

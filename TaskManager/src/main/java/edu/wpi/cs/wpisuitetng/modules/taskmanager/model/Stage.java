package edu.wpi.cs.wpisuitetng.modules.taskmanager.model;

import com.google.gson.Gson;

import edu.wpi.cs.wpisuitetng.modules.AbstractModel;

/**
 * Basic stage model
 * 
 * @author wavanrensselaer
 */
public class Stage extends AbstractModel {
	private String name;
	
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
	
	/**
	 * Sets the name of the stage
	 * @param name A name
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * Gets the name of the stage
	 * @return The name of the stage
	 */
	public String getName() {
		return this.name;
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
		return new Gson().toJson(this, Task.class);
	}

	@Override
	public Boolean identify(Object o) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean equals(Object o) {
		return o instanceof Stage
			&& this.name.equals(((Stage) o).getName());
	}
	
	@Override
	public int hashCode() {
		return this.name.hashCode();
	}
	
	@Override
	public String toString() {
		return this.name;
	}
}

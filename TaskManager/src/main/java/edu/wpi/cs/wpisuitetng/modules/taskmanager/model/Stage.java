/*******************************************************************************
 * Copyright (c) 2014 -- WPI Suite
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors: Team Six-Appeal
 ******************************************************************************/
package edu.wpi.cs.wpisuitetng.modules.taskmanager.model;


/**
 * Basic stage model
 * 
 * @author wavanrensselaer
 */
public class Stage {
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
	public String toString() {
		return this.name;
	}

	@Override
	public boolean equals(Object o) {
		if (this==o) {
			return true;
		}
		if (!(o instanceof Stage)) {
			return false;
		}
		
		return o instanceof Stage
			&& this.name.equals(((Stage) o).getName());
	}
	
	@Override
	public int hashCode() {
		return this.name.hashCode();
	}
}

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
package edu.wpi.cs.wpisuitetng.modules.taskmanager.view.components;

import javax.swing.JPanel;

/**
 * Abstract form element to standardize what an element should do
 * @author wavanrensselaer
 */
public abstract class FormElement extends JPanel {
	private static final long serialVersionUID = 661939133654711721L;

	/**
	 * This should check if all the input in the form element is valid and
	 * apply appropriate style to the fields. For instance, if a field is
	 * invalid, then the border might turn red.
	 * @return True if the input is valid and false otherwise.
	 */
	public boolean validateInput() {
		return true;
	}
	
	/**
	 * This should check if all the fields in the form element are valid and
	 * nothing else. Maybe you want to check if the fields are valid without
	 * applying new styles.
	 * @return True if all the fields are valid and false otherwise.
	 */
	public boolean hasValidInput() {
		return true;
	}
}

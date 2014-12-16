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

import java.awt.GridLayout;

import javax.swing.JButton;

/**
 * A panel that contains a group of buttons
 * @author wavanrensselaer
 */
public class ButtonGroup extends FormElement {
	private static final long serialVersionUID = -156399870170357184L;
	
	JButton[] buttons;
	
	/**
	 * Creates a button group
	 * @param buttons The buttons in the group
	 */
	public ButtonGroup(JButton... buttons) {
		this.buttons = buttons;
		
		this.setOpaque(false);
		this.setLayout(new GridLayout(1, buttons.length, 10, 0));
		
		for (int i = 0; i < this.buttons.length; i++) {
			this.add(this.buttons[i]);
		}
	}
}

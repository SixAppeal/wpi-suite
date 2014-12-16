/*******************************************************************************
 * Copyright (c) 2014 -- WPI Suite
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors: Nathan Hughes
 ******************************************************************************/

package edu.wpi.cs.wpisuitetng.modules.taskmanager.view.components;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JPanel;

/**
 * Deals with the vertical alignment of buttons
 * @author nhhughes
 *
 */
public class MemberButtonGroup extends FormElement {
	private static final long serialVersionUID = -156399870170357184L;
	
	JButton[] buttons;
	
	/**
	 * Creates a button group
	 * @param buttons The buttons in the group
	 */
	public MemberButtonGroup(JButton... buttons) {
		this.buttons = buttons;
		this.setLayout(new GridBagLayout());
		
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.anchor = GridBagConstraints.PAGE_START;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.insets = new Insets(60, 0, 0, 0);
		gbc.weightx = 1.0;
		gbc.gridx = 0;
		
		this.setOpaque(false);
		//this.setLayout(new GridLayout(1, buttons.length, 10, 0));
		
		gbc.gridy = 0;
		this.add(buttons[0], gbc);
		gbc.insets = new Insets(0, 0, 0, 0);
		gbc.gridy = 1;
		this.add(buttons[1], gbc);
		gbc.gridy = 2;
		gbc.weighty = 1.0;
		JPanel glue = new JPanel();
		glue.setVisible(false);
		this.add(glue, gbc);
	}
}

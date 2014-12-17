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
package edu.wpi.cs.wpisuitetng.modules.taskmanager.view.sidebar.histNcom;

import edu.wpi.cs.wpisuitetng.modules.taskmanager.view.components.FormElement;

import javax.swing.JScrollPane;

import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.util.List;

import javax.swing.ScrollPaneConstants;

/**
 * A form display class to handle scroll panes for tasks and comments
 * 
 * @author rwang3
 *
 */
public class FormDisplay extends FormElement {
	
	private static final long serialVersionUID = 1L;
	List<FormElement> elements;
	JScrollPane scrollPane;

	/**
	 * Create the panel.
	 */
	public FormDisplay() {
		
		this.setLayout(new GridBagLayout());
		
		this.scrollPane = new JScrollPane();
		this.scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		this.scrollPane.setLayout(new GridBagLayout());
		this.scrollPane.setMinimumSize(new Dimension(300, 0));
		this.scrollPane.setMaximumSize(new Dimension(300, Integer.MAX_VALUE));
		
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.fill = GridBagConstraints.HORIZONTAL;
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 0;
		this.add(scrollPane, gbc_scrollPane);

	}

}

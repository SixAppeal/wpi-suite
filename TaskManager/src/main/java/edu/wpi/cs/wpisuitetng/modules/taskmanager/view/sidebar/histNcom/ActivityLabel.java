/*******************************************************************************
 * Copyright (c) 2014 -- WPI Suite
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors: Troy Hughes
 ******************************************************************************/

package edu.wpi.cs.wpisuitetng.modules.taskmanager.view.sidebar.histNcom;

import javax.swing.JTextArea;

import java.awt.Dimension;
import java.awt.SystemColor;
import java.awt.Color;
import javax.swing.border.MatteBorder;

public class ActivityLabel extends JTextArea {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7750945195661034198L;

	
	/**
	 * Create the label.
	 */
	public ActivityLabel(String text) {
		
		this.setText(text);
		setPreferredSize(new Dimension(300, 40));
		setCaretColor(SystemColor.activeCaption);
		setSelectedTextColor(Color.DARK_GRAY);
		setEditable(false);
		setBorder(new MatteBorder(0, 0, 1, 0, SystemColor.activeCaption));
		setLineWrap(true);
		setWrapStyleWord(true);
		//setMaximumSize(new Dimension(280, 40));

	}

}
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

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

/**
 * A panel that handles the layout of form elements.
 * @author wavanrensselaer
 */
public class Form extends FormElement {
	private static final long serialVersionUID = -395511593512770018L;

	protected FormElement[] fields;
	
	/**
	 * Creates a form that aligns fields vertically.
	 * @param fields Either <code>FormField</code> or <code>FormGroup</code>.
	 */
	public Form(FormElement... fields) {
		this.fields = fields;
		this.setOpaque(false);
		this.setLayout(new GridBagLayout());
		
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.anchor = GridBagConstraints.PAGE_START;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.insets = new Insets(0, 0, 10, 0);
		gbc.weightx = 1.0;
		gbc.gridx = 0;
		
		for (int i = 0; i < fields.length; i++) {
			if (i == fields.length - 1) {
				gbc.insets.bottom = 0;
				gbc.weighty = 1.0;
			}
			gbc.gridy = i;
			this.add(fields[i], gbc);
			gbc.weighty = 0.0;
			gbc.weightx = 1.0;
		}
	}
	
	/**
	 * Gets the fields contained in this form
	 * @return The fields in this form
	 */
	public FormElement[] getFields() {
		return this.fields;
	}
	
	@Override
	public boolean hasValidInput() {
		for (FormElement field : this.fields) {
			if (!field.hasValidInput()) {
				return false;
			}
		}
		return true;
	}
	
	@Override
	public boolean validateInput() {
		boolean isValid = true;
		for (FormElement field : this.fields) {
			if (!field.validateInput()) {
				isValid = false;
			}
		}
		return isValid;
	}
}

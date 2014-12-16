/*******************************************************************************
 * Copyright (c) 2014 -- WPI Suite
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors: William Van Rensselaer
 ******************************************************************************/
package edu.wpi.cs.wpisuitetng.modules.taskmanager.view.components;

import java.awt.GridLayout;

/**
 * A panel that can contain multiple <code>FormField</code>s or other
 * components (like <code>JButton</code>s) inline
 * @author wavanrensselaer
 */
public class HorizontalForm extends Form {
	private static final long serialVersionUID = -5012730782794042722L;

	/**
	 * Creates a form that displays fields in one row.
	 * @param fields The form fields
	 */
	public HorizontalForm(FormElement... fields) {
		this.fields = fields;
		
		this.setOpaque(false);
		this.setLayout(new GridLayout(1, this.fields.length, 10, 0));

		for (int i = 0; i < this.fields.length; i++) {
			this.add(this.fields[i]);
		}
	}
}

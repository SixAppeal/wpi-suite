/*******************************************************************************
 * Copyright (c) 2014 -- WPI Suite
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors: SixAppeal
 ******************************************************************************/

package edu.wpi.cs.wpisuitetng.modules.taskmanager.view.components;

import static org.junit.Assert.*;

import javax.swing.JTextField;

import org.junit.Before;
import org.junit.Test;

public class TestForm {
	FormField field;
	Form group;
	
	Form form1;
	Form form2;
	
	@Before
	public void setup() {
		this.field = new FormField("A", new JTextField());
		this.group = new HorizontalForm();
		this.form1 = new Form();
		this.form2 = new Form(this.field, this.group);
	}
	
	@Test
	public void testConstructor() {
		assertNotNull(this.form1.getFields());
		assertEquals(0, this.form1.getFields().length);
		
		assertNotNull(this.form2.getFields());
		assertEquals(2, this.form2.getFields().length);
		assertEquals(this.field, this.form2.getFields()[0]);
		assertEquals(this.group, this.form2.getFields()[1]);
	}
}

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
		this.group = new Form(Form.HORIZONTAL);
		this.form1 = new Form();
		this.form2 = new Form(this.field, this.group);
	}
	
	@Test
	public void testConstructor() {
		assertNotNull(this.form1.getFields());
		assertEquals(0, this.form1.getFields().size());
		
		assertNotNull(this.form2.getFields());
		assertEquals(2, this.form2.getFields().size());
		assertEquals(this.field, this.form2.getFields().get(0));
		assertEquals(this.group, this.form2.getFields().get(1));
	}
}

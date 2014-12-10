package edu.wpi.cs.wpisuitetng.modules.taskmanager.view.components;

import static org.junit.Assert.*;

import javax.swing.JTextField;

import org.junit.Before;
import org.junit.Test;

public class TestFormGroup {
	FormField field1;
	FormField field2;
	
	HorizontalForm group1;
	HorizontalForm group2;
	
	@Before
	public void setup() {
		this.field1 = new FormField("Title", new JTextField());
		this.field2 = new FormField("Description", new JTextField());
		this.group1 = new HorizontalForm();
		this.group2 = new HorizontalForm(this.field1, this.field2);
	}
	
	@Test
	public void testConstructor() {
		assertNotNull(this.group1.getFields());
		assertEquals(0, this.group1.getFields().length);
		
		assertNotNull(this.group2.getFields());
		assertEquals(2, this.group2.getFields().length);
		assertEquals(this.field1, this.group2.getFields()[0]);
		assertEquals(this.field2, this.group2.getFields()[1]);
	}
}

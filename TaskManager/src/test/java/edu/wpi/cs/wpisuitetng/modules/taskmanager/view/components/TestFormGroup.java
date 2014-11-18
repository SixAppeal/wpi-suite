package edu.wpi.cs.wpisuitetng.modules.taskmanager.view.components;

import static org.junit.Assert.*;

import javax.swing.JTextField;

import org.junit.Before;
import org.junit.Test;

public class TestFormGroup {
	FormField field1;
	FormField field2;
	
	FormGroup group1;
	FormGroup group2;
	
	@Before
	public void setup() {
		this.field1 = new FormField("Title", new JTextField());
		this.field2 = new FormField("Description", new JTextField());
		this.group1 = new FormGroup();
		this.group2 = new FormGroup(this.field1, this.field2);
	}
	
	@Test
	public void testConstructor() {
		assertNotNull(this.group1.getFields());
		assertEquals(0, this.group1.getFields().size());
		
		assertNotNull(this.group2.getFields());
		assertEquals(2, this.group2.getFields().size());
		assertEquals(this.field1, this.group2.getFields().get(0));
		assertEquals(this.field2, this.group2.getFields().get(1));
	}
}

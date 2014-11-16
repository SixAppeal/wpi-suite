package edu.wpi.cs.wpisuitetng.modules.taskmanager.view.components;

import static org.junit.Assert.*;

import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.junit.Before;
import org.junit.Test;

public class TestFormField {
	JTextField textField;
	JTextArea textArea;
	
	FormField textFormField;
	FormField textAreaFormField;
	
	@Before
	public void setup() {
		textField = new JTextField();
		textArea = new JTextArea();
		
		textFormField = new FormField("Task", textField);
		textAreaFormField = new FormField("Description", textArea);
	}
	
	@Test
	public void testConstructor() {
		assertEquals("Task", textFormField.getName());
		assertEquals(textField, textFormField.getField());
		
		assertEquals("Description", textAreaFormField.getName());
		assertEquals(textArea, textAreaFormField.getField());
	}
}

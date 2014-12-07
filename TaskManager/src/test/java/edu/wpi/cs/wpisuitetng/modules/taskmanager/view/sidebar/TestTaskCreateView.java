package edu.wpi.cs.wpisuitetng.modules.taskmanager.view.sidebar;

import static org.junit.Assert.*;

import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.junit.Before;
import org.junit.Test;

import edu.wpi.cs.wpisuitetng.modules.taskmanager.model.Stage;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.model.StageList;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.presenter.Gateway;

public class TestTaskCreateView {
	Gateway gateway;
	TaskCreateView tcv;
	StageList stages1;
	StageList stages2;
	
	JTextField tjfield;
	JTextArea tjarea;
	
	
	@Before
	public void setup() {

		stages1 = new StageList();
		stages1.add(new Stage("New"));
		stages1.add(new Stage("Scheduled"));
		stages1.add(new Stage("In Progress"));
		stages1.add(new Stage("Complete"));
		
		tcv = new TaskCreateView(stages1);

	}
	
	
	@Test
	public void testConstructor(){
		assertNotNull(tcv);
	}

	@Test
	public void testValidateForm_empty(){
		tjfield = tcv.getTitle();
		tjfield.setText("");
		
		tjarea = tcv.getDescription();
		tjarea.setText("");
		
		assertTrue(tcv.isEmpty());
	}
	@Test
	public void testValidateForm_withTitle(){
		tjfield = tcv.getTitle();
		tjfield.setText("asdf");
		
		tjarea = tcv.getDescription();
		tjarea.setText("");
		
		assertFalse(tcv.isEmpty());
	}
	@Test
	public void testValidateForm_withDes(){
		tjfield = tcv.getTitle();
		tjfield.setText("");
		
		tjarea = tcv.getDescription();
		tjarea.setText("asdf");
		
		assertFalse(tcv.isEmpty());
	}
	@Test
	public void testValidateForm_withBoth(){
		tjfield = tcv.getTitle();
		tjfield.setText("asdf");
		
		tjarea = tcv.getDescription();
		tjarea.setText("asdf");
		
		assertFalse(tcv.isEmpty());
	}
	
	
	
}

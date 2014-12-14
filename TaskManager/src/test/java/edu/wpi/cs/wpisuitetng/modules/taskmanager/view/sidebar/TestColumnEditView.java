package edu.wpi.cs.wpisuitetng.modules.taskmanager.view.sidebar;

import static org.junit.Assert.*;

import javax.swing.JList;
import javax.swing.JTextField;

import org.junit.Before;
import org.junit.Test;

import edu.wpi.cs.wpisuitetng.modules.taskmanager.model.Stage;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.model.StageList;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.presenter.Gateway;

public class TestColumnEditView {
	Gateway gateway;
	ColumnEditView cev;
	StageList stages1;
	StageList stages2;
	
	@Before
	public void setup() {
		
		
		
		cev = new ColumnEditView();
		stages1 = new StageList();
		stages1.add(new Stage("New"));
		stages1.add(new Stage("Scheduled"));
		stages1.add(new Stage("In Progress"));
		stages1.add(new Stage("Complete"));
		
		stages2 = new StageList();
		stages2.add(new Stage("New"));
		stages2.add(new Stage("Scheduled"));
		stages2.add(new Stage("In Progress"));
		stages2.add(new Stage("Complete"));
		stages2.add(new Stage("NewStage"));
		
		cev.setStages(stages1);
		
		
//		gateway = new Gateway();
//		cev.setGateway(gateway);
	}
	
	@Test
	public void testConstructor(){
		assertNotNull(cev);
		assertEquals(cev.getStages(), stages1);	
	}
	
	@Test
	public void testAddStage_SameName(){
		JTextField testTitle = cev.getTitleEntry();
		testTitle.setText("New");
		cev.addStage();
		assertEquals(cev.getStages(), stages1);	
		
	}
	/*
	 * This adds a stage to detect errors in the add stage method
	 * This will error when it get's to the gateway call. 
	 */
	@Test (expected = NullPointerException.class)
	public void testAddStage_DifferentName(){
		JTextField testTitle = cev.getTitleEntry();
		testTitle.setText("NewStage");
		cev.addStage();
		
	}
	/*
	 * This adds a stage to detect errors in the change stage name method
	 * This will error when it get's to the gateway call. 
	 */
	@Test (expected = NullPointerException.class)
	public void testChangeName(){
		JList<Stage> testJList = cev.getStageJList();
		JTextField testEdit = cev.getNewName();
		
		testJList.setSelectedIndex(1);
		testEdit.setText("NewName!");
		
		cev.changeNameStage();
		
	}
	

}

package edu.wpi.cs.wpisuitetng.modules.taskmanager.model;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

/**
 * Test cases for the Stage class
 * @author krpeffer
 *
 */
public class TestStage {
	
	Stage stage;
	
	/**
	 * Set up a stage for testing
	 */
	@Before
	public void setup(){
		this.stage = new Stage();
	}
	
	/**
	 * test the constructor
	 */
	@Test
	public void testConstructor(){
		Stage testStage = new Stage("Stage name");
		assertNotNull(testStage);
		assertEquals(testStage.getName(), "Stage name");
	}
	
	/**
	 * test to be sure setName works properly
	 */
	@Test
	public void testSetName(){
		Stage testStage = new Stage();
		assertEquals(testStage.getName(), "");
		testStage.setName("Test Name");
		assertEquals(testStage.getName(), "Test Name");
	}
	
	/**
	 * test to be sure getName returns the correct string
	 */
	@Test
	public void testGetName(){
		Stage testStage = new Stage();
		testStage.setName("A Name");
		assertEquals(testStage.getName(), "A Name");
	}
	
	/**
	 * test for the toString override
	 */
	@Test
	public void testToString(){
		Stage testStage = new Stage("Stage Name");
		assertEquals(testStage.toString(), "Stage Name");
	}
}

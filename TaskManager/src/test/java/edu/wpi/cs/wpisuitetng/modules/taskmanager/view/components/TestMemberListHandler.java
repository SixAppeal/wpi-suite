package edu.wpi.cs.wpisuitetng.modules.taskmanager.view.components;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JTextField;

import org.junit.Before;
import org.junit.Test;

import edu.wpi.cs.wpisuitetng.modules.core.models.User;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.view.sidebar.MemberListHandler;

/*
public class TestMemberListHandler {
	MemberListHandler testHandler = new MemberListHandler();
	
	
	
	// Populate testUsernames in setup()
	List<String> testUsernames = new ArrayList<String>();
	List<String> testUsernamesAssigned = new ArrayList<String>();
	List<String> testUsernamesUnassigned = new ArrayList<String>();
	                                

	
	
	
	@Before
	public void setup(){
		testUsernames.add("Username1");
		testUsernames.add("Username2");
		testUsernames.add("Username3");
		testUsernames.add("Username4");
		
		testUsernamesAssigned.add("Username1");
		testUsernamesAssigned.add("Username2");
		
		testUsernamesUnassigned.addAll(testUsernames);
		testUsernamesUnassigned.removeAll(testUsernamesAssigned);
		
		
		

		// Debug Print's
//		System.out.println("Setup Unassigned: " +  this.testUsernamesUnassigned);
//		System.out.println("Setup Assigned:   " +  this.testUsernamesAssigned);
//		System.out.println("Setup Global:     " +  this.testUsernames);
	}
	
	@Test
	public void testConstructor(){
		assertEquals(this.testHandler.getAssigned(), new ArrayList<String>());
		assertEquals(this.testHandler.getUnassigned(), new ArrayList<String>());
		assertEquals(this.testHandler.getGlobal(), new ArrayList<String>());
		
	}
	
	@Test
	public void testGetNumAssigned(){
		assertEquals(this.testHandler.getNumAssigned(), new Integer(0));
		assertEquals(this.testHandler.getNumUnAssigned(), new Integer(0));
		assertEquals(this.testHandler.getNumMembers(), new Integer(0));
	}
	
	@Test
	public void testGlobalAdder(){
		this.testHandler.addGlobal("Username1");
		assertEquals(this.testHandler.getNumMembers(), new Integer(1));
	}
	
	@Test
	public void testGlobalAdder_2(){
		this.testHandler.addGlobal(testUsernames);
		// System should not allow douplicates, therefore should be 4 members
		assertEquals(this.testHandler.getNumMembers(), new Integer(4));
		assertEquals(this.testHandler.getGlobal(), testUsernames);
		
	}
	
	@Test
	public void testClearMethod(){
		MemberListHandler handler = new MemberListHandler();
		this.testHandler.clearMembers();

		assertEquals(this.testHandler.getAssigned(), handler.getAssigned());
		assertEquals(this.testHandler.getUnassigned(), handler.getUnassigned());
		assertEquals(this.testHandler.getGlobal(), handler.getGlobal());
	}
	
	@Test
	public void testPopulate(){
		
		this.testHandler.addGlobal(testUsernames);
		this.testHandler.populateMembers(testUsernamesAssigned);
		
		
		assertEquals(this.testHandler.getAssigned(), this.testUsernamesAssigned);
		assertEquals(this.testHandler.getUnassigned(), this.testUsernamesUnassigned);
		assertEquals(this.testHandler.getNumAssigned(), new Integer(2));
		assertEquals(this.testHandler.getNumUnAssigned(), new Integer(2));
		assertEquals(this.testHandler.getNumMembers(), new Integer(4));
		
	}
	
	@Test
	public void testAssignMember(){
		this.testHandler.addGlobal(testUsernames);
		
		this.testHandler.assignMember(this.testUsernamesUnassigned.get(1));
		
		this.testUsernamesAssigned.add(this.testUsernamesUnassigned.get(1));
		//this.testUsernamesUnassigned.remove(1);
		System.out.println(testHandler.getAssigned());
		System.out.println(testUsernamesAssigned);
		
		
		assertEquals(this.testHandler.getAssigned(), this.testUsernamesAssigned);

		assertEquals(this.testHandler.getUnassigned(), this.testUsernamesUnassigned);
		assertEquals(this.testHandler.getNumAssigned(), new Integer(3));
		assertEquals(this.testHandler.getNumUnAssigned(), new Integer(1));
		assertEquals(this.testHandler.getGlobal(), new Integer(4));
		
		
	}
	
	
	
	
	
	
}
*/


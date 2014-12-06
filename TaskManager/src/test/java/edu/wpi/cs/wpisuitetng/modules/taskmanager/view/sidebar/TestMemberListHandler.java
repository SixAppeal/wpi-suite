package edu.wpi.cs.wpisuitetng.modules.taskmanager.view.sidebar;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JTextField;

import org.junit.Before;
import org.junit.Test;

import edu.wpi.cs.wpisuitetng.modules.core.models.User;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.view.sidebar.MemberListHandler;


public class TestMemberListHandler {
	
	
	
	
	// Populate testUsernames in setup()
	List<String> testUsernames = new ArrayList<String>();
	List<String> testUsernamesAssigned = new ArrayList<String>();
	List<String> testUsernamesUnassigned = new ArrayList<String>();
	                                

	
	
	
	@Before
	public void setup(){
		MemberListHandler testHandler = MemberListHandler.getInstance();
		
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
		assertEquals(MemberListHandler.getInstance().getAssigned(), new ArrayList<String>());
		assertEquals(MemberListHandler.getInstance().getUnassigned(), new ArrayList<String>());
		assertEquals(MemberListHandler.getInstance().getGlobal(), new ArrayList<String>());
		
	}
	
	@Test
	public void testGetNumAssigned(){
		assertEquals(MemberListHandler.getInstance().getNumAssigned(), new Integer(0));
		assertEquals(MemberListHandler.getInstance().getNumUnAssigned(), new Integer(0));
		assertEquals(MemberListHandler.getInstance().getNumMembers(), new Integer(0));
	}
	
	@Test
	public void testGlobalAdder(){
		MemberListHandler.getInstance().addGlobal("Username1");
		assertEquals(MemberListHandler.getInstance().getNumMembers(), new Integer(1));
	}
	
	@Test
	public void testGlobalAdder_2(){
		MemberListHandler.getInstance().addGlobal(testUsernames);
		// System should not allow douplicates, therefore should be 4 members
		assertEquals(MemberListHandler.getInstance().getNumMembers(), new Integer(4));
		assertEquals(MemberListHandler.getInstance().getGlobal(), testUsernames);
		
	}
	
//	@Test
//	public void testClearMethod(){
//		MemberListHandler handler = new MemberListHandler();
//		MemberListHandler.getInstance().clearMembers();
//
//		assertEquals(MemberListHandler.getInstance().getAssigned(), handler.getAssigned());
//		assertEquals(MemberListHandler.getInstance().getUnassigned(), handler.getUnassigned());
//		assertEquals(MemberListHandler.getInstance().getGlobal(), handler.getGlobal());
//	}
	
	@Test
	public void testPopulate(){
		
		MemberListHandler.getInstance().addGlobal(testUsernames);
		MemberListHandler.getInstance().populateMembers(testUsernamesAssigned);
		
		
		assertEquals(MemberListHandler.getInstance().getAssigned(), this.testUsernamesAssigned);
		assertEquals(MemberListHandler.getInstance().getUnassigned(), this.testUsernamesUnassigned);
		assertEquals(MemberListHandler.getInstance().getNumAssigned(), new Integer(2));
		assertEquals(MemberListHandler.getInstance().getNumUnAssigned(), new Integer(2));
		assertEquals(MemberListHandler.getInstance().getNumMembers(), new Integer(4));
		
	}
	
	@Test
	public void testAssignMember(){
		MemberListHandler.getInstance().addGlobal(testUsernames);
		
		MemberListHandler.getInstance().assignMember(this.testUsernamesUnassigned.get(1));
		
		this.testUsernamesAssigned.add(this.testUsernamesUnassigned.get(1));
		//this.testUsernamesUnassigned.remove(1);
		System.out.println(MemberListHandler.getInstance().getAssigned());
		System.out.println(testUsernamesAssigned);
		
		
		assertEquals(MemberListHandler.getInstance().getAssigned(), this.testUsernamesAssigned);

		assertEquals(MemberListHandler.getInstance().getUnassigned(), this.testUsernamesUnassigned);
		assertEquals(MemberListHandler.getInstance().getNumAssigned(), new Integer(3));
		assertEquals(MemberListHandler.getInstance().getNumUnAssigned(), new Integer(1));
		assertEquals(MemberListHandler.getInstance().getGlobal(), new Integer(4));
		
		
	}
	
	
	
	
	
	
}



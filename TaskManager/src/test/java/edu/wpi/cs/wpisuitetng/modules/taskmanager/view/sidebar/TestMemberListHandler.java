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
		
		List<String> tuMembers = MemberListHandler.getInstance().getUnassigned();
		List<String> taMembers = MemberListHandler.getInstance().getAssigned();
		List<String> tgMembers = MemberListHandler.getInstance().getGlobal();
		
		tuMembers.clear();
		taMembers.clear();
		tgMembers.clear();
		
		
		
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
	public void testClearMethod(){
		List<String> tuMembers = MemberListHandler.getInstance().getUnassigned();
		List<String> taMembers = MemberListHandler.getInstance().getAssigned();
		
		tuMembers.addAll(testUsernamesAssigned);
		taMembers.addAll(testUsernamesAssigned);
		
		assertEquals(MemberListHandler.getInstance().getNumAssigned(), new Integer(2));
		assertEquals(MemberListHandler.getInstance().getNumUnAssigned(), new Integer(2));
		
		MemberListHandler.getInstance().clearMembers();

		assertEquals(MemberListHandler.getInstance().getNumAssigned(), new Integer(0));
		assertEquals(MemberListHandler.getInstance().getNumUnAssigned(), new Integer(0));
		assertEquals(MemberListHandler.getInstance().getGlobal(), MemberListHandler.getInstance().getGlobal());
	}
	
	@Test
	public void testPopulate(){
		List<String> global = MemberListHandler.getInstance().getGlobal();
		global.addAll(testUsernames);
		MemberListHandler.getInstance().populateMembers(testUsernamesAssigned);
		
		
		assertEquals(MemberListHandler.getInstance().getAssigned(), this.testUsernamesAssigned);
		assertEquals(MemberListHandler.getInstance().getUnassigned(), this.testUsernamesUnassigned);
		assertEquals(MemberListHandler.getInstance().getNumAssigned(), new Integer(2));
		assertEquals(MemberListHandler.getInstance().getNumUnAssigned(), new Integer(2));
		assertEquals(MemberListHandler.getInstance().getNumMembers(), new Integer(4));
		
	}
	
	@Test
	public void testAssignMember(){
		List<String> global = MemberListHandler.getInstance().getGlobal();
		global.addAll(testUsernames);
		MemberListHandler.getInstance().populateMembers(testUsernamesAssigned);
		
		this.testUsernamesAssigned.add(this.testUsernamesUnassigned.get(1));
		MemberListHandler.getInstance().assignMember(this.testUsernamesUnassigned.get(1));
		this.testUsernamesUnassigned.remove(1);
		
		assertEquals(MemberListHandler.getInstance().getAssigned(), this.testUsernamesAssigned);
		assertEquals(MemberListHandler.getInstance().getUnassigned(), this.testUsernamesUnassigned);
		assertEquals(MemberListHandler.getInstance().getNumAssigned(), new Integer(3));
		assertEquals(MemberListHandler.getInstance().getNumUnAssigned(), new Integer(1));
		assertEquals(MemberListHandler.getInstance().getNumMembers(), new Integer(4));
	}
	
	@Test
	public void testUnAssignMember(){
		List<String> global = MemberListHandler.getInstance().getGlobal();
		global.addAll(testUsernames);
		MemberListHandler.getInstance().populateMembers(testUsernamesAssigned);
		
		this.testUsernamesUnassigned.add(this.testUsernamesAssigned.get(1));
		MemberListHandler.getInstance().unAssignMember(this.testUsernamesAssigned.get(1));
		this.testUsernamesAssigned.remove(1);
		
		
		assertEquals(MemberListHandler.getInstance().getAssigned(), this.testUsernamesAssigned);
		assertEquals(MemberListHandler.getInstance().getUnassigned(), this.testUsernamesUnassigned);
		assertEquals(MemberListHandler.getInstance().getNumAssigned(), new Integer(1));
		assertEquals(MemberListHandler.getInstance().getNumUnAssigned(), new Integer(3));
		assertEquals(MemberListHandler.getInstance().getNumMembers(), new Integer(4));
	}
	
	@Test
	public void testAssignMultipleMember(){
		List<String> global = MemberListHandler.getInstance().getGlobal();
		global.addAll(testUsernames);
		MemberListHandler.getInstance().populateMembers(testUsernamesAssigned);
		
		this.testUsernamesAssigned.addAll(this.testUsernamesUnassigned);
		MemberListHandler.getInstance().assignMember(this.testUsernamesUnassigned);
		this.testUsernamesUnassigned.clear();
		
		assertEquals(MemberListHandler.getInstance().getAssigned(), this.testUsernamesAssigned);
		assertEquals(MemberListHandler.getInstance().getUnassigned(), this.testUsernamesUnassigned);
		assertEquals(MemberListHandler.getInstance().getNumAssigned(), new Integer(4));
		assertEquals(MemberListHandler.getInstance().getNumUnAssigned(), new Integer(0));
		assertEquals(MemberListHandler.getInstance().getNumMembers(), new Integer(4));
	}
	
	@Test
	public void testUnAssignMultipleMember(){
		List<String> global = MemberListHandler.getInstance().getGlobal();
		global.addAll(testUsernames);
		MemberListHandler.getInstance().populateMembers(testUsernamesAssigned);
		
		this.testUsernamesUnassigned.addAll(this.testUsernamesAssigned);
		MemberListHandler.getInstance().unAssignMember(this.testUsernamesAssigned);
		this.testUsernamesAssigned.clear();
		
		
		assertEquals(MemberListHandler.getInstance().getAssigned(), this.testUsernamesAssigned);
		assertEquals(MemberListHandler.getInstance().getUnassigned(), this.testUsernamesUnassigned);
		assertEquals(MemberListHandler.getInstance().getNumAssigned(), new Integer(0));
		assertEquals(MemberListHandler.getInstance().getNumUnAssigned(), new Integer(4));
		assertEquals(MemberListHandler.getInstance().getNumMembers(), new Integer(4));
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testAssignInvalid(){
		List<String> global = MemberListHandler.getInstance().getGlobal();
		global.addAll(testUsernames);
		MemberListHandler.getInstance().populateMembers(testUsernamesAssigned);
		
		MemberListHandler.getInstance().assignMember(new String("Troy"));

	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testunAssignInvalid(){
		List<String> global = MemberListHandler.getInstance().getGlobal();
		global.addAll(testUsernames);
		MemberListHandler.getInstance().populateMembers(testUsernamesAssigned);
		
		MemberListHandler.getInstance().unAssignMember(new String("Troy"));
		
		List<String> badList = new ArrayList<String>();
		badList.add("Troy");
		badList.add("Alex");
		
		MemberListHandler.getInstance().unAssignMember(badList);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testAssignMultipleInvalid(){
		List<String> global = MemberListHandler.getInstance().getGlobal();
		global.addAll(testUsernames);
		MemberListHandler.getInstance().populateMembers(testUsernamesAssigned);
		
		List<String> badList = new ArrayList<String>();
		badList.add("Troy");
		badList.add("Alex");
		
		MemberListHandler.getInstance().assignMember(badList);

	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testunAssignMultipleInvalid(){
		List<String> global = MemberListHandler.getInstance().getGlobal();
		global.addAll(testUsernames);
		MemberListHandler.getInstance().populateMembers(testUsernamesAssigned);
		
		List<String> badList = new ArrayList<String>();
		badList.add("Troy");
		badList.add("Alex");
		
		MemberListHandler.getInstance().unAssignMember(badList);

	}
	
	
	
	
	
	
	
	
	
}



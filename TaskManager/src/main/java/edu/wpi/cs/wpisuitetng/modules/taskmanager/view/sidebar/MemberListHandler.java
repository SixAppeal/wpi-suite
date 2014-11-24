package edu.wpi.cs.wpisuitetng.modules.taskmanager.view.sidebar;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import edu.wpi.cs.wpisuitetng.modules.core.models.User;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.localcache.Cache;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.presenter.Gateway;


/**
 * 
 * @author Thhughes
 * @author jrhennessy
 *
 *
 *	A handler for the member lists. 
 *	Will handle the updating of the lists
 */
public class MemberListHandler {
	
	protected Gateway gateway;
	
	private List<String> allMembersList;			
	private List<String> assignedMembersList;
	private List<String> globalMembersList;				// Only updated through localCache
	private final List<String> blankList;
	
	// TODO: Need to uncomment the subscribe gateway command to get cache information
	public MemberListHandler(){
		allMembersList = new ArrayList<String>();		// List of members NOT assigned to a task
		assignedMembersList = new ArrayList<String>();	// List of members who are assigned to a task
		globalMembersList = new ArrayList<String>();	// List of ALL members
		blankList = new ArrayList<String>();
		
		//this.gateway.toView("localcache", "subscribe", "member:MemberListHandler:updateAll");
		
		
	}
	
	
	/**
	 * 
	 * @param localCache localCache of the tasks
	 * 
	 * 	This get's and updates the users that are within the system
	 */
	public void updateAll(Cache localCache){
		setGlobal(localCache);	
		updateUnassigned();
		
	}
	
	/**
	 * 
	 * @param localCache local cache of all the data
	 * 
	 * 	Converts the array of Objects from the Loal Cache to an array of Users
	 * 	Then converts the array of users to a list<String> of the usernames
	 */
	private void setGlobal(Cache localCache){
		Object[] output = localCache.retrieve("member");
		User[] memberArray = objectsToUsers(output);
		globalMembersList = usersToUsernames(memberArray);

		
	}
	
	/**
	 * Converts an array of users to a list of usernames
	 * 
	 * @param userList list of users 
	 * @return	List<String> that are the usernames of the users
	 */
	private List<String> usersToUsernames(User[] userList){
		List<String> output = new ArrayList<String>(); 
		for (int i = 0; i < userList.length; i++){
			output.add(userList[i].getUsername());
		}
		return output;
	}
	
	/**
	 * Converts an array of objects to an array of users
	 * 
	 * @param inputObj Array of Objects
	 * @return array of Users
	 */
	private User[] objectsToUsers(Object[] inputObj){
		User[] memberArray = new User[inputObj.length];
		return memberArray = Arrays.copyOf(inputObj, inputObj.length, User[].class);
		
	}
	
	/**
	 * Sets the unassigned members list to those members not already assigned to a task
	 */
	private void updateUnassigned(){
		allMembersList = globalMembersList;
		allMembersList.removeAll(assignedMembersList);
		
	}

	
	
	
	/**
	 * 	Clears all the internal lists for a new task to be displayed.
	 */
	public void clearMembers(){
		// Provided in case of JList Displaying problems
		// As was previously used
		MemberListHandler tempHandler = new MemberListHandler();
		
		this.globalMembersList = tempHandler.getGlobal();
		this.assignedMembersList = tempHandler.getAssigned();
		this.allMembersList = tempHandler.getUnassigned();
		
	}
	
	/**
	 * Sets the assigned and unassigned lists based off of the input from the task
	 * @param newAssigned List of members currently assigned to a task
	 */
	public void populateMembers(List<String> newAssigned){
		List<String> temp = new ArrayList<String>(globalMembersList);		// Need this to avoid compiler errors found while testing
		this.assignedMembersList = newAssigned;
		this.allMembersList = temp;
		this.allMembersList.removeAll(this.assignedMembersList);
	}

	
	
	
	
	
	
	
	
	/**
	 * 
	 * @param toAdd String that is the name of the member to add
	 * 
	 * 	This will move a valid member from the allMembersList to the assignedMemberList
	 */
	public void assignMember(String toAdd){
		if (!globalMembersList.contains(toAdd)){
			throw new IllegalArgumentException("Can only add valid members");
		}
		allMembersList.remove(toAdd);
		assignedMembersList.add(toAdd);
		
	}
	
	/**
	 * Will move all valid members from allmemberslist to assignedmemberlist
	 * @param members the members to assign
	 */
	public void assignMember(List<String> members){
		for (String s: members){
			if (!globalMembersList.contains(s)){
				throw new IllegalArgumentException("Can only add valid members");
			}
		}
		assignedMembersList.addAll(members);
		allMembersList.removeAll(members);
		
	}
	
	/**
	 * Moves a valid member from the assignedmemberlist to allmemberslist
	 * @param toRem the member to remove from the list
	 */
	public void unAssignMember(String toRem){
		if (!assignedMembersList.contains(toRem)){
			throw new IllegalArgumentException("Can only add valid members");
		}
		assignedMembersList.remove(toRem);
		allMembersList.add(toRem);
		
	}
	
	/**
	 * Takes all valid members and moves them from the assignedmemberlist to the allmemberslist
	 * @param members the members to unassign from the task
	 */
	public void unAssignMember(List<String> members){
		for (String s: members){
			if (!assignedMembersList.contains(s)){
				throw new IllegalArgumentException("Can only add valid members");
			}
		}
		allMembersList.addAll(members);
		assignedMembersList.removeAll(members);
		
	}
	
	/**
	 * Global List Adder
	 * -- Should only be used for testing -- 
	 * 
	 * @param toAdd String to add to the global List
	 */
	public void addGlobal(String toAdd){
		globalMembersList.add(toAdd);
		
	}
	/**
	 * Global List Adder
	 * -- Should only be used for testing -- 
	 * 
	 * @param toAdd List of Strings to add to the global List
	 */
	public void addGlobal(List<String> toAdd){
		globalMembersList.addAll(toAdd);
	}

	
	/**
	 * Gets the list of members assigned to a task
	 * @return All members assigned to a task
	 */
	public List<String> getAssigned(){
		return assignedMembersList;
	}
	
	/**
	 * Gets the list of members not assigned to a task
	 * @return All members not assigned to a task
	 */
	public List<String> getUnassigned(){
		return allMembersList;
	}
	
	/**
	 * Gets the lsit of members 
	 * @return	all the members possible
	 */
	public List<String> getGlobal(){
		return this.globalMembersList;
	}
	
	
	/**
	 * Gets the number of members assigned to a task
	 * @return the number of members assigned to the task
	 */
	public Integer getNumAssigned(){
		return assignedMembersList.size();
	}
	
	/**
	 * Gets the number of members not assigned to a task
	 * @return the number of members not assigned to a task
	 */
	public Integer getNumUnAssigned(){
		return allMembersList.size();
	}
	
	/**
	 * Gets the number of members not assigned to a task
	 * @return the number of members not assigned to a task
	 */
	public Integer getNumMembers(){
		return globalMembersList.size();
	}
		
	
	



}

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
	private List<String> globalMembersList;
	
	
	public MemberListHandler(){
		allMembersList = new ArrayList<String>();		// List of members NOT assigned to a task
		assignedMembersList = new ArrayList<String>();	// List of members who are assigned to a task
		globalMembersList = new ArrayList<String>();	// List of ALL members
		
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
		setUnassigned();
		
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
	 * 
	 * @param localCache local cache of all the data
	 * 
	 * 	re-populates the global members list from the cache to ensure continuously up to date data. 
	 */
	private void setGlobal(Cache localCache){
		Object[] output = localCache.retreive("member");
		User[] memberArray = Arrays.copyOf(output, output.length, User[].class);
		
		for (int i = 0; i < memberArray.length; i++){
			globalMembersList.add(memberArray[i].getUsername());
		}
		
	}
	
	/**
	 * Sets the unassigned members list to those members not already assigned to a task
	 */
	private void setUnassigned(){
		allMembersList = globalMembersList;
		allMembersList.removeAll(assignedMembersList);
		
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
	 * Sets the assigned and unassigned lists based off of the input from the task
	 * @param newAssigned List of members currently assigned to a task
	 */
	public void populateMembers(List<String> newAssigned){
		this.assignedMembersList = newAssigned;
		this.allMembersList = globalMembersList;
		this.allMembersList.removeAll(this.assignedMembersList);
	}
}

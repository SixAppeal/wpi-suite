package edu.wpi.cs.wpisuitetng.modules.taskmanager.view.sidebar;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import edu.wpi.cs.wpisuitetng.modules.taskmanager.localcache.Cache;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.presenter.Gateway;


/**
 * 
 * @author Thhughes
 *
 *
 *	A handler for the member lists. 
 *	Will handle the updating of the lists
 */
public class MemberListHandler {
	
	protected Gateway gateway;
	
	List<String> allMembersList;
	List<String> assignedMembersList;
	List<String> globalMembersList;
	
	
	public MemberListHandler(){
		allMembersList = new ArrayList<String>();		// List of members NOT assigned to a task
		assignedMembersList = new ArrayList<String>();	// List of members who are assigned to a task
		globalMembersList = new ArrayList<String>();	// List of ALL members
		
		this.gateway.toView("localcache", "subscribe", "member:MemberListHandler:updateAll");
		
		
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
		if (globalMembersList.contains(toAdd)){
			throw new IllegalArgumentException("Can only add valid members");
		}
		allMembersList.remove(toAdd);
		assignedMembersList.add(toAdd);
		
	}
	
	public void setAssignMembers(List<String> members){
		assignedMembersList = members;
	}
	
	
	/**
	 * 
	 * @param localCache local cache of all the data
	 * 
	 * 	re-populates the global members list from the cache to ensure continuously up to date data. 
	 */
	private void setGlobal(Cache localCache){
		Object[] output = localCache.retreive("member");
		String[] memberArray = Arrays.copyOf(output, output.length, String[].class);
		
		for (int i = 0; i < memberArray.length; i++){
			globalMembersList.add(memberArray[i]);
		}
		
	}
	
	private void setUnassigned(){
		allMembersList = globalMembersList;
		allMembersList.removeAll(assignedMembersList);
		
	}
	public List<String> getAssigned(){
		return assignedMembersList;
	}
	
	public List<String> getUnassigned(){
		return allMembersList;
	}
	
}

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
	
	public void assignMembers(List<String> members){
		for (String s: members){
			if (!globalMembersList.contains(s)){
				throw new IllegalArgumentException("Can only add valid members");
			}
		}
		assignedMembersList.addAll(members);
		allMembersList.removeAll(members);
		
	}
	
	public void unAssignMember(String toRem){
		if (!assignedMembersList.contains(toRem)){
			throw new IllegalArgumentException("Can only add valid members");
		}
		assignedMembersList.remove(toRem);
		allMembersList.add(toRem);
		
	}
	
	public void unAssignMembers(List<String> members){
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
	
	public Integer getNumAssigned(){
		return assignedMembersList.size();
	}
	
	public Integer getNumUnAssigned(){
		return allMembersList.size();
	}
	
}

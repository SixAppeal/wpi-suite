package edu.wpi.cs.wpisuitetng.modules.taskmanager.view.sidebar;

import java.util.ArrayList;
import java.util.List;

import edu.wpi.cs.wpisuitetng.modules.core.models.User;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.localcache.Cache;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.localcache.LocalCache;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.model.StageList;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.presenter.Gateway;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.view.IView;

/**
 * 
 * @author Thhughes
 * @author jrhennessy
 *
 *
 *         A handler for the member lists. Will handle the updating of the lists
 */
public class MemberListHandler implements IView {

	private List<String> unassignedMembersList;
	private List<String> assignedMembersList;
	private List<String> allMembersList; // Only updated through localCache
	@SuppressWarnings("unused")
	private Gateway gateway;

	public MemberListHandler() {
		unassignedMembersList = new ArrayList<String>();
		assignedMembersList = new ArrayList<String>();
		allMembersList = new ArrayList<String>();
	}

	/**
	 * 
	 * @param localCache
	 *            localCache of the tasks
	 * 
	 *            This gets and updates the users that are within the system
	 */
	public void updateAll(LocalCache localCache) {
		setGlobal(localCache);
		updateUnassigned();
		// this.gateway.toView("SidebarView","doStuff");
	}

	/**
	 * 
	 * @param localCache
	 *            local cache of all the data
	 * 
	 *            Converts the array of Objects from the Loal Cache to an array
	 *            of Users Then converts the array of users to a list<String> of
	 *            the usernames
	 */
	private void setGlobal(Cache localCache) {
		Object[] output = localCache.retrieve("member");
		User[] memberArray = (User[]) output;
		this.allMembersList = new ArrayList<String>(usersToUsernames(memberArray));
	}

	/**
	 * Converts an array of users to a list of usernames
	 * 
	 * @param userList
	 *            list of users
	 * @return List<String> that are the usernames of the users
	 */
	private List<String> usersToUsernames(User[] userList) {
		List<String> output = new ArrayList<String>();
		for (int i = 0; i < userList.length; i++) {
			output.add(userList[i].getUsername());
		}
		return output;
	}

	/**
	 * Sets the unassigned members list to those members not already assigned to
	 * a task
	 */
	private void updateUnassigned() {
		unassignedMembersList = new ArrayList<String>();
		for (String s : allMembersList) {
			if (!assignedMembersList.contains(s)) {
				unassignedMembersList.add(s);
			}
		}
	}

	/**
	 * Clears all the internal lists for a new task to be displayed.
	 */
	public void clearMembers() {
		this.unassignedMembersList.clear();
		this.assignedMembersList.clear();
	}

	/**
	 * Sets the assigned and unassigned lists based off of the input from the
	 * task
	 * 
	 * @param newAssigned
	 *            List of members currently assigned to a task
	 */
	public void populateMembers(List<String> newAssigned) {
		List<String> temp = new ArrayList<String>(allMembersList);
		this.assignedMembersList = new ArrayList<String>(newAssigned);
		this.unassignedMembersList = temp;
		this.unassignedMembersList.removeAll(this.assignedMembersList);
	}

	/**
	 * 
	 * @param toAdd
	 *            String that is the name of the member to add
	 * 
	 *            This will move a valid member from the allMembersList to the
	 *            assignedMemberList
	 */
	public void assignMember(String toAdd) {
		if (!allMembersList.contains(toAdd)) {
			throw new IllegalArgumentException("Can only add valid members");
		}
		unassignedMembersList.remove(toAdd);
		assignedMembersList.add(toAdd);

	}

	/**
	 * Will move all valid members from allmemberslist to assignedmemberlist
	 * 
	 * @param members
	 *            the members to assign
	 */
	public void assignMember(List<String> members) {
		for (String s : members) {
			if (!allMembersList.contains(s)) {
				throw new IllegalArgumentException("Can only add valid members");
			}
		}
		assignedMembersList.addAll(members);
		unassignedMembersList.removeAll(members);

	}

	/**
	 * Moves a valid member from the assignedmemberlist to allmemberslist
	 * 
	 * @param toRem
	 *            the member to remove from the list
	 */
	public void unAssignMember(String toRem) {
		if (!assignedMembersList.contains(toRem)) {
			throw new IllegalArgumentException("Can only add valid members");
		}
		assignedMembersList.remove(toRem);
		unassignedMembersList.add(toRem);

	}

	/**
	 * Takes all valid members and moves them from the assignedmemberlist to the
	 * allmemberslist
	 * 
	 * @param members
	 *            the members to unassign from the task
	 */
	public void unAssignMember(List<String> members) {
		for (String s : members) {
			if (!assignedMembersList.contains(s)) {
				throw new IllegalArgumentException("Can only add valid members");
			}
		}
		unassignedMembersList.addAll(members);
		assignedMembersList.removeAll(members);

	}

	/**
	 * Global List Adder -- Should only be used for testing --
	 * 
	 * @param toAdd
	 *            String to add to the global List
	 */
	public void addGlobal(String toAdd) {
		allMembersList.add(toAdd);

	}

	/**
	 * Global List Adder -- Should only be used for testing --
	 * 
	 * @param toAdd
	 *            List of Strings to add to the global List
	 */
	public void addGlobal(List<String> toAdd) {
		allMembersList.addAll(toAdd);
	}

	/**
	 * Gets the list of members assigned to a task
	 * 
	 * @return All members assigned to a task
	 */
	public List<String> getAssigned() {
		return assignedMembersList;
	}

	/**
	 * Gets the list of members not assigned to a task
	 * 
	 * @return All members not assigned to a task
	 */
	public List<String> getUnassigned() {
		return unassignedMembersList;
	}

	/**
	 * Gets the lsit of members
	 * 
	 * @return all the members possible
	 */
	public List<String> getGlobal() {
		return this.allMembersList;
	}

	/**
	 * Gets the number of members assigned to a task
	 * 
	 * @return the number of members assigned to the task
	 */
	public Integer getNumAssigned() {
		return assignedMembersList.size();
	}

	/**
	 * Gets the number of members not assigned to a task
	 * 
	 * @return the number of members not assigned to a task
	 */
	public Integer getNumUnAssigned() {
		return unassignedMembersList.size();
	}

	/**
	 * Gets the number of members not assigned to a task
	 * 
	 * @return the number of members not assigned to a task
	 */
	public Integer getNumMembers() {
		return allMembersList.size();
	}

	@Override
	public void setGateway(Gateway gateway) {
		this.gateway = gateway;

	}

	@Override
	public void setStages(StageList sl) {
		// no purpose here
	}

}

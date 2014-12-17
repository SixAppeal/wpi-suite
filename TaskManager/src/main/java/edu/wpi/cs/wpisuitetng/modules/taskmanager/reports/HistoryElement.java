/*******************************************************************************
 * Copyright (c) 2014 -- WPI Suite
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors: Six-Appeal
 ******************************************************************************/

package edu.wpi.cs.wpisuitetng.modules.taskmanager.reports;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import edu.wpi.cs.wpisuitetng.modules.taskmanager.model.Task;

/**
 * Class to keep track of every scorable transaction
 * @author nhhughes
 *
 */
public class HistoryElement implements Comparable {
	private List<String> users;
	private String contributingMember;
	private int associatedTask;
	private boolean historyFlag;
	private List<String> oldContributingMembers;
	private Date date;
	
	/**
	 * Makes a history element out to track user activity
	 * @param originalTask task the history event was generated from
	 * @param contributingMember the member associated with the event
	 * @param historyFlag true if the event was a history event, false if it was a comment
	 * @param occuredAt date and time the event occurred at
	 */
	public HistoryElement(Task originalTask, String contributingMember, boolean historyFlag, Date occuredAt) {
		this.users = originalTask.getAssignedTo();
		this.contributingMember = contributingMember;
		this.historyFlag = historyFlag;
		this.associatedTask = originalTask.getId();
		this.oldContributingMembers = new ArrayList<String>();
		this.date = occuredAt;
	}
	
	/**
	 * Makes a history event with history of all previous events for the task
	 * @param updatedTask task to analyze
	 * @param originalHistory history event that occurred one time step previous
	 * @param contributingMember member associated with the event
	 * @param historyFlag true if the event was a history event, false if it was a comment
	 * @param occuredAt date and time the event occurred at
	 */
	public HistoryElement(Task updatedTask, HistoryElement originalHistory, String contributingMember, boolean historyFlag, Date occuredAt) {
		this.users = updatedTask.getAssignedTo();
		this.contributingMember = contributingMember;
		this.historyFlag = historyFlag;
		this.associatedTask = updatedTask.getId();
		this.oldContributingMembers = originalHistory.getOldContributingMembers();
		this.oldContributingMembers.add(originalHistory.getContributingMember());
		this.date = occuredAt;
	}
	
	/**
	 * The contributing member for this event
	 * @return the contributing member
	 */
	public String getContributingMember() {
		return contributingMember;
	}

	/**
	 * The users who previously contributed to this event
	 * @return list of previous users
	 */
	public List<String> getUsers() {
		return users;
	}

	/**
	 * Set the users for the event
	 * @param users
	 */
	public void setUsers(List<String> users) {
		this.users = users;
	}

	/**
	 * Get the id of the associated task
	 * @return id of the associated task
	 */
	public int getAssociatedTask() {
		return associatedTask;
	}

	/**
	 * Set the id of the associated task
	 * @param associatedTask id of the associated task
	 */
	public void setAssociatedTask(int associatedTask) {
		this.associatedTask = associatedTask;
	}

	/**
	 * Determines whether the event is associated with comments or history
	 * @return true if history, false if comment
	 */
	public boolean isHistoryFlag() {
		return historyFlag;
	}

	/**
	 * Set whether the event was history or not
	 * @param historyFlag true if history, false if comment
	 */
	public void setHistoryFlag(boolean historyFlag) {
		this.historyFlag = historyFlag;
	}

	/**
	 * Get contributing members
	 * @return list of members
	 */
	public List<String> getOldContributingMembers() {
		return oldContributingMembers;
	}

	/**
	 * Set contributing members
	 * @param oldContributingMembers list of members
	 */
	public void setOldContributingMembers(List<String> oldContributingMembers) {
		this.oldContributingMembers = oldContributingMembers;
	}

	/**
	 * Set the contributing member for the event
	 * @param contributingMember the contributing member
	 */
	public void setContributingMember(String contributingMember) {
		this.contributingMember = contributingMember;
	}
	
	/**
	 * Get the association score between two users
	 * If contributing user comments on the same task, return a strong score
	 * If contributing user changes the same task, return a medium score
	 * If user2 is an assigned member, return a low score
	 * @param user1 the contributor
	 * @param user2 "destination" member
	 * @return association score between two users
	 */
	public double getScore(String user1, String user2) {
		if (user1.equals(user2)) {
			return 0.0;
		}
		if (this.historyFlag){
			if (this.oldContributingMembers.contains(user2)) {
				return 0.55;
			}
			else if (this.users.contains(user2)) {
				return 0.1;
			}
		}
		else {
			if (this.oldContributingMembers.contains(user2)) {
				return 1.0;
			}
			else if (this.users.contains(user2)) {
				return 0.1;
			}
		}
		return 0.0;
	}

	/**
	 * Get the date associated with the history event
	 * @return the date associated with the history event
	 */
	public Date getDate() {
		return date;
	}

	/**
	 * Set the date associated with the history event
	 * @param date the date associated with the history event
	 */
	public void setDate(Date date) {
		this.date = date;
	}

	/**
	 * Compare two events based on their date
	 */
	@Override
	public int compareTo(Object o) {
		if (o instanceof HistoryElement) {
			return this.date.compareTo(((HistoryElement)o).getDate());
		}
		return -1;
	}
}

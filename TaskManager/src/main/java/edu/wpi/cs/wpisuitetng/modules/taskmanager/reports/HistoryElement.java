/*******************************************************************************
 * Copyright (c) 2014 -- WPI Suite
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors: SixAppeal
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
	
	public HistoryElement(Task originalTask, String contributingMember, boolean historyFlag, Date occuredAt) {
		this.users = originalTask.getAssignedTo();
		this.contributingMember = contributingMember;
		this.historyFlag = historyFlag;
		this.associatedTask = originalTask.getId();
		this.oldContributingMembers = new ArrayList<String>();
		this.date = occuredAt;
	}
	
	public HistoryElement(Task updatedTask, HistoryElement originalHistory, String contributingMember, boolean historyFlag, Date occuredAt) {
		this.users = updatedTask.getAssignedTo();
		this.contributingMember = contributingMember;
		this.historyFlag = historyFlag;
		this.associatedTask = updatedTask.getId();
		this.oldContributingMembers = originalHistory.getOldContributingMembers();
		this.oldContributingMembers.add(originalHistory.getContributingMember());
		this.date = occuredAt;
	}
	
	public String getContributingMember() {
		return contributingMember;
	}

	public List<String> getUsers() {
		return users;
	}

	public void setUsers(List<String> users) {
		this.users = users;
	}

	public int getAssociatedTask() {
		return associatedTask;
	}

	public void setAssociatedTask(int associatedTask) {
		this.associatedTask = associatedTask;
	}

	public boolean isHistoryFlag() {
		return historyFlag;
	}

	public void setHistoryFlag(boolean historyFlag) {
		this.historyFlag = historyFlag;
	}

	public List<String> getOldContributingMembers() {
		return oldContributingMembers;
	}

	public void setOldContributingMembers(List<String> oldContributingMembers) {
		this.oldContributingMembers = oldContributingMembers;
	}

	public void setContributingMember(String contributingMember) {
		this.contributingMember = contributingMember;
	}
	
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

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	@Override
	public int compareTo(Object o) {
		if (o instanceof HistoryElement) {
			return this.date.compareTo(((HistoryElement)o).getDate());
		}
		return -1;
	}
}

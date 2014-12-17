/*******************************************************************************
 * Copyright (c) 2014 -- WPI Suite
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors: Team Six-Appeal
 ******************************************************************************/
package edu.wpi.cs.wpisuitetng.modules.taskmanager.model;

import java.awt.Color;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Comparator;
import java.util.Map;

import java.text.SimpleDateFormat;

import com.google.gson.Gson;

import edu.wpi.cs.wpisuitetng.exceptions.WPISuiteException;
import edu.wpi.cs.wpisuitetng.modules.AbstractModel;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.Requirement;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.util.TaskUtil;

/**
 * 
 * The model we will be using to store in the database.  Contains all necessary information about a task
 * 
 * @author nhhughes
 * @author srojas
 * @author jrhennessy
 * @author thhughes
 * @author tmeehan
 * @author krpeffer
 * @author rwang3
 * @author rnorlando
 * @author akshoop
 */
public class Task extends AbstractModel {
	/**
	 * Comparator for tasks by priority
	 */
	public static final Comparator<Task> PRIORITY_COMPARATOR = new Comparator<Task>() {
		@Override
		public int compare(Task task1, Task task2) {
			return Integer.compare(task1.getPriority(), task2.getPriority());
		}
	};
	
	/**
	 * Color categories
	 */
	public static final int CATEGORY_NONE = 0;
	public static final int CATEGORY_GREEN = 1;
	public static final int CATEGORY_YELLOW = 2;
	public static final int CATEGORY_RED = 3;
	public static final int CATEGORY_BLUE = 4;
	
	/**
	 * A map of categories to colors
	 */
	public static final Map<Integer, Color> COLORS = new HashMap<Integer, Color>() {
		private static final long serialVersionUID = 297561479547563732L;

		{
			put(CATEGORY_NONE, Color.WHITE);
			put(CATEGORY_GREEN, new Color(0, 200, 0));
			put(CATEGORY_YELLOW, new Color(250, 217, 0));
			put(CATEGORY_RED, Color.RED);
			put(CATEGORY_BLUE, Color.BLUE);
		}
	};

	private int id;
	private boolean archived;
	private String title;
	private String description;
	private int category;
	private Stage stage;
	private List<String> assignedTo;
	private int estimatedEffort; 
	private int actualEffort;
	private Date dueDate;
	private Requirement requirement;
	private List<Activity> activities;
	private List<Comment> comments;
	private int priority;
	private String currentRequirementName;

	/**
	 * Default constructor (dummy task for initialization)
	 */
	public Task() {
		this("A New Task", "A New Task", CATEGORY_NONE, new Stage("New"), new LinkedList<String>(), 1, 1,
				new Date(), new Requirement(), new LinkedList<Activity>(), new LinkedList<Comment>());
	}

	/**
	 * Constructor for a task that has all of the fields
	 * 
	 * @param title name for the task
	 * @param description explanation of the task
	 * @param assignedTo list of members that are assigned to the task
	 * @param estimatedEffort number that represents how much effort (units of work)
	 * @param actualEffort number that represents the actual effort
	 * @param dueDate when the task is due
	 * @param requirement The specific requirement for the task
	 * @param activities list of activities for the task
	 * @param comments lists of comments members put for the task
	 * @throws IllegalArgumentException
	 */
	public Task(String title, String description, int category, Stage stage,
			List<String> assignedTo, Integer estimatedEffort,
			Integer actualEffort, Date dueDate, 
			Requirement requirement, List<Activity> activities, List<Comment> comments) throws IllegalArgumentException {
		super();
		this.title = TaskUtil.validateTitle(title);
		this.description = TaskUtil.validateDescription(description);
		this.stage = TaskUtil.validateStage(stage);
		this.category = category;
		this.assignedTo = assignedTo;
		this.estimatedEffort = TaskUtil.validateEffort(estimatedEffort);
		this.actualEffort = TaskUtil.validateEffort(actualEffort);
		this.dueDate = TaskUtil.validateDueDate(dueDate);
		this.requirement = requirement;
		this.activities = activities;
		this.comments = comments;
		this.activities = activities;
		this.archived = false;
		this.priority = 0;
		this.currentRequirementName = "";
	}
	
	/**
	 * Checks to see if the objects are equal
	 * @param o Object to compare against
	 */
	@Override
	public boolean equals(Object o) {
		if (this==o) {
			return true;
		}
		
		if (o instanceof Task) {
			Task task = (Task) o;
			return this.id == task.getId();
		}
		return false;
	}
	
	/**
	 * hasChanged checks the ID's of two tasks, if they are the same it checks if the task has changed. 
	 * This returns true iff the tasks has changed
	 * 
	 * @param task to be compared
	 * @return	True if two tasks with the same ID's but different other fields have been compared. 
	 * 			False if the tasks have different ID's 
	 * 			False if the tasks has not changed 
	 * @throws WPISuiteException 
	 */
	public boolean hasChanged(Task task) throws RuntimeException{
		if(this.equals(task)){
			// Set's comparison to be true if anything is not the same between the two tasks
			boolean comparison =
					   this.title.equals(task.getTitle())
					&& this.description.equals(task.getDescription())
					&& this.estimatedEffort == task.getEstimatedEffort()
					&& this.actualEffort == task.getActualEffort()
					&& this.dueDate.equals(task.getDueDate())
					&& this.category == task.getCategory()
					&& this.equalComments(task)
					&& this.equalActivities(task)
					&& this.archived == task.isArchived()
					&& this.stage.equals(task.getStage())
					&& this.assignedTo.equals(task.getAssignedTo())
					&& this.priority == task.getPriority()
					&& this.currentRequirementName.equals(task.getCurrentRequirementName());
			
			return !comparison;		// Return the opposite of this to say true when the task has been changed
		}
		throw new RuntimeException("Tried to compare task with different ID using hasChanged!"); 
		
	}

	/**
	 * Determines the hashCode of the task to be its ID
	 */
	@Override
	public int hashCode() { return this.id; }
	
	/**
	 * A simple toString
	 */
	public String toString() {
		return "Task[" + this.id + "][" + this.title + "](" + this.stage + ")";
	}
	
	/**
	 * @return a JSON text representation of this task
	 */
	public String toJson() {
		return new Gson().toJson(this, Task.class);
	}
	
	/**
	 * Necessary Method Implementation: our task model does not use this.
	 */
	@Override
	public void save() {
		throw new RuntimeException("Called save() on a task. This violates our methodology.");
	}

	/**
	 * Necessary Method Implementation: our task model does not use this.
	 */
	@Override
	public void delete() {
		throw new RuntimeException("Called delete() on a task. This violates our methodology.");
	}

	/**
	 * Necessary Method Implementation: our task model does not use this.
	 */
	@Override
	public Boolean identify(Object o) {
		throw new RuntimeException("Called identify() on a task. Please use equals() instead.");
	}

	/**
	 * @return Title of task
	 */
	public String getTitle() {
		return title;
	}
	
	/**
	 * Sets the category of the task. Should be one of:
	 * Task.CATEGORY_NONE, Task.CATEGORY_GREEN, Task.CATEGORY_YELLOW,
	 * Task.CATEGORY_RED, Task.CATEGORY_BLUE
	 * @param category A category
	 */
	public void setCategory(int category) {
		if (category != CATEGORY_NONE
				&& category != CATEGORY_RED
				&& category != CATEGORY_GREEN
				&& category != CATEGORY_BLUE
				&& category != CATEGORY_YELLOW) {
			this.category = CATEGORY_NONE;
		} else {
			this.category = category;
		}
	}
	
	/**
	 * Gets the category of the task
	 * @return The task's category
	 */
	public int getCategory() {
		return category;
	}
	
	/**
	 * adds changes to the task's history
	 */
	public void addToHistory(Object original, Object newInfo, String field) {
		if(!newInfo.equals(original))
		{
			activities.add(new Activity("The " + field + " was changed to " + newInfo.toString()));
		}
	}
	
	/**
	 * checks to see if the assigned members changed
	 * If so, it adds to the changes to the task history
	 */
	public void checkMemberChange(List<String> oldMembers, List<String> newMembers) {
		if(!oldMembers.equals(newMembers))
		{
			for(String mem: oldMembers)
			{
				if(!newMembers.contains(mem))
					this.activities.add(new Activity(mem + " was removed from " + this.getTitle()));
			}
			
			for(String mem: newMembers)
			{
				if(!oldMembers.contains(mem))
					this.activities.add(new Activity(mem + " was added to " + this.getTitle()));
			}
		}
	}

	/**
	 * Setter for title.  If title is too long, throws an exception
	 * @param title
	 * @throws IllegalArgumentException
	 */
	public void setTitle(String title) throws IllegalArgumentException {
		this.addToHistory(this.getTitle(), title, "Title");
		this.title = TaskUtil.validateTitle(title);
	}

	/**
	 * @return Description of task
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * 
	 * @param description Description of task
	 */
	public void setDescription(String description) throws IllegalArgumentException  {
		this.addToHistory(this.getDescription(), description, "Description");
		this.description = TaskUtil.validateDescription(description);
	}
	
	/**
	 * Gets the stage of this task
	 * @return The stage that this task belongs to
	 */
	public Stage getStage() {
		return this.stage;
	}
	
	/**
	 * Sets the stage of this task
	 * @param stage A stage
	 */
	public void setStage(Stage stage) throws IllegalArgumentException {
		this.addToHistory(this.getStage(), stage, "Stage");
		this.stage = TaskUtil.validateStage(stage);
	}

	/**
	 * 
	 * @return members associated with task
	 */
	public List<String> getAssignedTo() {
		return assignedTo;
	}

	/**
	 * 
	 * @param assignedTo members associated with task
	 */
	public void setAssignedTo(List<String> assignedTo) {
		this.checkMemberChange(this.getAssignedTo(), assignedTo);
		this.assignedTo = assignedTo;
	}

	/**
	 * 
	 * @return Task ID (used to discriminate between different tasks)
	 */
	public int getId() {
		return id;
	}

	/**
	 * 
	 * @param id Task ID (used to discriminate between different tasks)
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * 
	 * @return estimated effort for the task
	 */
	public Integer getEstimatedEffort() {
		return estimatedEffort;
	}

	/**
	 * This setter checks to make sure that the estimated effort is a positive integer and throws an error otherwise
	 * @param estimatedEffort estimated effort for the task
	 * @throws IllegalArgumentException
	 */
	public void setEstimatedEffort(Integer estimatedEffort) throws IllegalArgumentException {
		this.addToHistory(this.getEstimatedEffort(), estimatedEffort, "Estimated Effort");
		//checks that estimatedEffort is positive
		this.estimatedEffort = TaskUtil.validateEffort(estimatedEffort);
	}

	/**
	 * 
	 * @return actual effort for the task
	 */
	public Integer getActualEffort() {
		return actualEffort;
	}

	/**
	 * This setter checks to make sure that the actual effort is a positive integer and throws an error otherwise
	 * @param actualEffort actual effort for task
	 * @throws IllegalArgumentException
	 */
	public void setActualEffort(Integer actualEffort) throws IllegalArgumentException {
		this.addToHistory(this.getActualEffort(), actualEffort, "Actual Effort");
		// making sure that the input value is positive
		this.actualEffort = TaskUtil.validateEffort(actualEffort);
	}

	/**
	 * 
	 * @return due date for the task
	 */
	public Date getDueDate() {
		return dueDate;
	}

	/**
	 * 
	 * @param dueDate due date for the task
	 */
	public void setDueDate(Date dueDate) throws IllegalArgumentException  {
		this.addToHistory(this.getDueDate(), dueDate, "Due Date");
		this.dueDate = TaskUtil.validateDueDate(dueDate);
	}
	
	/**
	 * Retrieves the associated requirement of the task
	 * @return requirement Associated Requirement of the task
	 */
	public Requirement getRequirement() {
		return requirement;
	}
	
	/**
	 * Set the associated requirement of the task
	 * @param aReq Requirement to use for setting
	 */
	public void setRequirement(Requirement aReq) {
		String reqName = aReq.getName();
		this.addToHistory(this.getRequirement(), reqName, "Associated Requirement");
		this.requirement = TaskUtil.validateRequirement(aReq);
	}
	
	/**
	 * Retrieves the current requirement name
	 * @return reqName Current requirement name
	 */
	public String getCurrentRequirementName() {
		return this.currentRequirementName;
	}
	
	/**
	 * Sets the current requirement name
	 * @return reqName Current requirement name
	 */
	public void setCurrentRequirementName(String currentReqName) {
		this.addToHistory(this.getCurrentRequirementName(), currentReqName, "Associated Requirement");
		this.currentRequirementName = currentReqName;
	}
	
	/**
	 * 
	 * @return the priority value to be listed on screen
	 */
	public int getPriority()
	{
		return this.priority;
	}
	
	/**
	 *  sets the priority for the task
	 * @param what the priority should be
	 */
	public void setActivities(List<Activity> activities) throws IllegalArgumentException  {
		this.activities = activities;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	/**
	 * 
	 * @return list of activities on the task
	 */
	public List<Activity> getActivities() {
		return activities;
	}
	
	/**
	 * @return whether or not this task is archived
	 */
	public boolean isArchived() {
		return archived;
	}

	/**
	 * Master setter which updates this task according to a new task without changing its id.
	 * @param updatedTask
	 * @throws IllegalArgumentException
	 */
	public void updateFrom(Task updatedTask) throws IllegalArgumentException {
		this.id = updatedTask.id;
		this.title = new String(TaskUtil.validateTitle(updatedTask.getTitle()));
		this.description = new String(TaskUtil.validateDescription(updatedTask.getDescription()));
		this.stage = TaskUtil.validateStage(updatedTask.getStage());
		this.assignedTo = new LinkedList<String>(updatedTask.getAssignedTo());
		this.estimatedEffort = TaskUtil.validateEffort(new Integer(updatedTask.getEstimatedEffort()));
		this.actualEffort = TaskUtil.validateEffort(new Integer(updatedTask.getActualEffort()));
		this.dueDate = TaskUtil.validateDueDate(new Date(updatedTask.getDueDate().getTime()));
		this.activities = new LinkedList<Activity>(updatedTask.getActivities());
		this.comments = new LinkedList<Comment>(updatedTask.getComments());
		this.category = updatedTask.getCategory();
		this.archived = updatedTask.archived;
		this.priority = updatedTask.priority;
		this.currentRequirementName = updatedTask.getCurrentRequirementName();
//		this.requirement = updatedTask.requirement;
	}
	
	/**
	 * @return list of comments
	 */
	public List<Comment> getComments(){
		return this.comments;
	}
	
	/**
	 * sets the current list of comments to a new list of comments
	 * @param comments
	 */
	public void setComments(List<Comment> comments){
		this.comments = comments;
	}
	
	/**
	 * adds a comment to the list of comments and to the list of activities
	 * @param comment
	 */
	public void addComment(String user, String comment){
		this.comments.add(new Comment(user, comment));
		this.activities.add(new Activity(user + " made a comment!"));
	}
	
	
	/**
	 * Checks all the comments within a task and checks if they are equal to another task's comments
	 * 
	 * @param task to compare
	 * @return True iff the tasks have the same comments
	 */
	public boolean equalComments(Task task){
		for (Comment c: task.getComments()){
			for(Comment com: this.getComments()){
				if(!com.equals(c)){
					return false;
				}
			}
		}
		return true;
	}
	
	/**
	 * Checks all the activities within a task and checks if they are equal to another task's activites
	 * 
	 * @param task to compare
	 * @return True iff the tasks have the same comments
	 */
	public boolean equalActivities(Task task){
		for (Activity a: task.getActivities()){
			for(Activity act: this.getActivities()){
				if(!act.equals(a)){
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * Set the archival of this task
	 * @param b new archival status
	 */
	public void setArchived(boolean b) {
		Date archiveDate = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("h:mm a, MM/dd/yyyy");
		this.activities.add(new Activity("This task was " + (b?"":"Un") + "archived on " + sdf.format(archiveDate)));
		this.archived = b;
	}
}

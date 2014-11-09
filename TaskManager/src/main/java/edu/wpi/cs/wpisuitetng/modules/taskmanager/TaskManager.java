package edu.wpi.cs.wpisuitetng.modules.taskmanager;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import edu.wpi.cs.wpisuite.modules.taskmanager.view.TaskDetailView;
import edu.wpi.cs.wpisuitetng.janeway.modules.IJanewayModule;
import edu.wpi.cs.wpisuitetng.janeway.modules.JanewayTabModel;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.model.Activity;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.model.Member;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.model.Task;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.model.TaskStatus;

/**
 * 
 * @author SixAppeal, tmeehan
 * 
 *
 * A module for managing tasks within Teams
 *
 */
public class TaskManager implements IJanewayModule {

	//Access Level: Package
	String name;
	List<JanewayTabModel> tabs;
	
	Member jill = new Member("jill");
	Member jack = new Member("jack");
	Member bob = new Member("bob");
	Activity start = new Activity (jill, "jill got this party swingn'");
	Activity mid = new Activity (jack, "jack killed the bean stalk");
	Activity end = new Activity (bob, "cleaned it up");
	ArrayList<Member> peasants = new ArrayList<Member>();
	ArrayList<Activity> chores = new ArrayList<Activity>();
	


	@SuppressWarnings("deprecation")
	Date apoc = new Date(112, 11, 21);
	
	
	
	/**
	 * Constructs a TaskManager module and its tabs for the Janeway client.
	 */
	public TaskManager() {
		
		
		
		name = "Task Manager";
		tabs = new ArrayList<JanewayTabModel>();
		
		Task dummyTask = new Task("new title ", "this is a desc", new TaskStatus("cookies"), 
				peasants, 3, 9000, apoc, chores);
		
		tabs.add(new JanewayTabModel(
				name,
				new ImageIcon(),
				new JPanel(),
				new TaskDetailView(dummyTask)));
		
		
		peasants.add(jill);
		peasants.add(jack);
		peasants.add(bob);
		chores.add(start);
		chores.add(mid);
		chores.add(end);
		
		
		
	}
	
	/**
	 * @see edu.wpi.cs.wpisuitetng.janeway.modules.IJanewayModule#getName()
	 */
	@Override
	public String getName() {
		return name;
	}

	/**
	 * @see edu.wpi.cs.wpisuitetng.janeway.modules.IJanewayModule#getTabs()
	 */
	@Override
	public List<JanewayTabModel> getTabs() {
		return tabs;
	}

}

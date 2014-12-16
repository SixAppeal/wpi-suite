package edu.wpi.cs.wpisuitetng.modules.taskmanager.model;

import java.util.Calendar;
import java.text.SimpleDateFormat;

import edu.wpi.cs.wpisuitetng.janeway.config.ConfigManager;
/**
 * Class to capture an activity, which stores information based on changes made to a task
 * 
 * @author nhhughes
 * @author srojas
 * @author jrhennessy
 * @author krpeffer
 * 
 */
public class Activity {
	String user;
	String activity;
	Calendar dateAndTime;
	SimpleDateFormat sdf = new SimpleDateFormat("h:mm a, MMM d, yyyy");
	
	public Activity(){
		this.user = "";
		this.activity = "";
		this.dateAndTime = Calendar.getInstance();
	}
	
	/**
	 *  Constructor for an activity
	 * @param member member that made the comment
	 * @param comment comment that the member made
	 */
	public Activity(String comment){
		this.user = ConfigManager.getConfig().getUserName();
		this.activity = comment;
		this.dateAndTime = Calendar.getInstance();
	}

	/**
	 * @return the comment
	 */
	public String getActivity() {
		return activity;
	}

	/**
	 * @param comment the comment to set
	 */
	public void setActivity(String comment) {
		this.activity = comment;
	}
	
	public Calendar getTime() {
		return this.dateAndTime;
	}
	
	@Override
	public String toString(){
		return (sdf.format(this.dateAndTime.getTime()) + ": " + this.user + " " + this.activity);
	}
}

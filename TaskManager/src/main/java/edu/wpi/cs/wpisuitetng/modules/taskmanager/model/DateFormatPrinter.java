package edu.wpi.cs.wpisuitetng.modules.taskmanager.model;

import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DateFormatPrinter {

	
	private SimpleDateFormat sdf;
	private static DateFormatPrinter instance;
	
	private DateFormatPrinter() {
		sdf = new SimpleDateFormat("h:mm a, MMM d, yyyy");
	}
	
	public static DateFormatPrinter getInstance() {
		 if (instance == null) {
			 instance = new DateFormatPrinter();
		 }
		 return instance;
	}
	
	public String getString(Date dateAndTime, String user, String message) {
		return (sdf.format(dateAndTime) + " -- " + user + ": " + message);
	}
	
}

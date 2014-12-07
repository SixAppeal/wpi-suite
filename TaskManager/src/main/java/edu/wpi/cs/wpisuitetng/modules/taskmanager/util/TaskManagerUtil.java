package edu.wpi.cs.wpisuitetng.modules.taskmanager.util;

import java.awt.FontMetrics;

import javax.swing.SwingUtilities;

/**
 * 
 * @author srojas
 * Class to add general Utilities for using in the Task Manager
 *
 */
public class TaskManagerUtil {

	/**
	 * Shortens the string to fit a number of pixels
	 * Note: check if the font changes from being normal to bold in different components and if that affects it @see ColumnEditView addStages()
	 * 
	 * @param str string that you want to shorten
	 * @param pixelLimit the max pixel limit that you want to use
	 * @param fm the Font Metrics of the container you want to use (@see Column Edit View where it is used)
	 * returns: the same string if it's shorter than the limit in pixels or the shortened string if it is larger
	 */
	
	public static String reduceString(String str, int pixelLimit, FontMetrics fm){

		Integer stringL = str.length();
		String shortTitle = str;
		
		Integer stringW = SwingUtilities.computeStringWidth(fm ,str);

		while (stringW > pixelLimit){
			stringL = shortTitle.length();
			shortTitle = shortTitle.substring(0, stringL - 1);
			stringW = SwingUtilities.computeStringWidth(fm, shortTitle);
		}
		return shortTitle;
	}


}


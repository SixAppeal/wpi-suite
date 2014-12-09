package edu.wpi.cs.wpisuitetng.modules.taskmanager.util;

import java.awt.Color;
import java.awt.FontMetrics;

import javax.swing.SwingUtilities;

/**
 * 
 * @author srojas
 * Class to add general Utilities for using in the Task Manager
 *
 */
public class TaskManagerUtil {
	public static final Color BACKGROUND_COLOR = new Color(7, 63, 131);
	public static final Color SIDEBAR_COLOR = new Color(245, 245, 245);
	public static final Color TOOLBAR_COLOR = new Color(255, 255, 255);

	/**
	 * Shortens the string to fit a number of pixels
	 * Note: check if the font changes from being normal to bold in different components and if that affects it @see ColumnEditView addStages()
	 * 
	 * @param str string that you want to shorten
	 * @param pixelLimit the max pixel limit that you want to use
	 * @param fm the Font Metrics of the container you want to use (@see Column Edit View where it is used)
	 * returns: the same string if it's shorter than the limit in pixels or the shortened string if it is larger
	 */
	public static String reduceString(String str, int pixelLimit, FontMetrics fm) {
		int width = SwingUtilities.computeStringWidth(fm, str);
		
		while (width > pixelLimit && str.length() > 1) {
			str = str.substring(0, str.length() - 2) + "\u2026";
			width = SwingUtilities.computeStringWidth(fm, str);
		}
		return str;
	}
	
	/**
	 * Cuts a string and simulates a wrapping around feature FOR TWO LINES ONLY
	 * NOTE: should work, but it might be off by a letter*
	 * 
	 * @param str string to cut
	 * @param pixelLimit pixel limit that the string can take in your component
	 * @param fm the Font Metrics of the container you want to use (@see Column Edit View where it is used).
	 * @return the string with a \n that sends part of the string that does not fit to the next line
	 */
	public static String separateString(String str, int pixelLimit, FontMetrics fm) {
		int width = SwingUtilities.computeStringWidth(fm, str);
		String str2 = str;
		int count = 0;
		while (width > pixelLimit) {
			str = str.substring(0, str.length()-1);
			count++;
			width = SwingUtilities.computeStringWidth(fm, str);
		}
		for (int i = 0 ; i < str.length(); i ++){
			if(str.charAt(str.length()-i) == ' '){
				//increments count to prevent the method from cutting of a word. 
				str = str.substring(0,str.length()-i);
				count += i-1;
			}
		}
		//str2 is a string equal to the original string passed
		str2 = str2.substring(str2.length()-count,str2.length());
		str2 = "\n" + str2;
		//makes sure it does not span three lines
		if (SwingUtilities.computeStringWidth(fm, str2)>pixelLimit){
			while (width > pixelLimit) {
				str2 = str2.substring(0, str2.length()-1);
				width = SwingUtilities.computeStringWidth(fm, str2);
			}
		}
		//returns the string that spans two lines
		return str + str2;
	}
	
}


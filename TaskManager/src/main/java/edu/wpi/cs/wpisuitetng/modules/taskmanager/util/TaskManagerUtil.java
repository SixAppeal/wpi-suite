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
}


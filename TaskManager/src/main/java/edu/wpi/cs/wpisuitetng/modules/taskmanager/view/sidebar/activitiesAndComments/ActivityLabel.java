package edu.wpi.cs.wpisuitetng.modules.taskmanager.view.sidebar.activitiesAndComments;

import javax.swing.JTextArea;

import java.awt.Dimension;
import java.awt.SystemColor;
import java.awt.Color;
import javax.swing.border.MatteBorder;

public class ActivityLabel extends JTextArea {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7750945195661034198L;

	
	/**
	 * Create the label.
	 */
	public ActivityLabel(String text) {
		
		this.setText(text);
		setPreferredSize(new Dimension(300, 40));
		setCaretColor(SystemColor.activeCaption);
		setSelectedTextColor(Color.DARK_GRAY);
		setEditable(false);
		setBorder(new MatteBorder(0, 0, 3, 0, (Color) SystemColor.activeCaption));
		setLineWrap(true);
		setWrapStyleWord(true);
		//setMaximumSize(new Dimension(280, 40));

	}

}
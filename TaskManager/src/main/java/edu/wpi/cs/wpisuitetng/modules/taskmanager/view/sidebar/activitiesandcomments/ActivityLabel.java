package edu.wpi.cs.wpisuitetng.modules.taskmanager.view.sidebar.activitiesandcomments;

import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SpringLayout;
import javax.swing.JScrollPane;
import javax.swing.BoxLayout;
import javax.swing.border.LineBorder;
import java.awt.SystemColor;
import java.awt.Color;

public class ActivityLabel extends JPanel {

	/**
	 * Create the panel.
	 */
	public ActivityLabel() {
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		
		JScrollPane scrollPane = new JScrollPane();
		add(scrollPane);
		
		JTextArea label = new JTextArea();
		label.setSelectedTextColor(Color.DARK_GRAY);
		label.setEditable(false);
		label.setBorder(new LineBorder(SystemColor.activeCaption, 1, true));
		label.setLineWrap(true);
		label.setWrapStyleWord(true);
		scrollPane.setViewportView(label);

	}

}

package edu.wpi.cs.wpisuitetng.modules.taskmanager.view.sidebar.activitiesandcomments;

import javax.swing.JPanel;
import javax.swing.BoxLayout;
import javax.swing.JScrollPane;
import java.awt.Component;
import java.awt.BorderLayout;
import net.miginfocom.swing.MigLayout;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.LineBorder;
import java.awt.SystemColor;

public class ActivityDisplay extends JPanel{
	public ActivityDisplay() {
		setLayout(new MigLayout("", "[grow]", "[grow][120.00,grow][]"));
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBorder(new LineBorder(SystemColor.activeCaption, 1, true));
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		add(scrollPane, "cell 0 0 1 2,grow");
	}

}

package edu.wpi.cs.wpisuitetng.modules.taskmanager;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

import edu.wpi.cs.wpisuitetng.janeway.modules.IJanewayModule;
import edu.wpi.cs.wpisuitetng.janeway.modules.JanewayTabModel;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.presenter.*;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.view.MultiColumnView;

/**
 * 
 * @author SixAppeal
 *
 * A module for managing tasks within Teams
 *
 */
public class TaskManager implements IJanewayModule {

	//Access Level: Package
	String name;
	List<JanewayTabModel> tabs;
	Gateway gateway;
	JPanel headerPanel;
	JPanel mainPanel;
	MultiColumnView columnView;
	
	/**
	 * Constructs a TaskManager module and its tabs for the Janeway client.
	 */
	public TaskManager() {
		
		name = "Task Manager";
		tabs = new ArrayList<JanewayTabModel>();
		gateway = new Gateway();
		headerPanel = new JPanel();
		mainPanel = new JPanel();
		columnView = new MultiColumnView();
		
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.X_AXIS));
		// TODO: mainPanel.add(sidebarView);
		mainPanel.add(columnView);
		
		tabs.add(new JanewayTabModel(
				name,
				new ImageIcon(),
				headerPanel,
				mainPanel));
		
		TaskPresenter taskPresenter = new TaskPresenter();
		gateway.addPresenter("TaskPresenter", taskPresenter);
		gateway.addView("ColumnView", columnView);
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

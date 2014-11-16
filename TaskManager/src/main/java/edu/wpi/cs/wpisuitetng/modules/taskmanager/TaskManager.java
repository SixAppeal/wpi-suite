 package edu.wpi.cs.wpisuitetng.modules.taskmanager;


import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

import edu.wpi.cs.wpisuitetng.janeway.modules.IJanewayModule;
import edu.wpi.cs.wpisuitetng.janeway.modules.JanewayTabModel;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.presenter.*;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.view.columnar.MultiColumnView;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.view.sidebar.SidebarView;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.view.toolbar.ToolbarView;

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
	Gateway gateway;
	JPanel mainPanel;
	MultiColumnView columnView;
	SidebarView sidebarView;
	TaskPresenter taskPresenter;
	
	ToolbarView toolbarview = new ToolbarView(true);
	
	/**
	 * Constructs a TaskManager module and its tabs for the Janeway client.
	 */
	public TaskManager() {
		name = "Task Manager";
		tabs = new ArrayList<JanewayTabModel>();
		
		taskPresenter = new TaskPresenter();

		gateway = new Gateway();
		mainPanel = new JPanel();
		columnView = new MultiColumnView();
		sidebarView = new SidebarView();
		
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.X_AXIS));
		mainPanel.add(sidebarView);
		mainPanel.add(columnView);
		
		tabs.add(new JanewayTabModel(
				name,
				new ImageIcon(),
				toolbarview,
				mainPanel));
		
		gateway.addPresenter("TaskPresenter", taskPresenter);
		gateway.addView("SidebarView", sidebarView);
		gateway.addView("ColumnView", columnView);
		gateway.addView("ToolbarView", toolbarview);

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

	@Override
	public void finishInit() {
		gateway.toPresenter("TaskPresenter", "getAllTasks");
		
	}

}

/*******************************************************************************
 * Copyright (c) 2014 -- WPI Suite
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors: Will Rensselaer, Thomas Meehan, Alex Shoop, Ryan Orlando, Troy Hughes,
 * Jill Hennessy, Ryan Wang, Santiago Rojas, Dan Seaman, Kyle Peffer, Nathan Hughes, Will Temple
 ******************************************************************************/

package edu.wpi.cs.wpisuitetng.modules.taskmanager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

import edu.wpi.cs.wpisuitetng.janeway.modules.IJanewayModule;
import edu.wpi.cs.wpisuitetng.janeway.modules.JanewayTabModel;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.localcache.Cache;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.localcache.LocalCache;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.model.Task;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.presenter.*;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.view.columnar.ColumnView;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.view.sidebar.ColumnEditView;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.view.sidebar.MemberListHandler;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.view.sidebar.SidebarView;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.view.toolbar.ToolbarView;

/**
 * @author SixAppeal
 * 
 * @author wmtemple
 * @author thhughes
 * @author nhhughes
 * @author srojas
 * @author tmeehan
 * @author krpeffer
 * @author akshoop
 * @author dpseaman
 * @author jrhennessy
 * @author rwang3
 * @author tmeehan
 * @author wavanrensselaer
 * @author rnorlando
 *
 * A module for managing tasks within Teams
 */
public class TaskManager implements IJanewayModule {

	private Timer t;
	
	//Access Level: Package
	String name;
	List<JanewayTabModel> tabs;
	Gateway gateway;
	JPanel mainPanel;
	ColumnView columnView;
	SidebarView sidebarView;
	TaskPresenter taskPresenter;
	Cache localCache;
	ToolbarView toolbarview = new ToolbarView(true);
	MemberListHandler memberHandler;
	
	/**
	 * Constructs a TaskManager module and its tabs for the Janeway client.
	 * @throws IOException 
	 */
	public TaskManager() throws IOException {
		name = "Task Manager";
		tabs = new ArrayList<JanewayTabModel>();

		gateway = new Gateway();
		mainPanel = new JPanel();
		columnView = new ColumnView();
		memberHandler = new MemberListHandler();
		sidebarView = new SidebarView();
		
		localCache = new LocalCache();
		taskPresenter = new TaskPresenter(localCache);
		sidebarView.setCache((LocalCache)localCache);
		
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.X_AXIS));
		mainPanel.add(columnView);
		mainPanel.add(sidebarView);
		
		tabs.add(new JanewayTabModel(
				name,
				new ImageIcon(),
				toolbarview,
				mainPanel));
		
		gateway.addPresenter("TaskPresenter", taskPresenter);
		gateway.addPresenter("LocalCache", localCache);
		gateway.addView("SidebarView", sidebarView);
		gateway.addView("ColumnView", columnView);
		gateway.addView("ToolbarView", toolbarview);
		gateway.addView("MemberListHandler", memberHandler);
		
		localCache.subscribe("task:TaskPresenter:updateTasks");
		localCache.subscribe("member:TaskPresenter:notifyMemberHandler");
		localCache.subscribe("task:TaskPresenter:updateSearch");
		localCache.subscribe("stages:TaskPresenter:setStages");
		
		t = new Timer();

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

	/**
	 * @see edu.wpi.cs.wpisuitetng.janeway.modules.IJanewayModule#finishInit()
	 */
	@Override
	public void finishInit() {
		
		gateway.toPresenter("LocalCache", "sync", "task");
		gateway.toPresenter("LocalCache", "sync", "member");
		gateway.toPresenter("LocalCache", "sync", "archive");
		gateway.toPresenter("LocalCache", "sync", "stages");
		gateway.toView("ColumnView", "reflow");
		
		t.scheduleAtFixedRate(new TimerTask() {

			@Override
			public void run() {
				gateway.toPresenter("LocalCache", "sync", "task");
				gateway.toPresenter("LocalCache", "sync", "member");
				gateway.toPresenter("LocalCache", "sync", "archive");
				gateway.toPresenter("LocalCache", "sync", "stages");
				gateway.toView("ColumnView", "reflow");
				gateway.toView("SidebarView", "reflowTasks");
			}
			
		}, 0, 2000);
		//gateway.toPresenter("TaskPresenter", "getMembers");
	}

	/**
	 * @see edu.wpi.cs.wpisuitetng.janeway.modules.IJanewayModule#cleanup()
	 */
	@Override
	public void cleanup() {
		t.cancel();
	}

}

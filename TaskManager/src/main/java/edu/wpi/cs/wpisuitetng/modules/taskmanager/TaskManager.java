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

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Semaphore;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

import edu.wpi.cs.wpisuitetng.janeway.modules.IJanewayModule;
import edu.wpi.cs.wpisuitetng.janeway.modules.JanewayTabModel;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.localcache.Cache;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.localcache.InitializeManager;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.localcache.ThreadSafeLocalCache;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.model.Stage;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.model.StageList;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.model.Task;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.presenter.*;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.util.TaskManagerUtil;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.view.columnar.ColumnView;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.view.components.GradientPanel;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.view.sidebar.MemberListHandler;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.view.sidebar.SidebarView;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.view.toolbar.ToolbarView;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;

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
	ToolbarView toolbarview = new ToolbarView();
	MemberListHandler memberHandler;
	
	/**
	 * Constructs a TaskManager module and its tabs for the Janeway client.
	 * @throws IOException 
	 */
	public TaskManager() throws IOException {
		name = "Task Manager";
		tabs = new ArrayList<JanewayTabModel>();

		gateway = new Gateway();
		mainPanel = new GradientPanel();
		columnView = new ColumnView();
		//memberHandler = new MemberListHandler();
		sidebarView = new SidebarView();
		
		localCache = new ThreadSafeLocalCache();
		taskPresenter = new TaskPresenter(localCache);
		sidebarView.setCache((ThreadSafeLocalCache)localCache);
		
		mainPanel.setBackground(TaskManagerUtil.BACKGROUND_COLOR);
		mainPanel.setLayout(new GridBagLayout());
		
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.BOTH;
		gbc.insets = new Insets(0, 0, 0, 0);
		gbc.weighty = 1.0;
		gbc.weightx = 1.0;
		gbc.gridx = 0;
		gbc.gridy = 0;
		mainPanel.add(columnView, gbc);
		
		gbc.weightx = 0;
		gbc.gridx = 1;
		mainPanel.add(sidebarView, gbc);
		
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
		gateway.addView("MemberListHandler", MemberListHandler.getInstance());
				
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
		Semaphore waitingForInit = new Semaphore(-2, true); //semaphore to wait for all pending request to come in
		final Request networkRequestStages = Network.getInstance().makeRequest(
				"taskmanager/stages", HttpMethod.GET); //request for stages
		networkRequestStages.addObserver(new InitializeManager(localCache, waitingForInit));
		networkRequestStages.send();
		final Request networkRequestTasks = Network.getInstance().makeRequest(
				"taskmanager/task", HttpMethod.GET); //request for tasks
		networkRequestTasks.addObserver(new InitializeManager(localCache, waitingForInit));
		networkRequestTasks.send();
		final Request networkRequestMembers = Network.getInstance().makeRequest(
				"core/user", HttpMethod.GET); //request for members
		networkRequestMembers.addObserver(new InitializeManager(localCache, waitingForInit));
		networkRequestMembers.send();
		try {
			waitingForInit.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		//reflow with new information
		Stage[] toConvert = (Stage[])localCache.retrieve("stages");
		StageList stageList = new StageList();
		for (Stage toAdd : toConvert) {
			stageList.add(toAdd);
		}
		gateway.toView("ColumnView", "setState", (Task[])localCache.retrieve("task"), stageList);
		gateway.toView("SidebarView", "reflowTasks");
		gateway.toPresenter("TaskPresenter", "setStages");
		//initial long pull request
		gateway.toPresenter("LocalCache", "sync", "tasks");
//		gateway.toPresenter("LocalCache", "sync", "requirement");
		
		t.scheduleAtFixedRate(new TimerTask() {

			@Override
			public void run() {
//				gateway.toPresenter("LocalCache", "sync", "member");
//				gateway.toPresenter("LocalCache", "sync", "stages");
//				gateway.toPresenter("LocalCache", "sync", "requirement");
				//gateway.toView("ColumnView", "reflow");
				//gateway.toView("SidebarView", "reflowTasks");
			}
			
		}, 0, 1000);
	}

	/**
	 * @see edu.wpi.cs.wpisuitetng.janeway.modules.IJanewayModule#cleanup()
	 */
	@Override
	public void cleanup() {
		t.cancel();
	}

}

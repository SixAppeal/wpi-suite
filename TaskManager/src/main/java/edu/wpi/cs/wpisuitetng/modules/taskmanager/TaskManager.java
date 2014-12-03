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
import edu.wpi.cs.wpisuitetng.modules.taskmanager.presenter.*;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.view.columnar.ColumnView;
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
		
		localCache.subscribe("task:TaskPresenter:updateStages");
		localCache.subscribe("member:TaskPresenter:notifyMemberHandler");
		localCache.subscribe("task:TaskPresenter:updateSearch");
		
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
		t.scheduleAtFixedRate(new TimerTask() {

			@Override
			public void run() {
				gateway.toPresenter("LocalCache", "sync", "task");
				gateway.toPresenter("LocalCache", "sync", "member");
				gateway.toPresenter("LocalCache", "sync", "archive");
				gateway.toPresenter("LocalCache", "sync", "stage");
			}
			
		}, 0, 500);
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

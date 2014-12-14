/*******************************************************************************
 * 
 * Copyright (c) 2014 -- WPI Suite
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors: Nathan Hughes, Alexander Shoop, Will Rensselaer, Thomas Meehan, Ryan Orlando, Troy Hughes, Nathan Hughes
 ******************************************************************************/

package edu.wpi.cs.wpisuitetng.modules.taskmanager.view.sidebar;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.awt.Color;
import java.awt.Insets;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JTabbedPane;
import javax.swing.UIManager;
import javax.swing.plaf.basic.BasicTabbedPaneUI;

import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.Requirement;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.localcache.ThreadSafeLocalCache;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.model.StageList;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.model.Task;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.presenter.Gateway;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.util.TaskManagerUtil;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.view.IView;

/**
 * The sidebar that will be the container for task creation, editing, and reading
 * @author wavanrensselaer
 * @author akshoop
 * @author rnorlando
 * @author srojas
 * @author thhughes
 * @author nhhughes
 * @author dpseaman
 * @author tmeehan
 */
public class SidebarView extends JTabbedPane implements IView {
	private static final long serialVersionUID = -9157611802121055998L;

	public static final Color SIDEBAR_COLOR = new Color(245, 245, 245);

	private Gateway gateway;

	private StageList stages;
	@SuppressWarnings("unused")
	private List<String> requirements;
	private ThreadSafeLocalCache cache;
	// Components
	private List<IView> viewList;
	private SearchBox searchView;
	private ColumnEditView columnEditView;

	/**
	 * Constructs a sidebar view
	 * @throws IOException 
	 */

	public SidebarView() throws IOException {
		this.viewList = new ArrayList<IView>();
		this.stages = new StageList();
		this.requirements = new ArrayList<String>();
		
		this.searchView = new SearchBox();
		this.viewList.add(searchView);
		
		this.columnEditView = new ColumnEditView();
		this.viewList.add(columnEditView);
		
		this.setUI(new BasicTabbedPaneUI() {
			@Override
			public void installDefaults() {
				Color selected = UIManager.getColor("TabbedPane.selected");
				UIManager.put("TabbedPane.selected", TaskManagerUtil.SIDEBAR_COLOR);
				
				super.installDefaults();
				
				UIManager.put("TabbedPane.selected", selected);
				
				this.contentBorderInsets = new Insets(0, 0, 0, 0);
				this.highlight = TaskManagerUtil.SIDEBAR_COLOR;
				this.lightHighlight = TaskManagerUtil.SIDEBAR_COLOR;
				
			}
		});

		this.setOpaque(false);
		this.setTabPlacement(JTabbedPane.LEFT);
		this.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
		this.addTab(null, new ImageIcon(this.getClass().getResource("icon_search.png")),
				searchView);
		this.addTab(null, new ImageIcon(this.getClass().getResource("icon_column_edit.png")),
				columnEditView);
	}
	
	/**
	 * Adds a creation panel to the sidebar
	 */
	public void addCreatePanel() {
		// if there is a tab with the edit pane 
		for (IView view : viewList) {
			if (view instanceof TaskCreateView) {
				if (((TaskCreateView) view).isEmpty()) {
					this.setSelectedComponent((TaskCreateView)view);
					return;
				}
			}
		}
		TaskCreateView createView = new TaskCreateView(this.stages);
		createView.setGateway(this.gateway);
		this.viewList.add(createView);
		this.addTab(null, new ImageIcon(this.getClass().getResource("icon_plus.png")),
				createView);
		this.setSelectedComponent(createView);
		this.setVisible(true);
		
		createView.fixFocus();
	}
	
	/**
	 * Removes a creation panel from the sidebar
	 * @param createView The create panel to remove
	 * Shows the default panel
	 */
	public void removeCreatePanel(TaskCreateView createView) {
		try {
			this.removeTabAt(this.indexOfComponent(createView));
			this.viewList.remove(createView);
		} catch (IndexOutOfBoundsException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Adds an edit panel to the sidebar
	 * @param task The task to edit
	 */
	public void addEditPanel(Task task) {
		
		//if there is a tab with the edit pane 
		for (IView view : viewList) {
			if (view instanceof TaskEditView) {
				if (task.equals(((TaskEditView) view).getTask())) {
					setSelectedComponent((TaskEditView)view);
					return;
				}
			}
		}
		
		
		TaskEditView editView = new TaskEditView(task, stages);
		editView.setGateway(this.gateway);
		this.viewList.add(editView);
		this.addTab(null, new ImageIcon(this.getClass().getResource("icon_pencil.png")),
				editView);
		this.setSelectedComponent(editView);
		this.setVisible(true);
	}
	
	/**
	 * Passes the retrieved requirements array to the Task Edit View
	 */
	public void passInRequirements(String requirements) {
		Requirement[] requirementsArray = Requirement.fromJsonArray(requirements);
		
		// check if tab exists with the edit pane
		for (IView view : viewList) {
			if (view instanceof TaskEditView) {
				((TaskEditView) view).getRequirements(requirementsArray);
			}
		}
	}
	
	/**
	 * Removes an edit view from the sidebar
	 * @param editView The edit view to remove
	 */
	public void removeEditPanel(TaskEditView editView) {
		try {
			this.removeTabAt(this.indexOfComponent(editView));
			this.viewList.remove(editView);
		} catch (IndexOutOfBoundsException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Toggles the visibility of the sidebar
	 */
	public void toggle() {
		this.setVisible(!this.isVisible());
	}

	@Override
	public void setGateway(Gateway gateway) {
		this.gateway = gateway;
		for (IView view : this.viewList) {
			view.setGateway(this.gateway);
		}
	}
	
	/**
	 * Updates local cache for search box
	 * @param all_tasks All tasks (archived and unarchived) from local cache
	 * @throws IOException 
	 */
	public void updateSearchBox(ArrayList<Task> all_tasks) throws IOException {
		this.searchView.updateIndex(all_tasks);
	}
	
	public void setStages(StageList sl) {
		this.stages = sl;
		for (IView v : this.viewList) {
			if (v instanceof TaskCreateView) {
				((TaskCreateView) v).setStages(this.stages);
			}
		}
		columnEditView.setStages(this.stages);
	}

	public Task findTask(Task [] tasks, int id) {
		for (Task t: tasks) {
			if (t.getId() == id) {
				return t;
			}
		}
		return null;
	}
	
	public void setCache(ThreadSafeLocalCache cache) {
		this.cache = cache;
	}
	
	
	/**
	 * Updates all of the tasks in each of the TaskEditViews
	 * 
	 * @param tasks
	 */
	public void updateEditViews(Task[] tasks){
		List<Task> taskList = Arrays.asList(tasks);

		for (IView view : viewList) {
			if (view instanceof TaskEditView) {
				for(Task aTask: taskList){
					if (((TaskEditView) view).getTask().getId() == aTask.getId()) {
						((TaskEditView) view).updateEVTask(aTask);
						break;
					}		
				}
				}
			}
		}
		
	
	
	
	
	
	/**
	 * Goes through and re adds all of the tasks to the TaskEditViews
	 */
	
	/*
	 * This method causes an infinite loop and crashes the server everytime I run it... Not sure what's up here. 
	 * 
	 */
	public void reflowTasks() {
		Task[] reference = (Task[]) this.cache.retrieve("task");
		for (int i = 0; i < this.getComponentCount(); i ++) {
			if (this.getComponent(i) instanceof TaskEditView) {
				Task taskToFix = ((TaskEditView)this.getComponent(i)).getTask();
				Task updated = findTask(reference, taskToFix.getId());
				if (updated != null) {
					((TaskEditView)this.getComponent(i)).updateEverything(updated);
				}
			}
		}
	}
	
}

/*******************************************************************************
 * 
 * Copyright (c) 2014 -- WPI Suite
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors: Nathan Hughes, Alexander Shoop
 ******************************************************************************/

package edu.wpi.cs.wpisuitetng.modules.taskmanager.view.sidebar;

import java.util.ArrayList;
import java.util.List;
import java.awt.Color;
import java.awt.Dimension;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JTabbedPane;
import javax.swing.ScrollPaneConstants;

import edu.wpi.cs.wpisuitetng.modules.taskmanager.model.Task;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.presenter.Gateway;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.view.IView;

/**
 * The sidebar that will be the container for task creation, editing, and reading
 * @author wavanrensselaer
 * @author akshoop
 * @author rnorlando
 * @author srojas
 * @author thhughes
 * @author nhhughes
 */
public class SidebarView extends JTabbedPane implements IView {
	private static final long serialVersionUID = -9157611802121055998L;
	
	public static final Color SIDEBAR_COLOR = new Color(245, 245, 245);

	private Gateway gateway;

	// Components
	private List<IView> viewList;
	private SearchBox searchView;

	/**
	 * Constructs a sidebar view
	 * @throws IOException 
	 */
	public SidebarView() throws IOException {
		this.viewList = new ArrayList<IView>();
		
		this.searchView = new SearchBox();
		this.viewList.add(searchView);
		
		this.setTabPlacement(JTabbedPane.LEFT);
		this.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
		this.addTab(null, new ImageIcon(this.getClass().getResource("icon_search.png")),
				searchView);
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
		
		TaskCreateView createView = new TaskCreateView();
		createView.setGateway(this.gateway);
		this.viewList.add(createView);
		this.addTab(null, new ImageIcon(this.getClass().getResource("icon_plus.png")),
				createView);
		this.setSelectedComponent(createView);
	}
	
	/**
	 * Removes a creation panel from the sidebar
	 * @param createView The create panel to remove
	 */
	public void removeCreatePanel(TaskCreateView createView) {
		try {
			this.removeTabAt(this.indexOfComponent(createView));
			this.viewList.remove(createView);
		} catch (IndexOutOfBoundsException e) {
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
		
		
		TaskEditView editView = new TaskEditView(task);
		editView.setGateway(this.gateway);
		this.viewList.add(editView);
		this.addTab(null, new ImageIcon(this.getClass().getResource("icon_pencil.png")),
				editView);
		this.setSelectedComponent(editView);
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
			// Do nothing
		}
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
}

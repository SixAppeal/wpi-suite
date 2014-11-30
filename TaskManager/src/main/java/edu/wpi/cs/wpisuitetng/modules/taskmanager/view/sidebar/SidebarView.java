package edu.wpi.cs.wpisuitetng.modules.taskmanager.view.sidebar;

import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JTabbedPane;

import edu.wpi.cs.wpisuitetng.modules.taskmanager.model.Task;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.presenter.Gateway;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.view.IView;

/**
 * The sidebar that will be the container for task creation, editing, and reading
 * @author wavanrensselaer
 * @author akshoop
 * @author rnorlando
 * @author srojas
 * @author Thhughes
 * 
 */
public class SidebarView extends JTabbedPane implements IView {
	private static final long serialVersionUID = -9157611802121055998L;

	private Gateway gateway;
	
	// Components
	private List<IView> viewList;

	/**
	 * Constructs a sidebar view
	 */
	public SidebarView() {
		super();
		
		this.viewList = new ArrayList<IView>();
		
		TaskSearchView searchView = new TaskSearchView();
		this.viewList.add(searchView);
		
		this.setTabPlacement(JTabbedPane.LEFT);
		this.addTab(null, new ImageIcon(this.getClass().getResource("icon_search.png")),
				searchView);
	}
	
	/**
	 * Adds a creation panel to the sidebar
	 */
	public void addCreatePanel() {
		TaskCreateView createView = new TaskCreateView();
		createView.setGateway(this.gateway);
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
		} catch (IndexOutOfBoundsException e) {
		}
	}

	/**
	 * Adds an edit panel to the sidebar
	 * @param task The task to edit
	 */
	public void addEditPanel(Task task) {
		TaskEditView editView = new TaskEditView(task);
		editView.setGateway(this.gateway);
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
		} catch (IndexOutOfBoundsException e) {
			// Do nothing
		}
	}

	/**
	 * @see IView.setGateway
	 */
	@Override
	public void setGateway(Gateway gateway) {
		this.gateway = gateway;
		for (IView view : this.viewList) {
			view.setGateway(this.gateway);
		}
	}

}

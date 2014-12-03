package edu.wpi.cs.wpisuitetng.modules.taskmanager.view.sidebar;

import java.util.ArrayList;
import java.util.List;

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
 * @author Thhughes
 * 
 */
public class SidebarView extends JTabbedPane implements IView {
	private static final long serialVersionUID = -9157611802121055998L;

	private Gateway gateway;
	
<<<<<<< HEAD
	private JPanel container;
	private JPanel curView;
	private TaskEditView editView;
	private TaskCreateView createView;
	private TaskDefaultView defaultView;
	
=======
	// Components
	private List<IView> viewList;

>>>>>>> task-tab-sidebar
	/**
	 * Constructs a sidebar view
	 */
	public SidebarView() {
<<<<<<< HEAD
		this.container = new JPanel();
		this.createView = new TaskCreateView();
		this.editView = new TaskEditView();
		this.defaultView = new TaskDefaultView();
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
=======
		super();
>>>>>>> task-tab-sidebar
		
		this.viewList = new ArrayList<IView>();
		
<<<<<<< HEAD
		this.curView = this.defaultView;
		this.createView.setVisible(false);
		this.editView.setVisible(false);
		
		this.container.add(this.defaultView);
		this.container.add(this.createView);
		this.container.add(this.editView);
		this.add(container);
=======
		TaskSearchView searchView = new TaskSearchView();
		this.viewList.add(searchView);
		
		this.setTabPlacement(JTabbedPane.LEFT);
		this.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
		this.addTab(null, new ImageIcon(this.getClass().getResource("icon_search.png")),
				searchView);
>>>>>>> task-tab-sidebar
	}
	
	/**
	 * Adds a creation panel to the sidebar
	 */
	public void addCreatePanel() {
		// if there is a tab with the edit pane 
		for (IView view : viewList){
			if (view instanceof TaskCreateView){
				if (((TaskCreateView)view).isEmpty()){
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
		for (IView view : viewList){
			if (view instanceof TaskEditView){
				if (task.equals(((TaskEditView)view).getTask())){
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
<<<<<<< HEAD
=======
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

	/**
>>>>>>> task-tab-sidebar
	 * @see IView.setGateway
	 */
	@Override
	public void setGateway(Gateway gateway) {
		this.gateway = gateway;
<<<<<<< HEAD
		this.editView.setGateway(this.gateway);
		this.createView.setGateway(this.gateway);
=======
		for (IView view : this.viewList) {
			view.setGateway(this.gateway);
		}
>>>>>>> task-tab-sidebar
	}

}

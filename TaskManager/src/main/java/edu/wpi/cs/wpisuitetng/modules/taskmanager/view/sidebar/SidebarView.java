package edu.wpi.cs.wpisuitetng.modules.taskmanager.view.sidebar;

import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
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
 * 
 */
public class SidebarView extends JPanel implements IView {
	private static final long serialVersionUID = -9157611802121055998L;

	Gateway gateway;


	private TaskDefaultView defaultView;
	private JTabbedPane tabPane;
	private List<IView>  viewList; // list for views

	/**
	 * Constructs a sidebar view
	 */
	public SidebarView() {


		this.defaultView = new TaskDefaultView();

		this.tabPane = new JTabbedPane(JTabbedPane.LEFT);
		this.tabPane.addTab("Default View",this.defaultView);
		this.viewList = new ArrayList<IView>();

		this.setBackground(new Color(250, 250, 250));
		this.setMinimumSize(new Dimension(300, 0));
		this.setPreferredSize(new Dimension(300, 500));
		this.setMaximumSize(new Dimension(300, Integer.MAX_VALUE));
		this.add(tabPane);
	}



	/**
	 * Shows the creation panel 
	 */
	public void addCreatePanel() {
		TaskCreateView createView = new TaskCreateView();
		tabPane.addTab("Create Task", createView);
		this.tabPane.setSelectedIndex(this.tabPane.indexOfComponent(createView));
		this.viewList.add(createView);
	}

	/**
	 * Shows the edit panel
	 * @param task The task to edit
	 */
	public void addEditPanel(Task task) {
		TaskEditView editView = new TaskEditView();
		tabPane.addTab("Create Task", editView);
		this.tabPane.setSelectedIndex(this.tabPane.indexOfComponent(editView));

	}

	/**
	 * Shows the detail panel
	 * @param task The task to display
	 */
	public void addDetailPanel(Task task) {
		TaskDetailView detailView = new TaskDetailView();
		tabPane.addTab("Create Task", detailView);
		this.tabPane.setSelectedIndex(this.tabPane.indexOfComponent(detailView));

	}

	/**
	 * @see IView.setGateway
	 */
	@Override
	public void setGateway(Gateway gateway) {
		this.gateway = gateway;

	}

}

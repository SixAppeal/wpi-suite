package edu.wpi.cs.wpisuitetng.modules.taskmanager.view.sidebar;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

import edu.wpi.cs.wpisuitetng.modules.taskmanager.model.Task;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.presenter.Gateway;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.view.IView;

/**
 * The sidebar that will be the container for task creation, editing, and reading
 * @author wavanrensselaer
 * @author akshoop
 * @author rnorlando
 */
public class SidebarView extends JPanel implements IView {
	private static final long serialVersionUID = -9157611802121055998L;

	Gateway gateway;
	
	private JPanel container;
	private JPanel curView;
	private TaskDetailView detailView;
	private TaskEditView editView;
	private TaskCreateView createView;
	private TaskDefaultView defaultView;
	private SearchBox searchBox;
	
	/**
	 * Constructs a sidebar view
	 */
	public SidebarView() {
		this.container = new JPanel();
		this.detailView = new TaskDetailView();
		this.createView = new TaskCreateView();
		this.editView = new TaskEditView();
		this.defaultView = new TaskDefaultView();
		this.searchBox = new SearchBox();
		
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		this.container.setLayout(new BoxLayout(this.container, BoxLayout.X_AXIS));
		this.container.setBackground(new Color(250, 250, 250));
		this.container.setMinimumSize(new Dimension(300, 0));
		this.container.setPreferredSize(new Dimension(300, 500));
		this.container.setMaximumSize(new Dimension(300, Integer.MAX_VALUE));
		
		this.curView = this.defaultView;
		this.createView.setVisible(false);
		this.editView.setVisible(false);
		this.detailView.setVisible(false);
		
		this.container.add(this.defaultView);
		this.container.add(this.createView);
		this.container.add(this.editView);
		this.container.add(this.detailView);
		this.container.add(this.searchBox);
		this.add(container);
	}
	
	/**
	 * Updates the sidebar with all members from the server
	 * @param members All members available to assign to a task
	 */
	public void updateMembers(String member) {
			this.editView.addMember(member);
			this.createView.addMember(member);
	}
	
	public void clearMembers() {
		this.editView.setAllMembers(new String[0]);
		this.createView.setAllMembers(new String[0]);
	}
	
	/**
	 * Shows the default panel
	 */
	public void showDefaultPanel() {
		this.curView.setVisible(false);
		this.curView = this.defaultView;
		this.curView.setVisible(true);
	}

	/**
	 * Shows the creation panel 
	 */
	public void showCreatePanel() {
		this.curView.setVisible(false);
		this.curView = this.createView;
		this.curView.setVisible(true);
		this.createView.updateView(new Task());
	}
	
	/**
	 * Shows the edit panel
	 * @param task The task to edit
	 */
	public void showEditPanel(Task task) {
		this.editView.updateView(task);
		this.curView.setVisible(false);
		this.curView = this.editView;
		this.curView.setVisible(true);
	}
	
	/**
	 * Shows the detail panel
	 * @param task The task to display
	 */
	public void showDetailPanel(Task task) {
		this.curView.setVisible(false);
		this.curView = this.detailView;
		this.detailView.updateView(task);
		this.curView.setVisible(true);
		this.detailView.revalidate();
	}
	
	/**
	 * Shows the search box
	 */
	public void showSearchBox() {
		this.curView.setVisible(false);
		this.curView = this.searchBox;
		this.curView.setVisible(true);
	}
	
	/**
	 * @see IView.setGateway
	 */
	@Override
	public void setGateway(Gateway gateway) {
		this.gateway = gateway;
		this.detailView.setGateway(this.gateway);
		this.editView.setGateway(this.gateway);
		this.createView.setGateway(this.gateway);
	}

}

package edu.wpi.cs.wpisuitetng.modules.taskmanager.view.sidebar;

import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

import edu.wpi.cs.wpisuitetng.modules.taskmanager.model.Task;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.presenter.Gateway;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.view.IView;

/**
 * The sidebar that will be the container for task creation, editing, and reading
 * @author wavanrensselaer
 */
public class SidebarView extends JPanel implements IView {
	private static final long serialVersionUID = -9157611802121055998L;

	Gateway gateway;
	
	private JPanel container;
	private JPanel curPanel;
	private TaskDetailView detailView;
	private TaskEditView editView;
	private TaskEditView createView;
	
	/**
	 * Constructs a sidebar view
	 */
	public SidebarView() {
		this.container = new JPanel();
		this.detailView = new TaskDetailView(new Task());
		this.createView = new TaskEditView();
		this.editView = new TaskEditView();
		
		this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		
		this.container.setLayout(new BoxLayout(this.container, BoxLayout.X_AXIS));
		
		this.curPanel = this.editView;
		this.container.add(editView);
		this.add(container);
	}
	
	/**
	 * Shows the creation panel 
	 */
	public void showCreatePanel() {
		this.container.remove(this.curPanel);
		//this.container.add(this.createPanel);
	}
	
	/**
	 * @see IView.setGateway
	 */
	@Override
	public void setGateway(Gateway gateway) {
		this.gateway = gateway;
		this.detailView.setGateway(this.gateway);
		this.editView.setGateway(this.gateway);
	}

}

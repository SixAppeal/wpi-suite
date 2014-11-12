package edu.wpi.cs.wpisuitetng.modules.taskmanager.view.sidebar;

import java.awt.Dimension;

import javax.swing.JPanel;

import edu.wpi.cs.wpisuitetng.modules.taskmanager.model.Task;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.presenter.Gateway;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.view.IView;

/**
 * The sidebar that will be the container for task creation, editing, and reading
 * @author wavanrensselaer
 */
public class SidebarView extends JPanel implements IView {
	Gateway gateway;
	
	private JPanel container;
	private TaskDetailView detailView;
	private TaskEditView editView;
	
	/**
	 * Constructs a sidebar view
	 */
	public SidebarView() {
		this.container = new JPanel();
		this.detailView = new TaskDetailView(new Task());
		this.editView = new TaskEditView();
		
		this.container.setMinimumSize(new Dimension(300, 0));
		this.container.setMaximumSize(new Dimension(300, Integer.MAX_VALUE));
		
		this.detailView.setVisible(false);
		
		this.container.add(detailView);
		this.container.add(editView);
		this.add(container);
	}
	
	/**
	 * @see IView.setGateway
	 */
	@Override
	public void setGateway(Gateway gateway) {
		this.gateway = gateway;
	}

}

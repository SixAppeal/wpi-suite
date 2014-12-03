package edu.wpi.cs.wpisuitetng.modules.taskmanager.view.sidebar;

import java.awt.Dimension;

import javax.swing.JPanel;

import edu.wpi.cs.wpisuitetng.modules.taskmanager.presenter.Gateway;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.view.IView;

/**
 * View that hold the interface for searching for tasks
 * @author wavanrensselaer
 */
public class TaskSearchView extends JPanel implements IView {
	private static final long serialVersionUID = -4835527297310127363L;
	
	private Gateway gateway;
	
	/**
	 * Constructs a <code>TaskSearchView</code>
	 */
	public TaskSearchView() {
		this.setOpaque(false);
		this.setMinimumSize(new Dimension(300, 0));
		this.setPreferredSize(new Dimension(300, 1));
		this.setMaximumSize(new Dimension(300, Integer.MAX_VALUE));
	}
	
	@Override
	public void setGateway(Gateway gateway) {
		this.gateway = gateway;
	}
}

package edu.wpi.cs.wpisuitetng.modules.taskmanager.view.sidebar;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import edu.wpi.cs.wpisuitetng.modules.taskmanager.presenter.Gateway;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.view.IView;

/**
 * 
 * @author akshoop
 * @author rnorlando
 */
public class TaskDefaultView extends JPanel implements IView {
	private static final long serialVersionUID = -1095431577990725671L;
	
	private Gateway gateway;
	
	/**
	 * Default constructor
	 */
	public TaskDefaultView() {
		this.setBackground(new Color(250, 250, 250));
		this.setBorder(BorderFactory.createTitledBorder(""));
		this.setOpaque(false);
	}
	
	
	/**
	 * @see edu.wpi.cs.wpisuitetng.modules.taskmanager.view.IView#setGateway()
	 */
	@Override
	public void setGateway(Gateway gateway) {
		this.gateway = gateway;
	}
}

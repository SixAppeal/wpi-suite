package edu.wpi.cs.wpisuitetng.modules.taskmanager.view.sidebar;

import javax.swing.JPanel;

import org.jdesktop.swingx.JXSearchPanel;

import edu.wpi.cs.wpisuitetng.modules.taskmanager.presenter.Gateway;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.view.IView;

/**
 * The GUI Search Box for the Sidebar
 * 
 * @author akshoop
 * @author nhhughes
 */
public class SearchBox extends JPanel implements IView {

	/**
	 * Generated serial number
	 */
	private static final long serialVersionUID = 2745494576347107370L;

	private Gateway gateway;
	
	JXSearchPanel searchBox;

	
	/**
	 * General constructor
	 */
	public SearchBox() {
		searchBox = new JXSearchPanel();
		
		this.add(searchBox);
	}
	
	/**
	 * @see IView.setGateway
	 * @param gateway
	 */
	@Override
	public void setGateway(Gateway gateway) {
		this.gateway = gateway;
	}

}

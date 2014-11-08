package edu.wpi.cs.wpisuitetng.modules.taskmanager.view;

import edu.wpi.cs.wpisuitetng.modules.taskmanager.presenter.Gateway;

/**
 * Abstract View class that all views extend from
 * 
 * @author Will Van Rensselaer, Dan Seaman
 */
public abstract class AbsView {
	Gateway gateway;
	
	/**
	 * Constructs a View with the given Gateway
	 * @param gateway The gateway to use for this view
	 */
	public AbsView(Gateway gateway) {
		this.gateway = gateway;
	}
}

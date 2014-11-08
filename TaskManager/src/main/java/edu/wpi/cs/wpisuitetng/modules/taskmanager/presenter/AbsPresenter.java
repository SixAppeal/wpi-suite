package edu.wpi.cs.wpisuitetng.modules.taskmanager.presenter;

/**
 * Abstract Presenter class that all presenters extend from
 * 
 * @author Will Van Rensselaer, Dan Seaman
 */
public abstract class AbsPresenter {
	Gateway gateway;
	
	/**
	 * Constructs a Presenter with the given gateway
	 * @param gateway The gateway to use with this presenter
	 */
	public AbsPresenter(Gateway gateway) {
		this.gateway = gateway;
	}
}

package edu.wpi.cs.wpisuitetng.modules.taskmanager.presenter;

import java.io.Serializable;

/**
 * Presenter interface that all presenters implement
 * 
 * @author wavanrensselaer
 * @author dpseaman
 */
public interface IPresenter {
	/**
	 * This should set the gateway to be used by the presenter
	 * @param gateway A Gateway object
	 */
	public void setGateway(Gateway gateway);
}

package edu.wpi.cs.wpisuitetng.modules.taskmanager.view;

import edu.wpi.cs.wpisuitetng.modules.taskmanager.model.StageList;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.presenter.Gateway;

/**
 * View interface that all views implement
 * 
 * @author wavanrensselaer
 * @author dpseaman
 */
public interface IView {
	/**
	 * This method should set the gateway to be used by the view
	 * @param gateway A Gateway object
	 */
	public void setGateway(Gateway gateway);

	public void setStages(StageList sl);
}

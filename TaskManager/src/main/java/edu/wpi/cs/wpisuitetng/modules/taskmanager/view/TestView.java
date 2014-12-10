package edu.wpi.cs.wpisuitetng.modules.taskmanager.view;

import edu.wpi.cs.wpisuitetng.modules.taskmanager.model.StageList;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.presenter.Gateway;

/**
 * This class exists for the sole purpose of testing the gateway
 *
 * @author wavanrensselaer
 * @author dpseaman
 */
public class TestView implements IView {
	int number;
	Gateway gateway;
	
	/**
	 * Constructs a TestView
	 */
	public TestView() {
		this.number = 0;
	}
	
	/**
	 * @see IView.setGateway
	 */
	@Override
	public void setGateway(Gateway gateway) {
		this.gateway = gateway;
	}
	
	/**
	 * A method to call from the gateway
	 * @param data The data passed to this view from a presenter
	 */
	public void testMethod(Integer data) {
		this.number = data.intValue();
	}
	
	/**
	 * Gets the number passed to this view
	 * @return A number
	 */
	public int getNumber() {
		return this.number;
	}
}

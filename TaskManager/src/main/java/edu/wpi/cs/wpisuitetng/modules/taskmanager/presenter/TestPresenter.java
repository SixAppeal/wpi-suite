package edu.wpi.cs.wpisuitetng.modules.taskmanager.presenter;

import edu.wpi.cs.wpisuitetng.modules.taskmanager.presenter.Gateway;

/**
 * This class exists for the sole purpose of testing the Gateway.
 * @author Will Van Rensselaer, Dan Seaman
 */
public class TestPresenter implements IPresenter {
	int number;
	Gateway gateway;
	
	/**
	 * Constructs a TestPresenter
	 */
	public TestPresenter() {
		this.number = 0;
	}
	
	/**
	 * @see IPresenter.setGateway
	 */
	@Override
	public void setGateway(Gateway gateway) {
		this.gateway = gateway;
	}
	
	/**
	 * A test method for a view to call through the gateway
	 * @param data Data passed to the presenter
	 */
	public void testMethod(Object data) {
		this.number = ((Integer) data).intValue();
	}
	
	/**
	 * Gets the number passed to this presenter from the gateway
	 * @return A number
	 */
	public int getNumber() {
		return this.number;
	}
}

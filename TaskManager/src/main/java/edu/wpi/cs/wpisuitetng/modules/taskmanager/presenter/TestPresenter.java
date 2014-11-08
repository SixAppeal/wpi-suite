package edu.wpi.cs.wpisuitetng.modules.taskmanager.presenter;

import edu.wpi.cs.wpisuitetng.modules.taskmanager.presenter.Gateway;

/**
 * This class exists for the sole purpose of testing the Gateway.
 * @author Will Van Rensselaer, Dan Seaman
 */
public class TestPresenter extends AbsPresenter {
	int number;
	
	/**
	 * Constructs a TestPresenter
	 */
	public TestPresenter(Gateway gateway) {
		super(gateway);
		
		this.number = 0;
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

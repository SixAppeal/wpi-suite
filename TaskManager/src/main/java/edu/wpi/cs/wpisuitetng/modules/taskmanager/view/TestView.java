package edu.wpi.cs.wpisuitetng.modules.taskmanager.view;

import edu.wpi.cs.wpisuitetng.modules.taskmanager.presenter.Gateway;

/**
 * This class exists for the sole purpose of testing the gateway
 * @author Will Van Rensselaer, Dan Seaman
 */
public class TestView extends AbsView {
	int number;
	
	/**
	 * Constructs a TestView
	 */
	public TestView(Gateway gateway) {
		super(gateway);
		
		this.number = 0;
	}
	
	/**
	 * A method to call from the gateway
	 * @param data The data passed to this view from a presenter
	 */
	public void testMethod(Object data) {
		this.number = ((Integer) data).intValue();
	}
	
	/**
	 * Gets the number passed to this view
	 * @return A number
	 */
	public int getNumber() {
		return this.number;
	}
}

package edu.wpi.cs.wpisuitetng.modules.taskmanager.view.components;

import javax.swing.JComponent;

/**
 * A validator to attach to form fields
 * @author wavanrensselaer
 */
public interface FormFieldValidator {
	/**
	 * Checks if the component is valid or not
	 * @param component The component to validate
	 * @return True if the component is valid and false if not
	 */
	public boolean validate(JComponent component);
	
	/**
	 * Gets a message from the validator
	 * @return The message
	 */
	public String getMessage();
}

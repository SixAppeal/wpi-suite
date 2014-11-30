package edu.wpi.cs.wpisuitetng.modules.taskmanager.view.components;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

import net.miginfocom.swing.MigLayout;

/**
 * A form field to place in a form
 * @author wavanrensselaer
 */
public class FormField extends JPanel {
	private static final long serialVersionUID = -2031130076773334170L;
	
	/**
	 * The normal border assigned to fields
	 */
	public static final Border BORDER_NORMAL = BorderFactory.createCompoundBorder(
			BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1),
			BorderFactory.createEmptyBorder(5, 5, 5, 5));
	
	/**
	 * The border assigned to fields in the error state
	 */
	public static final Border BORDER_ERROR = BorderFactory.createCompoundBorder(
			BorderFactory.createLineBorder(Color.RED, 1),
			BorderFactory.createEmptyBorder(5, 5, 5, 5));
	
	private String name;
	private JLabel label;
	private JComponent field;
	private JLabel message;
	private FormFieldValidator validator;
	
	/**
	 * Constructs a <code>FormField</code> with a field
	 * @param field The component to be displayed as the field
	 */
	public FormField(JComponent field) {
		this(null, field, null);
	}
	
	/**
	 * Constructs a <code>FormField</code> with a name and an input component.
	 * The name will be displayed as a label for the field.
	 * @param name The name of the field
	 * @param field The component to be displayed as the field
	 */
	public FormField(String name, JComponent field) {
		this(name, field, null);
	}
	
	/**
	 * Constructs a <code>FormField</code> with a name, field, and validator.
	 * @param name The name of the field
	 * @param field The component to be displayed as the field
	 * @param validator A validator for the field
	 */
	public FormField(String name, JComponent field, FormFieldValidator validator) {
		this.name = name;
		this.label = new JLabel(this.name);
		this.field = field;
		this.message = new JLabel();
		this.validator = validator;
		
		if (this.name == null) {
			this.label.setVisible(false);
		}
		
		this.field.setBorder(BORDER_NORMAL);
		this.field.setBackground(Color.WHITE);
		
		this.message.setForeground(Color.RED);
		this.message.setVisible(false);
		
		this.setOpaque(false);
		this.setLayout(new MigLayout("wrap 1, fillx, ins 0, gap 5px"));
		
		this.add(this.label, "growx");
		this.add(this.field, "growx");
		this.add(this.message, "growx");
	}
	
	/**
	 * Gets the name of the field.
	 * @return The name of the field
	 */
	public String getName() {
		return this.name;
	}
	
	/**
	 * Gets the component associated with this field.
	 * @return The component associated with this field
	 */
	public JComponent getField() {
		return this.field;
	}
	
	/**
	 * Gets the validator for this field
	 * @return A <code>FormFieldValidator</code> for this field
	 */
	public FormFieldValidator getValidator() {
		return this.validator;
	}
	
	/**
	 * Checks if the input for this field is valid
	 * @return True if the input is valid and false otherwise
	 */
	public boolean hasValidInput() {
		return this.validator == null
			? true : this.validator.validate(this.field);
	}
	
	/**
	 * Validates the input for this field
	 * @return True if the input is valid and false otherwise
	 */
	public boolean validateInput() {
		if (this.validator != null) {
			if (!this.validator.validate(this.field)) {
				this.field.setBorder(BORDER_ERROR);
				if (this.validator.getMessage() != null) {
					this.message.setText("<html>" + this.validator.getMessage() + "</html>");
					this.message.setVisible(true);
				}
				return false;
			}
			this.message.setVisible(false);
			this.field.setBorder(BORDER_NORMAL);
		}
		return true;
	}
}

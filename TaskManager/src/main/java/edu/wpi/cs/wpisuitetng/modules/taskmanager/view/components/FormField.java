package edu.wpi.cs.wpisuitetng.modules.taskmanager.view.components;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

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
	
	/**
	 * This will give the field a default border
	 */
	public static final int INPUT_BOX = 1;
	
	/**
	 * This will give the field no border styling
	 */
	public static final int LABEL = 0;
	
	private GridBagLayout layout;
	private String name;
	private JLabel label;
	private JComponent field;
	private int type;
	private JLabel message;
	private FormFieldValidator validator;
	
	/**
	 * Constructs a <code>FormField</code> with a field
	 * @param field The component to be displayed as the field
	 */
	public FormField(JComponent field) {
		this(null, field, INPUT_BOX, null);
	}
	
	/**
	 * Constructs a <code>FormField</code> with a field and type
	 * @param field A component
	 * @param type The type of component being added (will determine styling)
	 */
	public FormField(JComponent field, int type) {
		this(null, field, type, null);
	}
	
	/**
	 * Constructs a <code>FormField</code> with a name and an input component.
	 * The name will be displayed as a label for the field.
	 * @param name The name of the field
	 * @param field The component to be displayed as the field
	 */
	public FormField(String name, JComponent field) {
		this(name, field, INPUT_BOX, null);
	}
	
	/**
	 * Constructs a <code>FormField</code>.
	 * @param name The name of the field
	 * @param field A component
	 * @param type The type of field
	 */
	public FormField(String name, JComponent field, int type) {
		this(name, field, type, null);
	}
	
	/**
	 * Constructs a <code>FormField</code>
	 * @param name The name of the field
	 * @param field A component
	 * @param validator The validator for this field
	 */
	public FormField(String name, JComponent field, FormFieldValidator validator) {
		this(name, field, INPUT_BOX, validator);
	}
	
	/**
	 * Constructs a <code>FormField</code> with a name, field, and validator.
	 * @param name The name of the field
	 * @param field The component to be displayed as the field
	 * @param type The type of field
	 * @param validator A validator for the field
	 */
	public FormField(String name, JComponent field, int type, FormFieldValidator validator) {
		this.layout = new GridBagLayout();
		this.name = name;
		this.label = new JLabel(this.name);
		this.field = field;
		this.type = type;
		this.message = new JLabel();
		this.validator = validator;
		
		if (this.name == null) {
			this.label.setVisible(false);
		}
		
		if (this.type == INPUT_BOX) {
			this.field.setBorder(BORDER_NORMAL);
			this.field.setBackground(Color.WHITE);
		}
		
		this.message.setForeground(Color.RED);
		this.message.setVisible(false);
		
		this.setOpaque(false);
		this.setLayout(this.layout);
		
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.insets = new Insets(0, 0, 5, 0);
		gbc.weightx = 1.0;
		gbc.gridx = 0;
		gbc.gridy = 0;
		this.add(this.label, gbc);
		
		gbc.gridy = 1;
		this.add(this.field, gbc);
		
		gbc.insets.bottom = 0;
		gbc.gridy = 2;
		this.add(this.message, gbc);
	}
	
	/**
	 * Gets the name of the field.
	 * @return The name of the field
	 */
	public String getName() {
		return this.name;
	}
	
	/**
	 * Sets the component for this field
	 * @param component A component
	 */
	public void setField(JComponent component) {
		this.setField(component, INPUT_BOX);
	}
	
	/**
	 * Sets the component of this field
	 * @param component A component
	 * @param type The type of field
	 */
	public void setField(JComponent component, int type) {
		GridBagConstraints gbc = this.layout.getConstraints(this.field);
		this.remove(this.field);
		this.field = component;
		
		if (type == INPUT_BOX) {
			this.field.setBorder(BORDER_NORMAL);
			this.field.setBackground(Color.WHITE);
		}
		
		this.add(this.field, gbc, 1);
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

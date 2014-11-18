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
	
	private String name;
	private JLabel label;
	private JComponent field;
	
	/**
	 * Constructs a <code>FormField</code> with a name and an input component.
	 * The name will be displayed as a label for the field.
	 * @param name The name of the field
	 * @param field
	 */
	public FormField(String name, JComponent field) {
		this.name = name;
		this.label = new JLabel(this.name);
		this.field = field;
		
		this.field.setBorder(BORDER_NORMAL);
		this.field.setBackground(Color.WHITE);
		
		this.setOpaque(false);
		this.setLayout(new GridBagLayout());
		
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.weightx = 1.0;
		gbc.gridx = 0;
		gbc.gridy = 0;
		this.add(this.label, gbc);
		
		gbc.insets = new Insets(5, 0, 0, 0);
		gbc.gridy = 1;
		this.add(this.field, gbc);
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
}
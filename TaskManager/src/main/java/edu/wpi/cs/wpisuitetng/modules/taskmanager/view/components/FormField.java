package edu.wpi.cs.wpisuitetng.modules.taskmanager.view.components;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * A form field to place in a form
 * @author wavanrensselaer
 *
 * @param <T> The component to use for input (i.e. JTextField)
 */
public class FormField<T extends JComponent> extends JPanel {
	private static final long serialVersionUID = -2031130076773334170L;
	
	private String name;
	private JLabel label;
	private T field;
	
	/**
	 * Constructs a <code>FormField</code> with a name and an input component.
	 * The name will be displayed as a label for the field.
	 * @param name The name of the field
	 * @param field
	 */
	public FormField(String name, T field) {
		this.name = name;
		this.label = new JLabel(this.name);
		this.field = field;
		
		this.label.setBorder(BorderFactory.createEmptyBorder(0, 0, 5, 0));
		this.field.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
				BorderFactory.createEmptyBorder(5, 5, 5, 5)));
		
		this.setOpaque(false);
		this.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
		this.setLayout(new GridBagLayout());
		
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.weightx = 1.0;
		gbc.gridx = 0;
		gbc.gridy = 0;
		this.add(this.label, gbc);
		
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
	public T getField() {
		return this.field;
	}
}

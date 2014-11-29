package edu.wpi.cs.wpisuitetng.modules.taskmanager.view.components;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JPanel;

/**
 * A panel that handles layout of <code>FormField</code>s or
 * <code>FormGroup</code>s. It is important to note that in it's current state,
 * this only handles layout, not validation or automatic submission. You should
 * keep needed references to any components added to this form as they will not
 * be easily accessible.
 * @author wavanrensselaer
 */
public class Form extends JPanel {
	private static final long serialVersionUID = -395511593512770018L;
	
	public static final int VERTICAL = 1;
	public static final int HORIZONTAL = 2;
	
	public int orientation;
	private List<JComponent> fields;
	
	/**
	 * Constructs a <code>Form</code> with the fields specified.
	 * @param fields Either <code>FormField</code> or <code>FormGroup</code>.
	 */
	public Form(JComponent... fields) {
		this(Form.VERTICAL, fields);
	}
	
	/**
	 * Constructs a <code>Form</code> with the specified orientation.
	 * Orientation may be one of <code>Form.VERTICAL</code> or
	 * <code>Form.HORIZONTAL</code>.
	 * @param orientation The orientation of the form
	 * @param fields The fields in the form
	 */
	public Form(int orientation, JComponent... fields) {
		this.orientation = orientation;
		this.fields = new ArrayList<JComponent>();
		
		this.setOpaque(false);
		this.setLayout(new GridBagLayout());
		
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.anchor = GridBagConstraints.PAGE_START;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.insets = this.orientation == Form.VERTICAL
				? new Insets(0, 0, 10, 0)
				: new Insets(0, 0, 0, 10);
		gbc.weightx = 1.0;
		
		for (int i = 0; i < fields.length; i++) {
			this.fields.add(fields[i]);
			
			if (this.orientation == Form.VERTICAL) {
				gbc.gridx = 0;
				gbc.gridy = i;
				
				if (i == fields.length - 1) {
					gbc.anchor = GridBagConstraints.FIRST_LINE_END;
					gbc.fill = GridBagConstraints.NONE;
					gbc.insets.bottom = 0;
					gbc.weightx = 0.0;
					gbc.weighty = 1.0;
				}
			} else {
				gbc.gridx = i;
				gbc.gridy = 0;
				
				if (i == fields.length - 1) {
					gbc.insets.right = 0;
				}
			}
			
			this.add(fields[i], gbc);
		}
	}
	
	/**
	 * Validates the form
	 * @return True if all fields in the form are valid and false otherwise.
	 */
	public boolean hasValidFields() {
		for (JComponent component : this.fields) {
			if (component instanceof Form && !((Form) component).hasValidFields()
				|| component instanceof FormField && !((FormField) component).hasValidInput()) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Gets the fields associated with this Form
	 * @return An <code>ArrayList</code> of the fields in this form
	 */
	public List<JComponent> getFields() {
		return this.fields;
	}
}

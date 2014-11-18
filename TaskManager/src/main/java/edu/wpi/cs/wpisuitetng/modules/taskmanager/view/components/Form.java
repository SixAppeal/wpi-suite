package edu.wpi.cs.wpisuitetng.modules.taskmanager.view.components;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
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
	
	private List<JComponent> fields;
	
	/**
	 * Constructs a <code>Form</code> with the fields specified.
	 * @param fields Either <code>FormField</code> or <code>FormGroup</code>.
	 */
	public Form(JComponent... fields) {
		this.fields = new ArrayList<JComponent>();
		
		this.setOpaque(false);
		this.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		this.setLayout(new GridBagLayout());
		
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.anchor = GridBagConstraints.PAGE_START;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.insets = new Insets(0, 0, 10, 0);
		gbc.weightx = 1.0;
		gbc.gridx = 0;
		
		for (int i = 0; i < fields.length; i++) {
			this.fields.add(fields[i]);
			
			if (i == fields.length - 1) {
				gbc.anchor = GridBagConstraints.FIRST_LINE_END;
				gbc.fill = GridBagConstraints.NONE;
				gbc.insets.bottom = 0;
				gbc.weightx = 0.0;
				gbc.weighty = 1.0;
			}
			
			gbc.gridy = i;
			this.add(fields[i], gbc);
		}
	}
	
	/**
	 * Gets the fields associated with this Form
	 * @return An <code>ArrayList</code> of the fields in this form
	 */
	public List<JComponent> getFields() {
		return this.fields;
	}
}

package edu.wpi.cs.wpisuitetng.modules.taskmanager.view.components;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

/**
 * A panel that can contain multiple <code>FormField</code>s inline
 * @author wavanrensselaer
 */
public class FormGroup extends JPanel {
	private static final long serialVersionUID = 7117806918063764495L;
	
	private List<FormField> fields;
	
	/**
	 * Constructs a <code>FormGroup</code>
	 * @param fields
	 */
	public FormGroup(FormField... fields) {
		this.fields = new ArrayList<FormField>();
		
		this.setOpaque(false);
		this.setLayout(new GridBagLayout());
		
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.weightx = 1.0;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridy = 0;

		for (int i = 0; i < fields.length; i++) {
			this.fields.add(fields[i]);
			
			gbc.gridx = i;
			this.add(fields[i], gbc);
			
			if (i == 0) {
				gbc.insets = new Insets(0, 20, 0, 0);
			}
		}
	}
	
	/**
	 * Gets the fields associated with this group
	 * @return A <code>List</code> of fields in this group
	 */
	public List<FormField> getFields() {
		return this.fields;
	}
}

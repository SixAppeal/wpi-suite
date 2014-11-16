package edu.wpi.cs.wpisuitetng.modules.taskmanager.view.components;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

/**
 * A panel that can contain multiple <code>FormField</code>s inline
 * @author wavanrensselaer
 */
public class FormGroup extends JPanel {
	private static final long serialVersionUID = 7117806918063764495L;
	
	List<FormField<?>> fields;
	
	/**
	 * Constructs a <code>FormGroup</code>
	 * @param fields
	 */
	public FormGroup(FormField<?>... fields) {
		this.fields = new ArrayList<FormField<?>>();
		
		for (int i = 0; i < fields.length; i++) {
			this.fields.add(fields[i]);
		}
	}
}

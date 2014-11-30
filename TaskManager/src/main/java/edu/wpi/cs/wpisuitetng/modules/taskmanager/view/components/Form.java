package edu.wpi.cs.wpisuitetng.modules.taskmanager.view.components;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;

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
		this.setLayout(new MigLayout("fillx, ins 0, gap 10px"));
		
		String constraint;
		for (int i = 0; i < fields.length; i++) {
			this.fields.add(fields[i]);
			
			if (this.orientation == HORIZONTAL) {
				constraint = "growy, w " + 100.0 / fields.length + "%";
			} else {
				constraint = "growx, wrap";
			}
			
			this.add(fields[i], constraint);
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

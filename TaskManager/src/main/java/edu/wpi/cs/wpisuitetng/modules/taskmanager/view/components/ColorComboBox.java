package edu.wpi.cs.wpisuitetng.modules.taskmanager.view.components;

import java.awt.Color;
import java.awt.Component;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

import edu.wpi.cs.wpisuitetng.modules.taskmanager.model.Task;

/**
 * An extension of JComboBox that supports picking a color.
 * @author wavanrensselaer
 */
public class ColorComboBox extends JComboBox<Integer> {
	private static final long serialVersionUID = -5192850203675378725L;

	/**
	 * Creates a combo box with the colors defined in Task.COLORS
	 */
	public ColorComboBox() {
		super();
		setRenderer(new Renderer());
		for (Integer i : Task.COLORS.keySet()) {
			addItem(i);
		}
	}

	@Override
	public void setSelectedItem(Object value) {
		super.setSelectedItem(value);
		Color color = Task.COLORS.get(value);
		this.setBackground(color);
	}
	
	/**
	 * Custom renderer for the JComboBox
	 * @author wavanrensselaer
	 */
	private class Renderer extends JLabel implements ListCellRenderer<Integer> {
		private static final long serialVersionUID = 6814815774530195813L;

		/**
		 * Creates an opaque renderer
		 */
		public Renderer() {
			setOpaque(true);
		}
		
		@Override
		public Component getListCellRendererComponent(
				JList<? extends Integer> list, Integer value, int index,
				boolean isSelected, boolean cellHasFocus) {
			Color color = Task.COLORS.get(value);
			
			setText(" ");
			setBackground(cellHasFocus ? color.brighter() : color);
			
			return this;
		}
	}
}

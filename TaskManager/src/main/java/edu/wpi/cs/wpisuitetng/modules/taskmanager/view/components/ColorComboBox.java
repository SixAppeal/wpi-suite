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
		setBackground(color);
		setForeground(averageColor(color) > 128 ? Color.BLACK : Color.WHITE);
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
			if (color.getAlpha() == 0) {
				color = Color.WHITE;
			}
			
			setBackground(cellHasFocus ? color.brighter() : color);
			setBorder(BorderFactory.createMatteBorder(1, 0, 1, 0, color.darker()));
			setForeground(averageColor(color) > 128 ? Color.BLACK : Color.WHITE);
			
			return this;
		}
	}
	
	/**
	 * Averages the components of a color
	 * @param color The color to average
	 * @return The average of the RGB component values of the color
	 */
	private static int averageColor(Color color) {
		return (color.getRed() + color.getGreen() + color.getBlue()) / 3;
	}
}

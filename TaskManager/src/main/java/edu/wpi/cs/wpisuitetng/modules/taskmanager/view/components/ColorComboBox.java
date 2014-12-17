/*******************************************************************************
 * Copyright (c) 2014 -- WPI Suite
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors: Team Six-Appeal
 ******************************************************************************/
package edu.wpi.cs.wpisuitetng.modules.taskmanager.view.components;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

import edu.wpi.cs.wpisuitetng.modules.taskmanager.model.Task;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.util.TaskManagerUtil;

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
		this.setForeground(TaskManagerUtil.averageColor(color) > 128 ? Color.BLACK : Color.WHITE);
		this.setBackground(color.brighter());
		this.revalidate();
		this.repaint();
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
			
			switch (value) {
			case Task.CATEGORY_BLUE:
				setText("BLUE");
				break;
			case Task.CATEGORY_GREEN:
				setText("GREEN");
				break;
			case Task.CATEGORY_NONE:
				setText("No Category");
				break;
			case Task.CATEGORY_RED:
				setText("RED");
				break;
			case Task.CATEGORY_YELLOW:
				setText("YELLOW");
				break;
			}
			
			setBackground( cellHasFocus? color.brighter() : color );
			setForeground(TaskManagerUtil.averageColor(color) > 128 ? Color.BLACK : Color.WHITE);

			//setBackground(cellHasFocus ? color.brighter() : color);
			
			return this;
		}
		
	}
}

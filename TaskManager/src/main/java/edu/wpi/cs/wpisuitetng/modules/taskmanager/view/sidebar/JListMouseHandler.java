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

package edu.wpi.cs.wpisuitetng.modules.taskmanager.view.sidebar;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JList;

/**
 * Mouse Handler to deal with clicking to select and deselect
 * @author nhhughes
 *
 */
public class JListMouseHandler implements MouseListener {

	JList<String> list;
	Boolean just_changed;
	int[] previous_indexes;
	int keyboard_event_count;
	
	/**
	 * Makes  a list mouse handler off of the specified JList
	 * @param list JList to use
	 */
	public JListMouseHandler (JList<String> list) {
		this.list = list;
		just_changed = false;
		previous_indexes = list.getSelectedIndices();
	}

	/**
	 * On a mousePressed, select and deselect the items in the list
	 */
	public void mousePressed(MouseEvent e) {
		int clicked_index = this.list.locationToIndex(e.getPoint());
		if (this.just_changed) {
			this.just_changed = false;
			
			for (int i : previous_indexes) {
				if (!this.inArray(i, this.list.getSelectedIndices())) {
					this.list.addSelectionInterval(i, i);
				}
			}
			if (this.inArray(clicked_index, this.list.getSelectedIndices()) && this.inArray(clicked_index, previous_indexes)) {
				this.list.removeSelectionInterval(clicked_index, clicked_index);
			}
		}
		else {
			list.removeSelectionInterval(clicked_index, clicked_index);
		}
		this.previous_indexes = this.list.getSelectedIndices();

	}

	public void mouseReleased(MouseEvent e) {}

	public void mouseEntered(MouseEvent e) {}

	public void mouseExited(MouseEvent e) {}

	public void mouseClicked(MouseEvent e) {}
	
	/**
	 * Method to deal with input that doesn't involve clicking
	 */
	public void update_selected() {
		if (this.keyboard_event_count == 0) {
			this.previous_indexes = this.list.getSelectedIndices();
			this.keyboard_event_count++;
		}
	}
	
	/**
	 * Clear all the selected indices
	 */
	public void clear() {
		this.list.clearSelection();
		this.previous_indexes = this.list.getSelectedIndices();
	}
	
	/**
	 * Checks if something was already select
	 * @param to_check list item to check
	 * @param array selected items
	 * @return
	 */
	private Boolean inArray(int to_check, int[] array) {
		for (int i : array) {
			if (i == to_check) {
				return true;
			}
		}
		return false;
	}

}

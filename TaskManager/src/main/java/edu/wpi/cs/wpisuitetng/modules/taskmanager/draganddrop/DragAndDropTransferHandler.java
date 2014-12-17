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

package edu.wpi.cs.wpisuitetng.modules.taskmanager.draganddrop;

import java.awt.datatransfer.Transferable;
import java.awt.dnd.DragSourceDragEvent;
import java.awt.dnd.DragSourceMotionListener;
import java.awt.event.InputEvent;

import javax.swing.JComponent;
import javax.swing.TransferHandler;

import edu.wpi.cs.wpisuitetng.modules.taskmanager.view.columnar.TaskView;

/**
 * 
 * Handles transfers that happen during drag and drop actions
 * 
 * @author tmeehan
 * @author rnOrlando
 * @author dpseaman
 *
 */
public class DragAndDropTransferHandler extends TransferHandler implements DragSourceMotionListener{
	
	private static final long serialVersionUID = 7376701064237929995L;

	/**
	 * Default constructor
	 */
	public DragAndDropTransferHandler() {
		super();
	}
		
	/**
	 * @see javax.swing.TransferHandler.createTransferable
	 */
	@Override
	public Transferable createTransferable(JComponent c) {
		if (c instanceof TaskView) {
			TaskView task = (TaskView) c;
			Transferable trans = new TransferableTaskString(task.getTask());
			return trans;
		}
		return null;
	}
	
	/**
	 * @see  java.awt.dnd.DragSourceMotionListener.dragMouseMoved
	 */
	@Override
	public void dragMouseMoved(DragSourceDragEvent dsde) {}
	
	/**
	 * @see  javax.swing.TransferHandler.getSourceActions
	 */
	@Override
	public int getSourceActions(JComponent c) {
		if (c instanceof TaskView) {
			return TransferHandler.COPY;
		}
		return TransferHandler.NONE;
	}
	
	/**
	 * @see  javax.swing.TransferHandler.exportDone
	 */
	@Override
	public void exportDone(JComponent c, Transferable data, int action) {
		// turn on server updates
		
		c.setVisible(true);
		
		super.exportDone(c, data, action);
	}
}

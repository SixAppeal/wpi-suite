/*******************************************************************************
 * Copyright (c) 2014 -- WPI Suite
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors: Nathan Hughes, Thomas Meehan, Dan Seaman
 ******************************************************************************/

package edu.wpi.cs.wpisuitetng.modules.taskmanager.view.columnar;

import edu.wpi.cs.wpisuitetng.modules.taskmanager.draganddrop.CustomDropTargetListener;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.draganddrop.DragAndDropTransferHandler;

import java.awt.dnd.DropTarget;

import javax.swing.JPanel;

/**
 * Container for the task views
 * 
 * @author nhhughes
 * @author dpseaman
 * @author tmeehan
 *
 */

public class StageDragDropPanel extends JPanel {
	
	private static final long serialVersionUID = 6924509690789899864L;
	private StageView view;
	
/**
 * Constructor
 * @param view parent container
 */
	public StageDragDropPanel(StageView view) {
		super();
		this.view = view;

		setTransferHandler(new DragAndDropTransferHandler());
		setDropTarget(new DropTarget(StageDragDropPanel.this,
				new CustomDropTargetListener(StageDragDropPanel.this)));
	}
	
	/**
	 * getter
	 * @return return the stage view
	 */
	public StageView getStageView() {
		return view;
	}
}
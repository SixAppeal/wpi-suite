/*******************************************************************************
 * Copyright (c) 2014 -- WPI Suite
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors: Ryan Orlando, Thomas Meehan
 ******************************************************************************/


package edu.wpi.cs.wpisuitetng.modules.taskmanager.draganddrop;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JComponent;
import javax.swing.TransferHandler;



/**
 * Generic drag and drop listener
 * @author rnorlando
 * @author tmeehan
 *
 */
public class DraggableMouseListener extends MouseAdapter 
{
	protected JComponent assoc;
	
	/**
	 * Constructor
	 * 
	 * @param t the component that got clicked
	 */
	public DraggableMouseListener(JComponent t) {
		this.assoc = t;
	}
	
	/**
	 * @see  java.awt.event.MouseAdapter.mousePressed
	 */
	@Override
	public void mousePressed(MouseEvent e) 
	{
		JComponent c = assoc;
		TransferHandler handler = c.getTransferHandler();
		handler.exportAsDrag(c, e, TransferHandler.COPY);
	}

}

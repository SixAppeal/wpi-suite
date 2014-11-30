package edu.wpi.cs.wpisuitetng.modules.taskmanager.draganddrop;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JComponent;
import javax.swing.TransferHandler;



/**
 * Generic drag and drop listener
 * @author rnorlando
 *
 */
public class DraggableMouseListener extends MouseAdapter 
{
	protected JComponent assoc;
	
	public DraggableMouseListener(JComponent t) {
		this.assoc = t;
	}
	
	@Override
	public void mousePressed(MouseEvent e) 
	{
		JComponent c = (JComponent) assoc;
		TransferHandler handler = c.getTransferHandler();
		handler.exportAsDrag(c, e, TransferHandler.COPY);
	}

}

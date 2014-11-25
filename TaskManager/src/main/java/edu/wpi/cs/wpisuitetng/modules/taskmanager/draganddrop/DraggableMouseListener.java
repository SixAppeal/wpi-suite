package edu.wpi.cs.wpisuitetng.modules.taskmanager.draganddrop;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JComponent;
import javax.swing.TransferHandler;

public class DraggableMouseListener extends MouseAdapter{
	
	@Override
	public void mousePressed(MouseEvent e) {
		System.out.println("Recognized Mouse Click");
		
		JComponent c = (JComponent) e.getSource();
		TransferHandler handler = c.getTransferHandler();
		handler.exportAsDrag(c, e, TransferHandler.COPY);
	}
}

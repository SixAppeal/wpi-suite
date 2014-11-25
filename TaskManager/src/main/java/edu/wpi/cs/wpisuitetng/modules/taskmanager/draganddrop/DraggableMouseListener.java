package edu.wpi.cs.wpisuitetng.modules.taskmanager.draganddrop;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JComponent;
import javax.swing.TransferHandler;

import edu.wpi.cs.wpisuitetng.modules.taskmanager.view.columnar.TaskView;

public class DraggableMouseListener extends MouseAdapter{
	
	TaskView assoc;
	
	public DraggableMouseListener(TaskView t) {
		this.assoc = t;
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		JComponent c = (JComponent) assoc;
		TransferHandler handler = c.getTransferHandler();
		handler.exportAsDrag(c, e, TransferHandler.COPY);
	}
}

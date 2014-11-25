package edu.wpi.cs.wpisuitetng.modules.taskmanager.draganddrop;

import java.awt.datatransfer.Transferable;
import java.awt.dnd.DragSourceDragEvent;
import java.awt.event.InputEvent;

import javax.swing.JComponent;
import javax.swing.TransferHandler;

import edu.wpi.cs.wpisuitetng.modules.taskmanager.view.columnar.TaskView;

public class DragAndDropTransferHandler extends TransferHandler{
	
	public DragAndDropTransferHandler() {
		super();
	}
		
	@Override
	public Transferable createTransferable(JComponent c) {
		System.out.println("Creating Transferable");	
		
		if (c instanceof TaskView) {
			Transferable trans = (TaskView) c;
			return trans;
		}
			
		return null;
	}
	
	public void dragMouseMoved(DragSourceDragEvent dsde) {}
	
	@Override
	public int getSourceActions(JComponent c) {
		
		if (c instanceof TaskView) {
			return TransferHandler.COPY;
		}
		
		return TransferHandler.NONE;
	}
	
	@Override 
	public void exportAsDrag(JComponent c, InputEvent e, int action) {
		// turn off server updates
		
		super.exportAsDrag(c, e, action);	
	}
	
	@Override
	public void exportDone(JComponent c, Transferable data, int action) {
		// turn on server updates
		
		c.setVisible(true);
		
		super.exportDone(c, data, action);
	}
}
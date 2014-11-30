package edu.wpi.cs.wpisuitetng.modules.taskmanager.draganddrop;

import java.awt.datatransfer.Transferable;
import java.awt.dnd.DragSourceDragEvent;
import java.awt.dnd.DragSourceMotionListener;
import java.awt.event.InputEvent;

import javax.swing.JComponent;
import javax.swing.TransferHandler;

import edu.wpi.cs.wpisuitetng.modules.taskmanager.view.columnar.TaskView;

public class DragAndDropTransferHandler extends TransferHandler implements DragSourceMotionListener{
	
	private static final long serialVersionUID = 7376701064237929995L;

	public DragAndDropTransferHandler() {
		super();
	}
		
	@Override
	public Transferable createTransferable(JComponent c) {
		if (c instanceof TaskView) {
			TaskView task = (TaskView) c;
			Transferable trans = new TransferableTaskString(task.getTask().toJson());
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
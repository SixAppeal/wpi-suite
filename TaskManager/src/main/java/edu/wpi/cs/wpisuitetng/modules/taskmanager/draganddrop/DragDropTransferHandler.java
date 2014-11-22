package edu.wpi.cs.wpisuitetng.modules.taskmanager.draganddrop;

import javax.swing.JComponent;
import javax.swing.TransferHandler;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.datatransfer.Transferable;
import java.awt.event.InputEvent;
import java.awt.image.BufferedImage;

/**
 * @author rnorlando
 * @author dpseaman
 * @author tmeehan
 * 
 * A class for handling the transfer of data for drag and drop
 */

public class DragDropTransferHandler extends TransferHandler {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -148729246710557L;

	/**
	 * Creates a transferable to use as the source data transfer. 
	 * @see javax.swing.TransferHandler#creatTransferable(javax.swing.JComponent)
	 * @param c is the JComponenet with the data we're trying to transfer
	 * @return 
	 */
	
	@Override
	public Transferable createTransferable(JComponent c) {
		if (c instanceof Transferable) {
			return (Transferable) c;
			
		}
		return null;
	}
	
	/**
	 * Returns all the actions we support
	 * @param c is the JComponent we are looking to see what actions are acceptable for
	 * @return the number that represents the actions that are permitted
	 * @see javax.swing.TransferHandler#getSourceActions(javax.swing.JComponent)
	 */
	
	@Override
	public int getSourceActions(JComponent c) {
		// all of our drag and drops are move events
		return TransferHandler.MOVE;
	}
	
	/**
	 * Put a shadow image in the new location for the task, then do whatever the super method does
	 * @param comp is the JComponent we're trying to move
	 * @param e event that caused the export as drag 
	 * @param action the action we are making
	 */
	@Override
	public void exportAsDrag(JComponent comp, InputEvent e, int action) {
		// ignore server while attempting to drag
		Image image = new BufferedImage(comp.getWidth(), comp.getHeight(), BufferedImage.TYPE_INT_ARGB);
		Graphics g = image.getGraphics();
		g = g.create();
		comp.paint(g);
		setDragImage(image);
		
		// initiate the drag
		super.exportAsDrag(comp, e, action);
	}
	
	@Override
	public void exportDone(JComponent source, Transferable data, int action) {
		// undo the server block, starting listening to it again
		// make the JComponent visible in its new location 
		source.setVisible(true);
		
		super.exportDone(source,  data,  action);
	}

}

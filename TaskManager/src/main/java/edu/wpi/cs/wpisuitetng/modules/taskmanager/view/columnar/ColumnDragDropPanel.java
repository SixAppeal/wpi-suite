package edu.wpi.cs.wpisuitetng.modules.taskmanager.view.columnar;

import edu.wpi.cs.wpisuitetng.modules.taskmanager.draganddrop.CustomDropTargetListener;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.draganddrop.DragAndDropTransferHandler;

import java.awt.dnd.DropTarget;

import javax.swing.JPanel;

public class ColumnDragDropPanel extends JPanel {
	
	private static final long serialVersionUID = 6924509690789899864L;
	private ColumnView view;

	public ColumnDragDropPanel(ColumnView view) {
		super();
		this.view = view;

		setTransferHandler(new DragAndDropTransferHandler());
		setDropTarget(new DropTarget(ColumnDragDropPanel.this,
				new CustomDropTargetListener(ColumnDragDropPanel.this)));
	}

	public ColumnView getColumnView() {
		return view;
	}
}
package edu.wpi.cs.wpisuitetng.modules.taskmanager.view.columnar;

import edu.wpi.cs.wpisuitetng.modules.taskmanager.draganddrop.CustomDropTargetListener;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.draganddrop.DragAndDropTransferHandler;

import java.awt.dnd.DropTarget;

import javax.swing.JPanel;

public class StageDragDropPanel extends JPanel {
	
	private static final long serialVersionUID = 6924509690789899864L;
	private StageView view;

	public StageDragDropPanel(StageView view) {
		super();
		this.view = view;

		setTransferHandler(new DragAndDropTransferHandler());
		setDropTarget(new DropTarget(StageDragDropPanel.this,
				new CustomDropTargetListener(StageDragDropPanel.this)));
	}

	public StageView getStageView() {
		return view;
	}
}
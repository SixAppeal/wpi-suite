package edu.wpi.cs.wpisuitetng.modules.taskmanager.draganddrop;

import java.awt.Cursor;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.dnd.DropTargetContext;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.wpi.cs.wpisuitetng.modules.taskmanager.view.columnar.ColumnView;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.view.columnar.TaskView;

public class CustomDropTargetListener implements DropTargetListener {
	
	private final ColumnView column;
	
	private static final Cursor droppableCursor = Cursor.getPredefinedCursor(Cursor.HAND_CURSOR),
			notDroppableCursor = Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR);
	
	public CustomDropTargetListener(ColumnView sheet) {
		this.column = sheet;
	}

	@Override
	public void dragEnter(DropTargetDragEvent dtde) {}
	@Override
	public void dropActionChanged(DropTargetDragEvent dtde) {}

	@Override
	public void dragExit(DropTargetEvent dte) {
		this.column.setCursor(notDroppableCursor);
	}

	@Override
	public void dragOver(DropTargetDragEvent dtde) {
		if (!this.column.getCursor().equals(droppableCursor)) {
			this.column.setCursor(droppableCursor);
		}
	}
	
	
	@Override
	public void drop(DropTargetDropEvent dtde) {
		
		this.column.setCursor(Cursor.getDefaultCursor());
		
		DataFlavor dragAndDropTaskFlavor = null;
		
		Object transferableObj = null;
		Transferable transferable = null;
		
		try {
			dragAndDropTaskFlavor = ColumnView.getTaskDataFlavor();
			
			transferable = dtde.getTransferable();
			DropTargetContext c = dtde.getDropTargetContext();
			
			if (transferable.isDataFlavorSupported(dragAndDropTaskFlavor)) {
				transferableObj = dtde.getTransferable().getTransferData(dragAndDropTaskFlavor);
			}
			
		} catch (Exception ex) {}
		
		if (transferableObj == null) {
			return;
		}
		
		TaskView droppedTask = (TaskView) transferableObj;
		
		
		final int dropYLoc = dtde.getLocation().y;
		
		Map<Integer, TaskView> yLocMapForTasks = new HashMap<Integer, TaskView>();
		yLocMapForTasks.put(dropYLoc, droppedTask);
		
		for (TaskView nextPanel : column.getTasks()) {
			
			int y = nextPanel.getY();
			
			if (!nextPanel.equals(droppedTask)) {
				yLocMapForTasks.put(y, nextPanel);
			}
		}
		
		List<Integer> sortableYValues = new ArrayList<Integer>();
		sortableYValues.addAll(yLocMapForTasks.keySet());
		Collections.sort(sortableYValues);
		
		List<TaskView> orderedTasks = new ArrayList<TaskView>();
		for (Integer i : sortableYValues) {
			orderedTasks.add(yLocMapForTasks.get(i));
		}
		
		List<TaskView> inMemoryTaskList = this.column.getTasks();
		inMemoryTaskList.clear();
		inMemoryTaskList.addAll(orderedTasks);
		
		this.column.redrawTasks();
	}
	

}

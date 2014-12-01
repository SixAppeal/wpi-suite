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

import edu.wpi.cs.wpisuitetng.modules.taskmanager.model.Task;

import com.google.gson.Gson;

import edu.wpi.cs.wpisuitetng.modules.taskmanager.view.columnar.ColumnView;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.view.columnar.StageDragDropPanel;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.view.columnar.TaskView;

/**
 * 
 * @author rnOrlando
 *
 */

public class CustomDropTargetListener implements DropTargetListener {
	
	private final StageDragDropPanel column;
	
	private static final Cursor droppableCursor = Cursor.getPredefinedCursor(Cursor.HAND_CURSOR),
			notDroppableCursor = Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR);
	
	public CustomDropTargetListener(StageDragDropPanel sheet) {
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
		System.out.println("Step 5 of 7: The user dropped the panel. The drop(...) method will compare the drops location with other panels and reorder the panels accordingly.");
        
		this.column.setCursor(Cursor.getDefaultCursor());
		
		DataFlavor dragAndDropTaskFlavor = null;
		
		Object transferableObj = null;
		Transferable transferable = null;
		
		try {
			dragAndDropTaskFlavor = TransferableTaskString.flavor;
						
			transferable = dtde.getTransferable();
			DropTargetContext c = dtde.getDropTargetContext();
			
			if (transferable.isDataFlavorSupported(dragAndDropTaskFlavor)) {
				transferableObj = dtde.getTransferable().getTransferData(dragAndDropTaskFlavor);
			}
			
			
		} catch (Exception ex) {}
		if (transferableObj == null) {
			return;
		}
		TransferableTaskString droppedTaskInfo = (TransferableTaskString) transferableObj;
		Task dropTask = (new Gson()).fromJson(droppedTaskInfo.getJsonTaskValue(), Task.class);
		dropTask.setStage(this.column.getStageView().getStage());
		TaskView droppedTask =  new TaskView(dropTask); 
		
		
		
		final int dropYLoc = dtde.getLocation().y;
		
		Map<Integer, TaskView> yLocMapForTasks = new HashMap<Integer, TaskView>();
		yLocMapForTasks.put(dropYLoc, droppedTask);
		
		for (TaskView nextPanel : column.getStageView().getTaskViews()) {
			
			int y = nextPanel.getY();
			
			if (nextPanel.getTaskID() != (droppedTask.getTaskID())) {
				yLocMapForTasks.put(y, nextPanel);
			}
		}
		for (Integer t: yLocMapForTasks.keySet()) {
			System.out.println(t);
		}
		System.out.println("drop \n \n");
		
		//TODO: change this so it uses priority/ changes priority
		List<Integer> sortableYValues = new ArrayList<Integer>();
		sortableYValues.addAll(yLocMapForTasks.keySet());
		Collections.sort(sortableYValues);
		
		List<TaskView> orderedTasks = new ArrayList<TaskView>();
		for (Integer i : sortableYValues) {
			orderedTasks.add(yLocMapForTasks.get(i));
		}
		
		List<TaskView> inMemoryTaskList = this.column.getStageView().getTaskViews();
		inMemoryTaskList.clear();
		inMemoryTaskList.addAll(orderedTasks);
		
		int priority = 1;
		for(TaskView t : inMemoryTaskList)
		{
			Task tr = t.getTask(); 
			tr.setPriority(priority);
			this.column.getStageView().getGateway().toPresenter("LocalCache", "update", "task", tr);
			priority++;
		}
		
		this.column.getStageView().setAllTasks(new ArrayList<TaskView>(inMemoryTaskList));
		
		this.column.getStageView().reflow();
		
		column.getStageView().getGateway().toPresenter("LocalCache", "update", "task", droppedTask.getTask());
		
		//this.column.getStageView().addTask(droppedTask.getTask());
		
		
		
	}
	

}

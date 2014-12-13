/*******************************************************************************
 * Copyright (c) 2014 -- WPI Suite
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors: Ryan Orlando, Thomas Meehan
 ******************************************************************************/


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
import edu.wpi.cs.wpisuitetng.modules.taskmanager.presenter.Gateway;

import com.google.gson.Gson;

import edu.wpi.cs.wpisuitetng.modules.taskmanager.view.columnar.StageDragDropPanel;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.view.columnar.TaskView;

/**
 * Our own custom listener
 * Listens for if the drag and drop is dropped on this entity
 * 
 * 
 * @author rnOrlando
 * @author tmeehan
 *
 */

public class CustomDropTargetListener implements DropTargetListener {
	
	private final StageDragDropPanel column;
	private static Gateway gateway;
	
	private static final Cursor droppableCursor = Cursor.getPredefinedCursor(Cursor.HAND_CURSOR),
			notDroppableCursor = Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR);
	/**
	 * @param sheet		The location where the element is potentially dropped
	 */
	public CustomDropTargetListener(StageDragDropPanel sheet) {
		this.column = sheet;
	}

	/**
	 * Set's the gateway on the drop listener. 
	 * 
	 * @param gateway
	 */
	public static void setGateway(Gateway PassedGateway){
		gateway = PassedGateway;
	}
	
	/**
	 * @see  java.awt.dnd.DropTargetListener.dragEnter
	 */
	@Override
	public void dragEnter(DropTargetDragEvent dtde) {}
	
	/**
	 * @see  java.awt.dnd.DropTargetListener.dropActionChanged
	 */
	@Override
	public void dropActionChanged(DropTargetDragEvent dtde) {}

	/**
	 * @see  java.awt.dnd.DropTargetListener.dragExit
	 */
	@Override
	public void dragExit(DropTargetEvent dte) {
		this.column.setCursor(notDroppableCursor);
	}

	/**
	 * @see  java.awt.dnd.DropTargetListener.dragOver
	 */
	@Override
	public void dragOver(DropTargetDragEvent dtde) {
		if (!this.column.getCursor().equals(droppableCursor)) {
			this.column.setCursor(droppableCursor);
		}
	}
	
	/**
	 * @see  java.awt.dnd.DropTargetListener.drop
	 */
	@Override
	public void drop(DropTargetDropEvent dtde) {
		this.column.setCursor(Cursor.getDefaultCursor());
		
		DataFlavor dragAndDropTaskFlavor = null;
		Object transferableObj = null;
		Transferable transferable = null;
		
		try {
			dragAndDropTaskFlavor = TransferableTaskString.flavor;
			transferable = dtde.getTransferable();
			//DropTargetContext c = dtde.getDropTargetContext();
			
			if (transferable.isDataFlavorSupported(dragAndDropTaskFlavor)) {
				transferableObj = dtde.getTransferable().getTransferData(dragAndDropTaskFlavor);
			}
		} catch (Exception ex) { }
		if (transferableObj == null) {
			return;
		}
		TransferableTaskString droppedTaskInfo = (TransferableTaskString) transferableObj;
		Task dropTask = (new Gson()).fromJson(droppedTaskInfo.getJsonTaskValue(), Task.class);
		dropTask.setStage(this.column.getStageView().getStage());
		TaskView droppedTask =  new TaskView(dropTask); 
		
		gateway.toPresenter("LocalCache", "update", "task:testing", dropTask);
		
		
		
		final int dropYLoc = dtde.getLocation().y;
		
		Map<Integer, TaskView> yLocMapForTasks = new HashMap<Integer, TaskView>();
		yLocMapForTasks.put(dropYLoc, droppedTask);
		for (TaskView nextPanel : column.getStageView().getTaskViews()) {
			
			int y = nextPanel.getY();
			if (nextPanel.getTaskID() != (droppedTask.getTaskID())) {
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
		
		List<TaskView> inMemoryTaskList = this.column.getStageView().getTaskViews();
		inMemoryTaskList.clear();
		inMemoryTaskList.addAll(orderedTasks);
		
		int priority = 1;
		for (TaskView t : inMemoryTaskList) {
			Task tr = t.getTask(); 
			tr.setPriority(priority);
			this.column.getStageView().getGateway().toPresenter("LocalCache", "update", "task:testing", tr);
			priority += 1;
		}
	}
	

}

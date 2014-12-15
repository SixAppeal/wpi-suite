
/*******************************************************************************
 * Copyright (c) 2014 -- WPI Suite
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors: Will Rensselaer, Thomas Meehan, Alex Shoop, Ryan Orlando, Troy Hughes
 ******************************************************************************/


package edu.wpi.cs.wpusuitetng.modules.taskmanager.draganddrop;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import edu.wpi.cs.wpisuitetng.modules.taskmanager.draganddrop.CustomDropTargetListener;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.model.Stage;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.model.Task;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.view.columnar.StageDragDropPanel;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.view.columnar.StageView;

/**
 * test the CustomDropTargetListene
 * @author rnOrlando
 *
 */
public class TestCustomDropTargetListener {

	Stage testStage;
	
	StageView testStageView;
	
	StageDragDropPanel testColumn;
	
	CustomDropTargetListener testCustomDropTargetListener;
	
	@Before
	public void setup()
	{
		testStage = new Stage();
		testStageView = new StageView(testStage, new Task[0]);
		testColumn = new StageDragDropPanel(testStageView);
		testCustomDropTargetListener = new CustomDropTargetListener(testColumn);
	}
	
	@Test
	public void testConstructor()
	{
		assertNotNull(this.testCustomDropTargetListener);
	}
	
	
	public void testDragExit()
	{
		/* do nothing (cant make a DropTargetDragEvent)
		testCustomDropTargetListener.dragExit(new DropTargetDragEvent(new DropTargetContext(null), new Point(0,0), 0, 0));
		assertEquals(testColumn.getCursor(), Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
		*/
	}
	
	//cant make rest of test unitl we can make DropTargetDragEvent
}

package edu.wpi.cs.wpusuitetng.modules.taskmanager.draganddrop;

import static org.junit.Assert.*;

import java.awt.Cursor;
import java.awt.Point;
import java.awt.dnd.DropTargetContext;
import java.awt.dnd.DropTargetDragEvent;

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

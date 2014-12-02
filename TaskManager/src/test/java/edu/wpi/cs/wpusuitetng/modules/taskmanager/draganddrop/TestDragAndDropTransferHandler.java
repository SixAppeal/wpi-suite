package edu.wpi.cs.wpusuitetng.modules.taskmanager.draganddrop;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import edu.wpi.cs.wpisuitetng.modules.taskmanager.draganddrop.DragAndDropTransferHandler;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.draganddrop.TransferableTaskString;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.model.Task;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.view.columnar.TaskView;

public class TestDragAndDropTransferHandler 
{

	DragAndDropTransferHandler test;
	Task testTask;
	TaskView testTaskView;
	
	@Before
	public void setup()
	{
		test = new DragAndDropTransferHandler();
		testTask = new Task();
		testTaskView = new TaskView(testTask);
	}
	
	@Test
	public void testConstructor()
	{
		assertNotNull(test);
	}
	
	@Test
	public void testCreateTransferable()
	{
		assertEquals(test.createTransferable(testTaskView), new TransferableTaskString(testTask) );
	}
	
	
}

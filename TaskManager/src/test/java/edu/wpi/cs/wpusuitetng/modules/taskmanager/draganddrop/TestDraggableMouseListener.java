package edu.wpi.cs.wpusuitetng.modules.taskmanager.draganddrop;

import static org.junit.Assert.assertNotNull;

import javax.swing.JComponent;
import javax.swing.JPanel;

import org.junit.Before;
import org.junit.Test;

import edu.wpi.cs.wpisuitetng.modules.taskmanager.draganddrop.DraggableMouseListener;

public class TestDraggableMouseListener {
	
	JComponent testJComp;
	DraggableMouseListener testDML;
	
	@Before
	public void Setup(){
		testJComp = new JPanel();
		testDML = new DraggableMouseListener(testJComp);
	}
	
	@Test
	public void testConstructor(){
		assertNotNull(testDML);
	}

}

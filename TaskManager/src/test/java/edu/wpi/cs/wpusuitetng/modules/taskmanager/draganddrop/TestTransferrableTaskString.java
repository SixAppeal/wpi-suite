
/*******************************************************************************
 * Copyright (c) 2014 -- WPI Suite
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors: Will Rensselaer, Thomas Meehan, Will Rensselaer, Alex Shoop, Ryan Orlando, Troy Hughes
 ******************************************************************************/

package edu.wpi.cs.wpusuitetng.modules.taskmanager.draganddrop;

import static org.junit.Assert.*;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import edu.wpi.cs.wpisuitetng.modules.taskmanager.draganddrop.TransferableTaskString;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.model.Task;

public class TestTransferrableTaskString {
	
	String testString;
	TransferableTaskString testTransferableTaskString_1;
	TransferableTaskString testTransferableTaskString_2;
	Task testTask;
	
	@Before
	public void setup(){
		testString = "Test String";
		testTask = new Task();
		testTransferableTaskString_1 = new TransferableTaskString(testString);
		testTransferableTaskString_2 = new TransferableTaskString(testTask);
		
		
		
	}
	
	@Test
	public void testGetJsonTaskValue(){
		assertEquals(testTransferableTaskString_1.getJsonTaskValue(),testString );
	}
	
	@Test
	public void testGetJsonfromTask(){
		assertEquals(testTransferableTaskString_2.getJsonTaskValue(),testTask.toJson());
		
	}
	
	@Test 
	public void testisDataFlavorWhenCorrect()
	{
		assertTrue(testTransferableTaskString_1.isDataFlavorSupported(TransferableTaskString.flavor));
	}
	
	@Test 
	public void testisDataFlavorWhenFalse()
	{
		assertTrue(!testTransferableTaskString_1.isDataFlavorSupported(new DataFlavor(Task.class, "Task")));
	}
	
	@SuppressWarnings("static-access")
	@Test 
	public void tasteDatFlavor(){
		assertEquals(testTransferableTaskString_1.getTransferDataFlavors()[0], TransferableTaskString.flavor);
		
	}
	
	@Test
	public void testGetTransferData() throws UnsupportedFlavorException, IOException 
	{	
		
		assertEquals(testTransferableTaskString_1.getTransferData(TransferableTaskString.flavor), testTransferableTaskString_1);
	}
	
	@Test(expected=UnsupportedFlavorException.class)
	public void testGetTransferData_Exception() throws UnsupportedFlavorException, IOException 
	{	
		
		assertEquals(testTransferableTaskString_1.getTransferData(new DataFlavor(Task.class, "Task")), testTransferableTaskString_1);
	}
	
	
	
	
	
}

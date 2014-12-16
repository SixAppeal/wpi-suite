/*******************************************************************************
 * Copyright (c) 2014 -- WPI Suite
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors: Team Six-Appeal
 ******************************************************************************/


package edu.wpi.cs.wpisuitetng.modules.taskmanager.draganddrop;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

import edu.wpi.cs.wpisuitetng.modules.taskmanager.model.Task;

/**
 * 
 * A holder for the a json string representation of a task so we can
 * pass it through the dragon drop json
 * 
 * @author tmeehan
 * @author rnorlando
 *
 */

public class TransferableTaskString implements Transferable  {

	public static DataFlavor flavor = new DataFlavor(TransferableTaskString.class, "TransferableTaskString");
	
	private DataFlavor[] flavors = {new DataFlavor(TransferableTaskString.class, "TransferableTaskString")};
	
	private String jsonTaskValue = "";
	
	/**
	 * 
	 * constructor
	 * @param s Json string
	 */
	
	public TransferableTaskString(String jsonStringValue)
	{
		this.jsonTaskValue = jsonStringValue;
	}
	

	public TransferableTaskString (Task task)
	{
		this.jsonTaskValue = task.toJson();
	}
	

	/**
	 * Returns an array of DataFlavor objects indicating the flavors the data
     * can be provided in.  The array should be ordered according to preference
     * for providing the data (from most richly descriptive to least descriptive).
     * @return an array of data flavors in which this data can be transferred
	 */
	@Override
	public DataFlavor[] getTransferDataFlavors() {
		
		return flavors;
	}

	/**
	 * Returns whether or not the specified data flavor is supported for
     * this object.
     * @param flavor the requested flavor for the data
     * @return boolean indicating whether or not the data flavor is supported
	 */
	@Override
	public boolean isDataFlavorSupported(DataFlavor flavor) {
		for (DataFlavor f : flavors) {
			if (f.equals(flavor)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 
     * Returns an object which represents the data to be transferred.  The class
     * of the object returned is defined by the representation class of the flavor.
     *
     * @return object which represents the data to be transferred
     * @param flavor the requested flavor for the data
     * @see DataFlavor#getRepresentationClass
     * @exception IOException                if the data is no longer available
     *              in the requested flavor.
     * @exception UnsupportedFlavorException if the requested data flavor is
     *              not supported.
	 */
	@Override
	public Object getTransferData(DataFlavor flavor)
			throws UnsupportedFlavorException, IOException {
		if(!isDataFlavorSupported(flavor))
		{
			throw new UnsupportedFlavorException(flavor);
		}
		else
		{
			return this;
		}
	}
	
	/**
	 * getter
	 * @return the json task value string
	 */
	public String getJsonTaskValue()
	{
		return this.jsonTaskValue;
	}
	
	@Override
	public boolean equals(Object obj)
	{
		if (this==obj) {
			return true;
		}
		if (!(obj instanceof TransferableTaskString)) {
			return false;
		}
		
		TransferableTaskString testing = (TransferableTaskString)obj;
		if(!testing.getJsonTaskValue().equals(this.getJsonTaskValue()))
		{
			return false;
		}
		return true;
	}

	/**
	 * Determines the hashCode of the task to be its ID
	 */
	@Override
	public int hashCode() { return jsonTaskValue.hashCode(); }
	
}

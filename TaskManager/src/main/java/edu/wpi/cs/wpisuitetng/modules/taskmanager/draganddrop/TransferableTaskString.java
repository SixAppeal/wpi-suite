package edu.wpi.cs.wpisuitetng.modules.taskmanager.draganddrop;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

import edu.wpi.cs.wpisuitetng.modules.taskmanager.view.columnar.TaskView;

public class TransferableTaskString implements Transferable  {

	public static DataFlavor flavor = new DataFlavor(TransferableTaskString.class, "TransferableTaskString");
	
	private DataFlavor[] flavors = {new DataFlavor(TransferableTaskString.class, "TransferableTaskString")};
	
	private String jsonTaskValue = "";
	
	public TransferableTaskString(String s)
	{
		this.jsonTaskValue = s;
	}
	
	@Override
	public DataFlavor[] getTransferDataFlavors() {
		
		return flavors;
	}

	@Override
	public boolean isDataFlavorSupported(DataFlavor flavor) {
		for (DataFlavor f : flavors) {
			if (f.equals(flavor)) {
				return true;
			}
		}
		return false;
	}

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
	
	public String getJsonTaskValue()
	{
		return this.jsonTaskValue;
	}

}

package edu.wpi.cs.wpisuitetng.modules.taskmanager.draganddrop;

import javax.swing.JComponent;

public interface CustomDropTarget 
{
	
	

	//adds the Jcomponetn that has been draged ver to it
	void add(JComponent c);
	
	//creates a the placeholder
	//For specific type of JComponent 
	void createPlaceHolder(JComponent c);
	
	//shows the place holder one way or the other
	void showPlaceHolder(boolean visible);
	
	//shwos that the componet is beign draged out
	void showDraging(JComponent c);
}

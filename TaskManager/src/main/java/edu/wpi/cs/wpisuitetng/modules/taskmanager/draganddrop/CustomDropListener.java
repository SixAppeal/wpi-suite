package edu.wpi.cs.wpisuitetng.modules.taskmanager.draganddrop;

import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;

public class CustomDropListener implements DropTargetListener
{

	CustomDropTarget target;
	
	public CustomDropListener(CustomDropTarget tar)
	{
		target = tar;
	}
	
	@Override
	public void dragEnter(DropTargetDragEvent dtde) 
	{
		//dont think we need this
		
		// Implemnt if have drag across whole srceen
		
	}

	@Override
	public void dragOver(DropTargetDragEvent dtde) 
	{
		//called when over and still over
		
		
	}

	@Override
	public void dropActionChanged(DropTargetDragEvent dtde) 
	{
		// called when user modifies current drop geturs
		
	}

	@Override
	public void dragExit(DropTargetEvent dte) 
	{
		//exits drop site
	}

	@Override
	public void drop(DropTargetDropEvent dtde) {
		//let go over a drop site
		
	}

	
}

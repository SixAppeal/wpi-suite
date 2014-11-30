package edu.wpi.cs.wpisuitetng.modules.taskmanager.draganddrop;


import java.awt.event.MouseEvent;

import edu.wpi.cs.wpisuitetng.modules.taskmanager.view.columnar.TaskView;

/**
 *  This is a custom class to watch the mouse actions on a task View
 * @author RnOrlando
 *
 */
public class TaskDraggableMouseListener extends DraggableMouseListener{
	
	private TaskView taskAssoc;
	
	public TaskDraggableMouseListener(TaskView t) {
		super(t);
		this.taskAssoc = t;
		
	}
	
	@Override
	public void mouseClicked(MouseEvent e) 
	{
		taskAssoc.getGateway().toPresenter("TaskPresenter", "viewTask", taskAssoc.getTask());
	}

	@Override
	public void mouseReleased(MouseEvent e) 
	{
		taskAssoc.getGateway().toPresenter("TaskPresenter", "viewTask", taskAssoc.getTask());
	}

	@Override
	public void mouseEntered(MouseEvent e) 
	{
		assoc.setBackground(TaskView.HOVER_COLOR);
	}

	@Override
	public void mouseExited(MouseEvent e) {
		assoc.setBackground(TaskView.BACKGROUND_COLOR);
	}
	
	@Override
	public void mousePressed(MouseEvent e) 
	{
		taskAssoc.getGateway().toPresenter("TaskPresenter", "viewTask", taskAssoc.getTask());
		super.mousePressed(e);
	}
}

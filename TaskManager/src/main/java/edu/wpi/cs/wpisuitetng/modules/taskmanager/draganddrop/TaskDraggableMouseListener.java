package edu.wpi.cs.wpisuitetng.modules.taskmanager.draganddrop;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JComponent;
import javax.swing.TransferHandler;

import edu.wpi.cs.wpisuitetng.modules.taskmanager.view.columnar.TaskView;

public class TaskDraggableMouseListener extends MouseAdapter{
	
	TaskView assoc;
	
	public TaskDraggableMouseListener(TaskView t) {
		this.assoc = t;
	}
	
	@Override
	public void mouseClicked(MouseEvent e) 
	{
		assoc.getGateway().toPresenter("TaskPresenter", "viewTask", assoc.getTask());
	}

	@Override
	public void mouseReleased(MouseEvent e) 
	{
		assoc.getGateway().toPresenter("TaskPresenter", "viewTask", assoc.getTask());
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		assoc.setBackground(TaskView.HOVER_COLOR);
	}

	@Override
	public void mouseExited(MouseEvent e) {
		assoc.setBackground(TaskView.BACKGROUND_COLOR);
	}
	
	@Override
	public void mousePressed(MouseEvent e) 
	{
		assoc.getGateway().toPresenter("TaskPresenter", "viewTask", assoc.getTask());
		JComponent c = (JComponent) assoc;
		TransferHandler handler = c.getTransferHandler();
		handler.exportAsDrag(c, e, TransferHandler.COPY);
	}
}

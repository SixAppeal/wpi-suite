package edu.wpi.cs.wpisuitetng.modules.taskmanager.view.sidebar;

import edu.wpi.cs.wpisuitetng.modules.taskmanager.view.IView;

/**
 * 
 * @author wmtemple
 * @author akshoop
 * @author rnorlando
 *
 * A view to be displayed when creating or modifying a task object in the GUI.
 *
 */
public class TaskCreateView extends TaskEditView implements IView {
	private static final long serialVersionUID = -1055431537990755671L;

	/**
	 * Gets the title of this view
	 */
	@Override
	protected String getTitle() {
		return "New Task";
	}
	
	/**
	 * Passes the task to the presenter to be stored
	 */
	@Override
	protected void taskOut() {
		this.clearForm();
		gateway.toPresenter("TaskPresenter", "createTask", t);
		//gateway.toPresenter("TaskPresenter", "getAllTasks");
	}
	
}

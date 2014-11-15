package edu.wpi.cs.wpisuitetng.modules.taskmanager.view.sidebar;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

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
	 * Clears the creation form
	 */
	public void clearForm() {
		this.titleEntry.setText("");
		this.descEntry.setText("");
		this.estEffortSpinner.setValue(1);
		this.actEffortSpinner.setValue(0);
		this.titleEntry.setBorder((new JTextField()).getBorder());
		this.descEntry.setBorder(null);
		this.descEntryScoller.setBorder((new JScrollPane()).getBorder());
		
		for(int i = 0; i < this.requiredFieldFlags.length; i++)
		{
			this.requiredFieldFlags[i] = false;
		}
		saveButton.setEnabled(false);
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

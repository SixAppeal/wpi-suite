package edu.wpi.cs.wpisuitetng.modules.taskmanager.view.columnar;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.datatransfer.DataFlavor;
import java.awt.dnd.DropTarget;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import edu.wpi.cs.wpisuitetng.modules.taskmanager.draganddrop.CustomDropTargetListener;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.draganddrop.DragAndDropTransferHandler;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.model.Task;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.presenter.Gateway;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.view.IView;

/**
 * View for an individual column in the columnal layout
 * @author wavanrensselaer
 * @author rnorlando
 */
public class ColumnView extends JPanel implements IView {
	private static final long serialVersionUID = 2174190454852340046L;
	
	private Gateway gateway;
	private String title;
	private JPanel columnPanel;
	private JLabel titleLabel;
	private JPanel titlePanel;
	private ArrayList<TaskView> tasks;
	
	private static DataFlavor dragAndDropPanelDataFlavor = null;
	
	/**
	 * Constructs a <code>ColumnView</code> which has a title and an
	 * <code>ArrayList</code> of the tasks to display.
	 * @param title The title of the column
	 */
	public ColumnView(String title) {
		this.title = title;
		this.columnPanel = new JPanel();
		this.titleLabel = new JLabel(this.title, JLabel.LEFT);
		this.titlePanel = new JPanel();
		this.tasks = new ArrayList<TaskView>();
		
		this.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 20));
		this.setOpaque(false);
		this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		
		this.titlePanel.setOpaque(false);
		this.titlePanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
		this.titlePanel.setLayout(new BoxLayout(this.titlePanel, BoxLayout.X_AXIS));
		this.titlePanel.add(this.titleLabel);
		
		this.columnPanel.setBackground(new Color(220, 220, 220));
		this.columnPanel.setLayout(new BoxLayout(this.columnPanel, BoxLayout.Y_AXIS));
		this.columnPanel.setMinimumSize(new Dimension(260, 0));
		this.columnPanel.setPreferredSize(new Dimension(260, 0));
		this.columnPanel.setMaximumSize(new Dimension(260, Integer.MAX_VALUE));
		
		this.columnPanel.add(this.titlePanel);
		for (TaskView task : this.tasks) {
			this.columnPanel.add(task);
		}
		this.add(this.columnPanel);
		
		this.setTransferHandler(new DragAndDropTransferHandler());
		this.setDropTarget(new DropTarget(ColumnView.this,
				new CustomDropTargetListener(ColumnView.this)));
	}
	
	/**
	 * Refresh the contents of this view
	 */
	public void refreshView() {
		
		columnPanel.revalidate();
		this.revalidate();
		
	}
	
	/**
	 * Adds a task to the column
	 * @param task A task model
	 */
	public void addTask(Task task) {
		TaskView taskView = new TaskView(task);
		taskView.setGateway(this.gateway);
		tasks.add(taskView);
		this.columnPanel.add(taskView);
	}
	
	public List<TaskView> getTasks() {
		return this.tasks;
	}
	
	/**
	 * @see IView.setGateway
	 */
	@Override
	public void setGateway(Gateway gateway) {
		this.gateway = gateway;
		for (TaskView taskView : this.tasks) {
			taskView.setGateway(this.gateway);
		}
	}
	
	/**
	 * removes all tasks from this column
	 */
	public void removeAllTasks() {
		for(TaskView t : this.tasks) {
			//System.out.println("Removing task " + t.getTaskID());
			this.columnPanel.remove(t);
			//System.out.println("Task removed from ColumnPanel!");
		}
		this.tasks.clear(); //removing a task from the array we are currently iterating over is /illegal/
		
	}
	
	/**
	 * removes a task from this column
	 * @param task the task to be removed
	 */
	public void removeTask(final Task task) {
		for (TaskView t: this.tasks){
			if (t.getTaskID() == task.getId()) {
				this.columnPanel.remove(t);
				
			}
		}
		
		//This predicate is used to remove the tasks outside of the iterator
		this.tasks.removeIf( new Predicate<TaskView>() {
			@Override
			public boolean test(TaskView t) {
				return t.getTaskID() == task.getId();
			}		
		});
		
		this.columnPanel.revalidate();
		this.columnPanel.repaint();
		
		this.revalidate();
		
	}

	/**
	 * @return title of this Column
	 */
	public String getTitle() {
		return title;
	}
	
	/**
	 * @return the number of tasks in this column
	 */
	public int getTaskCount() {
		return this.tasks.size();
	}
	
	///////////////////////////////////////////////////////////////////////////////////////
	
	public void redrawTasks() {
		for(TaskView t : this.tasks) {
			//System.out.println("Removing task " + t.getTaskID());
			this.columnPanel.remove(t);
			//System.out.println("Task removed from ColumnPanel!");
		}
		
		for (TaskView t : this.tasks) {
			this.add(t);
		}
		
		this.validate();
		this.repaint();
	}
	
	public static DataFlavor getTaskDataFlavor() throws Exception {
        // Lazy load/create the flavor
        if (dragAndDropPanelDataFlavor == null) {
            dragAndDropPanelDataFlavor = new DataFlavor(DataFlavor.javaJVMLocalObjectMimeType + ";class=TaskView");
        }

        return dragAndDropPanelDataFlavor;
    }
}

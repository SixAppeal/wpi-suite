package edu.wpi.cs.wpisuitetng.modules.taskmanager.view.columnar;

import java.awt.Color;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JLabel;
import javax.swing.JPanel;

import edu.wpi.cs.wpisuitetng.modules.taskmanager.draganddrop.DragAndDropTransferHandler;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.draganddrop.TaskDraggableMouseListener;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.model.Stage;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.model.Task;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.presenter.Gateway;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.view.IView;

/**
 * View for a task that display in the ColumnView
 * @author wavanrensselaer
 * @author akshoop
 * @author rnorlando
 */
public class TaskView extends JPanel implements Transferable, IView {
	private static final long serialVersionUID = 6255679649898290535L;
	
	/**
	 * The cutoff point for the title string in the task
	 */
	public static final int MAX_TITLE_LENGTH = 20;

	/**
	 * Background color of the TaskView
	 */
	public static final Color BACKGROUND_COLOR = new Color(255, 255, 255);
	
	/**
	 * Background color when the mouse is over the TaskView
	 */
	public static final Color HOVER_COLOR = new Color(245, 245, 245);

//	private Gateway gateway;
	//private Task task;
	
	private Gateway gateway;
	
	// State-related fields
	private Task task;
	private int id;
	
	// Components
	private JLabel titleLabel;
	

	/**
	 * Constructs a <code>TaskView</code>
	 * @param name The name of the task
	 */
	public TaskView(Task task) 
	{
		
		this.titleLabel = new JLabel("", JLabel.LEFT);

		this.setBackground(TaskView.BACKGROUND_COLOR);
		this.setLayout(new GridBagLayout());		
		
		this.addMouseListener(new TaskDraggableMouseListener(this));
		this.setTransferHandler(new DragAndDropTransferHandler());
		
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.insets = new Insets(10, 10, 10, 10);
		gbc.weightx = 1.0;
		gbc.gridx = 0;
		gbc.gridy = 0;
		this.add(this.titleLabel, gbc);
		
		this.setState(task);
	}
	
	/**
	 * Gets the task associated with this view
	 * @return The task associated with this view
	 */
	public Task getTask() {
		return this.task;
	}
	
	/**
	 * sets the stage for the task in the task view
	 * @param s stage that is being set to
	 */
	public void setStage(Stage s)
	{
		this.task.setStage(s);
	}
	
	/**
	 * Sets the state of this view, currently the task it represents.
	 * @param task The new task
	 */
	public void setState(Task task) {
		this.task = task == null ? new Task() : task;
		this.reflow();
	}
	
	/**
	 * Reflows this view when it's state changes
	 */
	public void reflow() {
		this.titleLabel.setText(this.task.getTitle());
		this.titleLabel.revalidate();
		this.revalidate();

	}
//	
//	public void archiveTask() {
//		
//		gateway.toPresenter("TaskPresenter", "archiveTask", task);
//		
//	}

	/**
	 * @see IView.setGateway
	 */
//	@Override
//	public void setGateway(Gateway gateway) {
//		this.gateway = gateway;
//	}
	
	public int getTaskID() {
		return this.id;
	}
	
	////////////////////////////////////////////////////////////////////////
	
	/**
	 * 
	 * @param flavor
	 * @return
	 */
	public Object getTransferData(DataFlavor flavor){
		 System.out.println("Step 7 of 7: Returning the data from the Transferable object. In this case, the actual panel is now transfered!");
	       
		DataFlavor thisFlavor = null;
		try {
			thisFlavor = ColumnView.getTaskDataFlavor();
		} catch (Exception ex) {
			System.err.println("Problem Lazy Loading: " + ex.getMessage());
			ex.printStackTrace(System.err);
			return null;
		}
		if (thisFlavor != null && flavor.equals(thisFlavor)) {
			return (Object)TaskView.this;  //This is the problem
		}
		return null;
	}
	
	/**
	 * 
	 * @return
	 */
	public DataFlavor[] getTransferDataFlavors() {
		
		DataFlavor[] flavors = {null};
		System.out.println("Step 4 of 7: Querying for acceptable DataFlavors to determine what is available. Our example only supports our custom RandomDragAndDropPanel DataFlavor.");
        
		try{
			flavors[0] = ColumnView.getTaskDataFlavor();
		} catch (Exception ex) {
			System.err.println("Problem Lazy Loading: " + ex.getMessage());
			ex.printStackTrace(System.err);
			return null;
		}
		return flavors;
	}
	
	public boolean isDataFlavorSupported(DataFlavor flavor) {
		

		DataFlavor[] flavors = {null};
		
		 System.out.println("Step 6 of 7: Verifying that DataFlavor is supported.  Our example only supports our custom RandomDragAndDropPanel DataFlavor.");
	        
		try{
			flavors[0] = ColumnView.getTaskDataFlavor();
		} catch (Exception ex) {
			System.err.println("Problem Lazy Loading: " + ex.getMessage());
			ex.printStackTrace(System.err);
			return false;
		}
		
		for (DataFlavor f : flavors) {
			if (f.equals(flavor)) {
				return true;
			}
		}
		return false;
	}


	@Override
	public void setGateway(Gateway gateway) {
		this.gateway = gateway;
	}
	
	/**
	 * gets the gateway
	 * @return gateway gotten
	 */
	public Gateway getGateway()
	{
		return gateway;
	}
}

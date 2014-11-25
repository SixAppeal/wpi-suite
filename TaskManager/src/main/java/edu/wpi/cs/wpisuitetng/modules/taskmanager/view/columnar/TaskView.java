package edu.wpi.cs.wpisuitetng.modules.taskmanager.view.columnar;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import edu.wpi.cs.wpisuitetng.modules.taskmanager.draganddrop.DragAndDropTransferHandler;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.draganddrop.DraggableMouseListener;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.model.Task;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.presenter.Gateway;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.view.IView;

/**
 * View for a task that display in the ColumnView
 * @author wavanrensselaer
 * @author akshoop
 * @author rnorlando
 */
public class TaskView extends JPanel implements Transferable {
	private static final long serialVersionUID = 6255679649898290535L;
	
	/**
	 * The cutoff point for the title string in the task
	 */
	public static final int MAX_TITLE_LENGTH = 20;
	
//	private Gateway gateway;
	//private Task task;
	private JPanel taskPanel;
	private JLabel nameLabel;
	private int id;
	/**
	 * Constructs a <code>TaskView</code>
	 * @param name The name of the task
	 */
	public TaskView(Task task) {
		//this.task = task;
		this.taskPanel = new JPanel();
		this.id = task.getId();
		String title = task.getTitle();
		if (title.length() > MAX_TITLE_LENGTH) {
			title = title.substring(0,  MAX_TITLE_LENGTH) + "\u2026";
		}
		
		this.nameLabel = new JLabel(title);
		
		this.setBorder(BorderFactory.createEmptyBorder(0, 20, 20, 20));
		this.setOpaque(false);
		this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		this.taskPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 25));
		
		this.taskPanel.setBackground(new Color(255, 255, 255));
		
		this.taskPanel.addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent e) {
				//gateway.toPresenter("TaskPresenter", "viewTask", task);
			}

			@Override
			public void mousePressed(MouseEvent e) {
				
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				taskPanel.setBackground(new Color(245, 245, 245));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				taskPanel.setBackground(new Color(255, 255, 255));
			}
		});
		
		this.nameLabel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 20));
		this.taskPanel.add(nameLabel);
		this.add(this.taskPanel);
		
		this.taskPanel.addMouseListener(new DraggableMouseListener(this));
		this.setTransferHandler(new DragAndDropTransferHandler());
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

}

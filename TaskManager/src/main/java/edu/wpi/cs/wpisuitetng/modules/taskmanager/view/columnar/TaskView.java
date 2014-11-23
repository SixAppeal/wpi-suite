package edu.wpi.cs.wpisuitetng.modules.taskmanager.view.columnar;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.TransferHandler;

import edu.wpi.cs.wpisuitetng.modules.taskmanager.draganddrop.DragDropTransferHandler;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.model.Task;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.presenter.Gateway;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.view.IView;

/**
 * View for a task that display in the ColumnView
 * @author wavanrensselaer
 * @author akshoop
 * @author rnorlando
 */
public class TaskView extends JPanel implements IView, Transferable{
	private static final long serialVersionUID = 6255679649898290535L;
	
	/**
	 * The cutoff point for the title string in the task
	 */
	public static final int MAX_TITLE_LENGTH = 20;
	
	private Gateway gateway;
	private Task task;
	private JPanel taskPanel;
	private JLabel nameLabel;
	private DataFlavor flavor = null;
	
	/**
	 * Constructs a <code>TaskView</code>
	 * @param name The name of the task
	 */
	public TaskView(Task task) {
		this.task = task;
		this.taskPanel = new JPanel();
		
		createUserInterface();
		
		MouseAdapter  listener = new CustomMouseListenrDragDrop();
		this.addMouseListener(listener);
		this.addMouseMotionListener(listener);
		
		this.setTransferHandler(new DragDropTransferHandler());
		
		//This should not be a drop target...
		//so this seems like a shitty fix but '~' cant think of another way of the top of my head
		
		this.setDropTarget(null);
	}
	
	/**
	 * creates the user interface
	 */
	private void createUserInterface()
	{
		String title = this.task.getTitle();
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
				gateway.toPresenter("TaskPresenter", "viewTask", task);
			}

			@Override
			public void mousePressed(MouseEvent e) 
			{
				//!!! should I call other mouse pressed here?
			}

			@Override
			public void mouseReleased(MouseEvent e) 
			{
				
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
	}

	/**
	 * @see IView.setGateway
	 */
	@Override
	public void setGateway(Gateway gateway) {
		this.gateway = gateway;
	}
	
	public int getTaskID() {
		return this.task.getId();
	}

	/**
	 * gets all supported DataFlavors for this transferable
	 */
	@Override
	public DataFlavor[] getTransferDataFlavors() 
	{
		DataFlavor[] flavors = { this.getFlavor() };
		return flavors;
	}

	/**
	 * says whether that flavor is supported by this transferable
	 * @param the flavor we are checking out
	 */
	@Override
	public boolean isDataFlavorSupported(DataFlavor flavor) 
	{
		return flavor.equals(this.getFlavor());
	}

	/**
	 * gets the Transfer data from the task view for a drag and from action thing
	 * @param flavor of object it is expecting
	 */
	@Override
	public Object getTransferData(DataFlavor flavor)
			throws UnsupportedFlavorException, IOException 
	{
		
		if (flavor.equals(this.getFlavor())) 
		{
			return this;
		} 
		else 
		{
			throw new UnsupportedFlavorException(flavor);
		}
		
	}
	
	/**
	 * gets this guys data flavor
	 * @return the flavor of the taskView
	 */
	private DataFlavor getFlavor() {
		if (flavor == null) {
			try 
			{
				flavor = new DataFlavor(
						DataFlavor.javaJVMLocalObjectMimeType
								+ ";class=taskManager.view.columar.TaskView");
			} 
			catch (ClassNotFoundException e) 
			{
				System.out.println("Could not create a TaskView Flavor");
				e.printStackTrace();
				//don't want to call error if this is a problem, will just return null
				//should fall apart later
				
			}
		}
		return flavor;
	}
	
	/**
	 * A custom mouse adapter for taskview class to respond to drag and drop stuff
	 * 
	 * @author rnOrlando
	 *
	 */
	private class CustomMouseListenrDragDrop extends MouseAdapter  
	{

		/**
		 * this method is called when mouse is pressed
		 * @param the mouse event that occured, to trigger mousePressed
		 */
		@Override
		public void mousePressed(MouseEvent e)
		{
			System.out.print("mouse Pressed on Task");
			//when clicked figure out where mouse is so that transferhandler can set it as a offset
			getTransferHandler().setDragImageOffset(e.getPoint());
		}
		
		
		/**
		 * this method is called when the mouse is draging on this
		 * @param mouse event that that occured to trigger mouseDragged
		 */
		@Override
		public void mouseDragged(MouseEvent e) {
			System.out.print("mouse Draged on Task");
			JComponent comp = (JComponent) e.getSource();
			TransferHandler handler = comp.getTransferHandler();
			handler.exportAsDrag(comp, e, TransferHandler.MOVE);
		}
	}

}

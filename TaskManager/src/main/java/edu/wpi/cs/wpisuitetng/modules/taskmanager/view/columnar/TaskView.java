package edu.wpi.cs.wpisuitetng.modules.taskmanager.view.columnar;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import edu.wpi.cs.wpisuitetng.modules.taskmanager.model.Task;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.presenter.Gateway;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.view.IView;

/**
 * View for a task that display in the ColumnView
 * @author wavanrensselaer
 */
public class TaskView extends JPanel implements IView {
	private static final long serialVersionUID = 6255679649898290535L;
	
	private Gateway gateway;
	private Task task;
	private JPanel taskPanel;
	private JLabel nameLabel;
	
	/**
	 * Constructs a <code>TaskView</code>
	 * @param name The name of the task
	 */
	public TaskView(Task task) {
		this.task = task;
		this.taskPanel = new JPanel();
		this.nameLabel = new JLabel(this.task.getTitle());
		
		this.setBorder(BorderFactory.createEmptyBorder(0, 20, 20, 20));
		this.setOpaque(false);
		this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		this.taskPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 100));
		
		this.taskPanel.setBackground(new Color(255, 255, 255));
		
		this.taskPanel.addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent e) {
				gateway.toPresenter("TaskPresenter", "viewTask", task);
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

}

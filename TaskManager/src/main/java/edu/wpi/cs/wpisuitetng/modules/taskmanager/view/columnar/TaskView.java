package edu.wpi.cs.wpisuitetng.modules.taskmanager.view.columnar;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import edu.wpi.cs.wpisuitetng.modules.taskmanager.presenter.Gateway;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.view.IView;

/**
 * View for a task that display in the ColumnView
 * @author wavanrensselaer
 */
public class TaskView extends JPanel implements IView {
	private static final long serialVersionUID = 6255679649898290535L;
	
	private Gateway gateway;
	private String name;
	private JPanel taskPanel;
	private JLabel nameLabel;
	
	/**
	 * Constructs a <code>TaskView</code>
	 * @param name The name of the task
	 */
	public TaskView(String name) {
		this.name = name;
		this.taskPanel = new JPanel();
		this.nameLabel = new JLabel(name);
		
		this.setBorder(BorderFactory.createEmptyBorder(0, 20, 20, 20));
		this.setOpaque(false);
		this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		this.taskPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 100));
		
		this.taskPanel.setBackground(new Color(255, 255, 255));
		
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

}

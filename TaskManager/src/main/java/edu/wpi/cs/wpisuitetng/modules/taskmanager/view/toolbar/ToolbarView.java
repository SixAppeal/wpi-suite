package edu.wpi.cs.wpisuitetng.modules.taskmanager.view.toolbar;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;

import edu.wpi.cs.wpisuitetng.janeway.gui.container.toolbar.DefaultToolbarView;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.presenter.Gateway;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.util.TaskManagerUtil;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.view.IView;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.view.components.GradientPanel;

/**
 * Sets up upper toolbar of RequirementManager tab
 * @author thhughes
 * @author rwang3
 */
public class ToolbarView extends GradientPanel implements IView {
	private static final long serialVersionUID = -5500795769276248345L;

	private Gateway gateway;
	
	private JButton createTaskButton;
	private JButton toggleSidebarButton;
	
	/**
	 * Constructs a ToolbarView object with an option that togles visibility
	 * @param visible boolean
	 */
	public ToolbarView() {
		this.createTaskButton = new JButton("Create Task");
		this.toggleSidebarButton = new JButton("Toggle Sidebar");
		
		this.createTaskButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				gateway.toPresenter("TaskPresenter", "toolbarCreate");
			}
		});
		
		this.toggleSidebarButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				gateway.toPresenter("TaskPresenter", "toolbarToggleSidebar");
			}
		});
		
		this.setBackground(TaskManagerUtil.TOOLBAR_COLOR);
		this.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(215, 215, 215)));
		this.setLayout(new GridBagLayout());
		
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.anchor = GridBagConstraints.LAST_LINE_START;
		gbc.insets = new Insets(0, 20, 20, 0);
		gbc.weighty = 1.0;
		gbc.gridx = 0;
		gbc.gridy = 0;
		
		this.add(this.createTaskButton, gbc);
		
		gbc.weightx = 1.0;
		gbc.gridx = 1;
		this.add(this.toggleSidebarButton, gbc);
	}

	/**
	 * @see IView.setGateway
	 */
	@Override
	public void setGateway(Gateway gateway) {
		this.gateway = gateway;
	}
}

/*******************************************************************************
 * Copyright (c) 2014 -- WPI Suite
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors: Team Six-Appeal
 ******************************************************************************/

package edu.wpi.cs.wpisuitetng.modules.taskmanager.view.toolbar;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import javax.swing.border.CompoundBorder;

import edu.wpi.cs.wpisuitetng.modules.taskmanager.presenter.Gateway;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.util.TaskManagerUtil;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.view.IView;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.view.components.GradientPanel;
import edu.wpi.cs.wpisuitetng.network.TrelloNetwork;

/**
 * Sets up upper toolbar of RequirementManager tab
 * @author thhughes
 * @author rwang3
 * @author dpseaman
 */
public class ToolbarView extends GradientPanel implements IView {
	private static final long serialVersionUID = -5500795769276248345L;

	private Gateway gateway;
	
	private JButton createTaskButton;

	private JButton helpButton;
	private JButton importButton;

	private JButton toggleSidebarButton;
	
	@SuppressWarnings("unused")
	private boolean click;
	
	/**
	 * Constructs a ToolbarView object with an option that toggles visibility
	 * @param visible boolean
	 */
	public ToolbarView() {
		this.createTaskButton = new JButton("  Create Task");


		this.helpButton = new JButton();
		this.toggleSidebarButton = new JButton("Toggle Sidebar");

		this.importButton = new JButton("Import");
		this.toggleSidebarButton = new JButton("  Toggle Sidebar");


		this.click = true;
		
		this.createTaskButton.setHorizontalAlignment(SwingConstants.CENTER);
		this.createTaskButton.setIcon(new ImageIcon(this.getClass().getResource("icon_plus.png")));
		Font font = this.createTaskButton.getFont();
		font = new Font(font.getName(), font.getStyle(), 16);
		CompoundBorder border = BorderFactory.createCompoundBorder(this.createTaskButton.getBorder(), 
				BorderFactory.createEmptyBorder(6, 6, 6, 6));
		this.createTaskButton.setFont(font);
		this.createTaskButton.setBorder(border);
		
		this.createTaskButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				gateway.toPresenter("TaskPresenter", "toolbarCreate");
			}
		});
		
		this.importButton.setFont(font);
		this.importButton.setBorder(border);
		this.importButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				TrelloNetwork.getInstance().beginImport();
			}
		});

		this.helpButton.setHorizontalAlignment(SwingConstants.CENTER);
		this.helpButton.setIcon(new ImageIcon(this.getClass().getResource("icon_question.png")));
		this.helpButton.setFont(font);
		this.helpButton.setBorder(border);
		
		this.helpButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

			}
		});			

		this.importButton.setFont(font);
		this.importButton.setBorder(border);
		this.importButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				TrelloNetwork.getInstance().beginImport();
			}
		});
		
		this.toggleSidebarButton.setHorizontalAlignment(SwingConstants.CENTER);
		//this.toggleSidebarButton.setIcon(new ImageIcon(this.getClass().getResource("icon_right.png")));
		this.toggleSidebarButton.setFont(font);
		this.toggleSidebarButton.setBorder(border);
		
		this.toggleSidebarButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				gateway.toPresenter("TaskPresenter", "toolbarToggleSidebar");
				
				/**
				if (click == true){
					toggleSidebarButton.setIcon(new ImageIcon(this.getClass().getResource("icon_left.png")));
					toggleSidebarButton.revalidate();
					toggleSidebarButton.repaint();
					click = false;
				}
				
				else{
					toggleSidebarButton.setIcon(new ImageIcon(this.getClass().getResource("icon_right.png")));
					toggleSidebarButton.revalidate();
					toggleSidebarButton.repaint();
					click = true;
				}
				**/
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
		
		gbc.gridx = 1;
		this.add(this.importButton, gbc);
		
		gbc.anchor = GridBagConstraints.LAST_LINE_END;
		gbc.weightx = 1.0;
		gbc.gridx = 2;
		this.add(this.toggleSidebarButton, gbc);
		
		gbc.insets.right = 20;
		gbc.gridx = 3;
		gbc.weightx = 0;
		this.add(this.helpButton, gbc);
	}

	/**
	 * @see IView.setGateway
	 */
	@Override
	public void setGateway(Gateway gateway) {
		this.gateway = gateway;
	}
}

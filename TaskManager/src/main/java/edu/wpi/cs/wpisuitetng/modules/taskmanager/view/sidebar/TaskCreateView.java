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
package edu.wpi.cs.wpisuitetng.modules.taskmanager.view.sidebar;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JComboBox;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;

import org.jdesktop.swingx.JXDatePicker;

import net.miginfocom.swing.MigLayout;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.model.Stage;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.model.StageList;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.model.Task;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.presenter.Gateway;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.view.IView;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.view.components.ButtonGroup;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.view.components.Form;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.view.components.FormField;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.view.components.FormFieldValidator;

/**
 * A view to be displayed when creating or modifying a task object in the GUI.
 *
 * @author wavanrensselaer
 * @author wmtemple
 * @author akshoop
 * @author rnorlando
 * @author srojas
 * @author tmeehan
 * @author dpseaman
 */

public class TaskCreateView extends JPanel implements IView {
	private static final long serialVersionUID = -1055431537990755671L;

	private Gateway gateway;

	// Components
	private JPanel container;
	private JScrollPane scrollPane;
	private JTextField title;
	private JTextArea description;
	private JComboBox<Stage> stages;
	private JXDatePicker dateInput;
	private JButton createButton;
	private JButton cancelButton;
	private Form form;

	/**
	 * Constructs a <code>TaskCreateView</code>
	 */
	public TaskCreateView(StageList iStages) {
		this.container = new JPanel();
		this.scrollPane = new JScrollPane(this.container);
		this.title = new JTextField();
		this.description = new JTextArea(5, 0);
		this.stages = new JComboBox<Stage>();
		this.setStages(iStages);
		this.dateInput = new JXDatePicker();
		this.createButton = new JButton("Create");
		this.cancelButton = new JButton("Cancel"); 
		TaskCreateView that = this;
		Task task = new Task();
		this.scrollPane.setBorder(BorderFactory.createEmptyBorder());
		this.scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

		this.description.setLineWrap(true);
		this.description.setWrapStyleWord(true);

		this.createButton.setEnabled(false);
		this.createButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				task.setTitle(title.getText());
				task.setDueDate(dateInput.getDate());
				task.setDescription(description.getText());
				task.setStage((Stage) stages.getSelectedItem());
				gateway.toPresenter("LocalCache", "store", "task:testing", task);
				gateway.toView("SidebarView", "removeCreatePanel", that);
			}
		});

		this.cancelButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (!title.getText().trim().equals("")
						|| !description.getText().trim().equals("")) {
					int value = JOptionPane.showConfirmDialog(container, "You will lose your changes.",
							"Are you sure?",
							JOptionPane.YES_NO_OPTION);
					if (value != JOptionPane.YES_OPTION) {
						return;
					}
				}
				gateway.toView("SidebarView", "removeCreatePanel", that);
			}
		});

		FormField titleField = new FormField("Title", this.title, new FormFieldValidator() {
			@Override
			public boolean validate(JComponent component) {
				return !((JTextField) component).getText().trim().equals("");
			}

			@Override
			public String getMessage() {
				return "Please enter a title.";
			}
		});
		this.title.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent e) {
				if (e.getKeyCode() != 9) { // tab key
					titleField.validateInput();
				}
				validateForm();
			}
		});

		FormField descriptionField = new FormField("Description", this.description, new FormFieldValidator() {
			@Override
			public boolean validate(JComponent component) {
				return !description.getText().trim().equals("");
			}

			
			@Override
			public String getMessage() {
				return "Please enter a description.";
			}
		});
		
		FormField dateInputForm = new FormField("Due Date", this.dateInput, new FormFieldValidator() {
			@Override
			public boolean validate(JComponent component) {
				return !(dateInput.getDate() == null);
			}

			
			@Override
			public String getMessage() {
				return "Please select a due date.";
			}
		});
		
		this.dateInput.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (dateInput != null) { 
					dateInputForm.validateInput();
				}
				validateForm();
			}
		});
		
		
		this.dateInput.addPropertyChangeListener("date", new PropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				task.setDueDate(dateInput.getDate());
			}
		});
		

		this.description.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent e) {
				if (e.getKeyCode() != 9) { // tab key
					descriptionField.validateInput();
				}
				validateForm();
			}
		});

		this.form = new Form(
			titleField,
			descriptionField,
			dateInputForm,
			new FormField("Stage", stages),
			new ButtonGroup(
				this.createButton,
				this.cancelButton
			)
		);

		this.container.setBackground(SidebarView.SIDEBAR_COLOR);
		this.container.setLayout(new MigLayout("fill, ins 20", "[260]"));
		this.container.add(this.form, "grow");

		this.scrollPane.setMinimumSize(new Dimension(300, 0));
		this.scrollPane.setMaximumSize(new Dimension(300, Integer.MAX_VALUE));
		
		this.setLayout(new MigLayout("fill, ins 0", "[300]"));
		this.add(this.scrollPane, "grow");

	}

	/**
	 * Validates the form
	 */
	public void validateForm() {
		this.createButton.setEnabled(this.form.hasValidInput());
	}

	@Override
	public void setGateway(Gateway gateway) {
		this.gateway = gateway;
	}

	
	/**
	 * Checks if the title or description are empty
	 * @return boolean describing if both fields contain stuff
	 * @return True if both are fields are empty
	 */
	public boolean isEmpty() {
		if (this.title.getText().trim().equals("") && this.description.getText().trim().equals("")) {
			return true;
		}
		return false;
	}

	/**
	 * Set stages on the create view
	 * @param sl stage lists to update from
	 */
	public void setStages(StageList sl) {
		if( !stages.equals(sl) ) {
			Object pSelected = stages.getSelectedItem();
			stages.removeAllItems();
			for (Stage s : sl) stages.addItem(s);
			
			
			if (pSelected != null && sl.contains(pSelected)) stages.setSelectedItem(pSelected);
		}
	}
	
	/**
	 * 
	 * @return JTextField object that is the title of the TaskCreateView
	 */
	public JTextField getTitle(){
		return this.title;
	}

	/**
	 * @return JTextArea that is the description of the TaskCreateView
	 */
	public JTextArea getDescription(){
		return this.description;
	}
	
	/**
	 *  sets the focus on a text field
	 */
	public void fixFocus(){
		this.title.requestFocusInWindow();
	}
	
	@Override
	public boolean equals(Object obj)
	{
		if(!obj.getClass().equals(this.getClass()))
		{
			return false;
		}
		
		return true;
	}
}


/*******************************************************************************
 * Copyright (c) 2014 -- WPI Suite
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors: Nathan Hughes
 ******************************************************************************/

package edu.wpi.cs.wpisuitetng.modules.taskmanager.view.sidebar;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Collection;
import java.util.LinkedList;

import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;

import edu.wpi.cs.wpisuitetng.modules.taskmanager.model.Stage;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.model.StageList;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.presenter.Gateway;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.util.TaskUtil;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.view.IView;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.view.components.FormField;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.model.Stage;
/**
 * 
 * A sidebar view for editing the stages in a project.
 * 
 * @author wmtemple
 * @author wavanrensselaer
 * @author thhughes
 *
 */
public class ColumnEditView extends JPanel implements IView {

	/**
	 * Eclipse-generated serial version UID
	 */
	private static final long serialVersionUID = 7727625542299321948L;

	private StageList stages;
	private JList<Stage> stageJList;
	private JTextField titleEntry;
	private JTextField newName;
	private JButton addButton;
	private JButton moveUpBtn;
	private JButton moveDnBtn;
	private JButton nameChange;
	private Gateway gateway;

	public ColumnEditView() {
		stages = new StageList();
		this.stageJList = new JList<Stage>();
		this.addButton = new JButton("+");
		this.moveUpBtn = new JButton("Move Up");
		this.moveDnBtn = new JButton("Move Down");
		this.titleEntry = new JTextField();
		this.newName = new JTextField();
		this.nameChange = new JButton("Edit Name");
		
		this.titleEntry.setBorder(FormField.BORDER_NORMAL);
		this.newName.setBorder(FormField.BORDER_NORMAL);

		addButton.addActionListener( new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				addStage();
			}});

		titleEntry.addKeyListener( new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {
				
			}

			@Override
			public void keyPressed(KeyEvent e) {
				updateTextBox();
			}

			@Override
			public void keyReleased(KeyEvent e) {
				updateTextBox();
			}
		});
		
		titleEntry.addFocusListener(new FocusListener() {
			@Override
			public void focusGained(FocusEvent e) {
				updateTextBox();
			}

			@Override
			public void focusLost(FocusEvent e) {
				updateTextBox();
			}
		});
		

		stageJList.addKeyListener( new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {}

			@Override
			public void keyPressed(KeyEvent e) {
				if( e.getKeyCode() == KeyEvent.VK_DELETE && !stageJList.isSelectionEmpty()) {

					if( stages.size() > 1 ) {
						stages.remove(stageJList.getSelectedIndex());
						updateJListAndPublish();
					} else {
						//TODO visual feedback when there is only one stage
					}
				}
			}

			@Override
			public void keyReleased(KeyEvent e) {}

		});

		moveUpBtn.addActionListener( new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				moveCurrentTaskUp();
			}});

		moveDnBtn.addActionListener( new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				moveCurrentTaskDn();
			}});


		nameChange.addActionListener( new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//add logic
				changeNameStage();
			}});
		



		GridBagConstraints gbc = new GridBagConstraints();
		this.setLayout(new GridBagLayout());

		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.weightx = 1.0;
		gbc.insets = new Insets(20, 20, 0, 10);
		gbc.fill = GridBagConstraints.HORIZONTAL;
		this.add(titleEntry, gbc);

		gbc.gridx = 1;
		gbc.weightx = 0;
		gbc.fill = GridBagConstraints.NONE;
		gbc.insets = new Insets(20, 0, 0, 20);
		this.add(addButton, gbc);

		gbc.gridwidth = 2;
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.weighty = 1.0;
		gbc.weightx = 1.0;
		gbc.insets = new Insets(10, 20, 10, 20);
		gbc.fill = GridBagConstraints.BOTH;
		this.add(stageJList, gbc);
		gbc.gridwidth = 1;

		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.weightx = 1.0;
		gbc.weighty = 0.;
		gbc.insets = new Insets(20, 20, 0, 20);
		gbc.fill = GridBagConstraints.HORIZONTAL;
		this.add(newName, gbc);

		gbc.gridx = 1;
		gbc.gridy = 2;
		gbc.weightx = 1.0;
		gbc.insets = new Insets(20, 20, 0, 20);
		this.add(nameChange, gbc);

		gbc.gridwidth = 1;
		gbc.weightx = 1.0;
		gbc.gridx = 0;
		gbc.gridy = 3;
		gbc.weighty = 0.0;
		gbc.insets = new Insets(10, 20, 20, 10);
		gbc.fill = GridBagConstraints.HORIZONTAL;
		this.add(moveDnBtn, gbc);

		gbc.gridx = 1;
		gbc.gridy = 3;
		gbc.insets = new Insets(10, 10, 20, 20);
		this.add(moveUpBtn, gbc);



	}

	protected void moveCurrentTaskUp() {
		int index = stageJList.getSelectedIndex();
		if( index == 0 ) return;
		Stage st = stages.remove(index);
		stages.add(index-1, st);
		updateJListAndPublish();
	}

	
	protected void moveCurrentTaskDn() {
		int index = stageJList.getSelectedIndex();
		if( (index+1) == stages.size() ) return;
		Stage st = stages.remove(index);
		stages.add(index+1, st);
		updateJListAndPublish();
	}

	public void setStages(StageList newStages) {
		if( !newStages.equals(stages)) {
			Stage pSelected = stageJList.getSelectedValue();
			this.stages.clear();
			this.stages.addAll(newStages);
			stageJList.setListData(new Stage[0]);
			stageJList.setListData(stages.toArray(new Stage[0]));
			if(pSelected != null && stages.contains(pSelected)) stageJList.setSelectedValue(pSelected, true);
		}
	}

	private void updateTextBox() {
		boolean valid = !TaskUtil.sanitizeInput(titleEntry.getText()).isEmpty();
		titleEntry.setBorder(valid ? FormField.BORDER_NORMAL : FormField.BORDER_ERROR);
		addButton.setEnabled(valid);
	}


	@Override
	public void setGateway(Gateway gateway) {
		this.gateway = gateway;
	}

	
	protected void changeNameStage(){
		boolean valid = !TaskUtil.sanitizeInput(newName.getText()).isEmpty();
		if (valid){
			
//			int index = stageJList.getSelectedIndex();	
			//Stage stage = stageJList.getSelectedValue();
			Stage stage;
			
			stage = stages.remove(stageJList.getSelectedIndex());
			
			String newStageName = newName.getText();
			
			this.gateway.toPresenter("LocalCache", "renameStage", stage.getName(), newStageName);
			
			stage.setName(newStageName);
			stages.add(stageJList.getSelectedIndex(), stage);
			updateJListAndPublish();
			newName.setText("");
			 
		}

	}
	
	protected void addStage(){
		boolean nameFlag = false;
		String newName = new String(titleEntry.getText());
		newName = TaskUtil.sanitizeInput(newName);
		for(Stage s: stages){
			if(s.getName().equals(newName)){
				nameFlag = true;
			}
		}
		if (nameFlag){
			//TODO visual feedback when there is the input is invalid
			// aka cannot name two stages the same thing. 
			
		}else{
			stages.add(new Stage(newName));
			updateJListAndPublish();
			titleEntry.setText("");
			addButton.setEnabled(false);
		}

		
	}
	
	private void updateJListAndPublish() {
		Stage pS = stageJList.getSelectedValue();
		stageJList.setListData(stages.toArray(new Stage[0]));
		stageJList.setSelectedValue(pS, true);
		publishStages();

	}

	private void publishStages() {
		this.gateway.toPresenter("TaskPresenter", "publishChanges", stages);
	}
	/**
	 * 
	 * @return returns the number of stages in the columneditview. 
	 */
	public StageList getStages(){
		return stages;
	}
	
	/**
	 * 
	 * @return JTextFieled for the new entry
	 */
	public JTextField getTitleEntry(){
		return this.titleEntry;
	}
	
	/**
	 * 
	 * @return JTextField for the new name column
	 */
	public JTextField getNewName(){
		return this.newName;
	}
	
	
	/**
	 * 
	 * @return JList<Stage> that this contains for testing
	 */
	public JList<Stage> getStageJList(){
		return this.stageJList;
	}
	

}

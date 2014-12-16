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
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import edu.wpi.cs.wpisuitetng.modules.taskmanager.model.Stage;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.model.StageList;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.presenter.Gateway;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.util.TaskManagerUtil;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.util.TaskUtil;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.view.IView;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.view.components.FormField;
/**
 * 
 * A sidebar view for editing the stages in a project.
 * 
 * @author wmtemple
 * @author wavanrensselaer
 * @author thhughes
 * @author tmeehan
 * @author srojas
 * @author dpseaman
 * @author tmeehan
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
	private JButton deleteBtn;
	private Gateway gateway;

	/**
	 * Creates a sidebar view to change the edit view 
	 */
	public ColumnEditView() { 
		stages = new StageList();
		this.stageJList = new JList<Stage>();
		this.addButton = new JButton("Create Stage");
		this.moveUpBtn = new JButton();
		this.moveDnBtn = new JButton();
		this.titleEntry = new JTextField();
		this.newName = new JTextField();
		this.nameChange = new JButton("Rename Stage");
		this.deleteBtn = new JButton("Delete Stage");
		
		this.titleEntry.setBorder(FormField.BORDER_NORMAL);
		this.newName.setBorder(FormField.BORDER_NORMAL);
		this.stageJList.setBorder(FormField.BORDER_NORMAL);
		
		this.moveUpBtn.setIcon(new ImageIcon(this.getClass().getResource("icon_up.png")));
		this.moveDnBtn.setIcon(new ImageIcon(this.getClass().getResource("icon_down.png")));
		
		this.moveUpBtn.setPreferredSize(new Dimension(100, 25));
		this.moveDnBtn.setPreferredSize(new Dimension(100, 25));
		
		// disable stage name editing and delete when there's no stage selected
		this.addButton.setEnabled(false);
		this.newName.setEnabled(false);
		this.nameChange.setEnabled(false);
		this.deleteBtn.setEnabled(false);
		
		if(!stageJList.isSelectionEmpty()) {
			this.nameChange.setEnabled(false);
			this.deleteBtn.setEnabled(true);
			this.newName.setEnabled(true);
		}

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
		
		titleEntry.addKeyListener(new KeyListener(){

			@Override
			public void keyTyped(KeyEvent e) {
				// For some reason doesn't work for the below methods to add stuffs... Not sure. 
			    
			}

			@Override
			public void keyPressed(KeyEvent e) {
			    if (e.getKeyCode() == KeyEvent.VK_ENTER ){
			    	System.out.println("Fuck Swing!");
			    	addStage();
			    }
			}

			@Override
			public void keyReleased(KeyEvent e) {
			    if (e.getKeyCode() == KeyEvent.VK_ENTER ){
			    	System.out.println("Yeah fuck Swing!");
			    	addStage();
			    }
			}
			

		});
		
		newName.addKeyListener( new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {
				
			}

			@Override
			public void keyPressed(KeyEvent e) {
				updateEditBox();
			}

			@Override
			public void keyReleased(KeyEvent e) {
				updateEditBox();
			}
		});
		
		newName.addFocusListener(new FocusListener() {
			@Override
			public void focusGained(FocusEvent e) {
				updateEditBox();
			}

			@Override
			public void focusLost(FocusEvent e) {
				updateEditBox();
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
		
		
		// turns edit field and delete back on when a stage is selected
		stageJList.addListSelectionListener(new ListSelectionListener(){

			@Override
			public void valueChanged(ListSelectionEvent e) {
				if (!stageJList.isSelectionEmpty()) {
					newName.setEnabled(true);
					deleteBtn.setEnabled(true);
				}else{
					deleteBtn.setEnabled(false);
					nameChange.setEnabled(false);
				}
			}
		});
		
		stageJList.addMouseListener(new MouseAdapter(){
		    @Override
		    public void mouseClicked(MouseEvent e){
		        if(e.getClickCount()==2){
		            scrollMain();
		        }
		    }
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
		
		deleteBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				if( !stageJList.isSelectionEmpty()) {
					if( stages.size() > 1) {
						Stage stage = stages.remove(stageJList.getSelectedIndex());
						updateJListAndPublish();
						gateway.toPresenter("LocalCache", "archiveTasksForStage", stage);
					} else {
						//TODO visual feedback when there is only one stage
					}
				}
				stageJList.setSelectedIndex(0);
				stageJList.clearSelection();
				
			}});
		
		

		this.setBackground(TaskManagerUtil.SIDEBAR_COLOR);
		GridBagConstraints gbc = new GridBagConstraints();
		this.setLayout(new GridBagLayout());

		//top left bottom right
		
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.weightx = 1.0;
		gbc.insets = new Insets(20, 20, 0, 10);
		gbc.fill = GridBagConstraints.HORIZONTAL;
		this.add(titleEntry, gbc);

		gbc.gridx = 1;
		gbc.weightx = 0;
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
		gbc.weighty = 0;
		gbc.insets = new Insets(0, 20, 0, 10);
		gbc.fill = GridBagConstraints.HORIZONTAL;
		this.add(newName, gbc);

		gbc.gridx = 1;
		gbc.gridy = 2;
		gbc.weightx = 0;
		gbc.insets = new Insets(0, 0, 0, 20);
		this.add(nameChange, gbc);

		gbc.weightx = 1.0;
		gbc.gridx = 0;
		gbc.gridy = 3;
		gbc.weighty = 0.0;
		gbc.insets = new Insets(10, 20, 0, 10);
		gbc.fill = GridBagConstraints.HORIZONTAL;
		this.add(moveDnBtn, gbc);

		gbc.gridx = 1;
		gbc.gridy = 3;
		gbc.insets = new Insets(10, 0, 0, 20);
		this.add(moveUpBtn, gbc);
		
		gbc.weightx = 1.0;
		gbc.gridx = 0;
		gbc.gridy = 4;
		gbc.weighty = 0.0;
		gbc.gridwidth = 2;
		gbc.insets = new Insets(10, 20, 20, 20);
		gbc.fill = GridBagConstraints.HORIZONTAL;
		this.add(deleteBtn, gbc);
		
		this.setMinimumSize(new Dimension(300, 0));
	}

	/**
	 * Move stage up in the list
	 */
	protected void moveCurrentTaskUp() {
		int index = stageJList.getSelectedIndex();
		if( index == 0 ) return;
		Stage st = stages.remove(index);
		stages.add(index-1, st);
		updateJListAndPublish();
	}

	/**
	 * move stage down in the list
	 */
	protected void moveCurrentTaskDn() {
		int index = stageJList.getSelectedIndex();
		if( (index+1) == stages.size() ) return;
		Stage st = stages.remove(index);
		stages.add(index+1, st);
		updateJListAndPublish();
	}

	/**
	 * update list with stages from the cache
	 * @param newStages list of stages to update from
	 */
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

	/**
	 * update create text box
	 */
	private void updateTextBox() {
		boolean valid = !TaskUtil.sanitizeInput(titleEntry.getText()).isEmpty();
		titleEntry.setBorder(valid ? FormField.BORDER_NORMAL : FormField.BORDER_ERROR);
		addButton.setEnabled(valid);
	}
	
	/**
	 * update edit text box
	 */
	private void updateEditBox() {
		boolean valid = !TaskUtil.sanitizeInput(newName.getText()).isEmpty();
		titleEntry.setBorder(valid ? FormField.BORDER_NORMAL : FormField.BORDER_ERROR);
		nameChange.setEnabled(valid);
	}


	@Override
	public void setGateway(Gateway gateway) {
		this.gateway = gateway;
	}

	/**
	 * rename the selected stage
	 */
	protected void changeNameStage(){
		boolean valid = !TaskUtil.sanitizeInput(newName.getText()).isEmpty();
		if (valid){
			
			//int index = stageJList.getSelectedIndex();	
			//Stage stage = stageJList.getSelectedValue();
			Stage stage;
			
			stage = stages.remove(stageJList.getSelectedIndex());
			
			String newStageName = newName.getText();
			
			this.gateway.toPresenter("LocalCache", "renameStage", stage.getName(), newStageName);
			
			stage.setName(newStageName);
			stages.add(stageJList.getSelectedIndex(), stage);
			updateJListAndPublish();
			newName.setText("");
			nameChange.setEnabled(false);
			 
		}

	}
	
	/**
	 * add a new stage to the stage list
	 */
	protected void addStage(){
		boolean nameFlag = false;
		String newStageName = new String(titleEntry.getText());
		newStageName = TaskUtil.sanitizeInput(newStageName);
		
		//font needs to be derived to bold first because it is bold in the JPane 
		Font font = titleEntry.getFont().deriveFont(Font.BOLD);
		FontMetrics fm = titleEntry.getFontMetrics(font);
		// if the string is less than the limit of pixels returns the same string, if more it returns the reduced string
		newStageName = TaskManagerUtil.reduceString(newStageName,245,fm);

		
		for(Stage s: stages){
			if(s.getName().equals(newStageName)){
				nameFlag = true;
			}
			if(s.getName().equals(new String(""))){
				stages.remove(s);
			}
		}
		if (nameFlag){
			//TODO visual feedback when there is the input is invalid
			// aka cannot name two stages the same thing. 
			
		}else{
			stages.add(new Stage(newStageName));
			updateJListAndPublish();
			titleEntry.setText("");
			addButton.setEnabled(false);
		}

	}
	
	/**
	 * update the stage list with the values from the JList
	 */
	private void updateJListAndPublish() {
		Stage pS = stageJList.getSelectedValue();
		stageJList.setListData(stages.toArray(new Stage[0]));
		stageJList.setSelectedValue(pS, true);
		publishStages();

	}

	/**
	 * Tell the cache that a change has occured
	 */
	private void publishStages() {
		this.gateway.toPresenter("TaskPresenter", "publishChanges", stages);
	}
	
	/**
	 * Scrolls the jlist to the correct spot
	 */
	private void scrollMain(){
		this.gateway.toView("ColumnView", "scrollToPlace", this.stageJList.getSelectedValue());
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

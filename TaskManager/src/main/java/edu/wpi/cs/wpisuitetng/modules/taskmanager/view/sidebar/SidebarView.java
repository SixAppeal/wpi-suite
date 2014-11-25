package edu.wpi.cs.wpisuitetng.modules.taskmanager.view.sidebar;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import edu.wpi.cs.wpisuitetng.modules.taskmanager.model.Task;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.presenter.Gateway;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.view.IView;

/**
 * The sidebar that will be the container for task creation, editing, and reading
 * @author wavanrensselaer
 * @author akshoop
 * @author rnorlando
 * @author srojas
 * 
 */
public class SidebarView extends JPanel implements IView {
	private static final long serialVersionUID = -9157611802121055998L;

	Gateway gateway;


	private TaskDefaultView defaultView;
	private JTabbedPane tabPane;
	/**
	 * @return the tabPane
	 */


	private List<IView>  viewList; // list for views

	/**
	 * Constructs a sidebar view
	 */
	public SidebarView() {
		this.defaultView = new TaskDefaultView();

		this.tabPane = new JTabbedPane(JTabbedPane.LEFT);
		this.tabPane.addTab("Default View",this.defaultView);
		this.viewList = new ArrayList<IView>();

		this.setBackground(new Color(250, 250, 250));
		this.setMinimumSize(new Dimension(300, 0));
		this.setPreferredSize(new Dimension(300, 500));
		this.setMaximumSize(new Dimension(300, Integer.MAX_VALUE));
		this.add(tabPane);

		this.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();

		gbc.anchor = GridBagConstraints.PAGE_START;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		gbc.gridx = 0;
		gbc.gridy = 0;
		this.add(tabPane, gbc);
	}



	/**
	 * Shows the creation panel 
	 */
	public void addCreatePanel() {
		//add logic to avoid creating multiple empty tabs
		if (isThereAnEmptyTab()){
			return;
		}
		TaskCreateView createView = new TaskCreateView();
		createView.setGateway(this.gateway);
		tabPane.addTab("Create Task", createView);
		this.tabPane.setSelectedIndex(this.tabPane.indexOfComponent(createView));
		this.viewList.add(createView);
	}

	
	/**
	 * this method checks all of the views in the tabs to see if there is an empty tab open
	 * @return boolean , it returns true if there is an empty tab open, false if there is not
	 */
	public boolean isThereAnEmptyTab(){
		for (IView view : viewList){
			if (view instanceof TaskCreateView){
				boolean titleEmpty = (((TaskCreateView)view).titleEntry.getText().equals("")) ;
				boolean descEmpty = (((TaskCreateView)view).descEntry.getText().equals(""));
				boolean estEffortDefault =(((TaskCreateView)view).estEffortSpinner.getValue().equals(0)) ;

				//if fields empty switch to existing empty tab
				if (titleEmpty && descEmpty && estEffortDefault){
					this.tabPane.setSelectedIndex(this.tabPane.indexOfComponent((TaskCreateView)view));	
					return true;
				}
			}
		}
		return false;
	}
	
	

	
	
	/**
	 * getter for Tab pane
	 * @return tabPane
	 */
	public JTabbedPane getTabPane() {
		return tabPane;
	}
	

	/**
	 * Shows the edit panel
	 * @param task The task to edit
	 */
	public void addEditPanel(Task task) {

		for (IView view : viewList){
			if (view instanceof TaskEditView){
				if (((TaskEditView)view).getTask().equals(task)){
					this.tabPane.setSelectedIndex(this.tabPane.indexOfComponent((TaskEditView)view));
					return;
				}
			}
		}
		TaskEditView editView = new TaskEditView();
		editView.setGateway(this.gateway);
		tabPane.addTab("Edit Task", editView);
		this.tabPane.setSelectedIndex(this.tabPane.indexOfComponent(editView));

	}

	/**
	 * Shows the detail panel
	 * @param task The task to display
	 */
	public void addDetailPanel(Task task) {
		TaskDetailView detailView = new TaskDetailView();
		detailView.setGateway(this.gateway);
		tabPane.addTab("Create Task", detailView);
		this.tabPane.setSelectedIndex(this.tabPane.indexOfComponent(detailView));

	}

	/**
	 * @see IView.setGateway
	 */
	@Override
	public void setGateway(Gateway gateway) {
		this.gateway = gateway;

	}

}

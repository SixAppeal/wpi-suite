package edu.wpi.cs.wpisuitetng.modules.taskmanager.view.sidebar;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.SystemColor;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

import net.miginfocom.swing.MigLayout;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.model.Activity;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.model.Task;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.presenter.Gateway;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.view.IView;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.view.components.Form;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.view.components.FormField;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.view.sidebar.activitiesAndComments.ActivityLabel;


public class HistoryView extends JPanel implements IView{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2079867404332847272L;
	private Gateway gateway;
	private JPanel container;
	private JScrollPane scrollpane;
	private List<JTextArea> historyFields;
	private GridBagLayout gbc;
	private Task internalTask; 			// Keeps an internal task for comparing.  
	
	
	public HistoryView(){
		this.internalTask = new Task();
		this.container = new JPanel();
		this.scrollpane = new JScrollPane(this.container);
		
		this.historyFields = new ArrayList<JTextArea>();
		
		gbc = new GridBagLayout();
		this.container.setLayout(gbc);
		
		this.scrollpane.setMinimumSize(new Dimension(300, 0));
		this.scrollpane.setMaximumSize(new Dimension(300, Integer.MAX_VALUE));
		
		//this.setLayout(new MigLayout("fill, ins 20", "[300]"));
		this.setLayout(new BorderLayout(0,0));
		
		this.add(this.scrollpane);
		
	}
	
	
	/**
	 * This method takes in a task and displays all the history for a given task. 
	 * 
	 * @param task
	 */
	public void displayActivities(Task task){
		if(!task.equals(internalTask)){
			List<Activity> activities = task.getActivities();
			historyFields.clear();
			
			for(Activity a: activities){
				ActivityLabel temp = new ActivityLabel(a.getActivity());		
				historyFields.add(temp);
			}
			reflowHistory();
			this.internalTask = task;
		}
		
	}
		
	
	/**
	 * This function reflows the history pane to update everything based off the 
	 * updated history list. 
	 */
	public void reflowHistory(){
		this.container.removeAll();
		GridBagConstraints gbc = new GridBagConstraints();
		
		gbc.anchor = GridBagConstraints.PAGE_START;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.weightx = 1;
		gbc.insets = new Insets(0,0,0,0);
		gbc.gridx = 0;
		gbc.gridy = 0;
		
		int i = 0;
		for(JTextArea j : historyFields){
			gbc.gridy = i;
			
			if (i != 0) {
				gbc.insets.top = 10;
			}
			
			if (i == historyFields.size() - 1) {
				//gbc.weighty = 1.0;
				gbc.insets.bottom = 20;
			}
			
			this.container.add(j, gbc);
			
			i++;
		}
	}
	
	
	
	@Override
	public void setGateway(Gateway gateway) {
		this.gateway = gateway;
		
	}
	
	


}

package edu.wpi.cs.wpisuitetng.modules.taskmanager.view.sidebar.histNcom;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import net.miginfocom.swing.MigLayout;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.model.Activity;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.model.Task;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.presenter.Gateway;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.view.IView;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.view.components.Form;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.view.components.FormField;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.view.sidebar.activitiesandcomments.ActivityLabel;

public class HistoryView extends JPanel implements IView{
	
	private Gateway gateway;
	private JPanel body;
	private JPanel container;
	private JScrollPane scrollpane;
	private JTextField title;
	private List<JTextField> historyFields;
	
	
	public HistoryView(){
		this.body = new JPanel();
		this.container = new JPanel();
		this.scrollpane = new JScrollPane(this.container);
		this.title = new JTextField();
		// Setup Title:
			title.setText("History");
		
		this.historyFields = new ArrayList<JTextField>();
		
		
		this.container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
		
		this.scrollpane.setMinimumSize(new Dimension(300, 0));
		this.scrollpane.setMaximumSize(new Dimension(300, Integer.MAX_VALUE));
		this.setLayout(new MigLayout("fill, ins 0", "[300]"));
		
		this.add(title);
		this.add(this.scrollpane, "grow");
		
	}
	
	public void displayActivities(Task task){
		List<Activity> activities = task.getActivities();
		historyFields.clear();
		
		for(Activity a: activities){
			
			historyFields.add(new ActivityLabel(a.getActivity()));
		}
		reflowHistory();
		
	}
		
		
	public void reflowHistory(){
		for(JTextField j : historyFields){
			this.container.add(j);
		}
	}
	
	
	
	@Override
	public void setGateway(Gateway gateway) {
		this.gateway = gateway;
		
	}

}

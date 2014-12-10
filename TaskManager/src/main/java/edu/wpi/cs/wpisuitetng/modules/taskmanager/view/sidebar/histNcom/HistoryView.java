package edu.wpi.cs.wpisuitetng.modules.taskmanager.view.sidebar.histNcom;

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

public class HistoryView extends JPanel implements IView{
	
	private Gateway gateway;
	private JPanel body;
	private JPanel container;
	private JScrollPane scrollpane;
	private List<JTextArea> historyFields;
	private GridBagLayout newLayout;
	
	
	public HistoryView(){
		this.body = new JPanel();
		this.container = new JPanel();
		this.scrollpane = new JScrollPane(this.container);
		
		this.historyFields = new ArrayList<JTextArea>();
		
		newLayout = new GridBagLayout();
		
		
		this.container.setLayout(newLayout);
		
		//this.container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
		
		this.scrollpane.setMinimumSize(new Dimension(300, 0));
		this.scrollpane.setMaximumSize(new Dimension(300, Integer.MAX_VALUE));
		this.setLayout(new MigLayout("fill, ins 0", "[300]"));
		
		this.add(this.scrollpane, "grow");
		//this.add(container);
		
	}
	
	public void displayActivities(Task task){
		List<Activity> activities = task.getActivities();
		historyFields.clear();
		
		for(Activity a: activities){
			JTextArea temp = new JTextArea();
			temp.setOpaque(false);
			temp.setText(a.getActivity());
			temp.setSelectedTextColor(Color.DARK_GRAY);
			temp.setEditable(false);
			temp.setLineWrap(true);
			temp.setWrapStyleWord(true);
			temp.setText(a.getActivity());
			temp.setMaximumSize(new Dimension(260, 40));
			historyFields.add(temp);
		}
		reflowHistory();
		
	}
		
		
	public void reflowHistory(){
		this.container.removeAll();
		GridBagConstraints gbc = new GridBagConstraints();
		
		gbc.anchor = GridBagConstraints.PAGE_START;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.weightx = 1.0;
		gbc.insets = new Insets(20,20,0,20);
		gbc.gridwidth = 1;
		gbc.gridx = 0;
		gbc.gridy = 0;
		//this.container.setLayout(new GridBagLayout()));
		int i = 0;
		for(JTextArea j : historyFields){
			gbc.gridy = i;
			
			if (i != 0) {
				gbc.insets.top = 10;
			}
			
			if (i == historyFields.size() - 1) {
				gbc.weighty = 1.0;
				gbc.insets.bottom = 20;
			}
			
			this.container.add(j, gbc);
			System.out.println("Slap da bitch on the ass");
			
			i++;
		}
		System.out.println("Da bitch was slapped " + i + " times on the ass.");
	}
	
	
	
	@Override
	public void setGateway(Gateway gateway) {
		this.gateway = gateway;
		
	}
	
	


}

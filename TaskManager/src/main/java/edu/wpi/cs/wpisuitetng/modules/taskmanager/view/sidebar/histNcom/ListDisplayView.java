package edu.wpi.cs.wpisuitetng.modules.taskmanager.view.sidebar.histNcom;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagLayout;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import edu.wpi.cs.wpisuitetng.modules.taskmanager.presenter.Gateway;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.view.IView;

public class ListDisplayView extends JPanel implements IView{
	
	JComponent[] elements;
	JScrollPane scrollpane;
	JPanel container;
	Gateway gateway;
	
	ListDisplayView(){
		this.scrollpane = new JScrollPane(this.container);
		GridBagLayout gbc = new GridBagLayout();
		
		this.container.setLayout(gbc);
		
		this.scrollpane.setMinimumSize(new Dimension(300, 0));
		this.scrollpane.setMaximumSize(new Dimension(300, Integer.MAX_VALUE));
		
		//this.setLayout(new MigLayout("fill, ins 20", "[300]"));
		this.setLayout(new BorderLayout(0,0));
		
		this.add(this.scrollpane);
	}

	@Override
	public void setGateway(Gateway gateway) {
		this.gateway = gateway;
		
	}
	
}

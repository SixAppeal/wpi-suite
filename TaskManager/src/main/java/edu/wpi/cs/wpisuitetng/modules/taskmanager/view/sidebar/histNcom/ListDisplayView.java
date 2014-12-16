package edu.wpi.cs.wpisuitetng.modules.taskmanager.view.sidebar.histNcom;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import edu.wpi.cs.wpisuitetng.modules.taskmanager.presenter.Gateway;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.view.IView;

public class ListDisplayView extends JPanel implements IView{
	
	JComponent[] elements;
	JScrollPane scrollpane;
	JPanel container;
	Gateway gateway;
	
	/**
	 * A custom panel for displaying JComponents in a one vertical column
	 * @param elements the array of JComponents to be displayed
	 */
	ListDisplayView(JComponent[] elements){
		this.elements = elements;
		this.scrollpane = new JScrollPane(this.container);
		
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
	
	public void reflow(){
		this.container.removeAll();
		
		GridBagConstraints gbc = new GridBagConstraints();
		
		gbc.anchor = GridBagConstraints.PAGE_START;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.weightx = 0;
		gbc.weighty = 0;
		gbc.insets = new Insets(0,0,0,0);
		gbc.gridx = 0;
		gbc.gridy = 0;
		
		int i = 0;
		for(JComponent j : elements){
			gbc.gridy = i;
			
			if (i != 0) {
				gbc.insets.top = 10;
			}
			
			if (i == elements.length - 1) {
				gbc.insets.bottom = 20;
			}
			gbc.insets = new Insets(0,0,0,0);
			this.container.add(j, gbc);
			
			i++;
		}
	}
	
	
	
}

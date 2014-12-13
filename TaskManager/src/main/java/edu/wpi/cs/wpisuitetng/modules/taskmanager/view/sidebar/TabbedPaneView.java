package edu.wpi.cs.wpisuitetng.modules.taskmanager.view.sidebar;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import edu.wpi.cs.wpisuitetng.modules.taskmanager.presenter.Gateway;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.view.IView;

public class TabbedPaneView extends JTabbedPane implements IView{
	
	JComponent[] panes;
	Gateway gateway;
	
	/**
	 * Overloaded constructor
	 * @param panes The panes to be added to the JTabbedPane
	 */
	TabbedPaneView(JComponent... panes){
		this.panes = panes;
		addToPanes(panes);
			
	}
	
	/**
	 * adds the specified panes to the J
	 * @param panes
	 */
	public void addToPanes(JComponent... panes){
		for(int i = 0; i < this.panes.length; i++){
			this.addTab(panes[i].getName(), panes[i]);
		}
	}
	
	
	@Override
	public void setGateway(Gateway gateway){
		this.gateway = gateway;
		for(JComponent j : panes ){
			((IView) j).setGateway(gateway);
		}
	}
	
}

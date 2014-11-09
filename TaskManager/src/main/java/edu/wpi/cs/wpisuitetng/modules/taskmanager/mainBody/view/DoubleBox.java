package edu.wpi.cs.wpisuitetng.modules.taskmanager.mainBody.view;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class DoubleBox extends JPanel{
	
	public DoubleBox(){
		this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		this.add(new BoxTest());
		this.add(new BoxTest());
		
	}
	
	
}

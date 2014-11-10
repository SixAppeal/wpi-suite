package edu.wpi.cs.wpisuitetng.modules.taskmanager.mainBody.view;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class DoubleBox extends JPanel{
	
	public DoubleBox(){
		this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		BoxTest box1 = new BoxTest();
		BoxTest box2 = new BoxTest();
		box1.setPreferredSize(box1.getPreferredSize());
		box2.setPreferredSize(box2.getPreferredSize());
		this.add(box1);
		this.add(box2);
		
	}
	
	
}

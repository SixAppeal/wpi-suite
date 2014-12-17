//: c14:Dialogs.java
//Creating and using Dialog Boxes.
//<applet code=Dialogs width=125 height=75></applet>
//From 'Thinking in Java, 3rd ed.' (c) Bruce Eckel 2002
//www.BruceEckel.com. See copyright notice in CopyRight.txt.
package edu.wpi.cs.wpisuitetng.modules.taskmanager.view.components;

import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;

@SuppressWarnings("serial")
class MyDialog extends JDialog {
	
	
	public MyDialog(JFrame parent) {
		 super(parent, "My dialog", true);
		 Container cp = getContentPane();
		 cp.setLayout(new FlowLayout());
		 cp.add(new JLabel("Here is my dialog"));
		 JButton ok = new JButton("OK");
		 
		 ok.addActionListener(new ActionListener() {
		   public void actionPerformed(ActionEvent e) {
		     dispose(); // Closes the dialog
		   }
		 });
	 cp.add(ok);
	 setSize(150, 125);
	}
}


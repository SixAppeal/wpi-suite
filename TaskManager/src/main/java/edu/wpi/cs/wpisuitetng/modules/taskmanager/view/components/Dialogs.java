package edu.wpi.cs.wpisuitetng.modules.taskmanager.view.components;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JFrame;

@SuppressWarnings("serial")
public class Dialogs extends JApplet {
	  private JButton b1 = new JButton("Accept");
	  private JButton b2 = new JButton("Cancel");

	  private MyDialog dlg = new MyDialog(null);
	  

	  public void init() {
		this.setLayout(new FlowLayout());
		
		
	    b1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				 frame.dispose();
			}
	    });
	    
	    b2.addActionListener(new ActionListener() {
		      public void actionPerformed(ActionEvent e) {
		    	  frame.dispose();
		      }
		});
	    
	    
	    
	    
	    
	    
	    this.getContentPane().add(b2);
	    this.getContentPane().add(b1);
	  }

	  private static JFrame frame;
	  
	  public static void run(JApplet applet, int width, int height) {
	    frame = new JFrame();
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    frame.getContentPane().add(applet);
	    frame.setSize(width, height);
	    applet.init();
	    applet.start();
	    frame.setVisible(true);
	  }
	}

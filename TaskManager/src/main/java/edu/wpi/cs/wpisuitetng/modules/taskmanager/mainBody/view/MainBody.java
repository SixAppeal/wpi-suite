/*******************************************************************************
 * Copyright (c) 2013 WPI-Suite
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Team Rolling Thunder
 ******************************************************************************/
package edu.wpi.cs.wpisuitetng.modules.taskmanager.mainBody.view;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;

import edu.wpi.cs.wpisuitetng.janeway.gui.container.toolbar.DefaultToolbarView;
import edu.wpi.cs.wpisuitetng.janeway.gui.container.toolbar.ToolbarGroupView;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.toolbar.view.buttons.CreateTaskButtonPanel;

/**
 * Sets up upper toolbar of RequirementManager tab
 * 
 *
 * @version $Revision: 1.0 $
 * @author justinhess
 */
public class MainBody  extends DefaultToolbarView {
	private List<JTextField> fields = new ArrayList<JTextField>();
//	
//	public static JPanel cyanA;
//	public static JPanel cyanB;
//	public static JPanel cyanC;
	

	/**
	 * Creates and positions option buttons in upper toolbar
	 * @param visible boolean
	 */
	public MainBody(boolean visible) {
		JPanel body = new JPanel();
		body.setLayout(new BoxLayout(body, BoxLayout.X_AXIS));
		JPanel spacing = new JPanel();
//		cyanA = createPane(5, "Four", Color.cyan);
//		cyanB = createPane(5, "Four", Color.red);
//		cyanC = createPane(5, "Four", Color.green);
		DoubleBox newTest = new DoubleBox();
		body.add(spacing);
		body.add(newTest);
		
		
		
		JScrollPane jsp = new JScrollPane(body,
			JScrollPane.VERTICAL_SCROLLBAR_NEVER,
			JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		this.validate();
//		Dimension d = this.getPreferredSize();
//		d.height /= 2;
//		jsp.getViewport().setPreferredSize(d);
		jsp.getVerticalScrollBar().setUnitIncrement(5);
//	            this.getPreferredSize().height / fields.size());
        this.add(jsp);
        this.setVisible(true);

        
        
        
	}
	
	public void addBox(JPanel toAdd){
		this.add(toAdd);
	}
	
    public JPanel createPane(int n, String s, Color c) {
        JPanel outer = new JPanel();
        outer.setLayout(new BoxLayout(outer, BoxLayout.Y_AXIS));
        outer.setBorder(BorderFactory.createLineBorder(c, 2));
        for (int i = 0; i < n; i++) {
            JPanel inner = new JPanel();
            inner.setLayout(new BoxLayout(inner, BoxLayout.X_AXIS));
            JLabel label = new JLabel(s + i + ":", JLabel.RIGHT);
            label.setPreferredSize(new Dimension(80, 32));
            inner.add(label);
            JTextField tf = new JTextField("Stackoverflow!", 32);
            inner.add(tf);
            fields.add(tf);
            outer.add(inner);
        }
        return outer;
    }


}

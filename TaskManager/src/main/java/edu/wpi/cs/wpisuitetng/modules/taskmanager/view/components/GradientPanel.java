/*******************************************************************************
 * Copyright (c) 2014 -- WPI Suite
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:  Team Six-Appeal
 ******************************************************************************/
package edu.wpi.cs.wpisuitetng.modules.taskmanager.view.components;

import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

/**
 * Panel with a gradient background
 * @author wavanrensselaer
 * @author rnorlando
 */
public class GradientPanel extends JPanel {
	private static final long serialVersionUID = 4872431855033675373L;
	
	@Override
	protected void paintComponent(Graphics g) {
		int w = getWidth();
		int h = getHeight();
	 
		// Paint a gradient from top to bottom
		GradientPaint gp = new GradientPaint(
	    	0, 0, getBackground(),
	    	0, h, getBackground().darker());

		Graphics2D g2d = (Graphics2D) g;
		
		g2d.setPaint(gp);
		g2d.fillRect(0, 0, w, h);
	}
}

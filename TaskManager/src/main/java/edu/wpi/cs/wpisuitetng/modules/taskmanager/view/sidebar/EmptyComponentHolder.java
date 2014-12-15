/*******************************************************************************
 * 
 * Copyright (c) 2014 -- WPI Suite
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors: Nathan Hughes, Alexander Shoop, Will Rensselaer, Thomas Meehan, Ryan Orlando, Troy Hughes, Nathan Hughes
 ******************************************************************************/


package edu.wpi.cs.wpisuitetng.modules.taskmanager.view.sidebar;

import java.awt.Component;


/**
 * This Class holds is an empty JPanel, but holds the info for anothe JPanel
 * 
 * @author rnOrlando
 *
 */
public class EmptyComponentHolder extends Component
{
	
	private static final long serialVersionUID = 14623864823L;
	
	public Component contents;
	
	/**
	 * Constructor For EmptyJPanelHolder just sents the contents
	 * @param con
	 */
	EmptyComponentHolder(Component con)
	{
		contents = con;
	}

}

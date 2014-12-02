/*******************************************************************************
 * Copyright (c) 2014 -- WPI Suite
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors: Nathan Hughes
 ******************************************************************************/
package edu.wpi.cs.wpisuitetng.modules.taskmanager.localcache;

import edu.wpi.cs.wpisuitetng.modules.AbstractModel;
import edu.wpi.cs.wpisuitetng.exceptions.NotImplementedException;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.presenter.IPresenter;

/**
 * Interface to describe cache functionality 
 * 
 * @author nhhughes
 *
 */
public interface Cache extends IPresenter {

	public void store(String request, AbstractModel toStore);
	
	public void update(String request, AbstractModel newObject);
	
	public Object[] retrieve(String request);
	
	public Object[] retrieve(String request, String filter) throws NotImplementedException;
	
	public void clearCache(String request);
	
	public void subscribe(String request);

	public void set(String request, String data);
	
	public void sync(String request);
	
	public void addVerified(String request, String data);
	
	public void updateVerified(String request, String data);

	public void initStageList();

}



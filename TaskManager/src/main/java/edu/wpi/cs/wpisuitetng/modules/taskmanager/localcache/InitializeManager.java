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

import java.util.concurrent.Semaphore;

import com.google.gson.Gson;

import edu.wpi.cs.wpisuitetng.modules.taskmanager.model.StageList;
import edu.wpi.cs.wpisuitetng.network.RequestObserver;
import edu.wpi.cs.wpisuitetng.network.models.IRequest;

/**
 * Request Observer to first sync with every different entity manager upon startup of Janeway
 * @author nathan
 *
 */
public class InitializeManager implements RequestObserver{
	
	private Cache localCache;
	private Semaphore initSync;
	
	/**
	 * Takes in a copy of the cache and a semaphore to let Janeway proceed with initialization
	 * @param localCache cache to use
	 * @param initSync semaphore to notify when a request is finished
	 */
	public InitializeManager(Cache localCache, Semaphore initSync){
		this.localCache = localCache;
		this.initSync = initSync;
	}

	/**
	 * Notify cache and semaphore when a successful response comes back
	 */
	@Override
	public void responseSuccess(IRequest iReq) {
		String[] splitPath = iReq.getUrl().getPath().split("/");
		if (splitPath[splitPath.length-1].equals("stages")) {
			String updateValue = iReq.getResponse().getBody();
			StageList[] returned = new Gson().fromJson(updateValue, StageList[].class);
			if (returned.length == 0){
				this.localCache.initStageList();
			}
			else {
				((ThreadSafeLocalCache)this.localCache).updateStages(updateValue);
			}
			this.initSync.release();
		}
		if (splitPath[splitPath.length-1].equals("task")) {
			String updateValue = iReq.getResponse().getBody();
			((ThreadSafeLocalCache)this.localCache).updateTasks(updateValue);
			this.initSync.release();
		}
		if (splitPath[splitPath.length-1].equals("user")) {
			String updateValue = iReq.getResponse().getBody();
			((ThreadSafeLocalCache)this.localCache).updateMembers(updateValue);
			this.initSync.release();
		}
//		if (splitPath[splitPath.length-1].equals("requirement")) {
//			String reqVal = iReq.getResponse().getBody();
//			((ThreadSafeLocalCache)this.localCache).updateReqs(reqVal);
//			this.initSync.release();
//		}
	}

	/**
	 * @see RequestObserver.responseError
	 */
	@Override
	public void responseError(IRequest iReq) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * @see RequestObserver.fail
	 */
	@Override
	public void fail(IRequest iReq, Exception exception) {
		// TODO Auto-generated method stub
		
	}

}

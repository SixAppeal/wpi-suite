package edu.wpi.cs.wpisuitetng.modules.taskmanager.localcache;

import java.util.concurrent.Semaphore;

import com.google.gson.Gson;

import edu.wpi.cs.wpisuitetng.modules.taskmanager.model.StageList;
import edu.wpi.cs.wpisuitetng.network.RequestObserver;
import edu.wpi.cs.wpisuitetng.network.models.IRequest;

public class InitializeManager implements RequestObserver{
	
	private Cache localCache;
	private Semaphore initSync;
	
	public InitializeManager(Cache localCache, Semaphore initSync){
		this.localCache = localCache;
		this.initSync = initSync;
	}

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
	}

	@Override
	public void responseError(IRequest iReq) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void fail(IRequest iReq, Exception exception) {
		// TODO Auto-generated method stub
		
	}

}

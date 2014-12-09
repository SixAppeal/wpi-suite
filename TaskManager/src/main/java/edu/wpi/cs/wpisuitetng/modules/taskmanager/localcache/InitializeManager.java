package edu.wpi.cs.wpisuitetng.modules.taskmanager.localcache;

import com.google.gson.Gson;

import edu.wpi.cs.wpisuitetng.modules.taskmanager.model.StageList;
import edu.wpi.cs.wpisuitetng.network.RequestObserver;
import edu.wpi.cs.wpisuitetng.network.models.IRequest;

public class InitializeManager implements RequestObserver{
	
	private Cache localCache;
	
	public InitializeManager(Cache localCache){
		this.localCache = localCache;
		
	}

	@Override
	public void responseSuccess(IRequest iReq) {
		String updateValue = iReq.getResponse().getBody();
		StageList[] returned = new Gson().fromJson(updateValue, StageList[].class);
		
		
		if (returned.length == 0){
			this.localCache.initStageList();
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

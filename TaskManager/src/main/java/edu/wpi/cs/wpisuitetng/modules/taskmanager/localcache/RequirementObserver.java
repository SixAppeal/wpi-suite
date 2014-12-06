package edu.wpi.cs.wpisuitetng.modules.taskmanager.localcache;

import java.util.List;

import edu.wpi.cs.wpisuitetng.modules.taskmanager.presenter.Gateway;
import edu.wpi.cs.wpisuitetng.network.RequestObserver;
import edu.wpi.cs.wpisuitetng.network.models.IRequest;

public class RequirementObserver implements RequestObserver {

	private Cache localCache;
	private String toUpdate;
	private List<String> requirements;
	private Gateway gateway;

	public RequirementObserver(Cache localCache, String toUpdate, List<String> requirements, Gateway gateway) {
		this.localCache = localCache;
		this.toUpdate = toUpdate;
		this.requirements = requirements;
		this.gateway = gateway;
	}

	@Override
	public void responseSuccess(IRequest iReq) {
		localCache.updateVerified(toUpdate, iReq.getBody());
		for (String s: requirements) {
			gateway.toPresenter(s.split(":")[0], s.split(":")[1]);
		}
	}

	/**
	 * @see RequestObserver.responseError
	 */
	@Override
	public void responseError(IRequest iReq) {
		System.err.println("Error Refreshing Cache for " + toUpdate);
	}

	/**
	 * @see RequestObserver.fail
	 */
	@Override
	public void fail(IRequest iReq, Exception exception) {
		System.err.println("Error Refreshing Cache for " + toUpdate);
	}
}

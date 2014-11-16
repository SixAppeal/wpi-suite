package edu.wpi.cs.wpisuitetng.modules.taskmanager.presenter;

import com.google.gson.Gson;

import edu.wpi.cs.wpisuitetng.modules.core.models.User;
import edu.wpi.cs.wpisuitetng.network.RequestObserver;
import edu.wpi.cs.wpisuitetng.network.models.IRequest;


/**
 * Class to deal with all member requests
 * 
 * @author nhhughes
 * @author jrhennessy
 */
public class GetMembersObserver implements RequestObserver {

	private TaskPresenter presenter;
	
	public GetMembersObserver(TaskPresenter presenter) {
		this.presenter = presenter;
	}
	
	/**
	 * Cache the members returned from the network
	 * @see RequestObserver.responseSuccess
	 */
	@Override
	public void responseSuccess(IRequest iReq) {
		// TODO Auto-generated method stub
		User[] members = new Gson().fromJson(iReq.getResponse().getBody(), User[].class);
		String[] to_submit = new String[members.length];
		for (int i = 0; i < members.length; i++) {
			to_submit[i] = members[i].getUsername();
		}
		presenter.updateMembers(to_submit);
		
	}

	/**
	 * @see RequestObserver.responseError
	 */
	@Override
	public void responseError(IRequest iReq) {
		System.err.println("GetMemberObserver: Error retrieving the members");
	}

	/**
	 * @see RequestObserver.fail
	 */
	@Override
	public void fail(IRequest iReq, Exception exception) {
		System.err.println("GetMemberObserver: Error retrieving all members");
	}
}

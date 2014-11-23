/**
 * 
 */
package edu.wpi.cs.wpisuitetng.modules.taskmanager.localcache;

import java.util.ArrayList;
import java.util.List;

import edu.wpi.cs.wpisuitetng.exceptions.NotImplementedException;
import edu.wpi.cs.wpisuitetng.modules.core.models.User;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.model.Task;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.model.TaskStatus;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.presenter.Gateway;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;

/**
 * This class is responsible for managing local copies of the database information
 * @author nhhughes
 *
 */
public class LocalCache implements Cache {
	
	private List<Task> tasks;
	private List<Task> archive;
	private List<User> members;
	private List<TaskStatus> statuses;
	private Gateway gateway;
	
	/**
	 * Initializes the local cache with a lookup table and some cache data structures
	 * @author nhhughes
	 */
	public LocalCache(Gateway gateway) {
		tasks = new ArrayList<Task>();
		archive = new ArrayList<Task>();
		members = new ArrayList<User>();
		statuses = new ArrayList<TaskStatus>();
		this.gateway = gateway;
	}
	

	/**
	 * @see edu.wpi.cs.wpisuitetng.modules.taskmanager.localcache.ICache#store(java.lang.String, java.lang.Object)
	 */
	@Override
	public boolean store(String request, Object toStore) {
		if (request.compareTo("task") == 0) {
			tasks.add((Task) toStore);
			return true;
		}
		if (request.compareTo("archive") == 0) {
			archive.add((Task) toStore);
			return true;
		}
		if (request.compareTo("member") == 0) {
			members.add((User) toStore);
			return true;
		}
		if (request.compareTo("stage") == 0) {
			statuses.add((TaskStatus) toStore);
			return true;
		}
		return false;
	}


	/**
	 * @see edu.wpi.cs.wpisuitetng.modules.taskmanager.localcache.ICache#update(java.lang.String, java.lang.Object)
	 */
	@Override
	public boolean update(String request, Object toUpdate) {
		if (request.compareTo("task") == 0) {
			tasks.remove((Task) toUpdate);
			tasks.add((Task) toUpdate);
			final Request networkRequest = Network.getInstance().makeRequest("taskmanager/task", HttpMethod.POST);
			networkRequest.setBody(((Task)toUpdate).toJson());
			networkRequest.addObserver(new CallbackManager(gateway, ""));
			networkRequest.send();
			return true;
		}
		if (request.compareTo("archive") == 0) {
			archive.remove((Task) toUpdate);
			archive.add((Task) toUpdate);
			final Request networkRequest = Network.getInstance().makeRequest("taskmanager/task", HttpMethod.POST);
			networkRequest.setBody(((Task)toUpdate).toJson());
			networkRequest.addObserver(new CallbackManager(gateway, ""));
			networkRequest.send();
			return true;
		}
		if (request.compareTo("member") == 0) {
			members.remove((User) toUpdate);
			members.add((User) toUpdate);
			final Request networkRequest = Network.getInstance().makeRequest("taskmanager/task", HttpMethod.POST);
			networkRequest.setBody(((Task)toUpdate).toJson());
			networkRequest.addObserver(new CallbackManager(gateway, ""));
			networkRequest.send();
			return true;
		}
		if (request.compareTo("stage") == 0) {
			statuses.remove((TaskStatus) toUpdate);
			statuses.add((TaskStatus) toUpdate);
			final Request networkRequest = Network.getInstance().makeRequest("taskmanager/task", HttpMethod.POST);
			networkRequest.setBody(((Task)toUpdate).toJson());
			networkRequest.addObserver(new CallbackManager(gateway, ""));
			networkRequest.send();
			return true;
		}
		return false;
	}

	/**
	 * @see edu.wpi.cs.wpisuitetng.modules.taskmanager.localcache.ICache#delete(java.lang.String, java.lang.Object)
	 */
	@Override
	public boolean delete(String request, Object toDelete) {
		if (request.compareTo("task") == 0) {
			tasks.remove((Task) toDelete);
			return true;
		}
		if (request.compareTo("archive") == 0) {
			archive.remove((Task) toDelete);
			return true;
		}
		if (request.compareTo("member") == 0) {
			members.remove((User) toDelete);
			return true;
		}
		if (request.compareTo("stage") == 0) {
			statuses.remove((TaskStatus) toDelete);
			return true;
		}
		return false;
	}

	/**
	 * @see edu.wpi.cs.wpisuitetng.modules.taskmanager.localcache.ICache#retreive(java.lang.String)
	 */
	@Override
	public Object[] retreive(String request) {
		if (request.compareTo("task") == 0) {
			return tasks.toArray(new Task[0]);
		}
		if (request.compareTo("archive") == 0) {
			return archive.toArray(new Task[0]);
		}
		if (request.compareTo("member") == 0) {
			return members.toArray(new User[0]);
		}
		if (request.compareTo("stage") == 0) {
			return members.toArray(new TaskStatus[0]);
		}
		return null;
	}

	/**
	 * @throws NotImplementedException 
	 * @see edu.wpi.cs.wpisuitetng.modules.taskmanager.localcache.ICache#retreive(java.lang.String, java.lang.String)
	 */
	@Override
	public Object[] retreive(String request, String filter) throws NotImplementedException {
		throw new NotImplementedException();
	}

	/**
	 * @see edu.wpi.cs.wpisuitetng.modules.taskmanager.localcache.ICache#clearCache(java.lang.String)
	 */
	@Override
	public boolean clearCache(String request) {
		if (request.compareTo("task") == 0) {
			tasks = new ArrayList<Task>();
			return true;
		}
		if (request.compareTo("archive") == 0) {
			archive = new ArrayList<Task>();
			return true;
		}
		if (request.compareTo("member") == 0) {
			members = new ArrayList<User>();
			return true;
		}
		if (request.compareTo("stage") == 0) {
			statuses = new ArrayList<TaskStatus>();
			return true;
		}
		return false;
	}

	/**
	 * @see edu.wpi.cs.wpisuitetng.modules.taskmanager.localcache.ICache#subscribe()
	 */
	@Override
	public void subscribe(String topic, String action, String callback) {
		// TODO Auto-generated method stub
		
	}

}

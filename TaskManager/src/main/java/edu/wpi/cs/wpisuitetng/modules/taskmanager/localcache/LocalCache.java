/**
 * 
 */
package edu.wpi.cs.wpisuitetng.modules.taskmanager.localcache;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;

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
	private Map<String, List<String>> callbacks;
	
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
		callbacks = new HashMap<String, List<String>>();
		callbacks.put("tasks", new ArrayList<String>());
		callbacks.put("archive", new ArrayList<String>());
		callbacks.put("members", new ArrayList<String>());
		callbacks.put("stage", new ArrayList<String>());
	}
	
	/**
	 * @throws NotImplementedException 
	 * @see edu.wpi.cs.wpisuitetng.modules.taskmanager.localcache.ICache#store(java.lang.String, java.lang.Object)
	 */
	@Override
	public void store(String request, Object toStore) throws NotImplementedException {
		if (request.equals("task")) {
			final Request networkRequest = Network.getInstance().makeRequest("taskmanager/task", HttpMethod.GET);
			networkRequest.addObserver(new CallbackManager(gateway, callbacks.get("tasks")));
			networkRequest.send();
		}
		if (request.equals("archive")) {
			final Request networkRequest = Network.getInstance().makeRequest("taskmanager/task", HttpMethod.GET);
			networkRequest.addObserver(new CallbackManager(gateway, callbacks.get("archive")));
			networkRequest.send();
		}
		if (request.equals("stage")) {
			final Request networkRequest = Network.getInstance().makeRequest("taskmanager/stage", HttpMethod.GET);
			networkRequest.addObserver(new CallbackManager(gateway, callbacks.get("stage")));
			networkRequest.send();
		}
	}

	/**
	 * @throws NotImplementedException 
	 * @see edu.wpi.cs.wpisuitetng.modules.taskmanager.localcache.ICache#update(java.lang.String, java.lang.Object, java.lang.Object)
	 */
	@Override
	public void update(String request, Object oldObject, Object newObject) throws NotImplementedException {
		if (request.equals("task")) {
			final Request networkRequest = Network.getInstance().makeRequest("taskmanager/task", HttpMethod.POST);
			networkRequest.setBody(((Task)newObject).toJson());
			networkRequest.addObserver(new CallbackManager(gateway, callbacks.get("tasks")));
			networkRequest.send();
		}
		if (request.equals("archive")) {
			final Request networkRequest = Network.getInstance().makeRequest("taskmanager/task", HttpMethod.POST);
			networkRequest.setBody(((Task)newObject).toJson());
			networkRequest.addObserver(new CallbackManager(gateway, callbacks.get("archive")));
			networkRequest.send();
		}
		if (request.equals("stage")) {
			final Request networkRequest = Network.getInstance().makeRequest("taskmanager/stage", HttpMethod.POST);
			//TODO Need to figure out how stages are stored
			//networkRequest.setBody(this.statuses.toJson());
			networkRequest.addObserver(new CallbackManager(gateway, callbacks.get("stage")));
			networkRequest.send();
		}
	}

	/**
	 * @throws NotImplementedException 
	 * @see edu.wpi.cs.wpisuitetng.modules.taskmanager.localcache.ICache#delete(java.lang.String, java.lang.Object)
	 */
	@Override
	public void delete(String request, Object toDelete) throws NotImplementedException {
		throw new NotImplementedException();
	}

	/**
	 * @see edu.wpi.cs.wpisuitetng.modules.taskmanager.localcache.ICache#retreive(java.lang.String)
	 */
	@Override
	public Object[] retreive(String request) {
		if (request.equals("task")) {
			return tasks.toArray(new Task[0]);
		}
		if (request.equals("archive")) {
			return archive.toArray(new Task[0]);
		}
		if (request.equals("member")) {
			return members.toArray(new User[0]);
		}
		if (request.equals("stage")) {
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

	public void sync(String request) {
		if (request.equals("task")) {
			final Request networkRequest = Network.getInstance().makeRequest("taskmanager/task", HttpMethod.GET);
			networkRequest.addObserver(new SyncManager((Cache)this, "task"));
			networkRequest.send();
		}
		if (request.equals("archive")) {
			final Request networkRequest = Network.getInstance().makeRequest("taskmanager/task", HttpMethod.GET);
			networkRequest.addObserver(new SyncManager((Cache)this, "archive"));
			networkRequest.send();
		}
		if (request.equals("member")) {
			final Request networkRequest = Network.getInstance().makeRequest("core/user", HttpMethod.GET);
			networkRequest.addObserver(new SyncManager((Cache)this, "member"));
			networkRequest.send();
		}
		if (request.equals("stage")) {
			//TODO actually write this
		}
	}
	
	/**
	 * @see edu.wpi.cs.wpisuitetng.modules.taskmanager.localcache.ICache#clearCache(java.lang.String)
	 */
	@Override
	public void clearCache(String request) {
		if (request.equals("task")) {
			tasks = new ArrayList<Task>();
		}
		if (request.equals("archive")) {
			archive = new ArrayList<Task>();
		}
		if (request.equals("member")) {
			members = new ArrayList<User>();
		}
		if (request.equals("stage")) {
			statuses = new ArrayList<TaskStatus>();
		}
	}

	/**
	 * @see edu.wpi.cs.wpisuitetng.modules.taskmanager.localcache.ICache#subscribe()
	 */
	@Override
	public void subscribe(String topic, String action, String callback) {
		// TODO Auto-generated method stub
		
	}
	
	public void set(String request, String updateValue) {
		if (request.equals("task")) {
			Task[] returned  = new Gson().fromJson(updateValue, Task[].class);
			tasks = new ArrayList<Task>();
			for (Task t: returned) {
				if (!t.isArchived()) {
					tasks.add(t);
				}
			}
		}
		if (request.equals("archive")) {
			Task[] returned  = new Gson().fromJson(updateValue, Task[].class);
			archive = new ArrayList<Task>();
			for (Task t: returned) {
				if (t.isArchived()) {
					archive.add(t);
				}
			}
		}
		if (request.equals("member")) {
			User[] returned = new Gson().fromJson(updateValue, User[].class);
			members = new ArrayList<User>(Arrays.asList(returned));
		}
		if (request.equals("stage")) {
			//TODO implement this
		}
	}

}

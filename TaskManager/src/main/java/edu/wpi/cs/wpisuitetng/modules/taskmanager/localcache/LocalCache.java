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
import edu.wpi.cs.wpisuitetng.modules.taskmanager.model.Stage;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.model.Task;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.presenter.Gateway;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.presenter.IPresenter;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;

/**
 * This class is responsible for managing local copies of the database
 * information and for communicating directly with the server
 * 
 * @author nhhughes
 *
 */
public class LocalCache implements Cache, IPresenter {

	List<Task> tasks;
	List<Task> archives;
	List<User> members;
	List<Stage> stages;
	Gateway gateway;
	Map<String, List<String>> callbacks;

	/**
	 * Initializes the local cache with a lookup table and some cache data
	 * structures
	 * 
	 * @author nhhughes
	 */
	public LocalCache() {
		tasks = new ArrayList<Task>();
		archives = new ArrayList<Task>();
		members = new ArrayList<User>();
		stages = new ArrayList<Stage>();
		callbacks = new HashMap<String, List<String>>();
		callbacks.put("task", new ArrayList<String>());
		callbacks.put("archive", new ArrayList<String>());
		callbacks.put("member", new ArrayList<String>());
		callbacks.put("stage", new ArrayList<String>());
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
			archives = new ArrayList<Task>();
		}
		if (request.equals("member")) {
			members = new ArrayList<User>();
		}
		if (request.equals("stage")) {
			stages = new ArrayList<Stage>();
		}
	}

	/**
	 * @see edu.wpi.cs.wpisuitetng.modules.taskmanager.localcache.ICache#subscribe()
	 */
	@Override
	public void subscribe(String request) {
		String topic = request.split(":")[0];
		String callback = request.split(":")[1] + ":" + request.split(":")[2];
		if (callbacks.containsKey(topic)) {
			List<String> currentCallbacks = callbacks.get(topic);
			currentCallbacks.add(callback);
		}
	}

	/**
	 * @see edu.wpi.cs.wpisuitetng.modules.taskmanager.localcache.ICache#retreive(java.lang.String)
	 */
	@Override
	public Object[] retrieve(String request) {
		if (request.equals("task")) {
			return tasks.toArray(new Task[0]);
		}
		if (request.equals("archive")) {
			return archives.toArray(new Task[0]);
		}
		if (request.equals("member")) {
			return members.toArray(new User[0]);
		}
		if (request.equals("stage")) {
			return members.toArray(new Stage[0]);
		}
		return null;
	}

	/**
	 * @throws NotImplementedException
	 * @see edu.wpi.cs.wpisuitetng.modules.taskmanager.localcache.ICache#retreive(java.lang.String,
	 *      java.lang.String)
	 */
	@Override
	public Object[] retrieve(String request, String filter)
			throws NotImplementedException {
		throw new NotImplementedException();
	}

	/**
	 * @see edu.wpi.cs.wpisuitetng.modules.taskmanager.localcache.ICache#store(java.lang.String,
	 *      java.lang.Object)
	 */
	@Override
	public void store(String request, Task toStore) {
		if (request.equals("task")) {
			final Request networkRequest = Network.getInstance().makeRequest(
					"taskmanager/task", HttpMethod.PUT);
			networkRequest.addObserver(new CallbackManager(gateway, callbacks
					.get("task")));
			networkRequest.addObserver(new AddManager(this, request));
			networkRequest.setBody(toStore.toJson());
			networkRequest.send();
		}
		if (request.equals("archive")) {
			final Request networkRequest = Network.getInstance().makeRequest(
					"taskmanager/task", HttpMethod.PUT);
			networkRequest.addObserver(new CallbackManager(gateway, callbacks
					.get("archive")));
			networkRequest.addObserver(new AddManager(this, request));
			networkRequest.setBody(toStore.toJson());
			networkRequest.send();
		}
		if (request.equals("stage")) {
			// TODO Implement this
		}
	}
	
	public void store(String request, List<Stage> toStore) {
		if (request.equals("stage")) {
			// TODO Implement this
		}
	}

	/**
	 * @see edu.wpi.cs.wpisuitetng.modules.taskmanager.localcache.ICache#update(java.lang.String,
	 *      java.lang.Object, java.lang.Object)
	 */
	@Override
	public void update(String request, Task newObject) {
		if (request.equals("task")) {
			final Request networkRequest = Network.getInstance().makeRequest(
					"taskmanager/task", HttpMethod.POST);
			networkRequest.setBody(((Task) newObject).toJson());
			networkRequest.addObserver(new CallbackManager(gateway, callbacks
					.get("tasks")));
			networkRequest.send();
		}
		if (request.equals("archive")) {
			final Request networkRequest = Network.getInstance().makeRequest(
					"taskmanager/task", HttpMethod.POST);
			networkRequest.setBody(((Task) newObject).toJson());
			networkRequest.addObserver(new CallbackManager(gateway, callbacks
					.get("archive")));
			networkRequest.send();
		}
		
	}

	public void update(String request, List<Stage> newObject) {
		if (request.equals("stage")) {
			// TODO Implement
		}
	}
	
	/**
	 * @see edu.wpi.cs.wpisuitetng.modules.taskmanager.localcache.ICache#sync(java.lang.String)
	 */
	@Override
	public void sync(String request) {
		if (request.equals("task")) {
			final Request networkRequest = Network.getInstance().makeRequest("taskmanager/task", HttpMethod.GET);
			networkRequest.addObserver(new SyncManager((Cache) this, "task", callbacks.get("task"), gateway));
			networkRequest.send();
		}
		if (request.equals("archive")) {
			final Request networkRequest = Network.getInstance().makeRequest(
					"taskmanager/task", HttpMethod.GET);
			networkRequest
					.addObserver(new SyncManager((Cache) this, "archive", callbacks.get("archive"), gateway));
			networkRequest.send();
		}
		if (request.equals("member")) {
			final Request networkRequest = Network.getInstance().makeRequest(
					"core/user", HttpMethod.GET);
			networkRequest.addObserver(new SyncManager((Cache) this, "member", callbacks.get("member"), gateway));
			networkRequest.send();
		}
		if (request.equals("stage")) {
			// TODO Implement this
		}
	}

	/**
	 * @see edu.wpi.cs.wpisuitetng.modules.taskmanager.localcache.ICache#addVerified(java.lang.String,
	 *      java.lang.String)
	 */
	@Override
	public void addVerified(String request, String updateValue) {
		System.out.println(updateValue);
		if (request.equals("task")) {
			Task t = new Gson().fromJson(updateValue, Task.class);
			System.out.println(t.getId() + t.getTitle() + t.getActualEffort()
					+ t.getEstimatedEffort() + t.getDueDate());
			tasks.add(t);
		}
		if (request.equals("archive")) {
			Task t = new Gson().fromJson(updateValue, Task.class);
			System.out.println(t);
			archives.add(t);
		}
		if (request.equals("stage")) {
			// TODO implement this
		}
	}

	/**
	 * @see edu.wpi.cs.wpisuitetng.modules.taskmanager.localcache.ICache#updateVerified(java.lang.String,
	 *      java.lang.String, java.lang.Object)
	 */
	@Override
	public void updateVerified(String request, String updateValue) {
		if (request.equals("task") || request.equals("archive")) {
			Task newValue = new Gson().fromJson(updateValue, Task.class);

			for (int i = 0; i < archives.size(); i++) {
				if (archives.get(i).getId() == newValue.getId()) {
					archives.remove(i);
					break;
				}
			}
			for (int i = 0; i < tasks.size(); i++) {
				if (tasks.get(i).getId() == newValue.getId()) {
					tasks.remove(i);
					break;
				}
			}
			if (newValue.isArchived()) {
				archives.add(newValue);
			} else {
				tasks.add(newValue);
			}
		}
		if (request.equals("stage")) {
			// TODO implement this
		}

	}

	/**
	 * @see edu.wpi.cs.wpisuitetng.modules.taskmanager.localcache.ICache#set(java.lang.String,
	 *      java.lang.String)
	 */
	@Override
	public void set(String request, String updateValue) {
		if (request.equals("task")) {
			Task[] returned = new Gson().fromJson(updateValue, Task[].class);
			tasks = new ArrayList<Task>();
			for (Task t : returned) {
				if (!t.isArchived()) {
					tasks.add(t);
				}
			}
		}
		if (request.equals("archive")) {
			Task[] returned = new Gson().fromJson(updateValue, Task[].class);
			archives = new ArrayList<Task>();
			for (Task t : returned) {
				if (t.isArchived()) {
					archives.add(t);
				}
			}
		}
		if (request.equals("member")) {
			User[] returned = new Gson().fromJson(updateValue, User[].class);
			members = new ArrayList<User>(Arrays.asList(returned));
		}
		if (request.equals("stage")) {
			// TODO implement this
		}
	}

	@Override
	public void setGateway(Gateway gateway) {
		this.gateway = gateway;
	}

}

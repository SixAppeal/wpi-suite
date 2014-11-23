/**
 * 
 */
package edu.wpi.cs.wpisuitetng.modules.taskmanager.localcache;

import edu.wpi.cs.wpisuitetng.exceptions.NotImplementedException;

/**
 * Interface to describe cache functionality 
 * 
 * @author nhhughes
 *
 */
public interface Cache {

	/**
	 * Takes a request that specifies where the object should be cached
	 * @param request Task, Member or anything else that needs to be cached
	 * @param toStore Object to be added to a type specific cache
	 * @return whether the request was successful
	 * @throws NotImplementedException 
	 */
	public void store(String request, Object toStore) throws NotImplementedException;
	
	/**
	 * Takes a request that specifies where in the cache the object should be updated
	 * @param request Task, Member, or anything else that needs to be cached
	 * @param toUpdate Object to be updates to a type specific cache
	 * @return whether the request was successful
	 * @throws NotImplementedException 
	 */
	public void update(String request, Object oldOjbect, Object newObject) throws NotImplementedException;
	
	/**
	 * Takes a request that specifies an object to delete in the cache and deletes it.
	 * @param request Task, Member, or anything else that needs to be cached
	 * @param toDelete Object to be delete
	 * @return whether the request was successful
	 * @throws NotImplementedException 
	 */
	public void delete(String request, Object toDelete) throws NotImplementedException;
	
	/**
	 * Takes a request for a certain cache and returns the cache
	 * @param request the cache type wanted
	 * @return a copy of the cache
	 */
	public Object[] retreive(String request);
	
	/**
	 * Takes a request for a certain cache and a criteria to filter the cache by
	 * and returns the results of the cache
	 * @param request cache type
	 * @param filter string, format to be determined later
	 * @return array of objects satisfying cache
	 * @throws NotImplementedException 
	 */
	public Object[] retreive(String request, String filter) throws NotImplementedException;
	
	/**
	 * Deletes all entries from one of the caches.  
	 * @param request Cache to delete from or 'all'
	 * @return whether a cache was successfully selected or not
	 */
	public void clearCache(String request);
	
	/**
	 * Used to have a view / presenter updated when the network has finished everything
	 * @param topic task, archive, etc..
	 * @param action update, create, delete, etc...
	 * @param callback class:method to have the gateway call
	 */
	public void subscribe(String topic, String action, String callback);
}

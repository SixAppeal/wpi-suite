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

	public void store(String request, Object toStore);
	
	public void update(String request, Object oldOjbect, Object newObject);
	
	public Object[] retrieve(String request);
	
	public Object[] retrieve(String request, String filter) throws NotImplementedException;
	
	public void clearCache(String request);
	
	public void subscribe(String request);

	public void set(String request, String data);
	
	public void sync(String request);
	
	public void addVerified(String request, String data);
	
	public void updateVerified(String request, String data, Object oldData);

}



/**
 * 
 */
package edu.wpi.cs.wpisuitetng.modules.taskmanager.localcache;
import java.util.List;

import edu.wpi.cs.wpisuitetng.modules.taskmanager.model.Stage;
import edu.wpi.cs.wpisuitetng.exceptions.NotImplementedException;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.model.Task;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.presenter.IPresenter;

/**
 * Interface to describe cache functionality 
 * 
 * @author nhhughes
 *
 */
public interface Cache extends IPresenter {

	public void store(String request, Task toStore);
	
	public void store(String request, List<Stage> toStore);
	
	public void update(String request, Task newObject);
	
	public void update(String request, List<Stage> newObject);
	
	public Object[] retrieve(String request);
	
	public Object[] retrieve(String request, String filter) throws NotImplementedException;
	
	public void clearCache(String request);
	
	public void subscribe(String request);

	public void set(String request, String data);
	
	public void sync(String request);
	
	public void addVerified(String request, String data);
	
	public void updateVerified(String request, String data);

}



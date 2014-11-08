package edu.wpi.cs.wpisuitetng.modules.taskmanager.presenter;

import java.util.HashMap;
import java.lang.reflect.Method;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.view.IView;

/**
 * A gateway that allows indirect communication between presenters and views.
 * All presenters should implement IPresenter and all views should implement
 * IView.
 * 
 * @author Will Van Rensselaer, Dan Seaman
 */
public class Gateway {
	HashMap<String, IPresenter> presenters;
	HashMap<String, IView> views;
	
	/**
	 * Constructs a Gateway
	 */
	public Gateway() {
		this.presenters = new HashMap<String, IPresenter>();
		this.views = new HashMap<String, IView>();
	}
	
	/**
	 * Adds a named presenter to the gateway.
	 * @param name The name of the presenter
	 * @param presenter The presenter to route to
	 */
	public void addPresenter(String name, IPresenter presenter) {
		presenter.setGateway(this);
		this.presenters.put(name, presenter);
	}
	
	/**
	 * Adds a named view to the gateway.
	 * @param name The name of the view
	 * @param view The view to route to
	 */
	public void addView(String name, IView view) {
		view.setGateway(this);
		this.views.put(name, view);
	}
	
	/**
	 * Calls a method of the specified view and passes it an object.
	 * @param name The name of the view
	 * @param methodName The method on the view to call
	 * @param data Some data to pass to the view
	 */
	public void toView(String name, String methodName, Object data) {
		Object view = this.views.get(name);
		
		try {
			Method method = view.getClass().getDeclaredMethod(methodName, Object.class);
			method.invoke(view, data);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Calls a method of the specified presenter and passes it an object.
	 * @param name The name of the presenter
	 * @param methodName The method on the presenter to call
	 * @param data Some data to pass to the presenter
	 */
	public void toPresenter(String name, String methodName, Object data) {
		Object presenter = this.presenters.get(name);
		
		try {
			Method method = presenter.getClass().getDeclaredMethod(methodName, Object.class);
			method.invoke(presenter, data);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

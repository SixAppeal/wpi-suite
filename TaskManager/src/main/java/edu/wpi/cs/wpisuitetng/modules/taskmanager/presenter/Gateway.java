package edu.wpi.cs.wpisuitetng.modules.taskmanager.presenter;

import java.util.HashMap;
import java.io.Serializable;
import java.lang.reflect.Method;

import javax.swing.JPanel;

import edu.wpi.cs.wpisuitetng.modules.taskmanager.view.IView;

/**
 * A gateway that allows indirect communication between presenters and views.
 * All presenters should implement IPresenter and all views should implement
 * IView.
 * 
 * @author wavanrensselaer
 * @author dpseaman
 * @author rnorlando
 */
public class Gateway implements Serializable {
	
	private static final long serialVersionUID = 984917443434525486L;
	
	/**
	 * 
	 */
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
	 * Helper function to toView and toPresenter.
	 * @param obj The presenter or view object
	 * @param name The name of the view or presenter
	 * @param methodName The name of the method to call
	 * @param args Arguments to pass to the method
	 */
	private void toObject(Object obj, String name, String methodName, Object[] args) {
		Class<?>[] classes = new Class[args.length];
		
		try {
			for (int i = 0; i < args.length; i++) {
				classes[i] = args[i].getClass();
			}
			
			Method method = obj.getClass().getDeclaredMethod(methodName, classes);
			method.invoke(obj, args);
		} catch (Exception e) {
			System.err.print("Gateway Error: " + name + "." + methodName + "(");
			for (int i = 0; i < classes.length; i++) {
				if (i != 0) {
					System.err.print(", ");
				}
				System.err.print(classes[i].getName());
			}
			System.err.print("): " + e.getClass().getName() + ":");
			System.err.println(e.getMessage());
			e.printStackTrace();
		}
	}
	
	/**
	 * Calls a method of the specified view and passes it some arguments.
	 * @param name The name of the view
	 * @param methodName The method on the view to call
	 * @param args Arguments to pass to the view method
	 */
	public void toView(String name, String methodName, Object... args) {
		Object view = this.views.get(name);
		
		toObject(view, name, methodName, args);
	}
	
	/**
	 * Calls a method of the specified presenter and passes it some arguments.
	 * @param name The name of the presenter
	 * @param methodName The method on the presenter to call
	 * @param args Arguments to pass to the method
	 */
	public void toPresenter(String name, String methodName, Object... args) {
		Object presenter = this.presenters.get(name);
		
		toObject(presenter, name, methodName, args);
	}
}

package edu.wpi.cs.wpisuitetng.modules.taskmanager.view.sidebar.histNcom;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import edu.wpi.cs.wpisuitetng.janeway.config.ConfigManager;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.model.Activity;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.model.Task;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.presenter.Gateway;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.view.sidebar.activitiesandcomments.ActivityLabel;

public class CommentView extends HistoryView{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8084724851168329463L;
	
	Task t;
	private Gateway gateway;
	private JPanel container;
	private JScrollPane scrollpane;
	private List<JTextArea> commentFields;
	private GridBagLayout gbc;
	JTextArea commentEntry;
	JButton commentButton;
	
	public CommentView(Task t){
		this.container = new JPanel();
		this.scrollpane = new JScrollPane(this.container);
		
		this.commentFields = new ArrayList<JTextArea>();
		
		gbc = new GridBagLayout();
		this.container.setLayout(gbc);
		
		this.scrollpane.setMinimumSize(new Dimension(300, 0));
		this.scrollpane.setMaximumSize(new Dimension(300, Integer.MAX_VALUE));
		
		//this.setLayout(new MigLayout("fill, ins 20", "[300]"));
		this.setLayout(new BorderLayout(0,0));
		
		this.add(this.scrollpane);
		
		
		commentEntry = new JTextArea();
		commentEntry.setLineWrap(true);
		commentEntry.setWrapStyleWord(true);
		commentEntry.addKeyListener(new KeyListener (){
			 public void keyPressed(KeyEvent keyEvent) {}
			 public void keyReleased(KeyEvent keyEvent) {}
			 public void keyTyped(KeyEvent keyEvent ) {
				 if( keyEvent.getKeyCode() == KeyEvent.VK_ENTER && commentEntry.getText() != "")	{
					 addToComments();
				 }
			 }
		});
		JScrollPane commentHolder = new JScrollPane(commentEntry);
		
		this.add(commentHolder, BorderLayout.SOUTH);
		
	}
	
	public void displayActivities(Task task){
		List<Activity> activities = task.getActivities();
		commentFields.clear();
		
		for(Activity a: activities){
			ActivityLabel temp = new ActivityLabel(a.getActivity());		
			commentFields.add(temp);
		}
		reflowHistory();
		
	}
	
	public void addToComments(){
		t.addComment(ConfigManager.getConfig().getUserName(), commentEntry.getText());
		displayActivities(this.t);
		commentEntry.setText("");
	}
}

package edu.wpi.cs.wpisuitetng.modules.taskmanager.view.sidebar.histNcom;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
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
import edu.wpi.cs.wpisuitetng.modules.taskmanager.model.Comment;
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
		this.t = t;
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
			 public void keyReleased(KeyEvent keyEvent) {
				 if( keyEvent.getKeyCode() == KeyEvent.VK_ENTER && commentEntry.getText() != "")	{
					 keyEvent.consume();
					 addToComments();
				 }
			 }
			 public void keyTyped(KeyEvent keyEvent ) {}
		});
		JScrollPane commentHolder = new JScrollPane(commentEntry);
		
		this.add(commentHolder, BorderLayout.SOUTH);
		
	}
	
	public void displayActivities(Task task){
		List<Comment> comments = task.getComments();
		commentFields.clear();
		
		for(Comment a: comments){
			ActivityLabel temp = new ActivityLabel(a.getComment());		
			commentFields.add(temp);
		}
		reflowHistory();
		
	}
	
	public void reflowHistory(){
		this.container.removeAll();
		GridBagConstraints gbc = new GridBagConstraints();
		
		gbc.anchor = GridBagConstraints.PAGE_START;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.weightx = 0;
		gbc.insets = new Insets(0,0,0,0);
		gbc.gridwidth = 1;
		gbc.gridx = 0;
		gbc.gridy = 0;
		//this.container.setLayout(new GridBagLayout()));
		int i = 0;
		for(JTextArea j : commentFields){
			gbc.gridy = i;
			
			if (i != 0) {
				gbc.insets.top = 10;
			}
			
			if (i == commentFields.size() - 1) {
				//gbc.weighty = 1.0;
				gbc.insets.bottom = 20;
			}
			
			this.container.add(j, gbc);
			System.out.println("Slap da bitch on the ass");
			
			i++;
		}
		System.out.println("Da bitch was slapped " + i + " times on the ass.");
	}
	
	public void addToComments(){
		t.addComment(ConfigManager.getConfig().getUserName(), commentEntry.getText());
		displayActivities(this.t);
		commentEntry.setText("");
	}
}

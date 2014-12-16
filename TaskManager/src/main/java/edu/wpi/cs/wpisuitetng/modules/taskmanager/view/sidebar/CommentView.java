package edu.wpi.cs.wpisuitetng.modules.taskmanager.view.sidebar;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.MatteBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import org.jfree.xml.generator.model.Comments;

import edu.wpi.cs.wpisuitetng.janeway.config.ConfigManager;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.model.Activity;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.model.Comment;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.model.Task;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.presenter.Gateway;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.view.IView;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.view.sidebar.histNcom.ActivityLabel;

public class CommentView extends JPanel implements IView{
	private Gateway gateway;
	private JPanel container;
	private JScrollPane scrollpane;
	private List<JTextArea> fields;
	private GridBagLayout layout;
	private Task internalTask; 			// Keeps an internal task for comparing. 
	private JTextArea taskCommentArea;
	JButton saveCommentButton;
	
	/**
	 * Constructor
	 */
	CommentView(){
		this.internalTask = new Task();
		this.container = new JPanel();
		this.scrollpane = new JScrollPane(this.container);
		
		this.fields = new ArrayList<JTextArea>();
		
		layout = new GridBagLayout();
		this.container.setLayout(layout);
		
		this.scrollpane.setMinimumSize(new Dimension(300, 0));
		this.scrollpane.setMaximumSize(new Dimension(300, Integer.MAX_VALUE));
		
		createTextArea();
		
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.anchor = GridBagConstraints.FIRST_LINE_START;
		gbc.fill = GridBagConstraints.HORIZONTAL;

		
		gbc.insets.top = 5;
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.weighty = 1.;
		gbc.weightx = 0.;
		this.add(scrollpane,gbc);
		gbc.weighty = 0.;
		gbc.weightx = 1.;
		gbc.gridy = 2;
		this.add(taskCommentArea, gbc);
		
		gbc.gridx = 1;
		this.add(saveCommentButton, gbc);
		
		//this.setLayout(new MigLayout("fill, ins 20", "[300]"));
//		this.setLayout(new BorderLayout(0,0));
		
		//this.add(this.scrollpane);
		
	}
	
	public void display(Task task){
		if(!task.equals(internalTask)){
			List<Comment> comments = task.getComments();
			fields.clear();
			
			for(Comment a: comments){
				ActivityLabel temp = new ActivityLabel(a.getComment());		
				fields.add(temp);
			}
			reflow();
			this.internalTask = task;
		}
		
	}
	
	/**
	 * This function reflows the history pane to update everything based off the 
	 * updated history list. 
	 */
	public void reflow(){
		this.container.removeAll();
		GridBagConstraints gbc = new GridBagConstraints();
		
		gbc.anchor = GridBagConstraints.PAGE_START;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.weightx = 0;
		gbc.weighty = 0;
		gbc.insets = new Insets(0,0,0,0);
		gbc.gridx = 0;
		gbc.gridy = 0;
		
		int i = 0;
		for(JTextArea j : fields){
			gbc.gridy = i;
			
			if (i != 0) {
				gbc.insets.top = 10;
			}
			
			if (i == fields.size() - 1) {
				gbc.insets.bottom = 20;
			}
			gbc.insets = new Insets(0,0,0,0);
			this.container.add(j, gbc);
			
			i++;
		}
	}
	
	public void createTextArea(){
		taskCommentArea = new JTextArea();
		taskCommentArea.setBorder(new MatteBorder(1, 1, 1, 1, (Color) SystemColor.activeCaption));
		taskCommentArea.setLineWrap(true);
		taskCommentArea.setWrapStyleWord(true);
		taskCommentArea.addKeyListener(new KeyListener(){
			@Override
		    public void keyPressed(KeyEvent e){
		        if(e.getKeyCode() == KeyEvent.VK_ENTER){
		        	e.consume();
		        	if(taskCommentArea.getText().trim().equals("")){
		        		taskCommentArea.setText("");
		        	}
		        	else{
		        		addToComments();
		        	}
		        	
		        }

		        
		    }

		    @Override
		    public void keyTyped(KeyEvent e) {
		    }

		    @Override
		    public void keyReleased(KeyEvent e) {
		    }
		});
		taskCommentArea.getDocument().addDocumentListener(new DocumentListener(){
			@Override
			public void changedUpdate(DocumentEvent e) {
			    if(taskCommentArea.getText().trim().equals("")){
			    	 saveCommentButton.setEnabled(false);
			    }
			    else {
			    	saveCommentButton.setEnabled(true);
			    }
			  }
			@Override
			public void removeUpdate(DocumentEvent e) {
				if(taskCommentArea.getText().trim().equals("")){
			    	 saveCommentButton.setEnabled(false);
			    }
			    else {
			    	saveCommentButton.setEnabled(true);
			    }
			  }
			@Override
			public void insertUpdate(DocumentEvent e) {
				if(taskCommentArea.getText().trim().equals("")){
			    	 saveCommentButton.setEnabled(false);
			    }
			    else {
			    	saveCommentButton.setEnabled(true);
			    }
			  }
		});
		
		saveCommentButton = new JButton("Save");
		saveCommentButton.setEnabled(false);
		saveCommentButton.addActionListener( new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addToComments();
			}
		});
	}
	
	public void addToComments(){
		internalTask.addComment(ConfigManager.getConfig().getUserName(), taskCommentArea.getText());
		display(internalTask);
		taskCommentArea.setText("");
		saveCommentButton.setEnabled(false);
	}

	@Override
	public void setGateway(Gateway gateway) {
		this.gateway = gateway;
		
	}
}

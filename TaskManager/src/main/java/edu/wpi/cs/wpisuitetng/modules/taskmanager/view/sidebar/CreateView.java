package edu.wpi.cs.wpisuitetng.modules.taskmanager.view.sidebar;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import edu.wpi.cs.wpisuitetng.modules.taskmanager.presenter.Gateway;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.view.IView;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.view.components.FormField;

public class CreateView extends JPanel implements IView {
	private static final long serialVersionUID = 4679326139629318517L;
	
	private Gateway gateway;
	
	private JPanel form;
	private FormField<JTextField> title;
	private FormField<JTextArea> description;
	private JButton submit;
	
	/**
	 * Constructs a <code>CreateView</code>
	 */
	public CreateView() {
		this.form = new JPanel();
		this.title = new FormField<JTextField>("Title",
				new JTextField());
		this.description = new FormField<JTextArea>("Description",
				new JTextArea(5, 0));
		this.submit = new JButton("Create");
		
		this.form.setOpaque(false);
		this.form.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		this.form.setLayout(new GridBagLayout());
		
		this.setOpaque(false);
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.anchor = GridBagConstraints.PAGE_START;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.weightx = 1.0;
		gbc.gridx = 0;
		gbc.gridy = 0;
		this.form.add(this.title, gbc);
		
		gbc.gridy = 1;
		this.form.add(this.description, gbc);
		
		gbc.anchor = GridBagConstraints.FIRST_LINE_END;
		gbc.fill = GridBagConstraints.NONE;
		gbc.weightx = 0.0;
		gbc.weighty = 1.0;
		gbc.gridy = 2;
		this.form.add(this.submit, gbc);
		
		this.add(this.form);
	}
	
	/**
	 * @see IView.setGateway
	 */
	@Override
	public void setGateway(Gateway gateway) {
		this.gateway = gateway;
	}
}

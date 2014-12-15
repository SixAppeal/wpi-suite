package edu.wpi.cs.wpisuitetng.modules.taskmanager.view.sidebar;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;

import org.jdesktop.swingx.JXDatePicker;

import edu.wpi.cs.wpisuitetng.modules.taskmanager.presenter.Gateway;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.util.TaskManagerUtil;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.view.IView;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.view.components.ButtonGroup;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.view.components.Form;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.view.components.FormField;
import edu.wpi.cs.wpisuitetng.modules.taskmanager.view.components.FormFieldValidator;

/**
 *  A view for creating a PDF of statistics
 * 
 * @author dpseaman
 *
 */

public class StatisticsView extends JPanel implements IView{
	
	private Gateway gateway;
	
	private JXDatePicker startDate;
	private JXDatePicker endDate;
	private JButton createButton;
	private Form form;
	
	
	public StatisticsView () {
		
		this.startDate = new JXDatePicker();
		this.endDate = new JXDatePicker();
		this.createButton = new JButton ("Create");
		
		this.createButton.setEnabled(false);
		
		// form field for start date
		FormField startDateForm = new FormField("Start Date", this.startDate, new FormFieldValidator() {
			@Override
			public boolean validate(JComponent component) {
				return !(startDate.getDate() == null);
			}

			@Override
			public String getMessage() {
				return "Please select a start date";
			}	
		});
		
		// validator for start date
		this.startDate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (startDate != null) { 
					startDateForm.validateInput();
				}
				validateForm();
			}
		});
		
		// form field for end date
		FormField endDateForm = new FormField("End Date", this.endDate, new FormFieldValidator() {
			@Override
			public boolean validate(JComponent component) {
				return !(endDate.getDate() == null);
			}

			@Override
			public String getMessage() {
				return "Please select an end date";
			}
		});
		
		
		// validator for end date
		this.endDate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (endDate != null) { 
					endDateForm.validateInput();
				}
				validateForm();
			}
		});
		
		this.createButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser saveFile = new JFileChooser();
                saveFile.showSaveDialog(null);
			}
		});

		this.form = new Form(
				startDateForm,
				endDateForm,
				new ButtonGroup(
						this.createButton
						)
				);


		this.setBackground(SidebarView.SIDEBAR_COLOR);
		this.setLayout(new MigLayout("fill, ins 20", "[260]"));
		this.add(this.form, "grow");
		
	}
	
	
	/**
	 *  Validates the form
	 */
	public void validateForm() {
		this.createButton.setEnabled(this.form.hasValidInput());
	}
	
	
	
	@Override
	public void setGateway(Gateway gateway) {
		// TODO Auto-generated method stub
	}		
	

}

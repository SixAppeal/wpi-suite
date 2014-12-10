package edu.wpi.cs.wpisuitetng.modules.taskmanager.view.components;

import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;

/**
 * A custom tabbed pane that actually looks good
 * @author wavanrensselaer
 */
public class SixyTabbedPane extends JPanel {
	private static final long serialVersionUID = -7103888830526260335L;

	public static final Color ACTIVE_COLOR = new Color(230, 230, 230);
	public static final Color INACTIVE_COLOR = new Color(200, 200, 200);
	public static final Color ACTIVE_BORDER_COLOR = new Color(0, 160, 0);
	
	private JPanel tabPanel;
	private JPanel container;
	private ArrayList<Tab> tabs;
	private ArrayList<JComponent> components;
	
	/**
	 * Constructs a tabbed pane
	 */
	public SixyTabbedPane() {
		this.tabPanel = new JPanel();
		this.container = new JPanel();
		this.tabs = new ArrayList<Tab>();
		this.components = new ArrayList<JComponent>();
		
		this.tabPanel.setOpaque(false);
		this.container.setBackground(ACTIVE_COLOR);
		
		this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		this.add(this.tabPanel);
		this.add(this.container);
	}
	
	/**
	 * Internal tab container
	 * @author wavanrensselaer
	 */
	private class Tab extends JPanel {
		private Icon icon;
		private JLabel iconLabel;
		private boolean isActive;
		
		/**
		 * Constructs a Tab
		 * @param icon The tab's icon
		 */
		public Tab(Icon icon) {
			this.icon = icon;
			this.iconLabel = new JLabel(icon);
			this.isActive = false;
			
			this.setPreferredSize(new Dimension(50, 50));
			this.setMaximumSize(new Dimension(50, 50));
			this.setBackground(INACTIVE_COLOR);
		}
	}
}

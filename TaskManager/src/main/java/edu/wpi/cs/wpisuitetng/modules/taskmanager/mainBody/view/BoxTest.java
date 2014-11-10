package edu.wpi.cs.wpisuitetng.modules.taskmanager.mainBody.view;
import java.awt.*;
import java.util.List;
import java.util.ArrayList;
import javax.swing.*;

/*
 * From: http://stackoverflow.com/questions/3174765/variable-layout-in-swing
 * 
 * @author StackOverflow
 * 
 * This was used as examples of GUI scroll bar's to help form the task display, and the Task Holder's. 
 * 
 */


public class BoxTest extends JPanel {

    private List<JTextField> fields = new ArrayList<JTextField>();
    public static JPanel cyanP;
    
    
    
    public BoxTest() {
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.add(createPane(3, "One ", Color.red));
        this.add(createPane(3, "Two ", Color.green));
        this.add(createPane(10, "Three ", Color.blue));
        cyanP = createPane(5, "Four", Color.cyan);
    }

    public JPanel createPane(int n, String s, Color c) {
        JPanel outer = new JPanel();
        outer.setLayout(new BoxLayout(outer, BoxLayout.Y_AXIS));
        outer.setBorder(BorderFactory.createLineBorder(c, 2));
        for (int i = 0; i < n; i++) {
            JPanel inner = new JPanel();
            inner.setLayout(new BoxLayout(inner, BoxLayout.X_AXIS));
            JLabel label = new JLabel(s + i + ":", JLabel.RIGHT);
            label.setPreferredSize(new Dimension(80, 32));
            inner.add(label);
            JTextField tf = new JTextField("Stackoverflow!", 32);
            inner.add(tf);
            fields.add(tf);
            outer.add(inner);
        }
        return outer;
    }
    
    private void addBox(JPanel toAdd){
    	this.add(toAdd);
    }

    private void display() {
        JFrame f = new JFrame();
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JScrollPane jsp = new JScrollPane(this,
            JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
            JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        this.validate();
        Dimension d = this.getPreferredSize();
        d.height /= 2;
        jsp.getViewport().setPreferredSize(d);
        jsp.getVerticalScrollBar().setUnitIncrement(
            this.getPreferredSize().height / fields.size());
        f.add(jsp);
        f.pack();
        f.setVisible(true);
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {

            @Override
            public void run() {
            	BoxTest test = new BoxTest();
                test.display();

            }
        });
    }
}
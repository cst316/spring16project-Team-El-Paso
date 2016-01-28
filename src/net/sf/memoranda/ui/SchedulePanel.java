package net.sf.memoranda.ui;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;

public class SchedulePanel extends JPanel {
    BorderLayout borderLayout1 = new BorderLayout();
    JToolBar toolBar = new JToolBar();
    JScrollPane scrollPane = new JScrollPane();
    
    public SchedulePanel ()
    {
        try {
            jbInit();
        }
        catch (Exception ex) {
           new ExceptionDialog(ex);
        }
    }
    
    void jbInit() throws Exception {
        toolBar.setFloatable(false);
        this.setLayout(borderLayout1);
        this.add(toolBar, BorderLayout.NORTH);
        scrollPane.getViewport().setBackground(Color.white);
        this.add(scrollPane, BorderLayout.CENTER);
    }
}

package net.sf.memoranda.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;

import net.sf.memoranda.date.CurrentDate;

public class SchedulePanel extends JPanel {
    BorderLayout borderLayout1 = new BorderLayout();
    //JToolBar toolBar = new JToolBar();
    //JScrollPane scrollPane = new JScrollPane();
    JLabel currentDateLabel = new JLabel();
    ScheduleCalendarPanel scheduleCal = new ScheduleCalendarPanel();
    
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
        //toolBar.setFloatable(false);
        this.setLayout(borderLayout1);
        //scheduleCal.jnCalendar.setMinimumSize(new Dimension(600,600));
        //scheduleCal.mntyPanel.setMaximumSize(new Dimension(0, 0));
        //scheduleCal.jnCalendar.setFillsViewportHeight(true);
        scheduleCal.schCalendar.setRowHeight(100);
        currentDateLabel.setFont(new java.awt.Font("Dialog", 0, 16));
        currentDateLabel.setOpaque(true);
        currentDateLabel.setBackground(Color.black);
        currentDateLabel.setForeground(Color.white);
        currentDateLabel.setText(CurrentDate.get().getFullDateString());
        //this.add(toolBar, BorderLayout.NORTH);
        //scrollPane.getViewport().setBackground(Color.white);
        //this.add(scrollPane, BorderLayout.CENTER);
        this.add(currentDateLabel, BorderLayout.NORTH);
        this.add(scheduleCal, BorderLayout.CENTER);
    }
}

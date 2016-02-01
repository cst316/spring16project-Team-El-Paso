/**
 * JNCalendarCellRenderer.java
 * Created on 14.02.2003, 0:09:11 Alex
 * Package: net.sf.memoranda.ui
 *
 * @author Alex V. Alishevskikh, alex@openmechanics.net
 * Copyright (c) 2003 Memoranda Team. http://memoranda.sf.net
 */
package net.sf.memoranda.ui;
import java.awt.Color;
import java.awt.Component;
import java.util.Calendar;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.SwingConstants;

import net.sf.memoranda.CurrentProject;
import net.sf.memoranda.EventsManager;
import net.sf.memoranda.Schedule;
import net.sf.memoranda.Task;
import net.sf.memoranda.date.CalendarDate;
/**
 *
 */
/*$Id: JNCalendarCellRenderer.java,v 1.5 2004/10/11 08:48:20 alexeya Exp $*/
public class ScheduleCalendarCellRenderer extends javax.swing.table.DefaultTableCellRenderer {
    private CalendarDate d = null;
    boolean disabled = false;
    ImageIcon evIcon = new ImageIcon(net.sf.memoranda.ui.AppFrame.class.getResource("resources/icons/en.png"));
    Task t = null;
    
    public void setTask(Task _t) {
        t = _t;
    }
    
    public Task getTask() {
        return t;
    }

    public Component getTableCellRendererComponent(
        JTable table,
        Object value,
        boolean isSelected,
        boolean hasFocus,
        int row,
        int column) {
        
		JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
		if (d == null) {
            label.setEnabled(false);
			label.setIcon(null);
            label.setBackground(new Color(0xE0,0xE0,0xE0));
            return label;
        }
        
		if (!isSelected) {
			CalendarDate cpsd = CurrentProject.get().getStartDate();
            CalendarDate cped = CurrentProject.get().getEndDate();
            if (!(((d.after(cpsd)) && (d.before(cped))) || (d.equals(cpsd)) || (d.equals(cped)))) {
				label.setBackground(new Color(0xF0,0xF0,0xF0));
				return label;
			}
        }		


		label.setHorizontalTextPosition(SwingConstants.LEFT);
		label.setVerticalTextPosition(SwingConstants.TOP);
		label.setEnabled(true);
		


        if (d.equals(CalendarDate.today())) {
        	label = new ScheduleLabel(d,Schedule.findDays(d));
        }
        
        if (d.after(CalendarDate.today()) && Schedule.dayHasTasks(d)) {
        	label = new ScheduleLabel(d,Schedule.findDays(d));
        }
        
		// set foreground color
		if (d.getCalendar().get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
            label.setForeground(new Color(255, 0, 0));
        }
		else { 		
			label.setForeground(Color.BLACK);
		}

		if (EventsManager.isNREventsForDate(d))
			label.setIcon(evIcon);
		else
			label.setIcon(null);
		
        return label;
    }

    public void setDate(CalendarDate date) {
        d = date;
    }

}

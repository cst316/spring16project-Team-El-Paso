/**
 * TaskTableModel.java         
 * -----------------------------------------------------------------------------
 * Project           Memoranda
 * Package           net.sf.memoranda.ui
 * Original author   Alex V. Alishevskikh
 *                   [alexeya@gmail.com]
 * Created           18.05.2005 15:16:11
 * Revision info     $RCSfile: TaskTableModel.java,v $ $Revision: 1.7 $ $State: Exp $  
 *
 * Last modified on  $Date: 2005/12/01 08:12:26 $
 *               by  $Author: alexeya $
 * 
 * @VERSION@ 
 *
 * @COPYRIGHT@
 * 
 * @LICENSE@ 
 */

package net.sf.memoranda.ui;

import javax.swing.event.*;
import javax.swing.tree.TreePath;

import net.sf.memoranda.*;
import net.sf.memoranda.date.CurrentDate;
import net.sf.memoranda.ui.treetable.AbstractTreeTableModel;
import net.sf.memoranda.ui.treetable.TreeTableModel;
import net.sf.memoranda.util.Local;
import net.sf.memoranda.util.Context;

import java.util.Hashtable;

/**
 * JAVADOC:
 * <h1>TaskTableModel</h1>
 * 
 * @version $Id: TaskTableModel.java,v 1.7 2005/12/01 08:12:26 alexeya Exp $
 * @author $Author: alexeya $
 */
public class TaskTableModel extends AbstractTreeTableModel {

    String[] columnNames = {"", Local.getString("To-do"), Local.getString("Description"),
            Local.getString("Start date"), Local.getString("End date"), Local.getString("Category"), 
            Local.getString("Priority"), Local.getString("Status"),
            "% " + Local.getString("done") };

    protected EventListenerList taskTableListenerList = new EventListenerList();

    private boolean activeOnly = check_activeOnly();
        
    /**
     * JAVADOC: Constructor of <code>TaskTableModel</code>
     * 
     * @param root
     */
    public TaskTableModel(){
        super(CurrentProject.get());
    }

    /**
     * @see net.sf.memoranda.ui.treetable.TreeTableModel#getColumnCount()
     */
    public int getColumnCount() {
        return columnNames.length;
    }

    /**
     * @see net.sf.memoranda.ui.treetable.TreeTableModel#getColumnName(int)
     */
    public String getColumnName(int column) {
        return columnNames[column];
    }

    /**
     * @see net.sf.memoranda.ui.treetable.TreeTableModel#getValueAt(java.lang.Object,
     *      int)
     */
    public Object getValueAt(Object node, int column) {
        if (node instanceof Project) {
            return null;
        }
        Task nodeTask = (Task) node;
        switch (column) {
        case 0:
            return "";
        case 1:
            return nodeTask;
        case 2:
            return nodeTask.getDescription();
        case 3:
            return nodeTask.getStartDate().getDate();
        case 4:
            if (nodeTask.getEndDate() == null) {
                return null;
            } else {
                return nodeTask.getEndDate().getDate();
            }        
        case 5: 
	    return nodeTask.getCategory(); 
        case 6:
            return getPriorityString(nodeTask.getPriority()); 
        case 7:
            return getStatusString(nodeTask.getStatus(CurrentDate.get()));
        case 8	:            
            //return new Integer(t.getProgress());
	    return nodeTask;
        case TaskTable.TASK_ID:
            return nodeTask.getID();
        case TaskTable.TASK:
            return nodeTask;
        }
        return "";
    }
    

    String getStatusString(int status) {
        switch (status) {
        case Task.ACTIVE:
            return Local.getString("Active");
        case Task.DEADLINE:
            return Local.getString("Deadline");
        case Task.COMPLETED:
            return Local.getString("Completed");
        case Task.LATE:
            return Local.getString("Late");
        case Task.FROZEN:
            return Local.getString("Frozen");
        case Task.LOCKED:
            return Local.getString("Locked");
        case Task.SCHEDULED:
            return Local.getString("Scheduled");
        }
        return "";
    }

    String getPriorityString(int priority) {
        switch (priority) {
        case Task.PRIORITY_NORMAL:
            return Local.getString("Normal");
        case Task.PRIORITY_LOW:
            return Local.getString("Low");
        case Task.PRIORITY_LOWEST:
            return Local.getString("Lowest");
        case Task.PRIORITY_HIGH:
            return Local.getString("High");
        case Task.PRIORITY_HIGHEST:
            return Local.getString("Highest");
        }
        return "";
    }

    /**
     * @see javax.swing.tree.TreeModel#getChildCount(java.lang.Object)
     */
    public int getChildCount(Object parent) {
        if (parent instanceof Project) {
        	if( activeOnly() ){
        		return CurrentProject.getTaskList().getActiveSubTasks(null, CurrentDate.get()).size();
        	} else {
        		return CurrentProject.getTaskList().getTopLevelTasks().size();
        	}
        }
        Task parentTask = (Task) parent;
        if(activeOnly()) {
        	return CurrentProject.getTaskList().getActiveSubTasks(parentTask.getID(), CurrentDate.get()).size();
        } else {
        	return parentTask.getSubTasks().size();
        }
    }

    /**
     * @see javax.swing.tree.TreeModel#getChild(java.lang.Object, int)
     */
    public Object getChild(Object parent, int index) {
        if (parent instanceof Project) {
            if( activeOnly() ) {
            	return CurrentProject.getTaskList().getActiveSubTasks(null, CurrentDate.get()).toArray()[index];
            } else {
            	return CurrentProject.getTaskList().getTopLevelTasks().toArray()[index];
            }
        }
        Task parentTask = (Task) parent;
        if( activeOnly() ) {
        	return CurrentProject.getTaskList().getActiveSubTasks(parentTask.getID(), CurrentDate.get()).toArray()[index];
        } else {
        	return parentTask.getSubTasks().toArray()[index];
        }
    }

    /**
     * @see net.sf.memoranda.ui.treetable.TreeTableModel#getColumnClass(int)
     */
    public Class getColumnClass(int column) {
        try {
            switch (column) {
            case 1:
                return TreeTableModel.class;
            case 0:
                return TaskTable.class;
            case 2:
            case 5:
            case 6:
            case 7:
                return Class.forName("java.lang.String");
            case 3:
            case 4:
                return Class.forName("java.util.Date");
            case 8:
                return Class.forName("java.lang.Integer");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
    
    public void fireTreeStructureChanged(){	    
	fireTreeStructureChanged(this, new Object[]{getRoot()}, new int[0], new Object[0]);
    }
    
    
    /**
     * Update cached data
     */
    public void fireUpdateCache(){
	activeOnly = check_activeOnly();	
    }

    public static boolean check_activeOnly(){
	Object object = Context.get("SHOW_ACTIVE_TASKS_ONLY");
	if(object == null) {
	    return false;
	}
	return object.toString().equals("true");
    }

    public boolean activeOnly(){
	return activeOnly;
    }
    
    public boolean isCellEditable(Object node, int column) {
	if(column == 7) { 
	    return true; 
	}
        return super.isCellEditable(node, column); 
    }

}
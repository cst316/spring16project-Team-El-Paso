package net.sf.memoranda;

import java.util.Iterator;
import java.util.Vector;

import net.sf.memoranda.date.CalendarDate;

public class Schedule {
	
	public Schedule() {
		
	}
	/**
	 * Fills a four-element array with the number of tasks that fall on the given day;
	 * position 0 of returnArray stores the number of times the given date is equal to any task's start date AND end date (closed label)
	 * position 1 of returnArray stores the number of times the given date is equal to any task's start date (start label)
	 * position 1 of returnArray stores the number of times the given date falls between the start and end dates of any task (open label)
	 * position 2 of returnArray stores the number of times the given date is equal to any task's end date (end label)
	 * @param d
	 * @return
	 */
	public static int[] findDays(CalendarDate d) {

		int[] returnArray = new int[4];
		Vector taskVector = (Vector) CurrentProject.getTaskList().getTopLevelTasks();
		Iterator taskItr = taskVector.iterator();
		while (taskItr.hasNext())
		{
			TaskImpl task = (TaskImpl)taskItr.next();
			//This ensures the counted tasks are active on the passed day
			if (task.getStatus(d) == Task.ACTIVE || task.getStatus(d) == Task.DEADLINE) {
				CalendarDate taskStartDate = task.getStartDate();
				CalendarDate taskEndDate = task.getEndDate();
				if (d.equals(taskStartDate) && d.equals(taskEndDate))
					returnArray[0]++;
				else if (d.equals(taskStartDate))
					returnArray[1]++;
				else if (d.after(taskStartDate) && d.before(taskEndDate))
					returnArray[2]++;
				else if (d.equals(taskEndDate))
					returnArray[3]++;
			}
		}
		return returnArray;
	}
	/** 
	 * This method returns true if there are tasks that fall on the passed day; otherwise returns false.
	 */
	public static boolean dayHasTasks(CalendarDate d) {
		int[] toCheck = new int[4];
		toCheck = findDays(d);
		int arrayTotal = toCheck[0] + toCheck[1] + toCheck[2] + toCheck[3];
		if (arrayTotal > 0)
			return true;
		else
			return false;
	}
	
}

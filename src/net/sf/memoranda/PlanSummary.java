package net.sf.memoranda;

import net.sf.memoranda.date.CalendarDate;
import nu.xom.Element;

public interface PlanSummary {

	/** Creates a new plan summary to attach to a project.
	 * 
	 * @param prjName The name of the project to which this summary is attached.
	 * @param planLOC The expected lines of code per hour over the life of this plan.
	 * @param arrayLOC An array containing values for the program size template fields, estimated in LOC.
	 * @param arrayTime An array containing values for the time per phase template fields, estimated in minutes.
	 * @return the new element.
	 */
	Element createPlanSummary(String prjName, int planLOC, int[] arrayLOC, int[] arrayTime);

	/**
	 * 
	 * @return The name of the project to which this plan summary is attached.
	 */
	String getPrjName();
	
	/**
	 * 
	 * @return The expected lines of code per hour over the life of this plan.
	 */
	int getPlanLOCperHr();
	
	/**
	 * 
	 * @return The measured Base lines of code in the project covered by this plan. 
	 */
	int getLOCBasePlan();
	
	/**
	 * 
	 * @return The estimated lines of code that will be deleted from the project.
	 */
	int getLOCDeletedPlan();
	
	/**
	 * 
	 * @return The estimated lines of code that will be modified from the project.
	 */
	int getLOCModifiedPlan();
	
	/**
	 * 
	 * @return The estimated lines of code that will be reused from the project.
	 */
	int getLOCReusedPlan();
	
	/**
	 * 
	 * @return The estimated new and changed lines of code added to the project.
	 */
	int getLOCNewPlan();
	
	/**
	 * 
	 * @return The estimated number of minutes spent in the planning phase of the project.
	 */
	int getTimePlanningPlan();
	
	/**
	 * 
	 * @return The estimated number of minutes spent in the design phase of the project.
	 */
	int getTimeDesignPlan();
	
	/**
	 * 
	 * @return The estimated number of minutes spent in the coding phase of the project.
	 */
	int getTimeCodePlan();
	
	/**
	 * 
	 * @return The estimated number of minutes spent in the compile phase of the project.
	 */
	int getTimeCompilePlan();
	
	/**
	 * 
	 * @return The estimated number of minutes spent in the testing phase of the project.
	 */
	int getTimeTestPlan();
	
	/**
	 * 
	 * @return The estimated number of minutes spent in the postmortem phase of the project.
	 */
	int getTimePMPlan();
	
	/**This method is appropriate to use for "To Date" or "Actual" calculations. To find actuals,
	 * pass the date the project closed.
	 * @param A CalendarDate object representing the desired end date of the period calculated,
	 * where the start date is the date the project was created.
	 * @return The total number of minutes accumulated for all planning tasks in the project.
	 */
	int getTimePlanningActual(CalendarDate cd);
	
	/**This method is appropriate to use for "To Date" or "Actual" calculations. To find actuals,
	 * pass the date the project closed.
	 * @param A CalendarDate object representing the desired end date of the period calculated,
	 * where the start date is the date the project was created.
	 * @return The total number of minutes accumulated for all design tasks in the project.
	 */
	int getTimeDesignActual(CalendarDate cd);
	
	/**This method is appropriate to use for "To Date" or "Actual" calculations. To find actuals,
	 * pass the date the project closed.
	 * @param A CalendarDate object representing the desired end date of the period calculated,
	 * where the start date is the date the project was created.
	 * @return The total number of minutes accumulated for all coding tasks in the project.
	 */
	int getTimeCodeActual(CalendarDate cd);
	
	/**This method is appropriate to use for "To Date" or "Actual" calculations. To find actuals,
	 * pass the date the project closed.
	 * @param A CalendarDate object representing the desired end date of the period calculated,
	 * where the start date is the date the project was created.
	 * @return The total number of minutes accumulated for all compile tasks in the project.
	 */
	int getTimeCompileActual(CalendarDate cd);
	
	/**This method is appropriate to use for "To Date" or "Actual" calculations. To find actuals,
	 * pass the date the project closed.
	 * @param A CalendarDate object representing the desired end date of the period calculated,
	 * where the start date is the date the project was created.
	 * @return The total number of minutes accumulated for all testing tasks in the project.
	 */
	int getTimeTestActual(CalendarDate cd);
	
	/**This method is appropriate to use for "To Date" or "Actual" calculations. To find actuals,
	 * pass the date the project closed.
	 * @param A CalendarDate object representing the desired end date of the period calculated,
	 * where the start date is the date the project was created.
	 * @return The total number of minutes accumulated for all postmortem tasks in the project.
	 */
	int getTimePMActual(CalendarDate cd);
	
}

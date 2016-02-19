package net.sf.memoranda;

import net.sf.memoranda.date.CalendarDate;
import nu.xom.Attribute;
import nu.xom.Element;

public class PlanSummaryImpl implements PlanSummary {
	
	private Element _el;
	
	/**
	 * Default constructor of PlanSummaryImpl.
	 */
	public PlanSummaryImpl() {
		_el = new Element("PlanSummary");
	}
	
	/**
	 * 
	 * @see net.sf.memoranda.PlanSummary#createPlanSummary
	 */
	public Element createPlanSummary(String prjName, int planLOC, int[] arrayLOC, int[] arrayTime) {
		new PlanSummaryImpl();
		_el.addAttribute(new Attribute("prjName", prjName));
		_el.addAttribute(new Attribute("planLOC", Integer.toString(planLOC)));
		//Pull integers from arrayLOC and put them in separate attributes
		_el.addAttribute(new Attribute("psizeBase", Integer.toString(arrayLOC[0])));
		_el.addAttribute(new Attribute("psizeDeleted", Integer.toString(arrayLOC[1])));
		_el.addAttribute(new Attribute("psizeMod", Integer.toString(arrayLOC[2])));
		_el.addAttribute(new Attribute("psizeReused", Integer.toString(arrayLOC[3])));
		_el.addAttribute(new Attribute("psizeNew", Integer.toString(arrayLOC[4])));
		//Pull integers from arrayTime and put them in separate attributes
		_el.addAttribute(new Attribute("timePlanning", Integer.toString(arrayTime[0])));
		_el.addAttribute(new Attribute("timeDesign", Integer.toString(arrayTime[1])));
		_el.addAttribute(new Attribute("timeCode", Integer.toString(arrayTime[2])));
		_el.addAttribute(new Attribute("timeCompile", Integer.toString(arrayTime[3])));
		_el.addAttribute(new Attribute("timeTest", Integer.toString(arrayTime[4])));
		_el.addAttribute(new Attribute("timePM", Integer.toString(arrayTime[5])));
		return _el;
	}
	
	/**
	 * 
	 * @see net.sf.memoranda.PlanSummary#getPrjName()
	 */
	public String getPrjName() {
		return _el.getAttributeValue("prjName");
	}
	
	/**
	 * 
	 * @see net.sf.memoranda.PlanSummary#getPlanLOCperHr()
	 */
	public int getPlanLOCperHr() {
		return Integer.parseInt(_el.getAttributeValue("planLOC"));
	}
	
	/**
	 * 
	 * @see net.sf.memoranda.PlanSummary#getLOCBasePlan()
	 */
	public int getLOCBasePlan() {
		return Integer.parseInt(_el.getAttributeValue("psizeBase"));
	}
	
	/**
	 * 
	 * @see net.sf.memoranda.PlanSummary#getLOCDeletedPlan()
	 */
	public int getLOCDeletedPlan() {
		return Integer.parseInt(_el.getAttributeValue("psizeDeleted"));
	}
	
	/**
	 * 
	 * @see net.sf.memoranda.PlanSummary#getLOCModifiedPlan()
	 */
	public int getLOCModifiedPlan() {
		return Integer.parseInt(_el.getAttributeValue("psizeMod"));
	}
	
	/**
	 * 
	 * @see net.sf.memoranda.PlanSummary#getLOCReusedPlan()
	 */
	public int getLOCReusedPlan() {
		return Integer.parseInt(_el.getAttributeValue("psizeReused"));
	}
	
	/**
	 * 
	 * @see net.sf.memoranda.PlanSummary#getLOCNewPlan()
	 */
	public int getLOCNewPlan() {
		return Integer.parseInt(_el.getAttributeValue("psizeNew"));
	}
	
	/**
	 * 
	 * @see net.sf.memoranda.PlanSummary#getTimePlanningPlan()
	 */
	public int getTimePlanningPlan() {
		return Integer.parseInt(_el.getAttributeValue("timePlanning"));
	}
	
	/**
	 * 
	 * @see net.sf.memoranda.PlanSummary#getTimeDesignPlan()
	 */
	public int getTimeDesignPlan() {
		return Integer.parseInt(_el.getAttributeValue("timeDesign"));
	}
	
	/**
	 * 
	 * @see net.sf.memoranda.PlanSummary#getTimeCodePlan()
	 */
	public int getTimeCodePlan() {
		return Integer.parseInt(_el.getAttributeValue("timeCode"));
	}
	
	/**
	 * 
	 * @see net.sf.memoranda.PlanSummary#getTimeCompilePlan()
	 */
	public int getTimeCompilePlan() {
		return Integer.parseInt(_el.getAttributeValue("timeCompile"));
	}
	
	/**
	 * 
	 * @see net.sf.memoranda.PlanSummary#getTimeTestPlan()
	 */
	public int getTimeTestPlan() {
		return Integer.parseInt(_el.getAttributeValue("timeTest"));
	}
	
	/**
	 * 
	 * @see net.sf.memoranda.PlanSummary#getTimePMPlan()
	 */
	public int getTimePMPlan() {
		return Integer.parseInt(_el.getAttributeValue("timePM"));
	}
	
	/**
	 * @see net.sf.memoranda.PlanSummary#getLOCperHrToDate()
	 */
	public int getLOCperHrToDate(CalendarDate cd) {
		//NYI
		return 0;
	}

	/**
	 * @see net.sf.memoranda.PlanSummary#getLOCDeletedActual()
	 */
	public int getLOCDeletedActual(CalendarDate cd) {
		// TODO Auto-generated method stub
		return 0;
	}

	/**
	 * @see net.sf.memoranda.PlanSummary#getLOCModifiedActual()
	 */
	public int getLOCModifiedActual(CalendarDate cd) {
		// TODO Auto-generated method stub
		return 0;
	}

	/**
	 * @see net.sf.memoranda.PlanSummary#getLOCReusedActual()
	 */
	public int getLOCReusedActual(CalendarDate cd) {
		// TODO Auto-generated method stub
		return 0;
	}

	/**
	 * @see net.sf.memoranda.PlanSummary#getTimePlanningActual()
	 */
	public int getTimePlanningActual(CalendarDate cd) {
		// TODO Auto-generated method stub
		return 0;
	}

	/**
	 * @see net.sf.memoranda.PlanSummary#getTimeDesignActual()
	 */
	public int getTimeDesignActual(CalendarDate cd) {
		// TODO Auto-generated method stub
		return 0;
	}

	/**
	 * @see net.sf.memoranda.PlanSummary#getTimeCodeActual()
	 */
	public int getTimeCodeActual(CalendarDate cd) {
		// TODO Auto-generated method stub
		return 0;
	}

	/**
	 * @see net.sf.memoranda.PlanSummary#getTimeCompileActual()
	 */
	public int getTimeCompileActual(CalendarDate cd) {
		// TODO Auto-generated method stub
		return 0;
	}

	/**
	 * @see net.sf.memoranda.PlanSummary#getTimeTestActual()
	 */
	public int getTimeTestActual(CalendarDate cd) {
		// TODO Auto-generated method stub
		return 0;
	}

	/**
	 * @see net.sf.memoranda.PlanSummary#getTimePMActual()
	 */
	public int getTimePMActual(CalendarDate cd) {
		// TODO Auto-generated method stub
		return 0;
	}
	
}

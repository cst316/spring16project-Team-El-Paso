/**
 * EventsManager.java Created on 08.03.2003, 12:35:19 Alex Package:
 * net.sf.memoranda
 * 
 * @author Alex V. Alishevskikh, alex@openmechanics.net Copyright (c) 2003
 *         Memoranda Team. http://memoranda.sf.net
 */
package net.sf.memoranda;

import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.Vector;
import java.util.Map;
import java.util.Collections;


import net.sf.memoranda.date.CalendarDate;
import net.sf.memoranda.util.CurrentStorage;
import net.sf.memoranda.util.Util;
import nu.xom.Attribute;
//import nu.xom.Comment;
import nu.xom.Document;
import nu.xom.Element;
import nu.xom.Elements;
import nu.xom.ParentNode;

/**
 *  Events Manager.
 */
/*$Id: EventsManager.java,vec 1.11 2004/10/06 16:00:11 ivanrise Exp $*/
public class EventsManager {
	/*	public static final String NS_JNEVENTS =
	"http://www.openmechanics.org/2003/jnotes-events-file";
	 */
	public static final int NO_REPEAT = 0;
	public static final int REPEAT_DAILY = 1;
	public static final int REPEAT_WEEKLY = 2;
	public static final int REPEAT_MONTHLY = 3;
	public static final int REPEAT_YEARLY = 4;

	public static Document _doc = null;
	static Element _root = null;

	static {
		CurrentStorage.get().openEventsManager();
		if (_doc == null) {
			_root = new Element("eventslist");
			/*_root.addNamespaceDeclaration("jnevents", NS_JNEVENTS);
			_root.appendChild(
				new Comment("This is JNotes 2 data file. Do not modify.")); */
			_doc = new Document(_root);
		} else {	
			_root = _doc.getRootElement();
		}
	}

	public static void createSticker(String text, int prior) {
		Element el = new Element("sticker");
		el.addAttribute(new Attribute("id", Util.generateId()));
		el.addAttribute(new Attribute("priority", prior+""));
		el.appendChild(text);
		_root.appendChild(el);
	}

	@SuppressWarnings("unchecked")
	public static Map getStickers() {
		Map map = new HashMap();
		Elements els = _root.getChildElements("sticker");
		for (int i = 0; i < els.size(); i++) {
			Element se = els.get(i);
			map.put(se.getAttribute("id").getValue(), se);
		}
		return map;
	}

	public static void removeSticker(String stickerId) {
		Elements els = _root.getChildElements("sticker");
		for (int i = 0; i < els.size(); i++) {
			Element se = els.get(i);
			if (se.getAttribute("id").getValue().equals(stickerId)) {
				_root.removeChild(se);
				break;
			}
		}
	}

	public static boolean isNrEventsForDate(CalendarDate date) {
		Day day = getDay(date);
		if (day == null) {
			return false;
		}
		if (day.getElement().getChildElements("event").size() > 0) {
			return true;
		}
		return false;
	}
	
	/**
	 * 
	 * @param date
	 * @return Returns a sorted Vector of all events on given date.
	 */
	public static Collection getEventsForDate(CalendarDate date) {
		Vector vec = new Vector();
		Day day = getDay(date);
		if (day != null) {
			Elements els = day.getElement().getChildElements("event");
			for (int i = 0; i < els.size(); i++) {
				vec.add(new EventImpl(els.get(i)));
			}
		}
		Collection rep = getRepeatableEventsForDate(date);
		if (rep.size() > 0) {
			vec.addAll(rep);
		}
		//EventsVectorSorter.sort(vec);
		Collections.sort(vec);
		return vec;
	}

	public static Event createEvent(
		CalendarDate date,
		int hh, 
		int mm, 
		int hhDuration,
		int mmDuration,
		String text, String location, String participants) {
		Element el = new Element("event");
		el.addAttribute(new Attribute("id", Util.generateId()));
		el.addAttribute(new Attribute("hour", String.valueOf(hh)));
		el.addAttribute(new Attribute("min", String.valueOf(mm)));
		el.addAttribute(new Attribute("durationHour", String.valueOf(hhDuration)));
		el.addAttribute(new Attribute("durationMin", String.valueOf(mmDuration)));
		el.addAttribute(new Attribute("Location", location));
		el.addAttribute(new Attribute("Participants", participants));
		el.appendChild(text);
		Day day = getDay(date);
		if (day == null) {
			day = createDay(date);
		}
		day.getElement().appendChild(el);
		return new EventImpl(el);
	}

	public static Event createRepeatableEvent(
		int type,
		CalendarDate startDate,
		CalendarDate endDate,
		int period,
		int hh,
		int mm, 
		int hhDuration,
		int mmDuration,
		String text, String location, String participants,
		boolean workDays) {
		Element el = new Element("event");
		Element rep = _root.getFirstChildElement("repeatable");
		if (rep == null) {
			rep = new Element("repeatable");
			_root.appendChild(rep);
		}
		el.addAttribute(new Attribute("repeat-type", String.valueOf(type)));
		el.addAttribute(new Attribute("id", Util.generateId()));
		el.addAttribute(new Attribute("hour", String.valueOf(hh)));
		el.addAttribute(new Attribute("min", String.valueOf(mm)));
		el.addAttribute(new Attribute("durationHour", String.valueOf(hhDuration)));
		el.addAttribute(new Attribute("durationMin", String.valueOf(mmDuration)));
		el.addAttribute(new Attribute("startDate", startDate.toString()));
		if (endDate != null) {
			el.addAttribute(new Attribute("endDate", endDate.toString()));
		}
		el.addAttribute(new Attribute("period", String.valueOf(period)));
		// new attribute for wrkin days - ivanrise
		el.addAttribute(new Attribute("workingDays",String.valueOf(workDays)));
		el.addAttribute(new Attribute("Location", location));
		el.addAttribute(new Attribute("Participants", participants));
		el.appendChild(text);
		rep.appendChild(el);
		return new EventImpl(el);
	}

	public static Collection getRepeatableEvents() {
		Vector vec = new Vector();
		Element rep = _root.getFirstChildElement("repeatable");
		if (rep == null) {
			return vec;
		}
		Elements els = rep.getChildElements("event");
		for (int i = 0; i < els.size(); i++) {
			vec.add(new EventImpl(els.get(i)));
		}
		return vec;
	}

	public static Collection getRepeatableEventsForDate(CalendarDate date) {
		Vector reps = (Vector) getRepeatableEvents();
		Vector vec = new Vector();
		for (int i = 0; i < reps.size(); i++) {
			Event ev = (Event) reps.get(i);
			
			// --- ivanrise
			//ignore this event if it's a 'only working days' event and today is weekend
			if(ev.getWorkingDays() && (date.getCalendar().get(Calendar.DAY_OF_WEEK) == 1
				|| date.getCalendar().get(Calendar.DAY_OF_WEEK) == 7)) {
				continue;
			}
			// ---
			/*
			 * /if ( ((date.after(ev.getStartDate())) &&
			 * (date.before(ev.getEndDate()))) ||
			 * (date.equals(ev.getStartDate()))
			 */
			//System.out.println(date.inPeriod(ev.getStartDate(),
			// ev.getEndDate()));
			if (date.inPeriod(ev.getStartDate(), ev.getEndDate())) {
				if (ev.getRepeat() == REPEAT_DAILY) {
					int ne = date.getCalendar().get(Calendar.DAY_OF_YEAR);
					int ns = ev.getStartDate().getCalendar().get(
							Calendar.DAY_OF_YEAR);
					//System.out.println((n - ns) % ev.getPeriod());
					if ((ne - ns) % ev.getPeriod() == 0) {
						vec.add(ev);
					}
				} else if (ev.getRepeat() == REPEAT_WEEKLY) {
					if (date.getCalendar().get(Calendar.DAY_OF_WEEK)
						== ev.getPeriod()) {
						vec.add(ev);
					}
				} else if (ev.getRepeat() == REPEAT_MONTHLY) {
					if (date.getCalendar().get(Calendar.DAY_OF_MONTH)
						== ev.getPeriod()) {
						vec.add(ev);
					}
				} else if (ev.getRepeat() == REPEAT_YEARLY) {
					int period = ev.getPeriod();
					if ((date.getYear() % 4) == 0
						&& date.getCalendar().get(Calendar.DAY_OF_YEAR) 
						> 60) {
						period++;
					}

					if (date.getCalendar().get(Calendar.DAY_OF_YEAR) 
						== period) {
						vec.add(ev);
					}
				}
			}
		}
		return vec;
	}

	public static Collection getActiveEvents() {
		return getEventsForDate(CalendarDate.today());
	}

	public static Event getEvent(CalendarDate date, int hh, int mm) {
		Day day = getDay(date);
		if (day == null) {
			return null;
		}
		Elements els = day.getElement().getChildElements("event");
		for (int i = 0; i < els.size(); i++) {
			Element el = els.get(i);
			if ((Integer.valueOf(el.getAttribute("hour").getValue()).intValue()
				== hh)
				&& (new Integer(el.getAttribute("min").getValue()).intValue()
					== mm)) {
				return new EventImpl(el);
			}
		}
		return null;
	}

	public static void removeEvent(CalendarDate date, int hh, int mm) {
		if (getDay(date) == null) {
			getDay(date).getElement().removeChild(getEvent(date, hh, mm).getContent());
		}
	}

	public static void removeEvent(Event ev) {
		ParentNode parent = ev.getContent().getParent();
		parent.removeChild(ev.getContent());
	}

	private static Day createDay(CalendarDate date) {
		Year ye = getYear(date.getYear());
		if (ye == null) {
			ye = createYear(date.getYear());
		}
		Month mo = ye.getMonth(date.getMonth());
		if (mo == null) {
			mo = ye.createMonth(date.getMonth());
		}
		Day day = mo.getDay(date.getDay());
		if (day == null) {
			day = mo.createDay(date.getDay());
		}
		return day;
	}

	private static Year createYear(int ye) {
		Element el = new Element("year");
		el.addAttribute(new Attribute("year", new Integer(ye).toString()));
		_root.appendChild(el);
		return new Year(el);
	}

	private static Year getYear(int ye) {
		Elements yrs = _root.getChildElements("year");
		String yy = new Integer(ye).toString();
		for (int i = 0; i < yrs.size(); i++) {
			if (yrs.get(i).getAttribute("year").getValue().equals(yy)) {
				return new Year(yrs.get(i));
			}
		}
		//return createYear(y);
		return null;
	}

	private static Day getDay(CalendarDate date) {
		Year ye = getYear(date.getYear());
		if (ye == null) {
			return null;
		}
		Month mo = ye.getMonth(date.getMonth());
		if (mo == null) {
			return null;
		}
		return mo.getDay(date.getDay());
	}

	static class Year {
		Element yearElement = null;

		Year(Element el) {
			yearElement = el;
		}

		public int getValue() {
			return new Integer(yearElement.getAttribute("year").
				getValue()).intValue();
		}

		public Month getMonth(int mo) {
			Elements ms = yearElement.getChildElements("month");
			String mm = new Integer(mo).toString();
			for (int i = 0; i < ms.size(); i++) {
				if (ms.get(i).getAttribute("month").getValue().equals(mm)) {
					return new Month(ms.get(i));
				}
			}
			//return createMonth(m);
			return null;
		}

		private Month createMonth(int mo) {
			Element el = new Element("month");
			el.addAttribute(new Attribute("month", new Integer(mo).toString()));
			yearElement.appendChild(el);
			return new Month(el);
		}

		public Vector getMonths() {
			Vector vec = new Vector();
			Elements ms = yearElement.getChildElements("month");
			for (int i = 0; i < ms.size(); i++) {
				vec.add(new Month(ms.get(i)));
			}
			return vec;
		}

		public Element getElement() {
			return yearElement;
		}

	}

	static class Month {
		Element moElement = null;

		Month (Element el) {
			moElement = el;
		}

		public int getValue() {
			return new Integer(moElement.getAttribute("month").getValue()).intValue();
		}

		public Day getDay(int day) {
			if (moElement == null) {
				return null;
			}
			Elements ds = moElement.getChildElements("day");
			String dd = Integer.valueOf(day).toString();
			for (int i = 0; i < ds.size(); i++) {
				if (ds.get(i).getAttribute("day").getValue().equals(dd)) {
					return new Day(ds.get(i));
				}
			}
			//return createDay(day);
			return null;
		}

		private Day createDay(int day) {
			Element el = new Element("day");
			el.addAttribute(new Attribute("day", new Integer(day).toString()));
			el.addAttribute(new Attribute("date",new CalendarDate(day,getValue(),
				new Integer(((Element) moElement.getParent()).getAttribute
				("year").getValue()).intValue()).toString()));
			moElement.appendChild(el);
			return new Day(el);
		}

		public Vector getDays() {
			if (moElement == null) {
				return null;
			}
			Vector vec = new Vector();
			Elements ds = moElement.getChildElements("day");
			for (int i = 0; i < ds.size(); i++) {
				vec.add(new Day(ds.get(i)));
			}
			return vec;
		}

		public Element getElement() {
			return moElement;
		}

	}

	static class Day {
		Element del = null;

		Day(Element el) {
			del = el;
		}

		public int getValue() {
			return new Integer(del.getAttribute("day").getValue()).intValue();
		}

		public Element getElement() {
			return del;
		}
	}
/*
	static class EventsVectorSorter {

		private static Vector keys = null;

		private static int toMinutes(Object obj) {
			Event ev = (Event) obj;
			return ev.getHour() * 60 + ev.getMinute();
		}

		private static void doSort(int L, int R) { // Hoar's QuickSort
			int i = L;
			int j = R;
			int x = toMinutes(keys.get((L + R) / 2));
			Object w = null;
			do {
				while (toMinutes(keys.get(i)) < x) {
					i++;
				}
				while (x < toMinutes(keys.get(j))) {
					j--;
				}
				if (i <= j) {
					w = keys.get(i);
					keys.set(i, keys.get(j));
					keys.set(j, w);
					i++;
					j--;
				}
			}
			while (i <= j);
			if (L < j) {
				doSort(L, j);
			}
			if (i < R) {
				doSort(i, R);
			}
		}

		public static void sort(Vector theKeys) {
			if (theKeys == null)
				return;
			if (theKeys.size() <= 0)
				return;
			keys = theKeys;
			doSort(0, keys.size() - 1);
		}

	}
*/
}

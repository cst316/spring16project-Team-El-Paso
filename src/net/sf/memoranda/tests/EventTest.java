package net.sf.memoranda.tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import net.sf.memoranda.EventImpl;
import net.sf.memoranda.EventsManager;
import net.sf.memoranda.date.CalendarDate;

public class EventTest {
	
	private EventImpl testEvent1;
	private EventImpl testEvent2;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		CalendarDate testDay = CalendarDate.today();
		CalendarDate nextDay = CalendarDate.tomorrow();
		CalendarDate priorDay = CalendarDate.yesterday();
		testEvent1 = (EventImpl) EventsManager.createEvent(testDay, 9, 30, 1, 30, "One's Details", 
				"One's Location", "One's Participants");
		testEvent2 = (EventImpl) EventsManager.createRepeatableEvent(2, testDay, nextDay, 2, 12, 45, 0, 45, "Two's Details", 
				"Two's Location", "Two's Participants", true);		

	}

	@Test
	public void test() {
		assertTrue(testEvent1.getHour() == 9);
		assertTrue(testEvent2.isRepeatable());
	}

}

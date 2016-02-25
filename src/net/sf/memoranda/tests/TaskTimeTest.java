package net.sf.memoranda.tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import net.sf.memoranda.ui.htmleditor.util.TaskTimer;

public class TaskTimeTest {
	
	String validTime;
	String invalidTime;
	String testTime;
	String nullTime;
	String largeTime;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		validTime = "00:11:01";
		invalidTime = "aa:00asd";
		nullTime = null;
		largeTime = "99:99:99";
	}

	@Test
	public void testTickTock() {
		testTime = TaskTimer.tickTock(validTime);
		assertEquals(testTime, "00:11:02");
		
		validTime = "01:59:59";
		testTime = TaskTimer.tickTock(validTime);
		assertEquals(testTime, "02:00:00");
		
		testTime = TaskTimer.tickTock(invalidTime);
		assertEquals(testTime, "00:00:01");
		
		testTime = TaskTimer.tickTock(nullTime);
		assertEquals(testTime, "00:00:01");
		
		// time is expected to rollover to 00:00:00 (larger than 99 hours is not supported)
		testTime = TaskTimer.tickTock(largeTime);
		assertEquals(testTime, "00:00:00");
	}

}

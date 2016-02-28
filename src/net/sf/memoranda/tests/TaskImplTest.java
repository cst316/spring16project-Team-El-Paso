package net.sf.memoranda.tests;

import static org.junit.Assert.*;

import java.util.Calendar;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import net.sf.memoranda.CurrentProject;
import net.sf.memoranda.Task;
import net.sf.memoranda.date.CalendarDate;
import net.sf.memoranda.date.CurrentDate;
import net.sf.memoranda.util.CurrentStorage;

public class TaskImplTest {
	private Task tTask = null;
	private CalendarDate sd = new CalendarDate(CurrentDate.get().getDate());
	

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		tTask = CurrentProject.getTaskList().createTask(sd, null, "Test Task", 200, 201, 1, 10, "Test task description", "Planning", "00:01:25", null);
		CurrentStorage.get().storeTaskList(CurrentProject.getTaskList(), CurrentProject.get());
		
		
	}

	@Test
	public void testTask() {
		assertEquals(tTask.getEffort(), 10);
		assertEquals(tTask.getText(), "Test Task");
		// entered as int stored as string
		assertEquals(tTask.getSize(), "200");
		// entered as int stored as string
		assertEquals(tTask.getActualSize(), "201");
		assertEquals(tTask.getPriority(), 1);
		assertEquals(tTask.getDescription(), "Test task description");
		assertEquals(tTask.getTime(), "00:01:25");
		assertEquals(tTask.getCategory(), "Planning");
		assertEquals(tTask.getParentId(), null);
	}
	
	@Test	
	public void testEffort() {
		Long tLong = (long) 1010101111;
		tTask.setEffort(tLong);
		assertEquals(tTask.getEffort(), 1010101111);
	}
		
	@Test	
	public void testDescrition() {
		tTask.setDescription(null);
		assertEquals(tTask.getDescription(), "");
		tTask.setDescription("");
		assertEquals(tTask.getDescription(), "");
		tTask.setDescription("New Test 1");
		assertEquals(tTask.getDescription(), "New Test 1");
		
	}
	
	@Test	
	public void testCategory() {
		tTask.setCategory(null);
		assertEquals(tTask.getCategory(), "");
		tTask.setCategory("Test Category");
		assertEquals(tTask.getCategory(), "Test Category");
		
	}
	
	@Test	
	public void testSize() {
		tTask.setSize(506);
		assertEquals(tTask.getSize(), "506");
		
	}
	
	@Test	
	public void testActualSize() {
		tTask.setActualSize(23);
		assertEquals(tTask.getActualSize(), "23");
		
	}
	
	@Test	
	public void testTime() {
		tTask.setTime("13:43:12");
		assertEquals(tTask.getTime(), "13:43:12");
		
	}
	
	@Test	
	public void testStatus() {
		// DeadLine
		assertEquals(tTask.getStatus(sd), 7);
		CalendarDate ed = new CalendarDate(30, 03, 2030);
		tTask.setEndDate(ed);
		assertEquals(tTask.getStatus(sd), 1);
		CalendarDate nsd = new CalendarDate(30, 03, 2020);
		tTask.setStartDate(nsd);
		assertEquals(tTask.getStatus(sd), 0);		
		nsd = new CalendarDate(30, 03, 2009);
		ed = new CalendarDate(30, 03, 2010);
		tTask.setStartDate(nsd);
		tTask.setEndDate(ed);
		assertEquals(tTask.getStatus(sd), 5);
		tTask.setProgress(100);
		assertEquals(tTask.getStatus(sd), 2);
		tTask.freeze();
		assertEquals(tTask.getStatus(sd), 4);
		
	}
	
	@Test	
	public void testPriority() {
		tTask.setPriority(2);
		assertEquals(tTask.getPriority(), 2);
		
	}
	

}

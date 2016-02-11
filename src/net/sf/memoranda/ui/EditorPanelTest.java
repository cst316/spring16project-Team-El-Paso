package net.sf.memoranda.ui;

import static org.junit.Assert.*;

import javax.swing.JButton;
import javax.swing.JTextField;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import net.sf.memoranda.ui.EditorPanel;
import net.sf.memoranda.ui.DailyItemsPanel;
import net.sf.memoranda.ui.WorkPanel;

public class EditorPanelTest {
	EditorPanel testabc = new EditorPanel(new DailyItemsPanel(new WorkPanel()));
	JTextField testTitleField = new JTextField();
	JButton testClear;
	JButton testSave;
	JButton testNew;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		testClear = testabc.clearB;
		testSave = testabc.saveB;
		testNew = testabc.newB;
		
			
	}
	
	// currently only testing that the buttons are visible
	// looking into further testing functionality 
	@Test
	public void testClearB() {
		assertTrue(testClear.getText().equals("Clear Note"));
				
	}
	
	@Test
	public void testSaveB() {
		assertTrue(testSave.getText().equals("Save Note"));
				
	}
	
	@Test
	public void testNewB() {
		assertTrue(testNew.getText().equals("New Note"));
				
	}
	
	// Make sure entry into Title field works 
	@Test
	public void testTitleJTextField() {
		testTitleField.setText("Test Title1");
		assertEquals("Test Title1", testTitleField.getText());
				
	}	

}

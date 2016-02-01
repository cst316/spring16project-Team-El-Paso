package net.sf.memoranda.ui;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

import net.sf.memoranda.date.CalendarDate;

public class ScheduleLabel extends JLabel {
	GridLayout schLabelLayout;
	JLabel dateLabel;
	JLabel blankLabel;
	JLabel taskIndicator;
	static Color LABEL_BLUE = new Color(45, 181, 240);
	static Border LABEL_BORDER_OPEN = BorderFactory.createMatteBorder(2, 0, 2, 0, Color.BLUE);
	static Border LABEL_BORDER_CLOSED = BorderFactory.createMatteBorder(2,  2,  2,  2, Color.BLUE);
	static Border LABEL_BORDER_START = BorderFactory.createMatteBorder(2, 2, 2, 0, Color.BLUE);
	static Border LABEL_BORDER_END = BorderFactory.createMatteBorder(2, 0, 2, 2, Color.BLUE);
	
	public ScheduleLabel(CalendarDate d, int[] numTasks) {
		int numTasksTotal = numTasks[0] + numTasks[1] + numTasks[2] + numTasks[3];
		System.out.println("From Schedule Label: " + numTasks[0] + numTasks[1] + numTasks[2] + numTasks[3]);
		int indexCount = 0;
		JLabel[] taskLabelArray = new JLabel[1];
		taskLabelArray[0] = new JLabel();
		if (numTasksTotal != 0) {
			if (numTasksTotal < 5) {
				schLabelLayout = new GridLayout(5, 1);
				taskLabelArray = new JLabel[5];
					for (int i = 0; i < taskLabelArray.length; i++)
						taskLabelArray[i] = new JLabel();
			}
			else {
				//Add two to the total number of tasks to give room for the date label and a blank label
				schLabelLayout = new GridLayout(numTasksTotal + 2, 1);
				taskLabelArray = new JLabel[numTasksTotal];
				for (int i = 0; i < taskLabelArray.length; i++)
					taskLabelArray[i] = new JLabel();
			}
		}
		this.setLayout(schLabelLayout);
		dateLabel = new JLabel();
		dateLabel.setHorizontalTextPosition(SwingConstants.LEFT);
		dateLabel.setText(Integer.toString(d.getDay()));
		blankLabel = new JLabel();
		blankLabel.setBackground(Color.WHITE);
		for (int i = 0; i < numTasks[0]; i++) {
			taskLabelArray[indexCount].setBorder(LABEL_BORDER_CLOSED);
			taskLabelArray[indexCount].setBackground(LABEL_BLUE);
			taskLabelArray[indexCount].setOpaque(true);
			indexCount++;
		}
		for (int i = 0; i < numTasks[1]; i++) {
			taskLabelArray[indexCount].setBorder(LABEL_BORDER_START);
			taskLabelArray[indexCount].setBackground(LABEL_BLUE);
			taskLabelArray[indexCount].setOpaque(true);
			indexCount++;
		}
		for (int i = 0; i < numTasks[2]; i++) {
			taskLabelArray[indexCount].setBorder(LABEL_BORDER_OPEN);
			taskLabelArray[indexCount].setBackground(LABEL_BLUE);
			taskLabelArray[indexCount].setOpaque(true);
			indexCount++;
		}
		for (int i = 0; i < numTasks[3]; i++) {
			taskLabelArray[indexCount].setBorder(LABEL_BORDER_END);
			taskLabelArray[indexCount].setBackground(LABEL_BLUE);
			taskLabelArray[indexCount].setOpaque(true);
			indexCount++;
		}
		this.add(dateLabel);
		this.add(blankLabel);
		for (int i = 0; i < taskLabelArray.length; i++) {
			if (taskLabelArray[i].getBackground() == LABEL_BLUE)
				this.add(taskLabelArray[i]);
		}
		this.setVisible(true);
	}
	
	public JLabel getScheduleLabel() {
	return this;
	}

}


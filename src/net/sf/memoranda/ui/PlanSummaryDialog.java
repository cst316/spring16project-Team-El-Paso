package net.sf.memoranda.ui;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class PlanSummaryDialog extends JDialog {

	JPanel mainPanel = new JPanel();
	JButton okayButton = new JButton();
	JButton cancelButton = new JButton();
	JLabel projectNameLabel = new JLabel();
	JTextField projectNameText = new JTextField();
	GridBagLayout formLayout;
	
	public PlanSummaryDialog(Frame frame, String title) {
        super(frame, title, true);
        try {
            jbInit();
            pack();
        }
        catch (Exception ex) {
            new ExceptionDialog(ex);
        }
	}
	
	void jbInit() throws Exception {
		this.setResizable(false);
        cancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cancelButton_actionPerformed(e);
            }
        });
        okayButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                okayButton_actionPerformed(e);
            }
        });
        mainPanel.setMinimumSize(new Dimension(100, 200));
        formLayout = new GridBagLayout();
        projectNameLabel.setText("Project Name:");
        projectNameText.setMinimumSize(new Dimension(60,20));
        mainPanel.add(projectNameLabel);
        mainPanel.add(projectNameText);
        this.getContentPane().add(mainPanel);
	}
	
    void cancelButton_actionPerformed(ActionEvent e) {
        this.dispose();
    }

    void okayButton_actionPerformed(ActionEvent e) {
        //CANCELLED = false;
        this.dispose();
    }
}

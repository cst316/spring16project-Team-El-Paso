package net.sf.memoranda.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.NumberFormat;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import net.sf.memoranda.CurrentProject;
import net.sf.memoranda.PlanSummaryImpl;
import net.sf.memoranda.Project;
import net.sf.memoranda.ProjectImpl;
import net.sf.memoranda.util.Local;
import nu.xom.Element;

public class PlanSummaryDialog extends JDialog {

	boolean newprj = false;
	boolean psExists = false;
	Project psProject = null;
	Element filledSummary = null;
	
	NumberFormat fieldFormat = NumberFormat.getNumberInstance();
	
	JPanel mainPanel = new JPanel();
	DocumentListener planListener;
	
	JPanel buttonPanel = new JPanel();
	JButton okayButton = new JButton();
	JButton cancelButton = new JButton();
	
	JLabel projectNameLabel = new JLabel();
	JTextField projectNameText = new JTextField();
	
	JLabel summaryLOCLabel = new JLabel();
	JLabel sLOCPlanLabel = new JLabel();
	JLabel sLOCActualLabel = new JLabel();
	JLabel sLOCToDateLabel = new JLabel();
	
	JLabel timeHeaderLabel = new JLabel();
	JLabel timePlanLabel = new JLabel();
	JLabel timeActualLabel = new JLabel();
	JLabel timeToDateLabel = new JLabel();
	JLabel timeToDatePercLabel = new JLabel();
	JLabel timeTotalLabel = new JLabel();
	
	JLabel timePlanningLabel = new JLabel();
	JLabel timeDesignLabel = new JLabel();
	JLabel timeCodeLabel = new JLabel();
	JLabel timeCompileLabel = new JLabel();
	JLabel timeTestLabel = new JLabel();
	JLabel timePostMortemLabel = new JLabel();
	
	JTextField sLOCPlanText = new JTextField();
	JTextField sLOCActualText = new JTextField();
	JTextField sLOCToDateText = new JTextField();
	
	JFormattedTextField pTimePlanText;
	JTextField pTimeActualText = new JTextField();
	JTextField pTimeToDateText = new JTextField();
	JTextField pTimeToDatePercText = new JTextField();
	
	JTextField dTimePlanText = new JTextField();
	JTextField dTimeActualText = new JTextField();
	JTextField dTimeToDateText = new JTextField();
	JTextField dTimeToDatePercText = new JTextField();
	
	JTextField cdTimePlanText = new JTextField();
	JTextField cdTimeActualText = new JTextField();
	JTextField cdTimeToDateText = new JTextField();
	JTextField cdTimeToDatePercText = new JTextField();
	
	JTextField cpTimePlanText = new JTextField();
	JTextField cpTimeActualText = new JTextField();
	JTextField cpTimeToDateText = new JTextField();
	JTextField cpTimeToDatePercText = new JTextField();
	
	JTextField tTimePlanText = new JTextField();
	JTextField tTimeActualText = new JTextField();
	JTextField tTimeToDateText = new JTextField();
	JTextField tTimeToDatePercText = new JTextField();
	
	JTextField pmTimePlanText = new JTextField();
	JTextField pmTimeActualText = new JTextField();
	JTextField pmTimeToDateText = new JTextField();
	JTextField pmTimeToDatePercText = new JTextField();
	
	JTextField ttlTimePlanText = new JTextField();
	JTextField ttlTimeActualText = new JTextField();
	JTextField ttlTimeToDateText = new JTextField();
	JTextField ttlTimeToDatePercText = new JTextField();
	
	GridBagLayout formLayout;
	GridBagConstraints formConstraints; 
	
	public PlanSummaryDialog(Frame frame, String title, String prjName, boolean prjnew, Project passedPrj) {
            super(frame, title, true);
            try {
                newprj = prjnew;
                psProject = passedPrj;
                jbInit(prjName);
                pack();
            } catch (Exception ex) {
                new ExceptionDialog(ex);
            }
	}
	
	void jbInit(String prjName) throws Exception {
		this.setResizable(false);
		if (!newprj) {
		    psExists = psProject.hasSummary();
		}
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

        mainPanel.setMinimumSize(new Dimension(550, 650));
        formLayout = new GridBagLayout();
        mainPanel.setLayout(formLayout);
        formConstraints = new GridBagConstraints();
        formConstraints.fill = GridBagConstraints.HORIZONTAL;
        formConstraints.anchor = GridBagConstraints.FIRST_LINE_START;
        formConstraints.insets = new Insets(5,5,5,5);
        
        projectNameLabel.setText("Project Name:");
        formConstraints.gridx = 0;
        formConstraints.gridy = 0;
        mainPanel.add(projectNameLabel, formConstraints);
        
        projectNameText.setEditable(false);
        projectNameText.setText(prjName);
        formConstraints.fill = GridBagConstraints.HORIZONTAL;
        formConstraints.gridx = 1;
        formConstraints.gridy = 0;
        mainPanel.add(projectNameText, formConstraints);
        
        formConstraints = new GridBagConstraints();
        formConstraints.insets = new Insets(5,5,5,5);
        formConstraints.fill = GridBagConstraints.HORIZONTAL;
        summaryLOCLabel.setText("Project LOC");
        formConstraints.gridx = 0;
        formConstraints.gridy = 1;
        mainPanel.add(summaryLOCLabel, formConstraints);
        
        //----------------------------------------LOC Summary Fields----------------------------->>
        sLOCPlanLabel.setText("Plan");
        formConstraints.gridx = 1;
        formConstraints.gridy = 1;
        mainPanel.add(sLOCPlanLabel, formConstraints);
        
        sLOCActualLabel.setText("Actual");
        formConstraints.gridx = 2;
        formConstraints.gridy = 1;
        mainPanel.add(sLOCActualLabel, formConstraints);
        
        sLOCToDateLabel.setText("To Date");
        formConstraints.gridx = 3;
        formConstraints.gridy = 1;
        mainPanel.add(sLOCToDateLabel, formConstraints);
        
        sLOCPlanText.setPreferredSize(new Dimension(100,20));
        sLOCPlanText.setText("0");
        if (psExists) {
        	sLOCPlanText.setText(Integer.toString(psProject.getPrjSummary().getPlanLOCperHr()));
        	sLOCPlanText.setEditable(false);
        }
        formConstraints.gridx = 1;
        formConstraints.gridy = 2;
        mainPanel.add(sLOCPlanText, formConstraints);
        
        sLOCActualText.setPreferredSize(new Dimension(100,20));
        sLOCActualText.setEditable(false);
        if (psExists) {
        	if (psProject.getStatus() == Project.COMPLETED) {
        		//gather actual LOC from all tasks in the project
        		sLOCActualText.setText(Integer.toString(psProject.getTaskList().calculateActualLOC()));
        	}
        }
        formConstraints.gridx = 2;
        formConstraints.gridy = 2;
        mainPanel.add(sLOCActualText, formConstraints);
        
        sLOCToDateText.setPreferredSize(new Dimension(100,20));
        sLOCToDateText.setEditable(false);
        formConstraints.gridx = 3;
        formConstraints.gridy = 2;
        mainPanel.add(sLOCToDateText, formConstraints);
        
        //---------------------------------------------TIME HEADERS------------------------------>>
        timeHeaderLabel.setText("Time in Phase (min.)");
        formConstraints.gridx = 0;
        formConstraints.gridy = 3;
        mainPanel.add(timeHeaderLabel, formConstraints);
        
        timePlanLabel.setText("Plan");
        formConstraints.gridx = 1;
        formConstraints.gridy = 3;
        mainPanel.add(timePlanLabel, formConstraints);
        
        timeActualLabel.setText("Actual");
        formConstraints.gridx = 2;
        formConstraints.gridy = 3;
        mainPanel.add(timeActualLabel, formConstraints);
        
        timeToDateLabel.setText("To Date");
        formConstraints.gridx = 3;
        formConstraints.gridy = 3;
        mainPanel.add(timeToDateLabel, formConstraints);
        
        timeToDatePercLabel.setText("To Date %");
        formConstraints.gridx = 4;
        formConstraints.gridy = 3;
        mainPanel.add(timeToDatePercLabel, formConstraints);
        
        //----------------------------------------TIME PLANNING FIELDS--------------------------->>
        timePlanningLabel.setText("  Planning");
        formConstraints.gridx = 0;
        formConstraints.gridy = 4;
        mainPanel.add(timePlanningLabel, formConstraints);
        
        pTimePlanText = planFields();
        pTimePlanText.setPreferredSize(new Dimension(100,20));
        pTimePlanText.setText("0");
        if (psExists) {
        	pTimePlanText.setText(Integer.toString(psProject.getPrjSummary().getTimePlanningPlan()));
        	pTimePlanText.setEditable(false);
        }
        formConstraints.gridx = 1;
        formConstraints.gridy = 4;
        mainPanel.add(pTimePlanText, formConstraints);
        
        pTimeActualText.setPreferredSize(new Dimension(100,20));
        pTimeActualText.setEditable(false);
        formConstraints.gridx = 2;
        formConstraints.gridy = 4;
        mainPanel.add(pTimeActualText, formConstraints);
        
        pTimeToDateText.setPreferredSize(new Dimension(100,20));
        pTimeToDateText.setEditable(false);
        formConstraints.gridx = 3;
        formConstraints.gridy = 4;
        mainPanel.add(pTimeToDateText, formConstraints);
        
        pTimeToDatePercText.setPreferredSize(new Dimension(100,20));
        pTimeToDatePercText.setEditable(false);
        formConstraints.gridx = 4;
        formConstraints.gridy = 4;
        mainPanel.add(pTimeToDatePercText, formConstraints);
        
        //--------------------------------------TIME DESIGN FIELDS------------------------------->>
        timeDesignLabel.setText("  Design");
        formConstraints.gridx = 0;
        formConstraints.gridy = 5;
        mainPanel.add(timeDesignLabel, formConstraints);
        
        dTimePlanText = planFields();
        dTimePlanText.setPreferredSize(new Dimension(100,20));
        dTimePlanText.setText("0");
        if (psExists) {
        	dTimePlanText.setText(Integer.toString(psProject.getPrjSummary().getTimeDesignPlan()));
        	dTimePlanText.setEditable(false);
        }
        formConstraints.gridx = 1;
        formConstraints.gridy = 5;
        mainPanel.add(dTimePlanText, formConstraints);
        
        dTimeActualText.setPreferredSize(new Dimension(100,20));
        dTimeActualText.setEditable(false);
        formConstraints.gridx = 2;
        formConstraints.gridy = 5;
        mainPanel.add(dTimeActualText, formConstraints);
        
        dTimeToDateText.setPreferredSize(new Dimension(100,20));
        dTimeToDateText.setEditable(false);
        formConstraints.gridx = 3;
        formConstraints.gridy = 5;
        mainPanel.add(dTimeToDateText, formConstraints);
        
        dTimeToDatePercText.setPreferredSize(new Dimension(100,20));
        dTimeToDatePercText.setEditable(false);
        formConstraints.gridx = 4;
        formConstraints.gridy = 5;
        mainPanel.add(dTimeToDatePercText, formConstraints);
        
        //--------------------------------------TIME CODE FIELDS--------------------------------->>
        timeCodeLabel.setText("  Code");
        formConstraints.gridx = 0;
        formConstraints.gridy = 6;
        mainPanel.add(timeCodeLabel, formConstraints);
        
        cdTimePlanText = planFields();
        cdTimePlanText.setPreferredSize(new Dimension(100,20));
        cdTimePlanText.setText("0");
        if (psExists) {
        	cdTimePlanText.setText(Integer.toString(psProject.getPrjSummary().getTimeCodePlan()));
        	cdTimePlanText.setEditable(false);
        }
        formConstraints.gridx = 1;
        formConstraints.gridy = 6;
        mainPanel.add(cdTimePlanText, formConstraints);
        
        cdTimeActualText.setPreferredSize(new Dimension(100,20));
        cdTimeActualText.setEditable(false);
        formConstraints.gridx = 2;
        formConstraints.gridy = 6;
        mainPanel.add(cdTimeActualText, formConstraints);
        
        cdTimeToDateText.setPreferredSize(new Dimension(100,20));
        cdTimeToDateText.setEditable(false);
        formConstraints.gridx = 3;
        formConstraints.gridy = 6;
        mainPanel.add(cdTimeToDateText, formConstraints);
        
        cdTimeToDatePercText.setPreferredSize(new Dimension(100,20));
        cdTimeToDatePercText.setEditable(false);
        formConstraints.gridx = 4;
        formConstraints.gridy = 6;
        mainPanel.add(cdTimeToDatePercText, formConstraints);
        
        //--------------------------------------TIME COMPILE FIELDS------------------------------>>
        timeCompileLabel.setText("  Compile");
        formConstraints.gridx = 0;
        formConstraints.gridy = 7;
        mainPanel.add(timeCompileLabel, formConstraints);
        
        cpTimePlanText = planFields();
        cpTimePlanText.setPreferredSize(new Dimension(100,20));
        cpTimePlanText.setText("0");
        if (psExists) {
        	cpTimePlanText.setText(Integer.toString(psProject.getPrjSummary().getTimeCompilePlan()));
        	cpTimePlanText.setEditable(false);
        }
        formConstraints.gridx = 1;
        formConstraints.gridy = 7;
        mainPanel.add(cpTimePlanText, formConstraints);
        
        cpTimeActualText.setPreferredSize(new Dimension(100,20));
        cpTimeActualText.setEditable(false);
        formConstraints.gridx = 2;
        formConstraints.gridy = 7;
        mainPanel.add(cpTimeActualText, formConstraints);
        
        cpTimeToDateText.setPreferredSize(new Dimension(100,20));
        cpTimeToDateText.setEditable(false);
        formConstraints.gridx = 3;
        formConstraints.gridy = 7;
        mainPanel.add(cpTimeToDateText, formConstraints);
        
        cpTimeToDatePercText.setPreferredSize(new Dimension(100,20));
        cpTimeToDatePercText.setEditable(false);
        formConstraints.gridx = 4;
        formConstraints.gridy = 7;
        mainPanel.add(cpTimeToDatePercText, formConstraints);
        
        //--------------------------------------TIME TEST FIELDS--------------------------------->>
        timeTestLabel.setText("  Test");
        formConstraints.gridx = 0;
        formConstraints.gridy = 8;
        mainPanel.add(timeTestLabel, formConstraints);
        
        tTimePlanText = planFields();
        tTimePlanText.setPreferredSize(new Dimension(100,20));
        tTimePlanText.setText("0");
        if (psExists) {
        	tTimePlanText.setText(Integer.toString(psProject.getPrjSummary().getTimeTestPlan()));
        	tTimePlanText.setEditable(false);
        }
        formConstraints.gridx = 1;
        formConstraints.gridy = 8;
        mainPanel.add(tTimePlanText, formConstraints);
        
        tTimeActualText.setPreferredSize(new Dimension(100,20));
        tTimeActualText.setEditable(false);
        formConstraints.gridx = 2;
        formConstraints.gridy = 8;
        mainPanel.add(tTimeActualText, formConstraints);
        
        tTimeToDateText.setPreferredSize(new Dimension(100,20));
        tTimeToDateText.setEditable(false);
        formConstraints.gridx = 3;
        formConstraints.gridy = 8;
        mainPanel.add(tTimeToDateText, formConstraints);
        
        tTimeToDatePercText.setPreferredSize(new Dimension(100,20));
        tTimeToDatePercText.setEditable(false);
        formConstraints.gridx = 4;
        formConstraints.gridy = 8;
        mainPanel.add(tTimeToDatePercText, formConstraints);
        
        //--------------------------------------TIME POSTMORTEM FIELDS--------------------------->>
        timePostMortemLabel.setText("  Postmortem");
        formConstraints.gridx = 0;
        formConstraints.gridy = 9;
        mainPanel.add(timePostMortemLabel, formConstraints);
        
        pmTimePlanText = planFields();
        pmTimePlanText.setPreferredSize(new Dimension(100,20));
        pmTimePlanText.setText("0");
        if (psExists) {
        	pmTimePlanText.setText(Integer.toString(psProject.getPrjSummary().getTimePMPlan()));
        	pmTimePlanText.setEditable(false);
        }
        formConstraints.gridx = 1;
        formConstraints.gridy = 9;
        mainPanel.add(pmTimePlanText, formConstraints);
        
        pmTimeActualText.setPreferredSize(new Dimension(100,20));
        pmTimeActualText.setEditable(false);
        if (psExists) {
        	if (psProject.getStatus() == Project.COMPLETED) {
        		//when implemented in tasks, this will tally actual times from this task type (postmortem)
        	}
        }
        formConstraints.gridx = 2;
        formConstraints.gridy = 9;
        mainPanel.add(pmTimeActualText, formConstraints);
        
        pmTimeToDateText.setPreferredSize(new Dimension(100,20));
        pmTimeToDateText.setEditable(false);
        formConstraints.gridx = 3;
        formConstraints.gridy = 9;
        mainPanel.add(pmTimeToDateText, formConstraints);
        
        pmTimeToDatePercText.setPreferredSize(new Dimension(100,20));
        pmTimeToDatePercText.setEditable(false);
        formConstraints.gridx = 4;
        formConstraints.gridy = 9;
        mainPanel.add(pmTimeToDatePercText, formConstraints);
        
        //--------------------------------------TIME TOTAL FIELDS--------------------------->>
        timeTotalLabel.setText("    Total");
        formConstraints.gridx = 0;
        formConstraints.gridy = 10;
        mainPanel.add(timeTotalLabel, formConstraints);
        
        ttlTimePlanText.setPreferredSize(new Dimension(100,20));
        ttlTimePlanText.setText("0");
        updatePlanTotal();
        ttlTimePlanText.setEditable(false);
        formConstraints.gridx = 1;
        formConstraints.gridy = 10;
        mainPanel.add(ttlTimePlanText, formConstraints);
        
        ttlTimeActualText.setPreferredSize(new Dimension(100,20));
        ttlTimeActualText.setEditable(false);
        formConstraints.gridx = 2;
        formConstraints.gridy = 10;
        mainPanel.add(ttlTimeActualText, formConstraints);
        
        ttlTimeToDateText.setPreferredSize(new Dimension(100,20));
        ttlTimeToDateText.setEditable(false);
        formConstraints.gridx = 3;
        formConstraints.gridy = 10;
        mainPanel.add(ttlTimeToDateText, formConstraints);
        
        ttlTimeToDatePercText.setPreferredSize(new Dimension(100,20));
        ttlTimeToDatePercText.setEditable(false);
        formConstraints.gridx = 4;
        formConstraints.gridy = 10;
        mainPanel.add(ttlTimeToDatePercText, formConstraints);
        
        //-------------------------------------------------------BUTTONS------------------------->>
        okayButton.setText(Local.getString("Ok"));
        cancelButton.setText(Local.getString("Cancel"));
        buttonPanel.add(okayButton);
        buttonPanel.add(cancelButton);
        formConstraints.anchor = GridBagConstraints.PAGE_END;
        formConstraints.gridwidth = GridBagConstraints.REMAINDER;
        formConstraints.weighty = 1.0;
        formConstraints.gridx = 0;
        formConstraints.gridy = 11;
        mainPanel.add(buttonPanel, formConstraints);
        
        this.getContentPane().add(mainPanel);
	}
	
    void cancelButton_actionPerformed(ActionEvent e) {
        this.dispose();
    }

    void okayButton_actionPerformed(ActionEvent e) {
    	if (!psExists) {
    		int[] arrayLOC = new int[5];
    		int[] arrayTime = new int[6];
    		arrayTime[0] = Integer.parseInt(pTimePlanText.getText());
    		arrayTime[1] = Integer.parseInt(dTimePlanText.getText());
    		arrayTime[2] = Integer.parseInt(cdTimePlanText.getText());
    		arrayTime[3] = Integer.parseInt(cpTimePlanText.getText());
    		arrayTime[4] = Integer.parseInt(tTimePlanText.getText());
    		arrayTime[5] = Integer.parseInt(pmTimePlanText.getText());
    		PlanSummaryImpl pSumm = new PlanSummaryImpl();
    		filledSummary = pSumm.createPlanSummary(projectNameText.getText(), Integer.parseInt(sLOCPlanText.getText()), arrayLOC, arrayTime);
    		//CurrentProject.get().addPlanSummary(el);
    	}
    	//Window hidden so the data (via getData() method) is still accessible - calling method responsible for disposal.
        this.setVisible(false);
    }
    
    JFormattedTextField planFields() {
    	JFormattedTextField rtnfld = new JFormattedTextField(fieldFormat);
    	rtnfld.setValue(0);
    	rtnfld.addFocusListener(new FocusAdapter() {
    		public void focusLost(FocusEvent e) {
    			EventQueue.invokeLater(new Runnable() {
    				public void run() {
    					updatePlanTotal();
    				}
    			});
    		}
    	});
    	rtnfld.addPropertyChangeListener("value", new PropertyChangeListener() {
    		public void propertyChange(PropertyChangeEvent e) {
    			updatePlanTotal();
    		}
    	});
    	return rtnfld;
    }
    
    void updatePlanTotal() {
    	int newTotal = Integer.parseInt(pTimePlanText.getText()) + Integer.parseInt(dTimePlanText.getText()) + Integer.parseInt(cdTimePlanText.getText())
    	 + Integer.parseInt(cpTimePlanText.getText()) + Integer.parseInt(tTimePlanText.getText()) + Integer.parseInt(pmTimePlanText.getText());
    	ttlTimePlanText.setText(Integer.toString(newTotal));
    }
    /**
     * Returns the completed summary Element object from this dialog. Dispose of the dialog after retrieval.
     * @return A XOM.NU Element object containing all of the dialog fields as attributes.
     */
    public Element getData() {
    	return filledSummary;
    }
}

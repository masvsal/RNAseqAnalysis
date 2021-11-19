package ui.tools;

import ui.display.DataFileDisplay;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//signals RNAseq display to execute count of RNAseq output data file. DisplayTool
public class CountSigGeneExpressionTool extends Tool implements ActionListener {

    //MODIFIES: this
    //EFFECTS: instantiates new CountSigGeneExpression object
    public CountSigGeneExpressionTool(ToolBar toolBar) {
        super(toolBar, "Count DE");
        button.addActionListener(this);
    }

    //EFFECTS: Signals display panel component to handle user input.
    public void actionPerformed(ActionEvent e) {
        DataFileDisplay display = toolBar.getDataFileDirectoryMenu().getDataFileDisplay();
        display.getVisualPanel().getThreshold();
        toolBar.getMainInterface().validate();
    }
}

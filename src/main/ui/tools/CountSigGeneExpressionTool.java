package ui.tools;

import ui.display.DataFileDisplay;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CountSigGeneExpressionTool extends Tool implements ActionListener {

    public CountSigGeneExpressionTool(ToolBar toolBar) {
        super(toolBar, "Count DE");
        button.addActionListener(this);
    }

    public void actionPerformed(ActionEvent e) {
        DataFileDisplay display = toolBar.getDataFileDirectoryMenu().getDataFileDisplay();
        display.getVisualPanel().getThreshold();
        toolBar.getMainInterface().validate();
    }
}

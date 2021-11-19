package ui.tools;

import model.Experiment;
import model.ExperimentDirectory;
import ui.display.DataFileDisplay;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ModifyTool extends Tool implements ActionListener {
    DataFileDisplay display;
    ConfirmTool confirmTool;


    public ModifyTool(ToolBar toolBar) {
        super(toolBar, "MD");
        button.addActionListener(this);
    }


    public void confirmModify() {
        display.saveModifiedPanel();
        toolBar.showConfirmTool(false);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        display = toolBar.getDataFileDirectoryMenu().getDataFileDisplay();
        display.createModifiableDisplayPanel();
        toolBar.showConfirmTool(true);
    }
}

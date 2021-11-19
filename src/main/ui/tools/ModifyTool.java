package ui.tools;

import model.Experiment;
import model.ExperimentDirectory;
import ui.display.DataFileDisplay;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//tool used to modify element. ListTool
public class ModifyTool extends Tool implements ActionListener {
    DataFileDisplay display;    //Datafiledisplay object that is being modified

    //MODIFIES: this
    //EFFECTS: instantiates new modifyTool object
    public ModifyTool(ToolBar toolBar) {
        super(toolBar, "MD");
        button.addActionListener(this);
    }

    //modifies: displaypanel
    //EFFECTS: signals display panel to save modified information to experiment directory. Removes confirm tool.
    public void confirmModify() {
        display.saveModifiedPanel();
        toolBar.showConfirmTool(false);
    }

    @Override
    //EFFECTS: Signals display panel to accept user input to allow modification of information.
    public void actionPerformed(ActionEvent e) {
        display = toolBar.getDataFileDirectoryMenu().getDataFileDisplay();
        display.createModifiableDisplayPanel();
        toolBar.showConfirmTool(true);
    }
}

package ui.tools;

import model.ExperimentDirectory;
import ui.menus.Menu;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//tool used to remove element from menu. ListTool
public class DeleteTool extends Tool implements ActionListener {

    //MODIFIES: this
    //EFFECTS: instantiates new DeleteTool object
    public DeleteTool(ToolBar toolBar) {
        super(toolBar, "-");
        button.addActionListener(this);
    }

    @Override
    //EFFECTS: Signals menu component to remove currently selected item
    public void actionPerformed(ActionEvent e) {
        Menu listToRemove = toolBar.getActiveList();
        listToRemove.removeItem();
    }
}

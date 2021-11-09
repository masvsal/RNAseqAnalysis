package ui.tools;

import model.ExperimentDirectory;
import ui.menus.Menu;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DeleteTool extends Tool implements ActionListener {

    public DeleteTool(ToolBar toolBar) {
        super(toolBar, "-");
        button.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Menu listToRemove = toolBar.getActiveList();
        listToRemove.removeItem();
    }
}

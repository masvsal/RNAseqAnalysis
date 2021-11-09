package ui.tools;

import ui.menus.Menu;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddTool extends Tool implements ActionListener {



    public AddTool(ToolBar toolBar) {
        super(toolBar, "+");
        button.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Menu listToAdd = toolBar.getActiveList();
        listToAdd.addItemName();
    }
}

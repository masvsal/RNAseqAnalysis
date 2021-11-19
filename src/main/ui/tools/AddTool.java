package ui.tools;

import ui.menus.Menu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//tool used to add new element to a menu. List tool
public class AddTool extends Tool implements ActionListener {

    //MODIFIES: this
    //EFFECTS: instantiates new AddTool object.
    public AddTool(ToolBar toolBar) {
        super(toolBar, "+");
        button.addActionListener(this);
    }

    @Override
    //Signals active toolbar list to add new item. this class is the listener for the addTool object. Called every time
    //addButton button is pressed.
    public void actionPerformed(ActionEvent e) {
        Menu listToAdd = toolBar.getActiveList();
        listToAdd.addItem();
    }
}

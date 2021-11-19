package ui.tools;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
//confirm tool. Usually invisible, set to visible when modify tool is called. Display tool

public class ConfirmTool extends Tool implements ActionListener {
    ModifyTool callingTool;         //modify tool that handles output from confirm tool.

    //MODIFIES: this
    //EFFECTS: instantiates new confirmTool object
    public ConfirmTool(ToolBar toolBar, ModifyTool tool) {
        super(toolBar, "Confirm");
        this.callingTool = tool;
        button.addActionListener(this);
    }

    @Override
    //EFFECTS: Signals modify tool to confirm modifidation. This class is the listener for the confirm object.
    // Called every time confirm button is pressed.
    public void actionPerformed(ActionEvent e) {
        callingTool.confirmModify();
    }
}

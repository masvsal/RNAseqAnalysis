package ui.tools;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ConfirmTool extends Tool implements ActionListener {
    ModifyTool callingTool;

    public ConfirmTool(ToolBar toolBar, ModifyTool tool) {
        super(toolBar, "Confirm");
        this.callingTool = tool;
        button.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        callingTool.confirmModify();
    }
}

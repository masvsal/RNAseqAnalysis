package ui.tools;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

//general tool class for button-based tools. Specifies general behavior of object
public abstract class Tool extends JComponent {
    protected static int HEIGHT = 50;           //height of tool

    protected JButton button;                   //user-interactable button
    protected ToolBar toolBar;                  //parent toolbar object that contains tool

    //getters. Returns height of object
    public int getHeight() {
        return HEIGHT;
    }

    //MODIFIES: this
    //EFFECTS: instantiates new tool object, creates button and sets parent toolbar
    public Tool(ToolBar parent, String text) {
        createButton(text);
        this.toolBar = parent;
    }

    //MODIFIES: this
    // EFFECTS: creates generic Jbutton object
    protected void createButton(String text) {
        button = new JButton(text);
        button.setMaximumSize(new Dimension(50,50));
    }

    // MODIFIES: this, parent
    // EFFECTS:  adds the given button to parent toolbar component
    public void addToParent(JComponent parent) {
        parent.add(BorderLayout.SOUTH, button);
    }

    //MODIFIES: this
    //EFFECTS: sets visibility of tool to given boolean value
    public void setVisibility(Boolean operator) {
        button.setVisible(operator);
    }

}



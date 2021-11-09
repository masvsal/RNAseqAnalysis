package ui.tools;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public abstract class Tool extends JComponent {
    protected JButton button;
    protected ToolBar toolBar;
    protected static int HEIGHT = 50;

    public int getHeight() {
        return HEIGHT;
    }

    public Tool(ToolBar parent, String text) {
        createButton(parent, text);
        //addToParent(parent);
        this.toolBar = parent;
    }

    // EFFECTS: creates button to activate tool
    protected void createButton(JComponent parent, String text) {
        button = new JButton(text);
        button.setMaximumSize(new Dimension(50,50));

        //addToParent(parent);
    }

    // MODIFIES: parent
    // EFFECTS:  adds the given button to the parent component
    public void addToParent(JComponent parent) {
        parent.add(BorderLayout.SOUTH, button);
    }

    public void setVisibility(Boolean operator) {
        button.setVisible(operator);
    }

}



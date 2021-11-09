package ui.menus;

import model.Experiment;
import model.interfaces.Directory;
import model.interfaces.NamedFile;
import ui.MainInterface;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public abstract class Menu extends JComponent implements ActionListener {
    protected static int PANE_X = 300;
    protected static int PANE_Y = 300;
    protected static int SCROLL_PANE_X = 150;
    protected static int SCROLL_PANE_Y = 90;
    protected JTextField textField;
    protected MainInterface mainInterface;
    protected JList<String> itemList;
    protected JScrollPane scrollPane;
    protected JLabel selection;
    protected int idx;
    protected String selectedExperimentName;

    public Menu(MainInterface mainInterface) {
        this.mainInterface = mainInterface;
    }

    public abstract void addItemName();

    public abstract void removeItem();

    public void modifyDescription() {
        createTextField("description");
        textField.setActionCommand("2");
    }

    public void setDescription(Directory directory) {
        String description = textField.getText();
        String newDescription = description.split(":")[1];

        NamedFile file = directory.getFile(idx + 1);
        file.setDescription(newDescription);
    }

    // REQUIRES: passed string must not contain the colon character ":"
    protected void createTextField(String textToDisplay) {
        textField = new JTextField(textToDisplay + ":", PANE_X);
        textField.addActionListener(this);
        add(BorderLayout.SOUTH, textField);

        mainInterface.validate();
    }

    protected void addDescription(Directory directory) {
        setDescription(directory);

        remove(itemList);
        setUpList();
        remove(textField);

        mainInterface.validate();
    }

    abstract void setUpList();

}

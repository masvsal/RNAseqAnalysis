package ui.menus;

import model.Experiment;
import model.interfaces.Directory;
import model.interfaces.NamedFile;
import ui.MainInterface;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;

//Abstract menu class, contains general behavior of directories
public abstract class Menu extends JComponent implements ActionListener {
    protected static int PANE_X = 250;          //horizontal size of all menus
    protected static int PANE_Y = 650;          //vertical size of all menus
    protected JTextField textField;             //interactable text field, used to create new menu elements
    protected MainInterface mainInterface;      //reference to parent component
    protected JList<String> itemList;           //list of items in menu
    protected JScrollPane scrollPane;           //scrollpane containing each unique list of items
    protected JLabel selection;                 //selection label indicating currently selected item
    protected int idx;                          // index of currently selected item
    protected String selectedExperimentName;    //name of currently selected item
    protected JPanel selectionPanel;            //panel containing selection label and text fields.


    //MODIFiES: this
    //EFFECTS:instantiates new menu object
    public Menu(MainInterface mainInterface) {
        this.mainInterface = mainInterface;
    }

    //modifies: this
    //effects: adds element to menu
    public abstract void addItem();

    //modifies: this
    //removes item from menu
    public abstract void removeItem();

    //modifies: this
    //effects: sets currently selected item's description to text currently in text field
    public void setDescription(Directory directory) {
        String description = textField.getText();
        String newDescription = description.split(":")[1];

        NamedFile file = directory.getFile(idx + 1);
        file.setDescription(newDescription);
    }

    // REQUIRES: passed string must not contain the colon character ":"
    //modifies: this
    //effects: instantiates a new text field in selectionPanel
    protected void createTextField(String textToDisplay) {
        textField = new JTextField(textToDisplay + ":", 20);
        textField.addActionListener(this);
        selectionPanel.add(textField);
        mainInterface.pack();

    }

    //modifies: this
    //effects: adds and shows new descriptions of given object
    protected void saveAndShowDescription(Directory directory) {
        setDescription(directory);

        remove(itemList);
        setUpList();
        remove(textField);

        mainInterface.validate();
    }

    //effect: sets up new list reflecting current state of items in directory
    abstract void setUpList();

}

package ui.menus;

import model.Experiment;
import model.ExperimentDirectory;
import model.GenericDataFile;
import model.interfaces.NamedFile;
import ui.MainInterface;
import model.logging.Event;
import model.logging.EventLog;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

//menu containing all experiments in experiment directory
public class ExperimentDirectoryMenu extends Menu implements ListSelectionListener, ActionListener {

    private ExperimentDirectory experimentDirectory;        //reference to main data structure




    //MODIFiES: this
    //EFFECTS:instantiates new Experiment Directory menu object. Adds panels and scrollpane.
    public ExperimentDirectoryMenu(DataFileDirectoryMenu dataFileDirectoryMenu, MainInterface mainInterface) {
        super(mainInterface);
        new JPanel();
        setPreferredSize(new Dimension(PANE_X, PANE_Y));
        setLayout(new BoxLayout(this, 1));

        this.experimentDirectory = mainInterface.getExperimentDirectory();

        selection = new JLabel("Please choose an experiment");
        selection.setHorizontalAlignment(SwingConstants.CENTER);


        selectionPanel = new JPanel();
        selectionPanel.setMaximumSize(new Dimension(PANE_X, 75));


        selectionPanel.add(selection);
        selectionPanel.setBorder(new LineBorder(Color.BLACK));

        add(selectionPanel);
        setUpList();

        mainInterface.pack();
        setVisible(true);
    }

    @Override
    //modifies: this, datafileDirectoryMeny
    //effects: changes state of menu to reflect currently selected item. If item is selected, creates new menu for it,
    //sets selection label to its name and sets this list to active list in toolbar
    public void valueChanged(ListSelectionEvent e) {
        idx = itemList.getSelectedIndex();
        if (idx != -1) {
            selectedExperimentName = experimentDirectory.getFile(idx + 1).getName();
            selection.setText(selectedExperimentName);
            mainInterface.getDataFileDirectoryMenu().addExperimentList(selectedExperimentName);
            mainInterface.getToolBar().setActiveList(this);
            mainInterface.validate();
            mainInterface.pack();
        } else {
            selection.setText("choose experiment");
        }
    }

    //EFFECTS: returns a string array of the names of experiments in experiment directory field
    private String[] directoryListToArray() {
        ArrayList<String> listOfNames = new ArrayList<>();

        for (NamedFile e:experimentDirectory.getListofExperiments()) {
            listOfNames.add(e.getName());
        }

        return listOfNames.toArray(new String[0]);
    }

    @Override
    //Modifies: MainInterFace, this
    //Effect: creates a text field below list prompting user to input name of a new experiment
    public void addItem() {
        createTextField("name");
        textField.setActionCommand("0");
    }

    @Override
    //Modifies: mainInterface, this
    //effects: removes selected experiment from experiment directory field
    public void removeItem() {
        String nameOfRemovedFile = experimentDirectory.getFile(idx + 1).getName();
        EventLog.getInstance().logEvent(
                new Event("DATA FILE: " + nameOfRemovedFile + "|" + "REMOVED FROM: Experiment Directory")
        );
        experimentDirectory.removeFile(idx + 1);

        remove(scrollPane);
        setUpList();

        mainInterface.pack();
    }


    //modifies: this
    //handles all text field input. If 'add' text field, creates new item and shows it. If 'description' text field,
    //modifies current item's description. Handles un-predictable user input.
    public void actionPerformed(ActionEvent e) {
        try {
            if (e.getActionCommand().equals("0")) {
                addName();
                mainInterface.validate();
            } else if (e.getActionCommand().equals("2")) {
                saveAndShowDescription(experimentDirectory);
                mainInterface.getDataFileDirectoryMenu().refreshDisplay();
            }
        } catch (IndexOutOfBoundsException err) {
            textField.setText("Bad Input. Please try again:");
        }
    }

    //modifies: this
    //effects: creates new experiment containing a null file. Shows experiment in menu. Records add to log
    private void addName() {
        String name = textField.getText();
        String newName = name.split(":")[1];

        Experiment newExperiment = new Experiment(newName);
        newExperiment.addFile(new GenericDataFile("nullfile", "000"));

        EventLog.getInstance().logEvent(
                new Event("EXPERIMENT: " + newName + "|" + "ADDED TO: Experiment Directory")
        );

        experimentDirectory.addFile(newExperiment);

        selectionPanel.remove(textField);

        remove(scrollPane);
        mainInterface.validate();
        setUpList();

        mainInterface.pack();
    }

    //effects: adds new scrollpane that reflects current state of data in experiment directory.
    public void setUpList() {
        itemList = new JList<>(directoryListToArray());
        itemList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        scrollPane = new JScrollPane(itemList);

        scrollPane.setBorder(new LineBorder(Color.BLACK));
        scrollPane.setMaximumSize(new Dimension(PANE_X, PANE_Y - 100));
        itemList.addListSelectionListener(this);

        add(scrollPane);
    }
}

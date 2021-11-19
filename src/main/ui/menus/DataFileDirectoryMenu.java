package ui.menus;

import model.Experiment;
import model.ExperimentDirectory;
import model.GenericDataFile;
import ui.MainInterface;
import ui.display.DataFileDisplay;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class DataFileDirectoryMenu extends Menu implements ListSelectionListener, ActionListener {

    private Experiment experiment;
    private ExperimentDirectory experimentDirectory;
    private DataFileDisplay dataFileDisplay;
    private String newName;


    //EFFECT: creates new data file directory menu object with a border layout, given experiment directory
    //,data file display, and selection display
    public DataFileDirectoryMenu(MainInterface mainInterface, DataFileDisplay dataFileDisplay) {
        super(mainInterface);
        new JPanel();
        setPreferredSize(new Dimension(PANE_X, PANE_Y));
        setLayout(new BoxLayout(this, 1));

        this.experimentDirectory = mainInterface.getExperimentDirectory();

        this.dataFileDisplay = dataFileDisplay;

        selection = new JLabel("Please choose a data file");
        selection.setHorizontalAlignment(SwingConstants.CENTER);

        selectionPanel = new JPanel();


        selectionPanel.add(selection);
        selectionPanel.setBorder(new LineBorder(Color.BLACK));
        selectionPanel.setMaximumSize(new Dimension(PANE_X, 75));
        add(selectionPanel);

    }


    @Override
    //Modifies: this, DataFileDisplay object, Tool Bar
    //Effect:If valid data file is selected, sets selection label to name of selected data file
    // tells dataFileDisplay to display selected experiment, sets active list to this. Else
    // tells selection label to display: "choose data file"
    public void valueChanged(ListSelectionEvent e) {
        idx = itemList.getSelectedIndex();
        if (idx != -1) {
            selectedExperimentName = experiment.getListOfDataFiles().get(idx).getName();
            selection.setText(selectedExperimentName);
            dataFileDisplay.displayDataFile(selectedExperimentName, this.experiment);
            mainInterface.getToolBar().setActiveList(this);
            mainInterface.validate();
        } else {
            selection.setText("choose data file");
        }
    }

    //EFFECTS: returns a string array of the names of datafiles in experiment directory field
    private String[] directoryListToArray() {
        ArrayList<String> listOfNames = new ArrayList<>();
        for (GenericDataFile e : experiment.getListOfDataFiles()) {
            listOfNames.add(e.getName());
        }

        return listOfNames.toArray(new String[0]);
    }

    //EFFECTS: returns the dataFileDisplay
    public DataFileDisplay getDataFileDisplay() {
        return dataFileDisplay;
    }

    //EFFECTS: returns experiment matching given name in experiment directory field
    private Experiment searchDirectoryListForExperiment(String name) {
        for (Experiment e:experimentDirectory.getListofExperiments()) {
            if (e.getName().equals(name)) {
                return e;
            }
        }
        Experiment experiment = new Experiment("No Experiment Selected");
        experiment.addFile(new GenericDataFile("blank", "blank"));
        return experiment;
    }

    //Modifies: this
    //EFFECT: replaces the current experiment list with one for the experiiment matching the given experiment name
    public void addExperimentList(String name) {
        Experiment experiment = searchDirectoryListForExperiment(name);
        if (itemList != null) {
            remove(scrollPane);
        }
        this.experiment = experiment;
        setUpList();
    }

    //MODIFIES: this
    //Effect: creates new list for Experiment in experiment field, adds it to this
    public void setUpList() {
        itemList = new JList<>(directoryListToArray());
        itemList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        itemList.addListSelectionListener(this);
        itemList.setVisible(true);

        scrollPane = new JScrollPane(itemList);
        scrollPane.setMaximumSize(new Dimension(PANE_X, PANE_Y - 100));

        add(BorderLayout.CENTER, scrollPane);
    }

    @Override
    //Modifies: MainInterFace, this
    //Effect: creates a text field below current list prompting user to input name of a new experiment
    public void addItemName() {
        super.createTextField("name");
        textField.setActionCommand("0");
    }

    //Modifies: MainInterFace, this
    //Effect: creates a text field below current list prompting user to input data for a new experiment
    public void addItemData() {
        super.createTextField("data");
        System.out.println(textField.getText());
        textField.setActionCommand("1");
    }

    @Override
    //Modifies: MainInterFace, this
    //Effect: removes item from current experiment directory
    public void removeItem() {
        experiment.removeFile(idx + 1);
        if (experiment.getListOfDataFiles().size() == 0) {
            experiment.addFile(new GenericDataFile("EmptyFile", "000"));
        }
        addExperimentList(experiment.getName());

        mainInterface.validate();
    }



    @Override
    //EFFECT: responds to input from text field. If name text field, stores name and prompts user for data.
    //if data text field, creates new experiment using name and data. If decscription text field, modifies current
    //experiment's description.
    public void actionPerformed(ActionEvent e) {
        try {
            if (e.getActionCommand().equals("0")) {
                setName();
            } else if (e.getActionCommand().equals("1")) {
                setData();
            } else {
                addDescription(experiment);
            }
            refreshDisplay();
        } catch (IndexOutOfBoundsException err) {
            textField.setText("Bad Input. Please try again:");
        }
    }
    //Modifies: this
    //Sets newName field as current text field's text. Replaces name text field with data text field

    private void setName() {
        String name  = textField.getText();
        newName = name.split(":")[1];
        selectionPanel.remove(textField);
        mainInterface.validate();
        addItemData();
    }

    //Modifies: this, maininterface
    //creates new experiment using newName field and text in Data text field

    private void setData() {
        String data = textField.getText();
        String newData = data.split(":")[1];
        selectionPanel.remove(textField);

        experiment.addFile(new GenericDataFile(newName, newData));
        addExperimentList(experiment.getName());

        mainInterface.validate();
    }

    //Effects: tells data display to display the Experiment in experiment field
    public void refreshDisplay() {
        dataFileDisplay.displayDataFile(selectedExperimentName, this.experiment);
    }
}

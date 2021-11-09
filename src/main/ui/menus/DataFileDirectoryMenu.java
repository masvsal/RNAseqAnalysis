package ui.menus;

import model.Experiment;
import model.ExperimentDirectory;
import model.GenericDataFile;
import ui.MainInterface;
import ui.display.DataFileDisplay;

import javax.swing.*;
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

    public DataFileDirectoryMenu(MainInterface mainInterface, DataFileDisplay dataFileDisplay) {
        super(mainInterface);
        new JPanel();
        setPreferredSize(new Dimension(PANE_X, PANE_Y));
        setLayout(new BorderLayout());

        this.experimentDirectory = mainInterface.getExperimentDirectory();

        this.dataFileDisplay = dataFileDisplay;

        selection = new JLabel("Please choose a data file");
        selection.setHorizontalAlignment(0);


        add(BorderLayout.NORTH, selection);

    }


    @Override
    public void valueChanged(ListSelectionEvent e) {
        idx = itemList.getSelectedIndex();
        if (idx != -1) {
            selectedExperimentName = experiment.getListOfDataFiles().get(idx).getName();
            selection.setText(selectedExperimentName);
            refreshDisplay();
            mainInterface.getToolBar().setActiveList(this);
            mainInterface.validate();
        } else {
            selection.setText("choose data file");
        }
    }

    private String[] directoryListToArray() {
        ArrayList<String> listOfNames = new ArrayList<>();
        for (GenericDataFile e : experiment.getListOfDataFiles()) {
            listOfNames.add(e.getName());
        }

        return listOfNames.toArray(new String[0]);
    }

    public DataFileDisplay getDataFileDisplay() {
        return dataFileDisplay;
    }

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

    public void addExperimentList(String name) {
        Experiment experiment = searchDirectoryListForExperiment(name);
        if (itemList != null) {
            remove(itemList);
        }
        this.experiment = experiment;
        setUpList();
    }

    public void setUpList() {
        itemList = new JList<>(directoryListToArray());
        itemList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        itemList.addListSelectionListener(this);
        itemList.setVisible(true);

        scrollPane = new JScrollPane(itemList);
        scrollPane.setPreferredSize(new Dimension(SCROLL_PANE_X, SCROLL_PANE_Y));

        add(BorderLayout.CENTER, itemList);
    }

    @Override
    public void addItemName() {
        super.createTextField("name");
        textField.setActionCommand("0");
    }

    public void addItemData() {
        super.createTextField("data");
        textField.setActionCommand("1");
    }

    @Override
    public void removeItem() {
        experiment.removeFile(idx + 1);
        if (experiment.getListOfDataFiles().size() == 0) {
            experiment.addFile(new GenericDataFile("EmptyFile", "000"));
        }
        addExperimentList(experiment.getName());

        mainInterface.validate();
    }



    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println(e);
        if (e.getActionCommand().equals("0")) {
            setName();
        } else if (e.getActionCommand().equals("1")) {
            setData();
        } else {
            addDescription(experiment);
        }
        refreshDisplay();
    }

    private void setName() {
        String name  = textField.getText();
        newName = name.split(":")[1];
        remove(textField);

        mainInterface.validate();
        addItemData();
    }

    private void setData() {
        String data = textField.getText();
        String newData = data.split(":")[1];
        remove(textField);

        experiment.addFile(new GenericDataFile(newName, newData));
        addExperimentList(experiment.getName());

        mainInterface.validate();
    }

    public void refreshDisplay() {
        dataFileDisplay.displayDataFile(selectedExperimentName, this.experiment);
    }
}

package ui.menus;

import model.Experiment;
import model.ExperimentDirectory;
import model.GenericDataFile;
import model.interfaces.NamedFile;
import ui.MainInterface;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class ExperimentDirectoryMenu extends Menu implements ListSelectionListener, ActionListener {
    private ExperimentDirectory experimentDirectory;
    private DataFileDirectoryMenu dataFileDirectoryMenu;



    public ExperimentDirectoryMenu(DataFileDirectoryMenu dataFileDirectoryMenu, MainInterface mainInterface) {
        super(mainInterface);
        new JPanel();
        setPreferredSize(new Dimension(PANE_X, PANE_Y));
        setLayout(new BoxLayout(this, 1));

        this.experimentDirectory = mainInterface.getExperimentDirectory();

        this.dataFileDirectoryMenu = dataFileDirectoryMenu;

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
    public void valueChanged(ListSelectionEvent e) {
        idx = itemList.getSelectedIndex();
        if (idx != -1) {
            selectedExperimentName = experimentDirectory.getFile(idx + 1).getName();
            selection.setText(selectedExperimentName);
            dataFileDirectoryMenu.addExperimentList(selectedExperimentName);
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
    public void addItemName() {
        createTextField("name");
        textField.setActionCommand("0");
    }

    @Override
    //Modifies: mainInterface, this
    //effects: removes selected experiment from experiment directory field
    public void removeItem() {
        experimentDirectory.removeFile(idx + 1);

        remove(scrollPane);
        setUpList();

        mainInterface.pack();
    }


    public void actionPerformed(ActionEvent e) {
        try {
            if (e.getActionCommand().equals("0")) {
                addName();
                mainInterface.validate();
            } else if (e.getActionCommand().equals("2")) {
                addDescription(experimentDirectory);
                dataFileDirectoryMenu.refreshDisplay();
            }
        } catch (IndexOutOfBoundsException err) {
            textField.setText("Bad Input. Please try again:");
        }
    }

    private void addName() {
        String name = textField.getText();
        String newName = name.split(":")[1];

        Experiment newExperiment = new Experiment(newName);
        newExperiment.addFile(new GenericDataFile("nullfile", "000"));

        experimentDirectory.addFile(newExperiment);

        selectionPanel.remove(textField);

        remove(scrollPane);
        mainInterface.validate();
        setUpList();

        mainInterface.pack();
    }


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

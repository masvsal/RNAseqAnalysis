package ui.menus;

import model.Experiment;
import model.ExperimentDirectory;
import model.GenericDataFile;
import model.interfaces.NamedFile;
import ui.MainInterface;

import javax.swing.*;
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
        setLayout(new BorderLayout());

        this.experimentDirectory = mainInterface.getExperimentDirectory();;

        this.dataFileDirectoryMenu = dataFileDirectoryMenu;

        selection = new JLabel("Please choose an experiment");
        selection.setHorizontalAlignment(0);

        add(BorderLayout.NORTH, selection);

        setUpList();

        setVisible(true);
    }

//    private void createTools() {
//        JPanel panel = new JPanel();
//        panel.setLayout(new FlowLayout());
//        panel.setVisible(true);
//        panel.setSize(new Dimension(200, 200));
//
//        AddTool addTool = new AddTool(experimentDirectory, panel);
//        DeleteTool deleteTool = new DeleteTool(experimentDirectory, panel);
//        ModifyDescriptionTool modifyDescriptionTool = new ModifyDescriptionTool(experimentDirectory, panel);
//
//        add(BorderLayout.SOUTH, panel);
//    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        idx = itemList.getSelectedIndex();
        if (idx != -1) {
            selectedExperimentName = experimentDirectory.getFile(idx + 1).getName();
            selection.setText(selectedExperimentName);
            dataFileDirectoryMenu.addExperimentList(selectedExperimentName);
            mainInterface.getToolBar().setActiveList(this);
            mainInterface.validate();
        } else {
            selection.setText("choose experiment");
        }
    }

    //EFFECTS: takes the experimentDirectory field and returns the name of contained experiments in array
    private String[] directoryListToArray() {
        ArrayList<String> listOfNames = new ArrayList<>();

        for (Experiment e:experimentDirectory.getListofExperiments()) {
            listOfNames.add(e.getName());
        }

        return listOfNames.toArray(new String[0]);
    }

    @Override
    public void addItemName() {
        createTextField("name");
        textField.setActionCommand("0");
    }

    @Override
    public void removeItem() {
        experimentDirectory.removeFile(idx + 1);

        remove(itemList);
        setUpList();

        mainInterface.validate();
    }


    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("0")) {
            addName();
        } else if (e.getActionCommand().equals("2")) {
            addDescription(experimentDirectory);
            dataFileDirectoryMenu.refreshDisplay();
        }
    }

    private void addName() {
        String name = textField.getText();
        String newName = name.split(":")[1];

        Experiment newExperiment = new Experiment(newName);
        newExperiment.addFile(new GenericDataFile("nullfile", "000"));

        experimentDirectory.addFile(newExperiment);

        remove(itemList);
        setUpList();
        remove(textField);

        mainInterface.validate();
    }


    public void setUpList() {
        itemList = new JList<>(directoryListToArray());
        itemList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        scrollPane = new JScrollPane(itemList);
        scrollPane.setPreferredSize(new Dimension(SCROLL_PANE_X, SCROLL_PANE_Y));

        itemList.addListSelectionListener(this);

        add(BorderLayout.CENTER, itemList);
    }
}

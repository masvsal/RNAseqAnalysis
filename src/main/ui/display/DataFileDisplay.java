package ui.display;

import model.Experiment;
import model.ExperimentDirectory;
import model.GenericDataFile;
import ui.MainInterface;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DataFileDisplay extends JComponent implements ActionListener {
    MainInterface mainInterface;
    ExperimentDirectory experimentDirectory;
    Experiment experiment;
    GenericDataFile dataFile;

    JLabel experimentName;
    JTextField experimentDescription;
    JLabel experimentDescriptionNM;
    JLabel name;
    JTextField description;
    JLabel descriptionNM;
    JTextField data;
    JLabel dataNM;
    JLabel dataLabel = new JLabel("Data: ");
    JLabel descriptionLabel = new JLabel("Description: ");

    JPanel experimentPanel = new JPanel();
    JPanel dataPanel = new JPanel();

    private static int PANE_X = 300;
    private static int PANE_Y = 300;

    public DataFileDisplay(MainInterface mainInterface) {
        new JPanel();
        setPreferredSize(new Dimension(PANE_X, PANE_Y));
        setLayout(new BoxLayout(this, 1));

        this.experimentDirectory = mainInterface.getExperimentDirectory();
        this.mainInterface = mainInterface;
    }

    public void displayDataFile(String name, Experiment experiment) {
        this.experiment = experiment;
        dataFile = searchExperimentForDataFile(name);

        if (dataFile != null) {
            createUnModifiableDisplayPanel();
        }
    }

    public void createModifiableDisplayPanel() {
        remove(experimentPanel);
        remove(dataPanel);
        setUpPanels();
        setUpUnModifiableElements();
        setUpModifiableInformation();
        addPanels(true);
    }

    public void createUnModifiableDisplayPanel() {
        remove(experimentPanel);
        remove(dataPanel);
        saveModifiedInformation();
        setUpPanels();
        setUpUnModifiableElements();
        setUpUnModifiableInformation();
        addPanels(false);
    }

    private void setUpPanels() {
        experimentPanel = new JPanel();
        experimentPanel.setLayout(new BorderLayout());
        experimentPanel.setMaximumSize(new Dimension(PANE_X, 100));
        experimentPanel.setBackground(Color.lightGray);
        dataPanel = new JPanel();
        dataPanel.setLayout(new BorderLayout());
        dataPanel.setMaximumSize(new Dimension(PANE_X, 150));
        dataPanel.setBorder(new LineBorder(Color.BLACK));
    }

    private void setUpUnModifiableElements() {
        experimentName = new JLabel("Experiment: " + experiment.getName());
        name = new JLabel("Name: " + dataFile.getName());
    }

    private void setUpModifiableInformation() {
        experimentDescription = new JTextField(experiment.getDescription());
        description = new JTextField(dataFile.getDescription());
        data = new JTextField(dataFile.getData());
    }

    private void setUpUnModifiableInformation() {
        experimentDescriptionNM = new JLabel("Description: " + experiment.getDescription());
        descriptionNM = new JLabel("Description: " + dataFile.getDescription());
        dataNM = new JLabel("Data: " + dataFile.getData());
    }

    private void saveModifiedInformation() {
        if (experimentDescription != null && description != null && data != null) {
            experiment.setDescription(experimentDescription.getText());
            experimentDescription = null;
            dataFile.setDescription(description.getText());
            description = null;
            dataFile.setData(data.getText());
            data = null;
        }
    }

    //EFFECTS: adds unodifiable elements to panel if false, adds modifiable if true

    private void addPanels(Boolean argument) {
        experimentPanel.add(BorderLayout.NORTH,experimentName);
        if (argument) {
            experimentPanel.add(BorderLayout.CENTER,experimentDescription);
        } else {
            experimentPanel.add(BorderLayout.CENTER,experimentDescriptionNM);
        }

        dataPanel.add(BorderLayout.NORTH,name);

        if (argument) {
            dataPanel.add(BorderLayout.CENTER,description);
        } else {
            dataPanel.add(BorderLayout.CENTER,descriptionNM);
        }

        if (argument) {
            dataPanel.add(BorderLayout.SOUTH,data);
        } else {
            dataPanel.add(BorderLayout.SOUTH,dataNM);
        }

        add(experimentPanel);
        add(dataPanel);
        mainInterface.validate();
    }


    private GenericDataFile searchExperimentForDataFile(String name) {
        for (GenericDataFile e: experiment.getListOfDataFiles()) {
            if (e.getName().equals(name)) {
                return e;
            }
        }
        return null;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
    }
}

package ui.display;

import model.Experiment;
import model.ExperimentDirectory;
import model.GenericDataFile;
import model.RnaSeqDataFile;
import ui.MainInterface;

import javax.swing.*;
import java.awt.*;

public class DataFileDisplay extends JComponent {
    MainInterface mainInterface;
    ExperimentDirectory experimentDirectory;
    Experiment experiment;
    GenericDataFile dataFile;

    RNAseqOverviewDisplay visualPanel = new RNAseqOverviewDisplay(this);
    DisplayPanel mainPanel;

    Boolean isRNAseqFile;

    private static int PANE_X;
    private static int PANE_Y;

    public DataFileDisplay(MainInterface mainInterface) {
        new JPanel();
        PANE_Y = mainInterface.getHeight();
        PANE_X = mainInterface.getWidth() / 2;
        setPreferredSize(new Dimension(PANE_X, PANE_Y));
        setLayout(new BoxLayout(this, 1));
        this.experimentDirectory = mainInterface.getExperimentDirectory();
        this.mainInterface = mainInterface;
    }

    public int getPaneX() {
        return PANE_X;
    }

    public int getPaneY() {
        return PANE_Y;
    }

    public void displayDataFile(String name, Experiment experiment) {
        this.experiment = experiment;
        dataFile = searchExperimentForDataFile(name);
        this.isRNAseqFile = dataFile instanceof RnaSeqDataFile;

        if (dataFile != null) {
            createUnModifiableDisplayPanel();
            createOverviewIfRNAseqFile();
        }
        mainInterface.validate();
    }

    public void refreshDataDisplay() {
        mainPanel.refreshDataPanel();
    }

    public void displayDataFile() {
        if (dataFile != null) {
            createUnModifiableDisplayPanel();
            createOverviewIfRNAseqFile();
        }
        mainInterface.validate();
    }


    private void createOverviewIfRNAseqFile() {
        if (isRNAseqFile) {
            createOverview((RnaSeqDataFile) dataFile);
            mainInterface.getToolBar().showRNAseqTools(true);
        } else {
            createBlankOverview();
            mainInterface.getToolBar().showRNAseqTools(false);
        }
    }

    public void createModifiableDisplayPanel() {
        remove(mainPanel);
        mainPanel = new ModifiableDisplayPanel(experiment, dataFile, this);
        add(BorderLayout.NORTH, mainPanel);
        remove(visualPanel);
        add(visualPanel);
    }

    public void saveModifiedPanel() {
        mainPanel.saveModifiedInformation();
        remove(mainPanel);
        createUnModifiableDisplayPanel();
        mainInterface.validate();
    }

    public void createUnModifiableDisplayPanel() {
        if (mainPanel != null) {
            remove(mainPanel);
            mainInterface.getToolBar().showConfirmTool(false);
        }
        mainPanel = new UnModifiableDisplayPanel(experiment, dataFile, this);
        add(BorderLayout.NORTH, mainPanel);
        remove(visualPanel);
        add(visualPanel);
    }

    private void createOverview(RnaSeqDataFile rnaSeqDataFile) {
        remove(visualPanel);
        visualPanel = new RNAseqOverviewDisplay(rnaSeqDataFile, this);
        add(visualPanel);
    }

    private void createBlankOverview() {
        remove(visualPanel);
        visualPanel = new RNAseqOverviewDisplay(this);
        add(visualPanel);
    }

    public RNAseqOverviewDisplay getVisualPanel() {
        return visualPanel;
    }
//
//    private void setUpPanels() {
//        experimentPanel = new JPanel();
//        experimentPanel.setLayout(new BorderLayout());
//        experimentPanel.setMaximumSize(new Dimension(PANE_X, 50));
//        experimentPanel.setBackground(Color.lightGray);
//        dataPanel = new JPanel();
//        dataPanel.setLayout(new BorderLayout());
//        dataPanel.setMaximumSize(new Dimension(PANE_X, 150));
//        dataPanel.setBorder(new LineBorder(Color.BLACK));
//    }
//
//    private void setUpUnModifiableElements() {
//        experimentName = new JLabel("Experiment: " + experiment.getName());
//        name = new JLabel("Name: " + dataFile.getName());
//    }
//
//    private void setUpModifiableInformation() {
//        experimentDescription = new JTextField(experiment.getDescription());
//        description = new JTextField(dataFile.getDescription());
//        data = new JTextField(dataFile.getData());
//    }
//
//    private void setUpUnModifiableInformation() {
//        experimentDescriptionNM = new JLabel("Description: " + experiment.getDescription());
//        descriptionNM = new JLabel("Description: " + dataFile.getDescription());
//        dataNM = new JLabel("Data: " + dataFile.getData());
//    }
//
//    private void saveModifiedInformation() {
//        if (experimentDescription != null && description != null && data != null) {
//            experiment.setDescription(experimentDescription.getText());
//            experimentDescription = null;
//            dataFile.setDescription(description.getText());
//            description = null;
//            dataFile.setData(data.getText());
//            data = null;
//        }
//    }
//
//    //EFFECTS: adds unodifiable elements to panel if false, adds modifiable if true
//
//    private void addPanels(Boolean argument) {
//        experimentPanel.add(BorderLayout.NORTH,experimentName);
//        if (argument) {
//            experimentPanel.add(BorderLayout.CENTER,experimentDescription);
//        } else {
//            experimentPanel.add(BorderLayout.CENTER,experimentDescriptionNM);
//        }
//
//        dataPanel.add(BorderLayout.NORTH,name);
//
//        if (argument) {
//            dataPanel.add(BorderLayout.CENTER,description);
//        } else {
//            dataPanel.add(BorderLayout.CENTER,descriptionNM);
//        }
//
//        if (argument) {
//            dataPanel.add(BorderLayout.SOUTH,data);
//        } else {
//            dataPanel.add(BorderLayout.SOUTH,dataNM);
//        }
//
//        add(experimentPanel);
//        add(dataPanel);
//    }


    private GenericDataFile searchExperimentForDataFile(String name) {
        for (GenericDataFile e : experiment.getListOfDataFiles()) {
            if (e.getName().equals(name)) {
                return e;
            }
        }
        return null;
    }
}

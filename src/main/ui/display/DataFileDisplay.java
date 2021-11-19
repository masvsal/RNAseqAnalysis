package ui.display;

import model.Experiment;
import model.ExperimentDirectory;
import model.GenericDataFile;
import model.RnaSeqDataFile;
import ui.MainInterface;

import javax.swing.*;
import java.awt.*;

//top-level display panel, displays information contained in data file to user
public class DataFileDisplay extends JComponent {
    private static int PANE_X;      //width of display panel
    private static int PANE_Y;      //height of display panel

    protected MainInterface mainInterface;                //parent frame
    private ExperimentDirectory experimentDirectory;    //reference to main data structure
    private Experiment experiment;                      //experiment containing displayed data file
    private GenericDataFile dataFile;                   //data file to display

    private  RNAseqOverviewDisplay visualPanel =
            new RNAseqOverviewDisplay(this); //panel w/ any necessary visual elements
    private DisplayPanel mainPanel;         //panel with all written data file information
    private boolean isRNAseqFile;           //boolean flag indicating whether file to display is of RNAseq type



    //MODIFIES: this
    //EFFECTS:  instantiates new dataFileDisplay object. Sets up panel.
    public DataFileDisplay(MainInterface mainInterface) {
        new JPanel();
        PANE_Y = mainInterface.getHeight();
        PANE_X = mainInterface.getWidth() / 2;
        setPreferredSize(new Dimension(PANE_X, PANE_Y));
        setLayout(new BoxLayout(this, 1));
        this.experimentDirectory = mainInterface.getExperimentDirectory();
        this.mainInterface = mainInterface;
    }

    ///getters
    //effects: gets width

    public int getPaneX() {
        return PANE_X;
    }

    //effects: gets height

    public int getPaneY() {
        return PANE_Y;
    }

    //MODIFIES: this
    //effects: generates and refreshes display for given data file in experiment. If not found,  does nothing

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

    //modifies: this
    //effects: generates and refreshes display for given data file in experiment. If not found,  does nothing
    //alternate method for displaying data panel without passing a new experiment.
    public void displayDataFile() {
        if (dataFile != null) {
            createUnModifiableDisplayPanel();
            createOverviewIfRNAseqFile();
        }
        mainInterface.validate();
    }

    //effects: tells data display sub-component to re-display data file to reflect any changed information
    public void refreshDataDisplay() {
        mainPanel.refreshDataPanel();
    }


    //MODIFIES: this
    //effect: creates visual overview if current data file is of type RNAseqdatafile.
    // If not, displays blank visual panel
    private void createOverviewIfRNAseqFile() {
        if (isRNAseqFile) {
            createOverview((RnaSeqDataFile) dataFile);
            mainInterface.getToolBar().showRNAseqTools(true);
        } else {
            createBlankOverview();
            mainInterface.getToolBar().showRNAseqTools(false);
        }
    }

    //MODIFIES: this
    //effect: creates user-interactable data display.
    public void createModifiableDisplayPanel() {
        remove(mainPanel);
        mainPanel = new ModifiableDisplayPanel(experiment, dataFile, this);
        add(BorderLayout.NORTH, mainPanel);
        remove(visualPanel);
        add(visualPanel);
    }

    //MODIFIES: this
    //effect: saves changed  to modifiable panel. Replaces modifiable display with unmodifiable display.
    public void saveModifiedPanel() {
        mainPanel.saveModifiedInformation();
        remove(mainPanel);
        createUnModifiableDisplayPanel();
        mainInterface.validate();
    }

    //MODIFIES: this
    //effect: generates and displays non-interactable display panel
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

    //modifies: this
    //effects: creates new visual element for rnaseq data file and displays it to the visual panel
    private void createOverview(RnaSeqDataFile rnaSeqDataFile) {
        remove(visualPanel);
        visualPanel = new RNAseqOverviewDisplay(rnaSeqDataFile, this);
        add(visualPanel);
    }

    //modifies: this
    //effects: creates new visual element for non-rnaseq data file and displays it to the visual panel
    private void createBlankOverview() {
        remove(visualPanel);
        visualPanel = new RNAseqOverviewDisplay(this);
        add(visualPanel);
    }

    //effects: returns visual panel
    public RNAseqOverviewDisplay getVisualPanel() {
        return visualPanel;
    }

    //effects: finds data file matching given name in given experiment.
    private GenericDataFile searchExperimentForDataFile(String name) {
        for (GenericDataFile e : experiment.getListOfDataFiles()) {
            if (e.getName().equals(name)) {
                return e;
            }
        }
        return null;
    }
}

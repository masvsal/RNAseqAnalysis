package ui.display;

import model.Experiment;
import model.GenericDataFile;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

//abstract component of data file display contianing information and information for displaypanel
public abstract class DisplayPanel extends JPanel {
    private static int PANE_X; //height of this
    private static int PANE_Y;  //width of this

    Experiment experiment;      //experiment containing data file
    GenericDataFile dataFile;   //data file to display

    DataFileDisplay parent;     //parent component which this is added to

    JPanel experimentPanel = new JPanel();  //component of this contianing information about experiment
    JPanel dataPanel = new JPanel();        //component of this containing info about data file
    JPanel visualPanel = new JPanel();      //componenet of this containing tabulated info about data file
    JLabel experimentName;                  //name of experiment to display
    JLabel name;                            //name of data file to display



    //MODIFIES: this
    //EFFECTS:  instantiates new DisplayPanel object. Sets up panel.
    public DisplayPanel(Experiment experiment, GenericDataFile dataFile, DataFileDisplay parent) {
        new JPanel();
        this.parent = parent;
        PANE_X = parent.getPaneX();
        PANE_Y = parent.getPaneY() / 2;
        setLayout(new BoxLayout(this, 1));
        setMinimumSize(new Dimension(PANE_X, PANE_Y));

        this.experiment = experiment;
        this.dataFile = dataFile;
        clearPanels();
        setUpPanels();
        setUpUnModifiableElements();
    }

    //modifies: this
    //effects: removes all panels from display panel
    protected void clearPanels() {
        remove(experimentPanel);
        remove(dataPanel);
        remove(visualPanel);
    }

    //MODIFIES: this
    //EFFECTS:  creates and adds new panels to display panel
    protected void setUpPanels() {
        experimentPanel = new JPanel();
        experimentPanel.setLayout(new BorderLayout());
        experimentPanel.setMaximumSize(new Dimension(PANE_X, PANE_Y / 2));
        experimentPanel.setBackground(Color.lightGray);
        dataPanel = new JPanel();
        dataPanel.setLayout(new BorderLayout());
        dataPanel.setMaximumSize(new Dimension(PANE_X, PANE_Y / 2));
    }

    //MODIFIES: this
    //EFFECTS:  creates and adds new labels that are not modifiable by the user. Includes names of experiment and
    //data file
    private void setUpUnModifiableElements() {
        experimentName = new JLabel("Experiment: " + experiment.getName());
        name = new JLabel("Name: " + dataFile.getName());
    }

    //modifies: this
    //changes information about data file to reflect most current state of information
    public abstract void refreshDataPanel();

    //modifies: this
    //effects: saves any changes to the data panel to experiment directory.
    public abstract void saveModifiedInformation();
}

package ui.display;

import model.Experiment;
import model.GenericDataFile;

import javax.swing.*;
import java.awt.*;

//class of display panel that does not allow user interaction
public class UnModifiableDisplayPanel extends DisplayPanel {
    JLabel experimentDescription; //description of experiment displayed
    JLabel description;           //description of data file displayed
    JLabel data;                  //data in data file being displayed

    //MODIFIES: this
    //EFFECTS:  instantiates object, creates and adds new panels to display panel. Sets up un-modifiable elements.
    public UnModifiableDisplayPanel(Experiment experiment, GenericDataFile dataFile, DataFileDisplay parent) {
        super(experiment, dataFile, parent);
        setUpUnModifiableInformation();
        addPanels(false);
    }

    //modifies: this
    //effects: adds panels to display panel
    protected void addPanels(Boolean argument) {
        experimentPanel.add(BorderLayout.NORTH,experimentName);
        experimentPanel.add(BorderLayout.CENTER,experimentDescription);
        dataPanel.add(BorderLayout.NORTH,name);
        dataPanel.add(BorderLayout.CENTER,description);
        dataPanel.add(BorderLayout.SOUTH,data);
        add(experimentPanel);
        add(dataPanel);
    }

    //modifies: this
    //effects: changes information about data file to reflect most current state of information
    public void refreshDataPanel() {
        dataPanel.remove(description);
        dataPanel.remove(data);
        description = new JLabel("Description: " + dataFile.getDescription());
        data = new JLabel("Data: " + dataFile.getData());
        dataPanel.add(BorderLayout.CENTER, description);
        dataPanel.add(BorderLayout.SOUTH, data);
        parent.mainInterface.validate();
    }

    //modifies: this
    //effects: creates and adds unmodifiable elements to display
    private void setUpUnModifiableInformation() {
        experimentDescription = new JLabel("Description: " + experiment.getDescription());
        description = new JLabel("Description: " + dataFile.getDescription());
        data = new JLabel("Data: " + dataFile.getData());
    }

    //modifies: this
    //effects: saves any modified information in the data panel to experiment directory.
    public void saveModifiedInformation() {
        if (experimentDescription != null && description != null && data != null) {
            experiment.setDescription(experimentDescription.getText());
            experimentDescription = null;
            dataFile.setDescription(description.getText());
            description = null;
            dataFile.setData(data.getText());
            data = null;
        }
    }
}

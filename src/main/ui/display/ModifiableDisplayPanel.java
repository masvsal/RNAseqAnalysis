package ui.display;

import model.Experiment;
import model.GenericDataFile;
import model.logging.Event;
import model.logging.EventLog;
import javax.swing.*;
import java.awt.*;

//class of display panel that allows user interaction to modify data.
public class ModifiableDisplayPanel extends DisplayPanel {
    JTextField experimentDescription; //description of experiment displayed
    JTextField description;            //description of data file displayed
    JTextField data;                   //data in data file being displayed


    //MODIFIES: this
    //EFFECTS:  creates and adds new panels to display panel. Sets up modifiable elements.
    public ModifiableDisplayPanel(Experiment experiment, GenericDataFile dataFile, DataFileDisplay parent) {
        super(experiment, dataFile, parent);
        setUpModifiableInformation();
        addPanels(true);
    }

    //modifies: this
    //effects: changes information about data file to reflect most current state of information
    public void refreshDataPanel() {
        dataPanel.remove(description);
        dataPanel.remove(data);
        description = new JTextField("Description: " + dataFile.getDescription());
        data = new JTextField("Data: " + dataFile.getData());
        dataPanel.add(BorderLayout.CENTER, description);
        dataPanel.add(BorderLayout.SOUTH, data);
        parent.mainInterface.validate();
    }


    //modifies: this
    //effects: creates and adds panels to display
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
    //effects: creates and adds modifiable elements to panels
    private void setUpModifiableInformation() {
        experimentDescription = new JTextField(experiment.getDescription());
        description = new JTextField(dataFile.getDescription());
        data = new JTextField(dataFile.getData());
    }

    //modifies: this
    //effects: saves any changes to the data panel to experiment directory.
    public void saveModifiedInformation() {
        if (experimentDescription != null && description != null && data != null) {
            experiment.setDescription(experimentDescription.getText());
            EventLog.getInstance().logEvent(
                    new Event("EXPERIMENT: " + experiment.getName() + "|" + "DESCRIPTION MODIFIED: "
                            + experimentDescription.getText()));
            experimentDescription = null;
            dataFile.setDescription(description.getText());
            EventLog.getInstance().logEvent(
                    new Event("DATA FILE: " + dataFile.getName() + "|" + "DESCRIPTION MODIFIED: "
                            + description.getText()));
            description = null;
            dataFile.setData(data.getText());
            EventLog.getInstance().logEvent(
                    new Event("DATA FILE: " + dataFile.getName() + "|" + "DATA MODIFIED: "
                            + data.getText()));
            data = null;
        }
    }
}

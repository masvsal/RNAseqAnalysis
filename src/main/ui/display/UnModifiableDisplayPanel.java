package ui.display;

import model.Experiment;
import model.GenericDataFile;

import javax.swing.*;
import java.awt.*;

public class UnModifiableDisplayPanel extends DisplayPanel {
    JLabel experimentDescription;
    JLabel description;
    JLabel data;

    public UnModifiableDisplayPanel(Experiment experiment, GenericDataFile dataFile, DataFileDisplay parent) {
        super(experiment, dataFile, parent);
        setUpUnModifiableInformation();
        addPanels(false);
    }

    protected void addPanels(Boolean argument) {
        experimentPanel.add(BorderLayout.NORTH,experimentName);
        experimentPanel.add(BorderLayout.CENTER,experimentDescription);
        dataPanel.add(BorderLayout.NORTH,name);
        dataPanel.add(BorderLayout.CENTER,description);
        dataPanel.add(BorderLayout.SOUTH,data);
        add(experimentPanel);
        add(dataPanel);
    }

    public void refreshDataPanel() {
        dataPanel.remove(description);
        dataPanel.remove(data);
        description = new JLabel("Description: " + dataFile.getDescription());
        data = new JLabel("Data: " + dataFile.getData());
        dataPanel.add(BorderLayout.CENTER, description);
        dataPanel.add(BorderLayout.SOUTH, data);
        parent.mainInterface.validate();
    }

    private void setUpUnModifiableInformation() {
        experimentDescription = new JLabel("Description: " + experiment.getDescription());
        description = new JLabel("Description: " + dataFile.getDescription());
        data = new JLabel("Data: " + dataFile.getData());
    }

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

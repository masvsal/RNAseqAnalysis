package ui.display;

import model.Experiment;
import model.GenericDataFile;

import javax.swing.*;
import java.awt.*;

public class ModifiableDisplayPanel extends DisplayPanel {
    JTextField experimentDescription;
    JTextField description;
    JTextField data;

    public ModifiableDisplayPanel(Experiment experiment, GenericDataFile dataFile, DataFileDisplay parent) {
        super(experiment, dataFile, parent);
        setUpModifiableInformation();
        addPanels(true);
    }

    public void refreshDataPanel() {
        dataPanel.remove(description);
        dataPanel.remove(data);
        description = new JTextField("Description: " + dataFile.getDescription());
        data = new JTextField("Data: " + dataFile.getData());
        dataPanel.add(BorderLayout.CENTER, description);
        dataPanel.add(BorderLayout.SOUTH, data);
        parent.mainInterface.validate();
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

    private void setUpModifiableInformation() {
        experimentDescription = new JTextField(experiment.getDescription());
        description = new JTextField(dataFile.getDescription());
        data = new JTextField(dataFile.getData());
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

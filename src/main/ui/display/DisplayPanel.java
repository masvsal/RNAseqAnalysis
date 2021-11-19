package ui.display;

import model.Experiment;
import model.GenericDataFile;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

public abstract class DisplayPanel extends JPanel {
    Experiment experiment;
    GenericDataFile dataFile;

    DataFileDisplay parent;

    JPanel experimentPanel = new JPanel();
    JPanel dataPanel = new JPanel();
    JPanel visualPanel = new JPanel();
    JLabel experimentName;
    JLabel name;

    private static int PANE_X;
    private static int PANE_Y;

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

    protected void clearPanels() {
        remove(experimentPanel);
        remove(dataPanel);
        remove(visualPanel);
    }

    protected void setUpPanels() {
        experimentPanel = new JPanel();
        experimentPanel.setLayout(new BorderLayout());
        experimentPanel.setMaximumSize(new Dimension(PANE_X, PANE_Y / 2));
        experimentPanel.setBackground(Color.lightGray);
        dataPanel = new JPanel();
        dataPanel.setLayout(new BorderLayout());
        dataPanel.setMaximumSize(new Dimension(PANE_X, PANE_Y / 2));
    }

    private void setUpUnModifiableElements() {
        experimentName = new JLabel("Experiment: " + experiment.getName());
        name = new JLabel("Name: " + dataFile.getName());
    }

    public abstract void refreshDataPanel();

    public abstract void saveModifiedInformation();
}

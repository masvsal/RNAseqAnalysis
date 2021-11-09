package ui;

import model.ExperimentDirectory;
import ui.display.DataFileDisplay;
import ui.menus.DataFileDirectoryMenu;
import ui.menus.ExperimentDirectoryMenu;
import ui.tools.ToolBar;

import javax.swing.*;
import java.awt.*;

public class MainInterface extends JFrame {
    public static final int WIDTH = 700;
    public static final int HEIGHT = 500;



    DataFileDirectoryMenu dataFileDirectoryMenu;
    ExperimentDirectoryMenu experimentDirectoryMenu;
    DataFileDisplay display;
    ToolBar toolBar;
    ExperimentDirectory experimentDirectory;

    public MainInterface(ExperimentDirectory experimentDirectory) {
        super("Main Interface");
        this.experimentDirectory = experimentDirectory;
        configure();
        initializeDisplays(experimentDirectory);
    }

    public DataFileDirectoryMenu getDataFileDirectoryMenu() {
        return dataFileDirectoryMenu;
    }

    public ExperimentDirectoryMenu getExperimentDirectoryMenu() {
        return experimentDirectoryMenu;
    }

    public ToolBar getToolBar() {
        return this.toolBar;
    }

    private void configure() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setMinimumSize(new Dimension(WIDTH, HEIGHT));
        setVisible(true);
    }

    //MODIFIES: this
    //EFFECTS: draws the JFrame window where the application operates
    private void initializeDisplays(ExperimentDirectory experimentDirectory) {
        display = new DataFileDisplay(this);
        dataFileDirectoryMenu = new DataFileDirectoryMenu(this, display);
        experimentDirectoryMenu = new ExperimentDirectoryMenu(dataFileDirectoryMenu, this);
        toolBar = new ToolBar(experimentDirectory, this);

        add(BorderLayout.NORTH, toolBar);
        add(BorderLayout.WEST, experimentDirectoryMenu);
        add(BorderLayout.CENTER, dataFileDirectoryMenu);
        add(BorderLayout.EAST, display);
        validate();
        pack();
    }

    public ExperimentDirectory getExperimentDirectory() {
        return this.experimentDirectory;
    }

}



package ui;

import model.ExperimentDirectory;
import ui.display.DataFileDisplay;
import ui.menus.DataFileDirectoryMenu;
import ui.menus.ExperimentDirectoryMenu;
import ui.tools.ToolBar;

import javax.swing.*;
import java.awt.*;

//Main interface with which the user interacts. Persists throughout life of app.
public class MainInterface extends JFrame {
    public static final int WIDTH = 1000;   //width of interface
    public static final int HEIGHT = 700;   //height of interface

    //components in main interface
    ExperimentDirectoryMenu experimentDirectoryMenu;    //menu that displays experiments in experiment directory
    DataFileDirectoryMenu dataFileDirectoryMenu;        //menu that displays data files in currently selected experiment
    DataFileDisplay display;                            //display that displays data in currently selected file
    ToolBar toolBar;                                    //panel that contains all tools with which user interacts.

    ExperimentDirectory experimentDirectory;            //data object that is modified by components of main interface

    //MODIFIES: this
    //EFFECTS: instantiates new main interface object.
    public MainInterface(ExperimentDirectory experimentDirectory) {
        super("Main Interface");
        this.experimentDirectory = experimentDirectory;
        configure();
        initializeDisplays(experimentDirectory);
    }

    //getters. Returns height or width of interface, data directory menu, experiment directory menu, or toolbar
    public int getHeight() {
        return HEIGHT;
    }

    public int getWidth() {
        return WIDTH;
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

    public ExperimentDirectory getExperimentDirectory() {
        return this.experimentDirectory;
    }

    //MODIFIES: this
    //EFFECTS: sets behavior of main interface frame

    private void configure() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setMinimumSize(new Dimension(WIDTH, HEIGHT));
        setVisible(true);
    }

    //MODIFIES: this
    //EFFECTS: creates and adds main components of main interface to frame
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
}



package ui;

import model.ExperimentDirectory;
import ui.display.DataFileDisplay;
import ui.menus.DataFileDirectoryMenu;
import ui.menus.ExperimentDirectoryMenu;
import model.logging.*;
import ui.tools.ToolBar;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

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

    //getters.
    //effects: returns height

    public int getHeight() {
        return HEIGHT;
    }

    //effects: returns width

    public int getWidth() {
        return WIDTH;
    }

    //effects: returns dataFileDirectoryMenu

    public DataFileDirectoryMenu getDataFileDirectoryMenu() {
        return dataFileDirectoryMenu;
    }

    //effects: returns ExperimentDirectoryMenu

    public ExperimentDirectoryMenu getExperimentDirectoryMenu() {
        return experimentDirectoryMenu;
    }

    //effects: returns ToolBar

    public ToolBar getToolBar() {
        return this.toolBar;
    }

    //effects: returns ExperimentDirectory

    public ExperimentDirectory getExperimentDirectory() {
        return this.experimentDirectory;
    }

    //MODIFIES: this
    //EFFECTS: sets behavior of main interface frame

    private void configure() {
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setLayout(new BorderLayout());
        setMinimumSize(new Dimension(WIDTH, HEIGHT));
        setVisible(true);
        setCloseBehavior();
    }


    //MODIFIES: this
    //effects: sets default close behavior

    @SuppressWarnings({"checkstyle:MethodLength", "checkstyle:SuppressWarnings"})
    public void setCloseBehavior() {
        addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {

            }

            @Override
            public void windowClosing(WindowEvent e) {
                configureClosingOperation();
            }

            @Override
            public void windowClosed(WindowEvent e) {
            }

            @Override
            public void windowIconified(WindowEvent e) {
            }

            @Override
            public void windowDeiconified(WindowEvent e) {
            }

            @Override
            public void windowActivated(WindowEvent e) {
            }

            @Override
            public void windowDeactivated(WindowEvent e) {

            }
        });
    }

    private void configureClosingOperation() {
        ScreenPrinter printer = new ScreenPrinter(EventLog.getInstance());
        printer.printLogToConsole();
        System.exit(0);
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



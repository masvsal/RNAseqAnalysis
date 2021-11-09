package ui.tools;

import model.ExperimentDirectory;
import ui.MainInterface;
import ui.menus.DataFileDirectoryMenu;
import ui.menus.ExperimentDirectoryMenu;
import ui.menus.Menu;

import javax.swing.*;
import java.awt.*;

public class ToolBar extends JPanel {
    MainInterface parent;
    private static int PANE_X = MainInterface.WIDTH;
    private static int PANE_Y = Tool.HEIGHT;
    AddTool addTool;
    DeleteTool deleteTool;
    ModifyTool modifyTool;
    SaveTool saveTool;
    ConfirmTool confirmTool;
    DataFileDirectoryMenu dataFileDirectoryMenu;
    ExperimentDirectoryMenu experimentDirectoryMenu;
    Color color = Color.GRAY;



    Menu activeList;

    public ToolBar(ExperimentDirectory experimentDirectory, MainInterface parent) {
        new JPanel();
        setBackground(color);
        setLayout(new BorderLayout());
        setSize(new Dimension(PANE_X, PANE_Y));

        this.parent = parent;
        this.dataFileDirectoryMenu = parent.getDataFileDirectoryMenu();
        this.experimentDirectoryMenu = parent.getExperimentDirectoryMenu();

        initializeTools(experimentDirectory);
    }

    public void setActiveList(Menu activeList) {
        this.activeList = activeList;
    }

    public Menu getActiveList() {
        return this.activeList;
    }

    public ExperimentDirectoryMenu getExperimentDirectoryMenu() {
        return experimentDirectoryMenu;
    }

    public DataFileDirectoryMenu getDataFileDirectoryMenu() {
        return dataFileDirectoryMenu;
    }

    public MainInterface getMainInterface() {
        return parent;
    }

    public void initializeTools(ExperimentDirectory experimentDirectory) {
        JPanel listPanel = new JPanel();
        JPanel displayPanel = new JPanel();
        listPanel.setBackground(color);
        displayPanel.setBackground(color);
        addTool = new AddTool(this);
        deleteTool = new DeleteTool(this);
        modifyTool = new ModifyTool(this);
        saveTool = new SaveTool(this);
        confirmTool = new ConfirmTool(this, modifyTool);
        confirmTool.setVisibility(false);

        saveTool.addToParent(listPanel);
        addTool.addToParent(listPanel);
        deleteTool.addToParent(listPanel);

        modifyTool.addToParent(displayPanel);
        confirmTool.addToParent(displayPanel);

        add(BorderLayout.WEST, listPanel);
        add(BorderLayout.EAST, displayPanel);
    }

    public void showConfirmTool(Boolean operator) {
        confirmTool.setVisibility(operator);
    }

}

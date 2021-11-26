package ui.tools;

import model.ExperimentDirectory;
import ui.MainInterface;
import ui.menus.DataFileDirectoryMenu;
import ui.menus.ExperimentDirectoryMenu;
import ui.menus.Menu;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

//component of main interface that contains and managess user-interactable tools
public class ToolBar extends JPanel {
    //Parameters
    private static int PANE_X = MainInterface.WIDTH;    //width of tool base, same as width of main interface
    private static int PANE_Y = Tool.HEIGHT;            //height of main interface, same as height of tool object

    //components of MainInterface modified by toolBar
    private MainInterface parent;                       //maininterface object that instantiated the ToolBar object
    private DataFileDirectoryMenu dataFileDirectoryMenu;

    //Tools modifying Menu components
    private AddTool addTool;
    private DeleteTool deleteTool;
    private SaveTool saveTool;

    //Tools modifying Display component
    private ModifyTool modifyTool;
    private ConfirmTool confirmTool;
    private CountSigGeneExpressionTool countSigGeneExpressionTool;
    private GraphTool graphTool;


    private Color color = Color.GRAY;   //color of toolbar

    Menu activeList;    //references the menu that has been selected most recently

    public ToolBar(ExperimentDirectory experimentDirectory, MainInterface parent) {
        new JPanel();
        setBackground(color);
        setLayout(new BorderLayout());
        setSize(new Dimension(PANE_X, PANE_Y));

        this.parent = parent;
        this.dataFileDirectoryMenu = parent.getDataFileDirectoryMenu();

        initializeTools(experimentDirectory);
    }

    //getters
    public Menu getActiveList() {
        return this.activeList;
    }

    public DataFileDirectoryMenu getDataFileDirectoryMenu() {
        return dataFileDirectoryMenu;
    }

    public MainInterface getMainInterface() {
        return parent;
    }

    //setters
    public void setActiveList(Menu activeList) {
        this.activeList = activeList;
    }



    //modify: this
    //Effects: creates and adds all tools to either listPanel or displayPanel in Toolbar
    // sets up bi-directional relationship between new tools and this toolbar
    public void initializeTools(ExperimentDirectory experimentDirectory) {
        JPanel listPanel = new JPanel();
        JPanel displayPanel = new JPanel();
        listPanel.setBackground(color);
        displayPanel.setBackground(color);

        addTool = new AddTool(this);
        deleteTool = new DeleteTool(this);
        saveTool = new SaveTool(this);

        modifyTool = new ModifyTool(this);
        confirmTool = new ConfirmTool(this, modifyTool);
        confirmTool.setVisibility(false);
        countSigGeneExpressionTool = new CountSigGeneExpressionTool(this);
        countSigGeneExpressionTool.setVisibility(false);
        graphTool = new GraphTool(this);
        graphTool.setVisibility(false);

        saveTool.addToParent(listPanel);
        addTool.addToParent(listPanel);
        deleteTool.addToParent(listPanel);

        modifyTool.addToParent(displayPanel);
        confirmTool.addToParent(displayPanel);
        countSigGeneExpressionTool.addToParent(displayPanel);
        graphTool.addToParent(displayPanel);

        add(BorderLayout.WEST, listPanel);
        add(BorderLayout.EAST, displayPanel);
    }

    //functions that control the visibility of tools

    //EFFECTS: sets visibility of confirm tool to given operator and visibility of modify tool to opposite of operator
    public void showConfirmTool(Boolean operator) {
        confirmTool.setVisibility(operator);
        modifyTool.setVisibility((!operator));
    }

    //sets visibility of any tools associated with RNAseq data files to given operator
    public void showRNAseqTools(Boolean operator) {
        countSigGeneExpressionTool.setVisibility(operator);
        graphTool.setVisibility(operator);
    }

}

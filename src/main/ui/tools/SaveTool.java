package ui.tools;

import model.ExperimentDirectory;
import persistence.JsonWriter;
import ui.MainInterface;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

//tool used to save current state of application. ListTool.
public class SaveTool extends Tool implements ActionListener {
    ExperimentDirectory experimentDirectory;   //current directory to be saved to disk

    //MODIFIES: this
    //EFFECTS: instantiates new saveTool object
    public SaveTool(ToolBar toolBar) {
        super(toolBar, "S");
        button.addActionListener(this);
    }

    @Override
    //EFFECTS: saves experiment directory field to file on press of SaveTool.
    public void actionPerformed(ActionEvent e) {
        MainInterface mainInterface = toolBar.getMainInterface();
        experimentDirectory = mainInterface.getExperimentDirectory();
        saveFile();
    }

    //MODIFIES: this
    //EFFECT: saves experiment directory to file.
    private void saveFile() {
        try {
            JsonWriter writer = new JsonWriter("data/Persistence/ExperimentDirectory.json");
            writer.open();
            writer.write(experimentDirectory);
            writer.close();
            System.out.println("File succesfully saved!");
        } catch (IOException e) {
            System.out.println("Error: Json file not found");
        }
    }
}

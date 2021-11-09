package ui.tools;

import model.ExperimentDirectory;
import persistence.JsonWriter;
import ui.MainInterface;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class SaveTool extends Tool implements ActionListener {
    ExperimentDirectory currentDirectory;

    public SaveTool(ToolBar toolBar) {
        super(toolBar, "S");
        button.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        MainInterface mainInterface = toolBar.getMainInterface();
        currentDirectory = mainInterface.getExperimentDirectory();
        saveFile();
    }

    //EFFECT: saves experiment directory to file
    private void saveFile() {
        try {
            JsonWriter writer = new JsonWriter("data/Persistence/ExperimentDirectory.json");
            writer.open();
            writer.write(currentDirectory);
            writer.close();
            System.out.println("File succesfully saved!");
        } catch (IOException e) {
            System.out.println("Error: Json file not found");
        }
    }
}

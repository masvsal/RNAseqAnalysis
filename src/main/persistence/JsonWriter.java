package persistence;

import model.ExperimentDirectory;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class JsonWriter {
    private static final int TAB = 4; //indentation factor when turning json object to string
    private PrintWriter writer; //stores writer object
    private String destination; //relative path top destination file


    //EFFECTS: instantiates writer to write data to destination file
    public  JsonWriter(String destination) {
        this.destination = destination;

    }

    // MODIFIES: this
    // EFFECTS:  opens writer object; throws FileNotFoundException if destination file can't be opened
    public void open() throws FileNotFoundException {
        writer = new PrintWriter(destination);
    }


    //MODIFIES: this
    //EFFECTS: writes JSON representation of experiment directory to file
    // including all contained experiments and data files
    //TODO: string created from toJson then writes string to file
    public void write(ExperimentDirectory experimentDirectory) {
        JSONObject jsonObject = experimentDirectory.toJson();
        saveToFile(jsonObject.toString(TAB));
    }

    // MODIFIES: this
    // EFFECTS:  closes writer object
    public void close() {
        writer.close();
    }

    // MODIFIES: this
    // EFFECTS: writes string to file.
    private void saveToFile(String json) {
        writer.print(json);
    }




}

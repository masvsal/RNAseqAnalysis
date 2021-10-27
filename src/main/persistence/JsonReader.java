package persistence;


//import com.oracle.javafx.jmx.json.JSONReader;
import model.Experiment;
import model.ExperimentDirectory;
import model.GenericDataFile;
import model.RnaSeqDataFile;
import netscape.javascript.JSObject;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

// Represents a reader that reads experiment directory from JSON data stored in file
public class JsonReader {
    private String source; //path to Json file containing experiment directory information

    //REQUIRES: must point to valid Json file, source path must be relative
    //EFFECT: instantiates new Json reader pointing to a Json file using given source path
    public JsonReader(String source) {
        this.source = source;
    }

    //EFFECT: reads Experiment directory from Json file and returns it.
    //throw IO exception if error ocurs during reading file
    public ExperimentDirectory read() throws IOException {
       String jsonData = readFile(source);
       JSONObject jsonObject = new JSONObject(jsonData);
       return parseExperimentDirectory(jsonObject);
    }

    //EFFECT: returns source json file as string and returns it
    //throws IO exception if reading file goes wrong
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(contentBuilder::append);
        }

        return contentBuilder.toString();
    }

    //EFFECT:  parses experiment directory from Json object and returns it
    private ExperimentDirectory parseExperimentDirectory(JSONObject jsObject) {
        ExperimentDirectory experimentDirectory = new ExperimentDirectory();
        addExperiments(experimentDirectory, jsObject);
        return experimentDirectory;
    }
    //MODIFIES: experiment directory
    //EFFECT: parses experiments from Json Object and adds them to the experiment directory

    private void addExperiments(ExperimentDirectory experimentDirectory, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("listOfExperiments");
        for (Object json : jsonArray) {
            JSONObject nextExperiment = (JSONObject) json;
            addExperiment(experimentDirectory, nextExperiment);
        }
    }

    //MODIFIES: experiment directory
    //EFFECT: parses single experiment from Json Object and adds it to the experiment directory
    private void addExperiment(ExperimentDirectory experimentDirectory, JSONObject jsonObject2) {
        String name = jsonObject2.getString("name");
        Experiment experiment = new Experiment(name);

        String description = jsonObject2.getString("description");
        experiment.setDescription(description);

        addDataFiles(experiment, jsonObject2);
        experimentDirectory.addFile(experiment);
    }

    //EFFECT: parses data files from Json Object and adds them to the experiment directory
    private void addDataFiles(Experiment experiment, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("listOfFiles");

        for (Object json : jsonArray) {
            JSONObject nextDataFile = (JSONObject) json;
            addDataFile(experiment, nextDataFile);
        }
    }

    //EFFECT: parses single data file from Json Object and adds it to the experiment directory
    private void addDataFile(Experiment experiment, JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        String data = jsonObject.getString("data");
        String description = jsonObject.getString("description");

        try {
            String path = jsonObject.getString("path");
            RnaSeqDataFile dataFile = new RnaSeqDataFile(name, data, path);
            dataFile.setDescription(description);
            experiment.addFile(dataFile);
        } catch (JSONException e) {
            GenericDataFile dataFile = new GenericDataFile(name, data);
            dataFile.setDescription(description);
            experiment.addFile(dataFile);
        }
    }

}

package model;

import model.interfaces.Directory;
import model.interfaces.NamedFile;
import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.LinkedList;
import java.util.Objects;

//represents an experiment conducted by a scientist having a name, description, and containing an arbitrary number of
// data files
public class Experiment implements NamedFile, Directory, Writable {

    private String name;                                    //name of Experiment.
    private String description;                             //description of Experiment.
    private LinkedList<GenericDataFile> listOfDataFiles;    //list of all the data files contained associated
                                                            // with experiment.



    //EFFECT: instantiates a new experiment with the given name and an empty list of data files.
    public Experiment(String name) {
        this.name = name;
        this.listOfDataFiles = new LinkedList<>();
        this.description = "";
    }

    @Override
    public String getName() {
        return this.name;
    }

    //EFFECT: returns description of experiment. If description is NULL, returns "no description"
    public String getDescription() {
        if (Objects.equals(this.description, "")) {
            return "no description";
        } else {
            return this.description;
        }
    }

    @Override
    public void setDescription(String description) {
        this.description = description;
    }


    //MODIFIES: this
    //EFFECT: adds a new files to the end (position = length of directory + 1) of the directory's list of files
    public void addFile(GenericDataFile file) {
        listOfDataFiles.add(file);
    }

    //REQUIRES: >= 1 file in directory's list of files and given position <= length of directory
    //EFFECT: returns file with matching position, index begins at 1
    public GenericDataFile getFile(Integer position) {
        return this.listOfDataFiles.get(position - 1);
    }

    //EFFECT: returns the number of files in the directory
    public Integer length() {
        return this.listOfDataFiles.size();
    }

    //REQUIRES: >= 1 file in directory's list of files and given position <= length of directory
    //EFFECT: removes the data file matching the given position from the directory's list of files, index begins at 1
    public void removeFile(Integer position) {
        this.listOfDataFiles.remove(position - 1);
    }


    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("description", description);
        json.put("listOfFiles", dataFilesToJson());
        return json;
    }

    // EFFECTS: returns data files assigned to this experiment as a Json array
    private JSONArray dataFilesToJson() {
        JSONArray jsonArray = new JSONArray();

        for (GenericDataFile d : listOfDataFiles) {
            jsonArray.put(d.toJson());
        }

        return jsonArray;
    }
}

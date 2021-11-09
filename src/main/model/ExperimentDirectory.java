package model;

import model.interfaces.Directory;
import model.interfaces.NamedFile;
import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.LinkedList;

//represents a directory of all the Experiments in system.

public class ExperimentDirectory implements Directory, Writable {
    private LinkedList<Experiment> listofExperiments;          //experiments contained within the experiment directory

    //EFFECT: instantiates new instance of the Experiment Directory class with an empty list of experiments
    //EFFECT: instantiates a new directory object with an empty list of files
    public ExperimentDirectory() {
        this.listofExperiments = new LinkedList<>();
    }


    //EFFECTS: returns list field
    public LinkedList<Experiment> getListofExperiments() {
        return listofExperiments;
    }

    //MODIFIES: this
    //EFFECT: adds a new files to the end (position = length of directory + 1) of the directory's list of files
    public void addFile(Experiment file) {
        listofExperiments.add(file);
    }


    //REQUIRES: >= 1 file in directory's list of files and given position <= length of directory
    //EFFECT: returns file with matching position, index begins at 1
    @Override
    public Experiment getFile(Integer position) {
        return this.listofExperiments.get(position - 1);
    }


    //EFFECT: returns the number of files in the directory
    @Override
    public Integer length() {
        return this.listofExperiments.size();
    }

    //REQUIRES: >= 1 file in directory's list of files and given position <= length of directory
    //EFFECT: removes the data file matching the given position from the directory's list of files, index begins at 1
    @Override
    public void removeFile(Integer position) {
        this.listofExperiments.remove(position - 1);
    }


    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("listOfExperiments", experimentsToJson());
        return json;
    }

    //EFFECTS: returns experiments in experiment directory as a JSON array
    private JSONArray experimentsToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Experiment e: listofExperiments) {
            jsonArray.put(e.toJson());
        }

        return jsonArray;
    }

}

package model;

import java.util.LinkedList;

public class Experiment {

    private String description;
    private String name;
    private LinkedList<DataFile> listOfDataFiles;

    //contains a number of data files

    public Experiment(String name) {
        this.name = name;
        this.listOfDataFiles = new LinkedList<>();
    }

    public void giveDescription(String sentence) {
        this.description = sentence;

    }

    public void addFile(DataFile file) {
        this.listOfDataFiles.add(file);
    }

    public DataFile getFile() {
        return null;
    }

    public String getName() {
        return null;
    }

    public String getDescription() {
        return null;
    }




}

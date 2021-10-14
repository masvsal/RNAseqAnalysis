package model;

import model.interfaces.Directory;
import model.interfaces.NamedFile;

import java.util.LinkedList;

//represents an experiment conducted by a scientist having a name, description, and containing an arbitrary number of
// data files
public class Experiment implements NamedFile, Directory {

    private String name;                                    //name of Experiment.
    private String description;                             //description of Experiment.
    private LinkedList<GenericDataFile> listOfDataFiles;    //list of all the data files contained associated
                                                            // with experiment.



    //EFFECT: instantiates a new experiment with the given name and an empty list of data files.
    public Experiment(String name) {
        this.name = name;
        this.listOfDataFiles = new LinkedList<>();
    }

    @Override
    public String getName() {
        return this.name;
    }

    //EFFECT: returns description of experiment. If description is NULL, returns "no description"
    public String getDescription() {
        if (this.description == null) {
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


}

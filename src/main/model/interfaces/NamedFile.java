package model.interfaces;
//represents all files that have a name and a description which can be referenced in a directory
//examples: experiment, genericdatafile, RNAseqDataFile

public interface NamedFile {

    //EFFECT: returns the name of a named file
    String getName();

    //EFFECT: returns description of file
    String getDescription();

    //MODIFIES: this
    //EFFECT: Sets the description field of named file to the given description
    void setDescription(String description);

}

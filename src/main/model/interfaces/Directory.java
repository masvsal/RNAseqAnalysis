package model.interfaces;

//represents a generic directory having a list of named files.
public interface Directory { //if this is never being instantiated, do I make it abstract?

    //REQUIRES: >= 1 file in directory's list of files and given position <= length of directory
    //EFFECT: returns file with matching position, index begins at 1
    NamedFile getFile(Integer position);

    //EFFECT: returns the number of files in the directory
    Integer length();

    //REQUIRES: >= 1 file in directory's list of files and given position <= length of directory
    //EFFECT: removes the data file matching the given position from the directory's list of files, index begins at 1
    void removeFile(Integer position);
}

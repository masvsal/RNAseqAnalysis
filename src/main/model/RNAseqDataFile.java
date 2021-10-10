package model;

import model.interfaces.NamedFile;

public class RNAseqDataFile extends GenericDataFile implements NamedFile {
    private String name;

    public RNAseqDataFile(String name, String data) {
        super(name, data);
    }
    //a subtype of the datafile class


    //REQUIRES: threshold > 0
    //EFFECT: returns new data file that contains the name and fold change of all the genes in the given file
    //that contain a fold change > than the given threshold
    public GenericDataFile significantChangesInGeneExpression(RNAseqDataFile file, int threshold) {
        return null;
    }
}

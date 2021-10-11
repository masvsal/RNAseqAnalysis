package model;

import model.interfaces.NamedFile;
import java.io.*;
import java.util.Scanner;

//a subtype of the datafile class

public class RNAseqDataFile extends GenericDataFile implements NamedFile {
    private String path;


    //REQUIRES: path must be not be static, must point to a .csv file with format:
    // row 1: symbol (String), row 2: Operon (String)
    // row 3: Challenge Condition RNA copy # (Integer), row 4: WT RNA copy # (Integer)
    //EFFECT: instantiates a new instance of RNAseqDataFile
    public RNAseqDataFile(String name, String data, String path) {
        super(name, data);
        this.path = path;
    }


    //EFFECT: returns path of given file
    public String getPath() {
        return this.path;
    }

    //EFFECT: returns path of given file
    public void setPath(String path) {
        this.path = path;
    }

    //REQUIRES: threshold > 0
    //EFFECT: returns the number of genes in the given file that contain a fold change >= than the given threshold.
    public Integer countSignificantChangesInGeneExpression(float threshold) {
        Integer counter = 0;
        try {
            Scanner sc = new Scanner(new File(this.path));
            sc.useDelimiter(",");
            while (sc.hasNext()) {
                String nextLine = sc.nextLine(); //grabs next line
                String[] arrayOfLine = nextLine.split(",", 4);
                counter = counter + isThresholdExceeded(arrayOfLine, threshold);
            }
            sc.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return counter;

    }


    //EFFECT: if difference between WT and Challenge copy # in .csv file are > threshold, return 1. Else, return 0.
    private Integer isThresholdExceeded(String[] arrayOfLine, Float threshold) {
        Float chalCondtnCopyNum = Float.parseFloat(arrayOfLine[2]);
        Float wildTypeCopyNum = Float.parseFloat(arrayOfLine[3]);
        Float foldChange = chalCondtnCopyNum / wildTypeCopyNum;
        if (foldChange < 1) {
            foldChange = 1 / foldChange;
        }
        if (foldChange >= threshold) {
            return 1;
        } else {
            return 0;
        }
    }


}

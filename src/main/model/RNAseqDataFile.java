package model;

import com.sun.org.apache.xpath.internal.operations.String;
import model.interfaces.NamedFile;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

import static java.lang.Math.abs;

//a subtype of the datafile class

public class RNAseqDataFile extends GenericDataFile implements NamedFile {
    private java.lang.String path;


    //REQUIRES: path must be not be static, must point to a .csv file with format:
    // row 1: symbol (String), row 2: Operon (String)
    // row 3: Challenge Condition RNA copy # (Integer), row 4: WT RNA copy # (Integer)
    //EFFECT: instantiates a new instance of RNAseqDataFile
    public RNAseqDataFile(java.lang.String name, java.lang.String data, java.lang.String path) {
        super(name, data);
        this.path = path;
    }


    //EFFECT: returns path of given file
    public java.lang.String getPath() {
        return this.path;
    }

    //EFFECT: sets path of given file
    public void setPath(java.lang.String path) {
        this.path = path;
    }

 /*   //MODIFIES: .csv file referenced in this object's path field
    //EFFECT: adds a row to .csv file using given gene name, operon, WT copy number, and Challenge copy number
    public void newRow(String name, String operon, Float wildTypeCopyNumber, Float challengeCopyNumber) {}*/

    //REQUIRES: threshold > 0
    //EFFECT: returns the number of genes in the given file that contain a fold change >= than the given threshold.
    public Integer countSigChangeExpression(float threshold) {
        int counter = 0;
        try {
            Scanner sc = new Scanner(new File(java.lang.String.valueOf(this.path)));
            sc.useDelimiter(",");
            java.lang.String headerline = sc.nextLine();
            while (sc.hasNext()) {
                java.lang.String nextLine = sc.nextLine(); //grabs next line
                java.lang.String[] arrayOfLine = nextLine.split(",", 4);
                if (abs(isThresholdExceeded(arrayOfLine, threshold)) > 0) {
                    counter = counter + 1;
                }
            }
            sc.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return counter;
    }

    //REQUIRES: threshold > 0, numOfGenes >= 0
    //EFFECT: returns a 2d array with the name and fold change of a given number of genes in the given file
    // with fold change >= to the given threshold. If numOfGenes == -1, return all genes with a significant change
    public ArrayList<ArrayList<java.lang.String>> getGeneNamesWithSigChangeExpression(float threshold, Integer numOfGenes) {
        ArrayList<ArrayList<java.lang.String>> geneNameAndFoldChange = new ArrayList<>();
        int counter = 0;
        try {
            Scanner sc = new Scanner(new File(java.lang.String.valueOf(this.path)));
            sc.useDelimiter(",");
            java.lang.String headerline = sc.nextLine();

            while (sc.hasNext() && ((counter < numOfGenes) || (numOfGenes == 0))) {

                java.lang.String nextLine = sc.nextLine(); //grabs next line
                java.lang.String[] arrayOfLine = nextLine.split(",", 4);
                Float foldchange = isThresholdExceeded(arrayOfLine, threshold);

                if (abs(foldchange) > (float) 1) {
                    ArrayList<java.lang.String> newArrayList = new ArrayList<>();
                    newArrayList.add(arrayOfLine[0]);

                    newArrayList.add(java.lang.String.valueOf(foldchange));
                    geneNameAndFoldChange.add(newArrayList);
                    counter = counter + 1;
                }
            }
            sc.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return geneNameAndFoldChange;
    }


    //EFFECT: if difference between WT and Challenge copy # in .csv file are > threshold, return 1. Else, return 0.
    private Float isThresholdExceeded(java.lang.String[] arrayOfLine, Float threshold) {
        float chalCondtnCopyNum = Float.parseFloat(arrayOfLine[2]);
        float wildTypeCopyNum = Float.parseFloat(arrayOfLine[3]);
        if (chalCondtnCopyNum == 0) {
            chalCondtnCopyNum = (float) 1;
        }
        if (wildTypeCopyNum == 0) {
            wildTypeCopyNum = (float) 1;
        }
        Float foldChange = calculateFoldChange(chalCondtnCopyNum, wildTypeCopyNum);
        if (abs(foldChange) >= threshold) {
            return foldChange;
        } else {
            return (float) 0;
        }
    }

    private Float calculateFoldChange(Float chalCondtnCopyNum, Float wildTypeCopyNum) {
        float foldChange = chalCondtnCopyNum / wildTypeCopyNum;
        if (foldChange < 1) {
            foldChange = -1 * (1 / foldChange);
        }
        return foldChange;
    }

}

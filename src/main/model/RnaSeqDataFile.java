package model;


import model.interfaces.NamedFile;
import org.json.JSONObject;

import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;
import java.util.stream.Collectors;

import static java.lang.Math.abs;
import static java.lang.Math.sqrt;

//a subtype of the datafile class. Stores a reference to, and interacts with, RNAseq output file.

public class RnaSeqDataFile extends GenericDataFile implements NamedFile {
    private java.lang.String path;          //relative path to RNAseq output file (.csv)
    static float Q = (float) 1;             //ratio of WT to Challenge Condition in RNAseq data


    //REQUIRES: relative path from content root->file pointing .csv file with the following format:
    //row 1: Header (not included in analysis)
    // Column 1: symbol (String), Column 2: Operon (String), column 3: Challenge Condition RNA copy # (Float)
    // column 4: WT RNA copy # (Float))
    //EFFECT: instantiates a new instance of RNAseqDataFile
    public RnaSeqDataFile(java.lang.String name, java.lang.String data, java.lang.String path) {
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

 /*   IGNORE:
    //MODIFIES: .csv file referenced in this object's path field
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
                Float foldChange = lowCountFilter(arrayOfLine, threshold);
                if (abs(foldChange) > 0) {
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
    //EFFECT: returns an array list containing n array lists, where n is the given number of genes
    // Each sub-list contains name & fold change of a gene in the RNAseq file with fold change >= to the given threshold
    //sub-lists are ordered by magnitude of fold change
    // If numOfGenes == -1, include all genes with fold change >= threshold.
    public ArrayList<ArrayList<java.lang.String>> getGeneNamesWithSigChangeExpression(float threshold,
                                                                                      Integer numOfGenes) {
        ArrayList<ArrayList<java.lang.String>> allSigNameAndFoldChange;
        ArrayList<ArrayList<java.lang.String>> subSigNameAndFoldChange = new ArrayList<>();

        if (numOfGenes != 0) {

            allSigNameAndFoldChange = getAllGenesWithSigChangeExpression(threshold);
            subSigNameAndFoldChange = allSigNameAndFoldChange;

            if (numOfGenes != -1 && numOfGenes < allSigNameAndFoldChange.size()) {
                subSigNameAndFoldChange =
                        new ArrayList<>(allSigNameAndFoldChange.subList(0, numOfGenes));
            }
        }

        subSigNameAndFoldChange = orderFoldChangesLargeToSmall(subSigNameAndFoldChange);

        return subSigNameAndFoldChange;
    }

    //EFFECT: returns ordered list of genes and fold changes, ordered by the magnitude of the fold change
    private ArrayList<ArrayList<java.lang.String>> orderFoldChangesLargeToSmall(
            ArrayList<ArrayList<java.lang.String>> list) {
        ArrayList<ArrayList<java.lang.String>> returnList = (ArrayList<ArrayList<String>>) list.stream()
                .sorted((g1,g2) -> {
                    if (abs(Double.parseDouble(g1.get(1))) > abs(Double.parseDouble(g2.get(1)))) {
                        return -1;
                    } else if ((abs(Double.parseDouble(g1.get(1))) == abs(Double.parseDouble(g2.get(1))))) {
                        return 0;
                    } else {
                        return 1;
                    }
                }).collect(Collectors.toList());
        return returnList;
    }

    //EFFECT: returns all the genes in csv RNAseq file referenced by file that have foldchange >= given threshold
    private ArrayList<ArrayList<java.lang.String>> getAllGenesWithSigChangeExpression(float threshold) {

        ArrayList<ArrayList<java.lang.String>> geneNameAndFoldChange = new ArrayList<>();

        try {
            Scanner sc = new Scanner(new File(java.lang.String.valueOf(this.path)));
            sc.useDelimiter(",");
            java.lang.String headerline = sc.nextLine();

            while (sc.hasNext()) {
                java.lang.String nextLine = sc.nextLine(); //grabs next line
                java.lang.String[] arrayOfLine = nextLine.split(",", 4); //splits line into array
                Float foldChange;

                //bias correction:

                //low count filtering:
                foldChange = lowCountFilter(arrayOfLine, threshold);

                if (abs(foldChange) > (float) 1) {
                    ArrayList<java.lang.String> newArrayList = new ArrayList<>();
                    newArrayList.add(arrayOfLine[0]);

                    newArrayList.add(java.lang.String.valueOf(foldChange));
                    geneNameAndFoldChange.add(newArrayList);
                }
            }
            sc.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return geneNameAndFoldChange;
    }

    //
//    //EFFECTS: corrects bias in a given gene based on gene length
//    private String[] biasCorrectorI(String[] arrayOfLine) {
//        String[] correctedArray = new String[0];
//
//        return correctedArray;
//    }

    //effect: returns result of wald test on given gene for some WT and Challenge condtn expression
    //TODO: make filter out non-sigificant differences between WT and Challenge
    private Float lowCountFilter(String[] arrayOfLine, Float threshold) {
        Float foldChange;
        if (Double.parseDouble(arrayOfLine[2]) <= 0.5 && Double.parseDouble(arrayOfLine[3]) <= 0.5) {
            foldChange = (float) 0;
        } else {
            foldChange = isThresholdExceeded(arrayOfLine, threshold); //gets fold change if exceeded
        }
        return foldChange;
    }

//    //effect: returns result of wald test on given gene for some WT and Challenge condtn expression
//    private Float waldTestFilter(String[] arrayOfLine, Float threshold) {
//        float z1;
//        float chalCondtnCopyNum = Float.parseFloat(arrayOfLine[2]);
//        float wildTypeCopyNum = Float.parseFloat(arrayOfLine[3]);
//        float denominator = (float) sqrt(chalCondtnCopyNum + wildTypeCopyNum * (Q * Q));
//        float numerator = chalCondtnCopyNum - wildTypeCopyNum * Q;
//        z1 = numerator / denominator;
//        return z1;
//    }

    //EFFECT: If fold change of given row >= threshold, return fold change. Else, return 0.
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

    //EFFECT: calculate the fold change between two given float values
    //fold change = (challenge condition copy number)/(wild type copy number).
    // If less than 1: negative reciprocal of fold change

    private Float calculateFoldChange(Float chalCondtnCopyNum, Float wildTypeCopyNum) {
        float foldChange = chalCondtnCopyNum / wildTypeCopyNum;
        if (foldChange < 1) {
            foldChange = -1 * (1 / foldChange);
        }
        return foldChange;
    }

    @Override
    public JSONObject toJson() {
        JSONObject jsonObject = new JSONObject();

        jsonObject.put("name", super.getName());
        jsonObject.put("description", super.getDescription());
        jsonObject.put("data", super.getData());

        jsonObject.put("path", path);

        return jsonObject;


    }

}

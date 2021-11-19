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
    private static double LOW_COUNT_THRESHOLD = 0.5; //Lowest possible level of expression to be considered for
                                                    // diff gene expression analysis.
    private String path;                            //Relative path to RNAseq output file (.csv).
    static float Q = (float) 1;                     //Ratio of WT to Challenge Condition in RNAseq data.



    //REQUIRES: relative path from content root->file pointing .csv file with the following format:
    //row 1: Header (not included in analysis)
    // Column 1: symbol (String), Column 2: Operon (String), column 3: Challenge Condition RNA copy # (Float)
    // column 4: WT RNA copy # (Float))
    //EFFECT: instantiates a new instance of RNAseqDataFile
    public RnaSeqDataFile(String name, String data, String path) {
        super(name, data);
        this.path = path;
    }


    //EFFECT: returns path of given file
    public java.lang.String getPath() {
        return this.path;
    }

    //EFFECT: sets path of given file
    public void setPath(String path) {
        this.path = path;
    }

 /*   IGNORE:
    //MODIFIES: .csv file referenced in this object's path field
    //EFFECT: adds a row to .csv file using given gene name, operon, WT copy number, and Challenge copy number
    public void newRow(String name, String operon, Float wildTypeCopyNumber, Float challengeCopyNumber) {}
    */

    //REQUIRES: threshold > 0
    //EFFECT: returns number of genes in .csv file that contain a fold change >= than given threshold level
    //genes are low-count filtered before their fold change is evaluated
    public Integer countSigChangeExpression(float threshold) {
        ArrayList<ArrayList<String>> output = getAllGenesWithSigChangeExpression(threshold);
        return output.size();
    }

    //REQUIRES: threshold > 0, numOfGenes >= 0
    //EFFECT: returns an array list of n array lists, where n is the number of genes in .csv file
    // Each sub-list contains name & fold change of a gene in the RNAseq file with fold change >= to the given threshold
    //sub-lists are ordered by magnitude of fold change
    // If numOfGenes == -1, include all genes with fold change >= threshold.
    //genes are low-count filtered before their fold change is evaluated
    public ArrayList<ArrayList<String>> getGeneNamesWithSigChangeExpression(float threshold,
                                                                                      Integer numOfGenes) {
        ArrayList<ArrayList<String>> allSigNameAndFoldChange;
        ArrayList<ArrayList<String>> subSigNameAndFoldChange = new ArrayList<>();

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
    private ArrayList<ArrayList<String>> orderFoldChangesLargeToSmall(
            ArrayList<ArrayList<String>> list) {
        ArrayList<ArrayList<String>> returnList = (ArrayList<ArrayList<String>>) list.stream()
                .sorted((g1,g2) -> Double.compare(abs(Double.parseDouble(g2.get(1))),
                        abs(Double.parseDouble(g1.get(1))))).collect(Collectors.toList());
        return returnList;
    }

    //EFFECT: returns all the genes in csv RNAseq file referenced by file that have foldchange >= given threshold
    //genes are low-count filtered before their fold change is evaluated
    private ArrayList<ArrayList<String>> getAllGenesWithSigChangeExpression(float threshold) {

        ArrayList<ArrayList<String>> geneNameAndFoldChange = new ArrayList<>();

        try {
            Scanner sc = new Scanner(new File(String.valueOf(this.path)));
            sc.useDelimiter(",");
            String headerline = sc.nextLine();

            while (sc.hasNext()) {
                String nextLine = sc.nextLine(); //grabs next line
                String[] arrayOfLine = nextLine.split(",", 4); //splits line into array
                Float foldChange;

                //bias correction:

                //low count filtering:
                foldChange = lowCountFilter(arrayOfLine, threshold);

                if (abs(foldChange) > (float) 1) {
                    ArrayList<String> newArrayList = new ArrayList<>();
                    newArrayList.add(arrayOfLine[0]);

                    newArrayList.add(String.valueOf(foldChange));
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

    //effect: return 0 if the WT and Challenge expression of a given gene is under LOW_COUNT_THRESHOLD
    private Float lowCountFilter(String[] arrayOfLine, Float threshold) {
        Float foldChange;

        if (Double.parseDouble(arrayOfLine[2]) <= LOW_COUNT_THRESHOLD
                && Double.parseDouble(arrayOfLine[3]) <= LOW_COUNT_THRESHOLD) {
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
    private Float isThresholdExceeded(String[] arrayOfLine, Float threshold) {

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

    //Effects: Returns mean fold change of given data sample
    public float getMean() {
        float total = (float) 0;
        ArrayList<ArrayList<String>> output = getAllGenesWithSigChangeExpression(0);
        float size = (float) output.size();
        for (ArrayList<String> a : output) {
            total = total + Float.parseFloat(a.get(1));
        }
        if (size == 0) {
            return (float) 0;
        }

        return total / size;
    }

    //effect: returns the standard deviation within given RNAseq data sample
    public float getStandardDeviation() {
        ArrayList<ArrayList<String>> output = getAllGenesWithSigChangeExpression(0);
        float mean = getMean();
        float summation = (float) 0;
        for (ArrayList<String> a : output) {
            Float difference = (Float.parseFloat(a.get(1)) - mean);
            summation = summation + (difference * difference);
        }
        float divided = summation / ((float)(output.size() - 1));
        return (float) sqrt(divided);
    }



}

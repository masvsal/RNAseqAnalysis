package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RnaSeqDataFileTest {
    RnaSeqDataFile dataFile;
    ArrayList<ArrayList<String>> arrayNameFoldChange;

    //tests for count number of genes w ∆ in gene expression
    @BeforeEach
    public void setup() {
        arrayNameFoldChange = new ArrayList<>();


        dataFile =
                new RnaSeqDataFile("test name", "test data", "data/RNAseqDataFileTests/NoData.csv");

        assertEquals(0,dataFile.countSigChangeExpression((float)2.0));
        assertEquals(arrayNameFoldChange, dataFile.getGeneNamesWithSigChangeExpression((float)2.0, 0));
        assertEquals(0, dataFile.getMean());

        assertEquals("data/RNAseqDataFileTests/NoData.csv",dataFile.getPath());
    }

    private void list5Up5Down() {
        ArrayList<String> listArray1 = new ArrayList<>();
        listArray1.add("yidK");
        listArray1.add("-1.9493177");

        ArrayList<String> listArray2 = new ArrayList<>();
        listArray2.add("yidL");
        listArray2.add("-3.6319616");

        ArrayList<String> listArray3 = new ArrayList<>();
        listArray3.add("yidP");
        listArray3.add("-2.051282");

        ArrayList<String> listArray4 = new ArrayList<>();
        listArray4.add("yidQ");
        listArray4.add("-10.0");

        ArrayList<String> listArray5 = new ArrayList<>();
        listArray5.add("yidR");
        listArray5.add("-2.0");

        ArrayList<String> listArray6 = new ArrayList<>();
        listArray6.add("yidX");
        listArray6.add("2.6666667");

        ArrayList<String> listArray7 = new ArrayList<>();
        listArray7.add("yidZ");
        listArray7.add("3.87");

        ArrayList<String> listArray8 = new ArrayList<>();
        listArray8.add("yieE");
        listArray8.add("2.425");

        ArrayList<String> listArray9 = new ArrayList<>();
        listArray9.add("yieF");
        listArray9.add("2.0");

        ArrayList<String> listArray10 = new ArrayList<>();
        listArray10.add("yieG");
        listArray10.add("9.922066");

        arrayNameFoldChange.add(listArray4);
        arrayNameFoldChange.add(listArray10);
        arrayNameFoldChange.add(listArray7);
        arrayNameFoldChange.add(listArray2);
        arrayNameFoldChange.add(listArray6);
        arrayNameFoldChange.add(listArray8);
        arrayNameFoldChange.add(listArray3);
        arrayNameFoldChange.add(listArray5);
        arrayNameFoldChange.add(listArray9);
        arrayNameFoldChange.add(listArray1);

    }
    //invalid path
    @Test
    public void testcountSignificantChangesInGeneExpressionInvalidPath() {
        dataFile.setPath("data/RNAseqDataFileTests/InvalidPath.csv");
        assertEquals(0, dataFile.countSigChangeExpression((float)2.0));
    }

    //0 WT and Challenge condition detected
    @Test
    public void testcountSignificantChangesInGeneExpression0DetectedThreshold2() {
        dataFile.setPath("data/RNAseqDataFileTests/0Detected.csv");
        assertEquals(2, dataFile.countSigChangeExpression((float)2.0));
    }
    //blank excel file
    @Test
    public void testcountSignificantChangesInGeneExpressionNoDataDetected0() {}

    //file with 2 borderline significant changes in gene expression, threshold 2.0
    @Test
    public void testcountSignificantChangesInGeneExpressionBorderlineCaseThreshold2NDetected2() {
        dataFile.setPath("data/RNAseqDataFileTests/BorderlineCase.csv");
        assertEquals(2, dataFile.countSigChangeExpression((float)2.0));
    }

    //file with no significant changes in gene expression, threshold 100.0
    @Test
    public void testcountSignificantChangesInGeneExpression5Up5DownThreshold100NDetected0() {
        dataFile.setPath("data/RNAseqDataFileTests/5Down5Up.csv");
        assertEquals(0, dataFile.countSigChangeExpression((float)100.0));
    }

    //file with all significant changes in gene expression, threshold 1.0
    @Test
    public void testcountSignificantChangesInGeneExpression5Up5DownThreshold1NDetected10() {
        dataFile.setPath("data/RNAseqDataFileTests/5Down5Up.csv");
        assertEquals(10, dataFile.countSigChangeExpression((float)1.0));
    }

    //file with some significant changes in gene expression, threshold 2.0
    @Test
    public void testcountSignificantChangesInGeneExpression5Up5DownThreshold2NDetected9() {
        dataFile.setPath("data/RNAseqDataFileTests/5Down5Up.csv");
        assertEquals(9, dataFile.countSigChangeExpression((float)2.0));
    }

    //tests for getPath()
    //file with path
    @Test
    public void testGetPath() {}

    //tests for setPath()

    //set path to file
    @Test
    public void testSetPath() {
        dataFile.setPath("data/RNAseqDataFileTests/5Down5Up.csv");
        assertEquals("data/RNAseqDataFileTests/5Down5Up.csv",dataFile.getPath());
    }

    //set path tests


    //same name and operon "x" "x" 0 0
    //same WT and Challenge copy # "x" "y" z z
    //WT > Challenge copy #
    //WT < Challenge copy #


    //tests for get names of genes with significant ∆ in gene expression
    //all genes in blank excel file
    @Test
    public void testgetGeneNamesWithSigChangeExpressionNoDataAllGenes() {}

    //invalid path
    @Test
    public void testGetGeneNamesWithSigChangeInvalidPath() {
        dataFile.setPath("data/RNAseqDataFileTests/InvalidPath.csv");
        assertEquals(new ArrayList<>(), dataFile.getGeneNamesWithSigChangeExpression((float) 2.0, 10));
    }

    //0 WT and Challenge condition detected
    @Test
    public void testGetGeneNamesWithSigChangeExpression0DetectedThreshold2() {
        dataFile.setPath("data/RNAseqDataFileTests/0Detected.csv");

        ArrayList<String> listArray1 = new ArrayList<>();
        listArray1.add("yidP");
        listArray1.add("-2.0");

        ArrayList<String> listArray2 = new ArrayList<>();
        listArray1.add("yidQ");
        listArray1.add("-10.0");

        arrayNameFoldChange.add(listArray1);
        arrayNameFoldChange.add(listArray2);

        assertEquals(2, dataFile.countSigChangeExpression((float)2.0));
    }

    //file with 2 borderline significant changes in gene expression, threshold 2.0, all genes and first 2 genes
    @Test
    public void testGetGeneNamesWithSigChangeExpressionBorderlineCaseThreshold2First1Genes() {
        dataFile.setPath("data/RNAseqDataFileTests/BorderlineCase.csv");
        ArrayList<String> listArray1 = new ArrayList<>();
        listArray1.add("yidK");
        listArray1.add("-2.0");
        arrayNameFoldChange.add(listArray1);
        assertEquals(arrayNameFoldChange, dataFile.getGeneNamesWithSigChangeExpression((float)2.0, 1));
    }

    //file with 2 borderline significant changes in gene expression, threshold 2.0, first gene
    @Test
    public void testGetGeneNamesWithSigChangeExpressionBorderlineCaseThreshold2AllGenes() {
        dataFile.setPath("data/RNAseqDataFileTests/BorderlineCase.csv");

        ArrayList<String> listArray1 = new ArrayList<>();
        listArray1.add("yidK");
        listArray1.add("-2.0");

        ArrayList<String> listArray2 = new ArrayList<>();
        listArray2.add("yidK");
        listArray2.add("2.0");

        arrayNameFoldChange.add(listArray1);
        arrayNameFoldChange.add(listArray2);

        assertEquals(arrayNameFoldChange, dataFile.getGeneNamesWithSigChangeExpression((float)2.0, -1));
        assertEquals(arrayNameFoldChange, dataFile.getGeneNamesWithSigChangeExpression((float)2.0, 2));
    }

    //file with no significant changes in gene expression, threshold 100.0, all genes
    @Test
    public void testGetGeneNamesWithSigChangeExpression5Up5DownThreshold100AllGenes() {
        dataFile.setPath("data/RNAseqDataFileTests/5Down5Up.csv");
        assertEquals(arrayNameFoldChange, dataFile.getGeneNamesWithSigChangeExpression((float)100.0, 0));
    }

    //all significant changes in gene expression, threshold 1.0, all genes
    @Test
    public void testGetGeneNamesWithSigChangeExpression5Up5DownThreshold1AllGenes() {
        dataFile.setPath("data/RNAseqDataFileTests/5Down5Up.csv");
        //arrayNameFoldChange = new String[][]{{"yidK", "-1.949"},{"yidL", "-3.631"},{"yidP", "-2.051"},{"yidQ", "-10.0"},
        //        {"yidR", "-2.0"},{"yidX", "2.666"},{"yidZ", "3.87"},{"yieE", "2.425"},{"yieF", "2.0"},
        //        {"yieG", "9.922"}};
        list5Up5Down();
        assertEquals(arrayNameFoldChange, dataFile.getGeneNamesWithSigChangeExpression((float)1.0, -1));
    }
    //all significant changes in gene expression, threshold 1.0, first 3 genes
    @Test
    public void testGetGeneNamesWithSigChangeExpression5Up5DownThreshold1First3Genes() {
        dataFile.setPath("data/RNAseqDataFileTests/5Down5Up.csv");
    //    arrayNameFoldChange = new String[][]{{"yidK", "-1.949"},{"yidL", "-3.631"},{"yidP", "-2.051"}};
        ArrayList<String> listArray1 = new ArrayList<>();
        listArray1.add("yidK");
        listArray1.add("-1.9493177");

        ArrayList<String> listArray2 = new ArrayList<>();
        listArray2.add("yidL");
        listArray2.add("-3.6319616");

        ArrayList<String> listArray3 = new ArrayList<>();
        listArray3.add("yidP");
        listArray3.add("-2.051282");

        arrayNameFoldChange.add(listArray2);
        arrayNameFoldChange.add(listArray3);
        arrayNameFoldChange.add(listArray1);

        assertEquals(arrayNameFoldChange, dataFile.getGeneNamesWithSigChangeExpression((float)1.0, 3));
    }

    //threshold 5.0, more gene list available than exists
    @Test
    public void testGetGeneNamesWithSigChangeExpression5Up5DownThreshold5First3Genes() {
        dataFile.setPath("data/RNAseqDataFileTests/5Down5Up.csv");
    //    arrayNameFoldChange = new String[][]{{"yidQ", "-10.0"},{"yieG", "9.922"}};

        ArrayList<String> listArray4 = new ArrayList<>();
        listArray4.add("yidQ");
        listArray4.add("-10.0");

        ArrayList<String> listArray10 = new ArrayList<>();
        listArray10.add("yieG");
        listArray10.add("9.922066");

        arrayNameFoldChange.add(listArray4);
        arrayNameFoldChange.add(listArray10);

        assertEquals(arrayNameFoldChange, dataFile.getGeneNamesWithSigChangeExpression((float)5.0, 3));
    }

    //threshold 5.0, first gene
    @Test
    public void testGetGeneNamesWithSigChangeExpression5Up5DownThreshold5First1Genes() {
        dataFile.setPath("data/RNAseqDataFileTests/5Down5Up.csv");
    //    arrayNameFoldChange = new String[][]{{"yidQ", "-10.0"}};

        ArrayList<String> listArray4 = new ArrayList<>();
        listArray4.add("yidQ");
        listArray4.add("-10.0");

        arrayNameFoldChange.add(listArray4);

        assertEquals(arrayNameFoldChange, dataFile.getGeneNamesWithSigChangeExpression((float)5.0, 1));
    }

    @Test
    public void testGetMeanBorderlineCase() {
        dataFile.setPath("data/RNAseqDataFileTests/BorderlineCase.csv");
        assertEquals(0, dataFile.getMean());
    }

    @Test
    public void test5Up5Down() {
        dataFile.setPath("data/RNAseqDataFileTests/5Down5Up.csv");
        Float theMean = dataFile.getMean();
        assertEquals((float) 0.12511711, theMean);
    }

    @Test
    public void testGetStandardDeviation() {
        dataFile.setPath("data/RNAseqDataFileTests/5Down5Up.csv");
        assertEquals(5.327686786651611, dataFile.getStandardDeviation());
    }
    //test toJson
    //test with path




}

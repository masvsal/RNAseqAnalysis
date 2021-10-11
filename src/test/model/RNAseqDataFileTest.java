package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class RNAseqDataFileTest {
    RNAseqDataFile dataFile;

    //tests for count number of genes w ∆ in gene expression
    @BeforeEach
    public void setup() {
        dataFile =
                new RNAseqDataFile("test name", "test data", "data/RNAseqDataFileTests/NoData.csv");

        assertEquals(0,dataFile.countSignificantChangesInGeneExpression((float)2.0));
    }

    //blank excel file
    @Test
    public void testcountSignificantChangesInGeneExpressionNoDataDetected0() {}

    //file with 2 borderline significant changes in gene expression, threshold 2.0
    @Test
    public void testcountSignificantChangesInGeneExpressionBorderlineCaseThreshold2NDetected2() {
        dataFile.setPath("data/RNAseqDataFileTests/BorderlineCase.csv");
        assertEquals(2, dataFile.countSignificantChangesInGeneExpression((float)2.0));
    }

    //file with no significant changes in gene expression, threshold 100.0
    @Test
    public void testcountSignificantChangesInGeneExpression5Up5DownThreshold100NDetected0() {
        dataFile.setPath("data/RNAseqDataFileTests/5Down5Up.csv");
        assertEquals(0, dataFile.countSignificantChangesInGeneExpression((float)100.0));
    }

    //file with all significant changes in gene expression, threshold 1.0
    @Test
    public void testcountSignificantChangesInGeneExpression5Up5DownThreshold1NDetected10() {
        dataFile.setPath("data/RNAseqDataFileTests/5Down5Up.csv");
        assertEquals(10, dataFile.countSignificantChangesInGeneExpression((float)1.0));
    }

    //file with some significant changes in gene expression, threshold 2.0
    @Test
    public void testcountSignificantChangesInGeneExpression5Up5DownThreshold2NDetected9() {
        dataFile.setPath("data/RNAseqDataFileTests/5Down5Up.csv");
        assertEquals(9, dataFile.countSignificantChangesInGeneExpression((float)2.0));
    }

    //tests for getPath()
    //file with path
    @Test
    public void testGetPath() {
        assertEquals("data/RNAseqDataFileTests/NoData.csv",dataFile.getPath());
    }

    //tests for setPath()

    //set path to file
    @Test
    public void testSetPath() {
        assertEquals("data/RNAseqDataFileTests/NoData.csv",dataFile.getPath());
        dataFile.setPath("data/RNAseqDataFileTests/5Down5Up.csv");
        assertEquals("data/RNAseqDataFileTests/5Down5Up.csv",dataFile.getPath());
    }
}

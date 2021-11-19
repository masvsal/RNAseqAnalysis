package ui.display;

import model.RnaSeqDataFile;
import ui.display.components.Bar;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
//visualization of data in RNAseq output file. Contained within separate frame.

public class Graph extends JFrame {
    private static int WIDTH = 800;
    private static int HEIGHT = 800;

    RnaSeqDataFile dataFile;
    ArrayList<ArrayList<String>> output;
    int[] histogramDataArray;

    //histogram parameters
    private int lengthOfBucket = 10;
    private int numOfBucketsRounded;
    private float lowestFC;
    private float standardDev;

    public Graph(RnaSeqDataFile rnaSeqDataFile, float standardDev) {
        super(rnaSeqDataFile.getName() + " Visualization");
        dataFile = rnaSeqDataFile;
        this.standardDev = standardDev;
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        setSize(new Dimension(WIDTH, HEIGHT));
        output = dataFile.getGeneNamesWithSigChangeExpression(0,
                -1, false);

        renderHistogram();
        validate();
        setVisible(true);
    }

    //renders and adds histogram to graph
    private void renderHistogram() {
        createDataArray();
        populateHistogramDataArray();
        drawGraph();
    }

    private void drawGraph() {
        System.out.println(Arrays.toString(histogramDataArray));
        int widthOfBucket = WIDTH / numOfBucketsRounded;
        System.out.println(widthOfBucket);
        add(new Bar(histogramDataArray, widthOfBucket, standardDev));
    }
    //create array containing "buckets" for each range of data

    private void createDataArray() {
        //highest FC:
        float highestFC = Float.parseFloat(output.get(0).get(1));
        //lowest FC:
        lowestFC = Float.parseFloat(output.get(output.size() - 1).get(1));
        //range
        float range = highestFC - lowestFC;
        //number of buckets
        float numOfBuckets = range / lengthOfBucket;
        numOfBucketsRounded = (int) Math.round(Math.ceil(numOfBuckets));
        //initalize Data array
        histogramDataArray = new int[numOfBucketsRounded];
        Arrays.fill(histogramDataArray, 0);

        System.out.println(highestFC);
        System.out.println(lowestFC);
        System.out.println(range);
        System.out.println(numOfBuckets);
        System.out.println(numOfBucketsRounded);
        System.out.println("data array size:" + histogramDataArray.length);
    }

    private void populateHistogramDataArray() {
        ArrayList<Float> listOfFC = convertOutputToFloatList();
        populateBuckets(listOfFC);
    }

    private void populateBuckets(ArrayList<Float> listOfFC) {
        for (float f: listOfFC) {
            float pos = (f - lowestFC) / lengthOfBucket;
            if (f < 0) {
                pos = pos - 1;
            }
            double posRoundUp = Math.ceil(pos);
            int posInt = (int) posRoundUp;

            if (posInt == histogramDataArray.length) {
                posInt = posInt - 1;
            }
            if (f == lowestFC) {
                posInt = 0;
            }

            histogramDataArray[posInt] = histogramDataArray[posInt] + 1;
        }
    }

    private ArrayList<Float> convertOutputToFloatList() {
        ArrayList<Float> returnList = new ArrayList<>();
        for (ArrayList<String> a: output) {
            String string  = a.get(1);
            float floatval = Float.parseFloat(string);
            returnList.add(floatval);
        }
        return returnList;
    }

}

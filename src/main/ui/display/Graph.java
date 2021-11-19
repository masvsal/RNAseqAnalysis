package ui.display;

import model.RnaSeqDataFile;
import ui.display.components.Bar;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
//visualization of data in RNAseq output file. Contained within separate frame.

//graph visualization window of a particular RNAseq data file
public class Graph extends JFrame {
    private static int WIDTH = 800;     //width of frame
    private static int HEIGHT = 1500;   //height of frame

    RnaSeqDataFile dataFile;            //data file to graph
    ArrayList<ArrayList<String>> output;    //list of top differentially expressed genes in order from most + to most -
    int[] histogramDataArray;               //array containing containing buckets of values used to generate histogram

    //histogram parameters
    private int lengthOfBucket = 10;    //range of values that fall into a single bucket
    private int numOfBucketsRounded;    //number of value buckets in histogram
    private float lowestFC;             //lowest foldchange in file
    private float highestFC;            //highest foldchange in file
    private float standardDev;          //standard deviation of foldchange in file


    //MODIFIES: this
    //EFFECTS: instantiates new graph frame. Populates and renders histogram in frame.
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

    //MODIFIES: this
    //effects: populates histogram array, renders array, and adds histogram to graph frame.
    private void renderHistogram() {
        createDataArray();
        populateHistogramDataArray();
        drawGraph();
    }

    //MODIFIES: this
    //EFFECTS: creates new rendered graph and adds it to frame
    private void drawGraph() {
        System.out.println(Arrays.toString(histogramDataArray));
        int widthOfBucket = WIDTH / numOfBucketsRounded;
        System.out.println(widthOfBucket);
        add(new Bar(histogramDataArray, widthOfBucket, standardDev, lowestFC, highestFC, WIDTH));
    }

    //modifies: this
    //EFFECTS: finds number of buckets appropriate to range of data.
    // creates array containing value buckets, each bucket populated with 0.
    private void createDataArray() {
        //highest FC:
        highestFC = Float.parseFloat(output.get(0).get(1));
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
    }

    //modifies: this
    //effects: converts nested list to regular array list and executes population of buckets.
    private void populateHistogramDataArray() {
        ArrayList<Float> listOfFC = convertOutputToFloatList();
        populateBuckets(listOfFC);
    }

    //modifies: this
    //effects: scans through list of fold changes in file, incrementing one bucket in histogram data array by 1 for each
    //value. Position to increment calculated by dividing distance between value and minimum value by the length of
    //each bucket. If value is negative, subtracts 1 position to adjust for 0-indexing. Rounds up position. values with
    //calulcated positions on edges of histogram range are lowered by 1 position.
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

    //effects: converts nested RNAseq diffEx output list to array list containing float values and returns it.
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

package ui.display;

import model.RnaSeqDataFile;
import ui.MainInterface;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Scanner;
import model.logging.Event;
import model.logging.EventLog;

//visual element of display panel. Visible when rna seq file is displayed
public class RNAseqOverviewDisplay extends JPanel implements ActionListener {
    private static int PANE_X;              //height of panel
    private static int PANE_Y;              //width of panel

    private RnaSeqDataFile dataFile;        //data file to display
    private DataFileDisplay parent;         //parent component
    private JPanel statsOverview;           //panel containing statsticial information about file and any text fields
    private float standardDev;              //standard deviation in RNAseq output
    private JTextField textField;           //user-interaction object
    private JScrollPane scrollPane;         //table displaying genes and diff expression in RNAseq output file
    private Float threshold;                //minimum amount of FC chosen by user


    //MODIFIES: this
    //EFFECTS: instantiates visible object and adds all RNAseq sub-panels.
    public RNAseqOverviewDisplay(RnaSeqDataFile rnaSeqDataFile, DataFileDisplay parent) {
        new JPanel();
        PANE_X = parent.getPaneX();
        PANE_Y = parent.getPaneY() / 2;
        setMaximumSize(new Dimension(PANE_X, PANE_Y));
        setLayout(new BoxLayout(this, 1));
        dataFile = rnaSeqDataFile;
        this.parent = parent;
        showStatsOverview();
        showTopGene();
    }

    //MODIFIES: this
    //EFFECTS: instantiates invisible object
    public RNAseqOverviewDisplay(DataFileDisplay parent) {
        new JPanel();
        PANE_X = parent.getPaneX();
        PANE_Y = parent.getPaneY() / 2;
        setBorder(new LineBorder(Color.BLACK));
        setMaximumSize(new Dimension(PANE_X, PANE_Y));
    }

    //MODIFIES: this
    //EFFECTS: creates table showing top 100 most differentially expressed genes in file. Adds panel to display.
    private void showTopGene() {
        ArrayList<ArrayList<String>> output =
                dataFile.getGeneNamesWithSigChangeExpression(0, 100, true);
        String[][] array = create2DArray(output);
        createGeneTable(array);
    }

    //MODIFIES: this
    //EFFECTS: generates gene and dif expression table from 2-d array
    private void createGeneTable(String[][] array) {
        if (scrollPane != null) {
            remove(scrollPane);
        }
        String[] names = {"name", "FC"};
        JTable table = new JTable(array, names);
        scrollPane = new JScrollPane(table);
        scrollPane.setMaximumSize(new Dimension(PANE_X, 2 * PANE_Y / 3));
        add(scrollPane);
        parent.mainInterface.pack();
    }

    //MODIFIES: this
    //EFFECTS: generates and returns 2-d array from nested array list containing differential gene epxression data
    private String[][] create2DArray(ArrayList<ArrayList<String>> output) {
        String[][] array = new String[output.size()][2];
        int counter = 0;
        for (ArrayList<String> a: output) {
            array[counter][0] = a.get(0);
            array[counter][1] = a.get(1);
            counter = counter + 1;
        }
        return array;
    }

    //modifies: this
    //effects: creates and adds statistical overview of RNAseq data file
    private void showStatsOverview() {
        statsOverview = new JPanel();
        statsOverview.setMaximumSize(new Dimension(PANE_X, PANE_Y / 3));
        statsOverview.setBackground(Color.LIGHT_GRAY);
        statsOverview.setLayout(new FlowLayout());
        float mean = dataFile.getMean();
        standardDev = dataFile.getStandardDeviation();

        JLabel meanLabel = new JLabel("Mean: " + mean);
        JLabel standardDevLabel = new JLabel("Standard Dev: " + standardDev);

        statsOverview.add(meanLabel);
        statsOverview.add(standardDevLabel);
        add(statsOverview);
    }

    //modifies: this
    //EFFECT:  creates input field to collect user-specified threshold
    public void getThreshold() {
        createTextField("Threshold: ");
        textField.setActionCommand("0");
    }

    //modifies: this
    //EFFECT:  creates input field to collect user-specified number of genes
    public void getTopGene() {
        createTextField("Num of genes: ");
        textField.setActionCommand("1");
    }

    //modifies: this
    //EFFECT:  creates and adds textfield to statOverview panel displaying given string
    private void createTextField(String text) {
        textField = new JTextField(text, 20);
        textField.addActionListener(this);
        textField.setVisible(true);
        statsOverview.add(textField);
        remove(scrollPane);
        parent.mainInterface.pack();
    }

    //modifies: this
    //Effect: performs differential expression count given a threshold. Saves it to data field in data file.
    private void executeDiffExpressionCount(float threshold) {
        Integer numOfChanges = dataFile.countSigChangeExpression(threshold);
        String newData = "# of significant changes @ threshold: " + threshold + " = " + numOfChanges;
        dataFile.setData(newData);
        EventLog.getInstance().logEvent(
                new Event("DATA FILE: " + dataFile.getName() + "|" + "DATA MODIFIED: "
                        + newData));
        parent.refreshDataDisplay();
    }

    //MODIFIES: this
    //EFFECTS: executes search for top n genes with at least x threshold where x and n are user specified
    private void showDiffGeneExpression(float threshold, int numGenes) {
        ArrayList<ArrayList<String>> geneList =
                dataFile.getGeneNamesWithSigChangeExpression(threshold, numGenes, true);
        String[][] geneArray = create2DArray(geneList);
        System.out.println(geneList);
        createGeneTable(geneArray);
    }


    @Override
    //MODIFIES: this
    //handles user interaction with text fields. Coordinates creation of text fields to execute differential expression
    // count. If a threshold text field is passing action command, stores given threshold and initiates
    //diff expression count. If number of genes text field is passing action command, uses stored threshold and
    //number of genes to initiate diff expression count.
    public void actionPerformed(ActionEvent e) {
        String name = textField.getText();
        String commandString = name.split(": *")[1];
        try {
            float command = Float.parseFloat(commandString);

            if (e.getActionCommand().equals("0")) {
                threshold = command;
                executeDiffExpressionCount(command);
                statsOverview.remove(textField);
                getTopGene();
            } else if (e.getActionCommand().equals("1")) {
                int commandInt = Integer.parseInt(commandString);
                System.out.println(commandInt);
                statsOverview.remove(textField);
                showDiffGeneExpression(threshold, commandInt);
            }

        } catch (NumberFormatException err) {
            textField.setText("bad input. Please try again:");
        }
    }

    //EFFECTS: visualizes given RNAseq file by creating graph object in a new frame.
    public void createGraph() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Graph(dataFile, standardDev);
            }
        });
    }
}

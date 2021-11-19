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

public class RNAseqOverviewDisplay extends JPanel implements ActionListener {
    private RnaSeqDataFile dataFile;
    private DataFileDisplay parent;
    private JPanel statsOverview;
    private float standardDev;
    private JTextField textField;
    private JScrollPane scrollPane;
    private Float threshold;
    private static int PANE_X;
    private static int PANE_Y;

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

    public RNAseqOverviewDisplay(DataFileDisplay parent) {
        new JPanel();
        PANE_X = parent.getPaneX();
        PANE_Y = parent.getPaneY() / 2;
        setBorder(new LineBorder(Color.BLACK));
        setMaximumSize(new Dimension(PANE_X, PANE_Y));
    }

    private void showTopGene() {
        ArrayList<ArrayList<String>> output =
                dataFile.getGeneNamesWithSigChangeExpression(0, 100, true);
        String[][] array = create2DArray(output);
        createGeneTable(array);
    }

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

    //EFFECT:  uses user-specified threshold to count the number of genes that meet or exceed fold-change threshold
    public void getThreshold() {
        createTextField("Threshold: ");
        textField.setActionCommand("0");
    }

    public void getTopGene() {
        createTextField("Num of genes: ");
        textField.setActionCommand("1");
    }


    private void createTextField(String text) {
        textField = new JTextField(text, 20);
        textField.addActionListener(this);
        textField.setVisible(true);
        statsOverview.add(textField);
        remove(scrollPane);
        parent.mainInterface.pack();
    }

    //Effect: performs differential expression count given a threshold. Saves it to data field in data file.
    private void executeDiffExpressionCount(float threshold) {
        Integer numOfChanges = dataFile.countSigChangeExpression(threshold);
        dataFile.setData("# of significant changes @ threshold: " + threshold + " = " + numOfChanges);
        parent.refreshDataDisplay();
    }

    //Executes search for top genes
    private void showDiffGeneExpression(float threshold, int numGenes) {
        ArrayList<ArrayList<String>> geneList =
                dataFile.getGeneNamesWithSigChangeExpression(threshold, numGenes, true);
        String[][] geneArray = create2DArray(geneList);
        System.out.println(geneList);
        createGeneTable(geneArray);
    }


    @Override
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

    //visualizes given RNAseq file by creating graph object
    public void createGraph() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Graph(dataFile, standardDev);
            }
        });
    }
}

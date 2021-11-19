package ui;

import model.Experiment;
import model.ExperimentDirectory;
import model.GenericDataFile;
import model.RnaSeqDataFile;
import persistence.JsonReader;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.IOException;

public class BootUpInterface extends JFrame implements ActionListener {
    private static int WIDTH = 350;
    private static int HEIGHT = 200;
    ExperimentDirectory experimentDirectory;

    public BootUpInterface(ExperimentDirectory experimentDirectory) {
        super("Bootup Sequence");
        this.experimentDirectory = experimentDirectory;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new FlowLayout());
        setSize(new Dimension(WIDTH, HEIGHT));
        addTools();
        setVisible(true);
        validate();


    }

    private void addTools() {
        JButton yesBut = new JButton("Yes");
        JButton noBut = new JButton("No");
        JLabel message = new JLabel("Would you like to reload last saved directory");
        message.setSize(new Dimension(100, 50));

        yesBut.setActionCommand("y");
        noBut.setActionCommand("n");

        noBut.addActionListener(this);
        yesBut.addActionListener(this);

        yesBut.setVisible(true);
        noBut.setVisible(true);
        message.setVisible(true);

        add(message);
        add(yesBut);
        add(noBut);

    }

    //EFFECT: loads experiment directory from file
    public void reload() {
        JsonReader reader = new JsonReader("data/Persistence/ExperimentDirectory.json");
        try {
            experimentDirectory = reader.read();

        } catch (IOException e) {
            System.out.println("Error: path not valid");
        }
    }

    //MDOFIES: this
    //EFFECTS: Closes window
    private void closeBootupScreen() {
        this.dispose();
    }

    //MODIFIES: this
    //EFFECTS: initializes experiment directory, 2 experiments, and 4 data files.
    //places 2 data files in each experiment. Places experiments in experiment directory
    public void init() {
        experimentDirectory = new ExperimentDirectory();

        Experiment experiment1 = new Experiment("Effect of pH on E. Coli");
        Experiment experiment2 = new Experiment("Effect of Temperature on E. Coli");
        Experiment experiment3 = new Experiment("RNAseq");

        GenericDataFile dataFile1 = new GenericDataFile("trial 1, pH = 5", "sample survival rate = 98%");
        GenericDataFile dataFile2 = new GenericDataFile("trial 2, pH = 10", "sample survival rate = 13%");
        GenericDataFile dataFile3 = new GenericDataFile("trial 1, Temp = 70 F",
                "sample survival rate = 100%");
        GenericDataFile dataFile4 = new GenericDataFile("trial 2, Temp = 100 F",
                "sample survival rate = 10%");
        RnaSeqDataFile dataFile5 = new RnaSeqDataFile("TF = NaC", "Analysis not run yet",
                "data/RNAseqExampleFiles/Nac_RNASeq _ NoFC_v2.csv");
        RnaSeqDataFile dataFile6 = new RnaSeqDataFile("TF = CsiR", "Analysis not run yet",
                "data/RNAseqExampleFiles/CsiR_RNAseq_NoFC_v2.csv");

        experiment1.addFile(dataFile1);
        experiment1.addFile(dataFile2);
        experiment2.addFile(dataFile3);
        experiment2.addFile(dataFile4);
        experiment3.addFile(dataFile5);
        experiment3.addFile(dataFile6);

        experimentDirectory.addFile(experiment1);
        experimentDirectory.addFile(experiment2);
        experimentDirectory.addFile(experiment3);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getActionCommand().equals("y")) {
            reload();
        } else {
            init();
        }
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MainInterface(experimentDirectory);
                closeBootupScreen();
            }
        });

    }
}

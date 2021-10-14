package ui;

import model.Experiment;
import model.ExperimentDirectory;
import model.GenericDataFile;
import model.RNAseqDataFile;
import model.interfaces.Directory;
import model.interfaces.NamedFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

//file directory application
public class DirectoryApp {
    private ExperimentDirectory experimentDirectory;


    //EFFECTS: runs the file directory application
    public DirectoryApp() {
        init();
        runExperimentDirectory();
    }

    //MODIFIES: this
    //EFFECTS: processes user input for experiment directory
    private void runExperimentDirectory() {
        boolean keepGoing = true;
        Scanner myObj = new Scanner(System.in);
        String command;

        while (keepGoing) {
            System.out.println("Welcome to the file explorer! :) \n");
            System.out.println("*A* = add Experiment");
            System.out.println("*R* = remove Experiment");
            System.out.println("*Q* = quit");
            printRange(experimentDirectory);
            displayExperiments();
            command = myObj.nextLine();
            command = command.toLowerCase();

            if (command.equals("q")) {
                keepGoing = false;
            } else if (command.equals("a")) {
                addExperiment();
            } else if (command.equals("r")) {
                removeFile(experimentDirectory);
            } else {
                processExperimentDirectoryCommand(command);
            }
        }

        System.out.println("\nGoodbye!");
    }

    //MODIFIES: this
    //EFFECTS: processes user input inside of an experiment
    private void runExperiment(Experiment selExperiment) {
        boolean keepGoing = true;
        Scanner myObj = new Scanner(System.in);

        String command;

        while (keepGoing) {
            System.out.println("*A* = add data file \n*R* = remove data file \n*M* = modify description");
            System.out.println("*Q* = quit");
            printRange(selExperiment);
            displayDataFiles(selExperiment);
            command = myObj.nextLine();
            command = command.toLowerCase();

            if (command.equals("q")) {
                keepGoing = false;
            } else if (command.equals("a")) {
                addDataFile(selExperiment);
            } else if (command.equals("r")) {
                removeFile(selExperiment);
            } else if (command.equals("m")) {
                newDescription(selExperiment);
            } else {
                processExperimentCommand(command, selExperiment);
            }
        }
    }


    //MODIFIES: this
    //EFFECTS: processes user input inside of an experiment
    private void runDataFile(Experiment selExperiment, GenericDataFile file) {
        boolean keepGoing = true;
        Scanner myObj = new Scanner(System.in);
        boolean isRNAseqData = file instanceof RNAseqDataFile;

        String command;

        while (keepGoing) {
            System.out.println("*M* = modify description");
            System.out.println("*Q* = quit");
            if (isRNAseqData) {
                System.out.println("*C* = count significant differential gene expression");
                System.out.println("*P* = print significant differential gene expression");
            }
            displayDataFile(file);
            command = myObj.nextLine();
            command = command.toLowerCase();

            if (command.equals("q")) {
                keepGoing = false;
            } else {
                interpretDataFileCommand(command, file, isRNAseqData);
            }
        }
    }


    private void interpretDataFileCommand(String command, GenericDataFile file, Boolean isRNAseqData) {
        if (command.equals("m")) {
            newDescription(file);
        } else if (command.equals("c") && isRNAseqData) {
            countDiffGeneExpression((RNAseqDataFile) file);
        } else if (command.equals("p") && isRNAseqData) {
            printDiffGeneExpression((RNAseqDataFile) file);
        } else {
            System.out.println("Invalid Command");
        }
    }

    //Effect: prints range of numbers to select, if more than one experiment in directory.
    // If no files, does not print anything
    public void printRange(Directory directory) {
        Integer length = directory.length();
        if (length > 0) {
            System.out.println("Enter any number in the range 1 to "
                    + length + " to open a file \n");
        }
    }

    //MODIFIES: this
    //EFFECTS: processes user command while in an experimental directory
    private void processExperimentDirectoryCommand(String command) {
        Integer intCommand = Integer.parseInt(command);
        if (intCommand <= experimentDirectory.length()) {
            Experiment selExperiment = experimentDirectory.getFile(intCommand);
            runExperiment(selExperiment);

        } else {
            System.out.println("\nInvalid Selection: Out of Range \nPlease Select Again");
        }
    }

    //MODIFIES: this
    //EFFECTS: processes user command while in an experiment
    private void processExperimentCommand(String command, Experiment selExperiment) {
        Integer intCommand = Integer.parseInt(command);
        if (intCommand <= selExperiment.length()) {
            GenericDataFile selDataFile = selExperiment.getFile(intCommand);
            runDataFile(selExperiment, selDataFile);

        } else {
            System.out.println("\nInvalid Selection: Out of Range \nPlease Select Again");
        }
    }

    //MODIFIES: this
    //EFFECTS: initializes experiment directory, 2 experiments, and 4 data files.
    //places 2 data files in each experiment. Places experiments in experiment directory
    private void init() {
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
        RNAseqDataFile dataFile5 = new RNAseqDataFile("TF = NaC", "Analysis not run yet",
                "data/RNAseqExampleFiles/Nac_RNASeq _ NoFC_v2.csv");
        RNAseqDataFile dataFile6 = new RNAseqDataFile("TF = CsiR", "Analysis not run yet",
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

    //EFFECTS: displays all the experiments in an Experiment directory to user
    private void displayExperiments() {
        System.out.println("Experiments:");
        displayFiles(experimentDirectory);
    }


    //EFFECTS: displays all the data files in an experiment to user
    private void displayDataFiles(Experiment experiment) {
        System.out.println(experiment.getName() + ":");
        System.out.println("description: " + experiment.getDescription());
        displayFiles(experiment);
    }

    //EFFECTS: displays the content of a data file to a user
    private void displayDataFile(GenericDataFile file) {
        boolean isRNAseqData = file instanceof RNAseqDataFile;
        System.out.println("Name: " + file.getName());
        System.out.println("Description: " + file.getDescription());
        System.out.println("__________________________________________");
        if (isRNAseqData) {
            printRNAseqdata((RNAseqDataFile) file);
        }
        System.out.println("Data: " + file.getData());
        System.out.println("******************************************");
    }

    //EFFECTS: prints first 10 rows of csv file referenced by RNAseq data file, gives user the option print the rest
    private void printRNAseqdata(RNAseqDataFile file) {
        try {
            System.out.println("First 10 pieces of data:");
            int counter = 0;
            Scanner sc = new Scanner(new File(file.getPath()));
            sc.useDelimiter(",");
            while (sc.hasNext() && counter <= 11) {
                String nextLine = sc.nextLine(); //grabs next line}
                System.out.println(nextLine);
                counter = counter + 1;
            }
        } catch (FileNotFoundException e) {
            System.out.println("file not found");
        }
    }



    //EFFECTS: displays all the experiments in a directory to user
    private void displayFiles(Directory directory) {
        for (Integer i = 1; i <= directory.length(); i++) {
            System.out.println(i + "] " + directory.getFile(i).getName());
        }
        System.out.println("******************************************");
    }

    //MODIFIES: experimentDirectory
    //EFFECTS: adds experiment to experiment directory
    private void addExperiment() {
        Scanner myObj = new Scanner(System.in);
        System.out.println("Enter name of new experiment:");
        String name = myObj.nextLine();
        experimentDirectory.addFile(new Experiment(name));
    }

    //MODIFIES: experimentDirectory
    //EFFECTS: adds data file to selected experiment in experiment directory
    private void addDataFile(Experiment selExperiment) {
        Scanner myObj = new Scanner(System.in);

        System.out.println("Enter name of new data file:");
        String name = myObj.nextLine();
        System.out.println("Enter new data:");
        String data = myObj.nextLine();

        selExperiment.addFile(new GenericDataFile(name, data));
    }

    //MODIFIES: Directory
    //EFFECTS: removes file from selected directory
    private void removeFile(Directory directory) {
        Scanner myObj = new Scanner(System.in);

        System.out.println("Enter # of file to remove:");
        Integer number = Integer.parseInt(myObj.nextLine());

        directory.removeFile(number);
    }

    //MODIFIES: selected file
    //EFFECTS: user specifies new description of file
    private void newDescription(NamedFile selFile) {
        Scanner myObj = new Scanner(System.in);

        System.out.println("Enter new description:");
        String description = myObj.nextLine();

        selFile.setDescription(description);
    }

    private void countDiffGeneExpression(RNAseqDataFile file) {
        Scanner myObj = new Scanner(System.in);

        System.out.println("Enter significance threshold:");
        String threshold = myObj.nextLine();

        Integer numOfChanges = file.countSigChangeExpression(Float.parseFloat(threshold));
        file.setData("# of significant changes @ threshold: " + threshold + " = " + numOfChanges);
    }

    private void printDiffGeneExpression(RNAseqDataFile file) {
        Scanner myObj = new Scanner(System.in);

        System.out.println("Enter significance threshold:");
        String threshold = myObj.nextLine();

        System.out.println("How many genes would you like to include in your search?");
        String numOfGenes = myObj.nextLine();

        ArrayList<ArrayList<String>> numOfChanges =
                file.getGeneNamesWithSigChangeExpression(Float.parseFloat(threshold), Integer.parseInt(numOfGenes));
        System.out.println(numOfChanges);
    }
}

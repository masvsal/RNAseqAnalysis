package ui;

import model.Experiment;
import model.ExperimentDirectory;
import model.GenericDataFile;
import model.interfaces.Directory;
import model.interfaces.NamedFile;

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
            System.out.println("*A* = add experiment");
            System.out.println("*R* = remove experiment");
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
            System.out.println("Name: " + selDataFile.getName());
            System.out.println("Description: " + selDataFile.getDescription());
            System.out.println("__________________________________________");
            System.out.println("Data: " + selDataFile.getData());
            System.out.println("******************************************");

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

        GenericDataFile dataFile1 = new GenericDataFile("trial 1, pH = 5", "sample survival rate = 98%");
        GenericDataFile dataFile2 = new GenericDataFile("trial 2, pH = 10", "sample survival rate = 13%");
        GenericDataFile dataFile3 = new GenericDataFile("trial 1, Temp = 70 F",
                "sample survival rate = 100%");
        GenericDataFile dataFile4 = new GenericDataFile("trial 2, Temp = 100 F",
                "sample survival rate = 10%");

        experiment1.addFile(dataFile1);
        experiment1.addFile(dataFile2);
        experiment2.addFile(dataFile3);
        experiment2.addFile(dataFile4);

        experimentDirectory.addFile(experiment1);
        experimentDirectory.addFile(experiment2);
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
}

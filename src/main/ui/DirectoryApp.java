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
        runExperimentDirectory();
    }

    //MODIFIES: this
    //EFFECTS: processes user input for experiment directory
    private void runExperimentDirectory() {
        boolean keepGoing = true;
        Scanner myObj = new Scanner(System.in);
        String command;

        init();

        while (keepGoing) {
            System.out.println("Welcome to the file explorer! :) \n");
            System.out.println("*A* = add experiment");
            System.out.println("*R* = remove experiment");
            System.out.println("*Q* = quit");
            System.out.println("Enter any number in the range 1 to "
                    + experimentDirectory.length() + " to analyze an existing experiment \n");
            displayExperiments();
            command = myObj.nextLine();
            command = command.toLowerCase();

            if (command.equals("q")) {
                keepGoing = false;
            } else if (command.equals("a")) {
                addExperiment();
            } else if (command.equals("r")) {
                removeExperiment();
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

        init();
        while (keepGoing) {
            System.out.println("*A* = add data file \n *R* = remove data file \n *M* = modify description");
            System.out.println("*Q* = quit");
            System.out.println("Enter any number in the range 1 to "
                    + selExperiment.length() + " to inspect an existing data file. \n");
            displayDataFiles(selExperiment);
            command = myObj.nextLine();
            command = command.toLowerCase();

            if (command.equals("q")) {
                keepGoing = false;
            } else if (command.equals("a")) {
                addDataFile(selExperiment);
            } else if (command.equals("r")) {
                removeDataFile(selExperiment);
            } else if (command.equals("m")) {
                newDescription(selExperiment);
            } else {
                processExperimentCommand(command, selExperiment);
            }
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
        for (Integer i = 1; i <= experimentDirectory.length(); i++) {
            System.out.println(i + "] " + experimentDirectory.getFile(i).getName());
        }
        System.out.println("******************************************");
    }


    //EFFECTS: displays all the data files in an experiment to user
    private void displayDataFiles(Experiment experiment) {
        System.out.println("description: " + experiment.getDescription());
        System.out.println(experiment.getName() + " contains the following data files:");
        for (Integer i = 1; i <= experiment.length(); i++) {
            System.out.println(i + "] " + experiment.getFile(i).getName());
        }
        System.out.println("******************************************");
        System.out.println("What data file would you like to examine?" + "\nEnter the corresponding # below:");
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
    //EFFECTS: removes experiment from experiment directory
    private void removeExperiment() {
        Scanner myObj = new Scanner(System.in);
        System.out.println("Enter # of experiment that you would like to remove:");
        Integer number = Integer.parseInt(myObj.nextLine());
        experimentDirectory.removeFile(number);
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

    //MODIFIES: experimentDirectory
    //EFFECTS: removes data file to selected experiment in experiment directory
    private void removeDataFile(Experiment selExperiment) {
        Scanner myObj = new Scanner(System.in);

        System.out.println("Enter # of data file to remove:");
        Integer number = Integer.parseInt(myObj.nextLine());

        selExperiment.removeFile(number);
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

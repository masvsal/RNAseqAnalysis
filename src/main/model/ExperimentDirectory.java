package model;

import java.util.LinkedList;

//list of Experiments in system

public class ExperimentDirectory {
    private LinkedList<Experiment> listOfExperiments;

    public ExperimentDirectory() {
        listOfExperiments = new LinkedList<>();
    }

    public void addExperiment(Experiment exp) {
        listOfExperiments.add(exp);
    }

    public Experiment getFirstExperiment() {
        return null;
    }

    public Integer length() {
        return 0;
    }


}

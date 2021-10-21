package model;

import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;


public class ExperimentDirectoryTest {
    ExperimentDirectory experimentDirectory;
    JSONObject jsonFile;

    @BeforeEach
    public void setup() {
        experimentDirectory = new ExperimentDirectory();
        assertEquals(0, experimentDirectory.length());
    }

 //tests for add experiment

    //empty directory
    @Test
    public void testAddExperimentEmptyDirectory1Add() {
        Experiment newExperiment = new Experiment("test1");
        experimentDirectory.addFile(newExperiment);
        assertEquals(1, experimentDirectory.length());
        assertEquals(newExperiment,experimentDirectory.getFile(1));
    }
    //directory with 1 item
    @Test
    public void testAddExperiment1ItemInDirectory1Add() {
        Experiment newExperiment1 = new Experiment("test1");
        Experiment newExperiment2 = new Experiment("test2");

        experimentDirectory.addFile(newExperiment1);
        experimentDirectory.addFile(newExperiment2);

        assertEquals(2, experimentDirectory.length());
        assertEquals(newExperiment1,experimentDirectory.getFile(1));
    }


    //tests for getFile
    @Test
    public void getFile1ItemInDirectory() {
        Experiment newExperiment1 = new Experiment("test1");
        experimentDirectory.addFile(newExperiment1);
        assertEquals(newExperiment1, experimentDirectory.getFile(1));
    }

    //directory with more than 1 item
    @Test
    public void getFile3ItemsInDirectory() {
        Experiment newExperiment1 = new Experiment("test1");
        Experiment newExperiment2 = new Experiment("test2");
        Experiment newExperiment3 = new Experiment("test3");

        experimentDirectory.addFile(newExperiment1);
        experimentDirectory.addFile(newExperiment2);
        experimentDirectory.addFile(newExperiment3);
        assertEquals(newExperiment2, experimentDirectory.getFile(2));
        assertEquals(newExperiment1, experimentDirectory.getFile(1));
        assertEquals(newExperiment3, experimentDirectory.getFile(3));
    }

    //tests for length()
    //empty directory

    @Test
    public void testLengthEmptyDirectory() {
        assertEquals(0, experimentDirectory.length());
    }

    @Test
    public void testLength1ItemInDirectory() {
        Experiment newExperiment = new Experiment("test1");

        experimentDirectory.addFile(newExperiment);

        assertEquals(1, experimentDirectory.length());
    }

    //directory with 1
    //directory with 3
    @Test
    public void testLength3ItemsInDirectory() {
        Experiment newExperiment1 = new Experiment("test1");
        Experiment newExperiment2 = new Experiment("test2");
        Experiment newExperiment3 = new Experiment("test3");

        experimentDirectory.addFile(newExperiment1);
        experimentDirectory.addFile(newExperiment2);
        experimentDirectory.addFile(newExperiment3);

        assertEquals(3, experimentDirectory.length());
    }

    //tests for remove file

    //tests for remove file
    @Test
    public void testRemoveFile1FileInDirectoryFirstFile(){
        Experiment newFile1 = new Experiment("test1");

        experimentDirectory.addFile(newFile1);

        assertEquals(1, experimentDirectory.length());

        experimentDirectory.removeFile(1);

        assertEquals(0, experimentDirectory.length());
    }

    @Test
    public void testRemoveFile3ItemsInDirectoryFirstFile() {

        Experiment newExperiment1 = new Experiment("test1");
        Experiment newExperiment2 = new Experiment("test2");
        Experiment newExperiment3 = new Experiment("test3");

        experimentDirectory.addFile(newExperiment1);
        experimentDirectory.addFile(newExperiment2);
        experimentDirectory.addFile(newExperiment3);

        assertEquals(3,experimentDirectory.length());

        experimentDirectory.removeFile(1);

        assertEquals(2, experimentDirectory.length());
    }

    @Test
    public void testRemoveFile3ItemsInDirectoryLastFile() {

        Experiment newExperiment1 = new Experiment("test1");
        Experiment newExperiment2 = new Experiment("test2");
        Experiment newExperiment3 = new Experiment("test3");

        experimentDirectory.addFile(newExperiment1);
        experimentDirectory.addFile(newExperiment2);
        experimentDirectory.addFile(newExperiment3);

        assertEquals(3,experimentDirectory.length());

        experimentDirectory.removeFile(3);

        assertEquals(2, experimentDirectory.length());
    }

    //test toJson
    //empty experiment directory
    //1 experiment
    //3 experiments
}

package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class ExperimentTest {
    Experiment experiment;
    String description;

    @BeforeEach
    public void setup() {
        experiment = new Experiment("Blank 123");
        description = experiment.getDescription();
        assertEquals("no description", description);
    }

    //tests for get name
    //experiment with name
    @Test
    public void testGetNameNameExists() {
        String name = experiment.getName();
        assertEquals("Blank 123", name);

    }

    //tests for get description
    @Test
    public void testGetDescriptionNoDescription() {}

    @Test
    public void testGetDescriptionYesDescription() {
        experiment.setDescription("blank 321");

        description = experiment.getDescription();

        assertEquals("blank 321", description);
    }

    //tests for give description

    //experiment with no description
    @Test
    public void testGiveDescriptionNoDescription() {
        experiment.setDescription("1234");

        description = experiment.getDescription();
        assertEquals("1234", description);

    }

    @Test
    public void testGiveDescriptionYesDescription() {
        experiment.setDescription("blank 321");

        String description = experiment.getDescription();
        assertEquals("blank 321", description);

        experiment.setDescription("un-blank 321");

        description = experiment.getDescription();
        assertEquals("un-blank 321", description);
    }

    //tests for add File
    //empty directory
    @Test
    public void testAddFileEmptyExperiment1Add() {
        GenericDataFile newFile = new GenericDataFile("test1", "123");

        experiment.addFile(newFile);

        assertEquals(1, experiment.length());
        assertEquals(newFile,experiment.getFile(1));
    }

    //experiment with 1 data file
    @Test
    public void testAddFile1ItemInExperiment1Add() {
        GenericDataFile newFile1 = new GenericDataFile("test1", "123");
        GenericDataFile newFile2 = new GenericDataFile("test2", "345");

        experiment.addFile(newFile1);
        experiment.addFile(newFile2);

        assertEquals(2, experiment.length());
        assertEquals(newFile1,experiment.getFile(1));
    }

    //tests for getFile

    @Test
    public void getFile1ItemInDirectory() {
        GenericDataFile newFile1 = new GenericDataFile("test1", "123");
        experiment.addFile(newFile1);
        assertEquals(newFile1, experiment.getFile(1));
    }

    //directory with more than 1 item
    @Test
    public void getFile3ItemsInDirectoryFirstFile() {
        GenericDataFile newFile1 = new GenericDataFile("test1", "123");
        GenericDataFile newFile2 = new GenericDataFile("test2", "345");
        GenericDataFile newFile3 = new GenericDataFile("test2", "678");

        experiment.addFile(newFile1);
        experiment.addFile(newFile2);
        experiment.addFile(newFile3);

        assertEquals(newFile1, experiment.getFile(1));
    }

    @Test
    public void getFile3ItemsInDirectorySecondFile() {
        GenericDataFile newFile1 = new GenericDataFile("test1", "123");
        GenericDataFile newFile2 = new GenericDataFile("test2", "345");
        GenericDataFile newFile3 = new GenericDataFile("test2", "678");

        experiment.addFile(newFile1);
        experiment.addFile(newFile2);
        experiment.addFile(newFile3);

        assertEquals(newFile2, experiment.getFile(2));
    }

    @Test
    public void getFile3ItemsInDirectoryThirdFile() {
        GenericDataFile newFile1 = new GenericDataFile("test1", "123");
        GenericDataFile newFile2 = new GenericDataFile("test2", "345");
        GenericDataFile newFile3 = new GenericDataFile("test2", "678");

        experiment.addFile(newFile1);
        experiment.addFile(newFile2);
        experiment.addFile(newFile3);

        assertEquals(newFile3, experiment.getFile(3));
    }

    @Test
    public void testLengthEmptyExperiment() {
        assertEquals(0, experiment.length());
    }

    @Test
    public void testLength3ItemsInDirectory() {
        GenericDataFile newFile1 = new GenericDataFile("test1", "123");
        GenericDataFile newFile2 = new GenericDataFile("test2", "345");
        GenericDataFile newFile3 = new GenericDataFile("test2", "678");

        experiment.addFile(newFile1);
        experiment.addFile(newFile2);
        experiment.addFile(newFile3);

        assertEquals(3,experiment.length());
    }

    //tests for remove file
    @Test
    public void testRemoveFile1FileInExperimentFirstFile(){
        GenericDataFile newFile1 = new GenericDataFile("test1", "123");

        experiment.addFile(newFile1);

        assertEquals(1, experiment.length());

        experiment.removeFile(1);

        assertEquals(0, experiment.length());
    }

    @Test
    public void testRemoveFile3ItemsInExperimentFirstFile() {

        GenericDataFile newFile1 = new GenericDataFile("test1", "123");
        GenericDataFile newFile2 = new GenericDataFile("test2", "345");
        GenericDataFile newFile3 = new GenericDataFile("test2", "678");

        experiment.addFile(newFile1);
        experiment.addFile(newFile2);
        experiment.addFile(newFile3);

        assertEquals(3,experiment.length());

        experiment.removeFile(1);

        assertEquals(2, experiment.length());
    }

    @Test
    public void testRemoveFile3ItemsInExperimentThirdFile() {

        GenericDataFile newFile1 = new GenericDataFile("test1", "123");
        GenericDataFile newFile2 = new GenericDataFile("test2", "345");
        GenericDataFile newFile3 = new GenericDataFile("test2", "678");

        experiment.addFile(newFile1);
        experiment.addFile(newFile2);
        experiment.addFile(newFile3);

        assertEquals(3,experiment.length());

        experiment.removeFile(3);

        assertEquals(2, experiment.length());
    }

    //TODO: test new getfile() method
}

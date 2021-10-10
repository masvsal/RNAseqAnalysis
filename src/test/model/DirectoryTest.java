package model;

import model.interfaces.Directory;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class DirectoryTest {
    Directory directory;

//    @BeforeEach
//    public void setup() {
//      directory = new Directory();
//      assertEquals(0, directory.length());
//    }
//    //tests for add experiment
//
//    //empty directory
//    @Test
//    public void testAddExperimentEmptyDirectory1Add() {
//        Experiment newFile = new Experiment("test1");
//        directory.addFile(newFile);
//        assertEquals(1, directory.length());
//        assertEquals(newFile,directory.getFile(1));
//    }
//    //directory with 1 item
//    @Test
//    public void testAddExperiment1ItemInDirectory1Add() {
//        Experiment newFile1 = new Experiment("test1");
//        Experiment newFile2 = new Experiment("test2");
//
//        directory.addFile(newFile1);
//        directory.addFile(newFile2);
//
//        assertEquals(2, directory.length());
//        assertEquals(newFile1,directory.getFile(1));
//    }
//
//
//    //tests for getfirstexperiment
//    @Test
//    public void getFirstExperiment1ItemInDirectory() {
//        Experiment newExperiment1 = new Experiment("test1");
//        directory.addFile(newExperiment1);
//        assertEquals(newExperiment1, directory.getFile(1));
//    }
//
//    //directory with more than 1 item
//    @Test
//    public void getFirstExperiment3ItemsInDirectory() {
//        Experiment newExperiment1 = new Experiment("test1");
//        Experiment newExperiment2 = new Experiment("test2");
//        Experiment newExperiment3 = new Experiment("test3");
//
//        directory.addFile(newExperiment1);
//        directory.addFile(newExperiment2);
//        directory.addFile(newExperiment3);
//        assertEquals(newExperiment2, directory.getFile(2));
//        assertEquals(newExperiment1, directory.getFile(1));
//        assertEquals(newExperiment3, directory.getFile(3));
//    }
//
//    //tests for length()
//    //empty directory
//
//    @Test
//    public void testLengthEmptyDirectory() {
//        assertEquals(0, directory.length());
//    }
//
//    @Test
//    public void testLength1ItemInDirectory() {
//        Experiment newExperiment = new Experiment("test1");
//
//        directory.addFile(newExperiment);
//
//        assertEquals(1,directory.length());
//    }
//
//    //directory with 1
//    //directory with 3
//    @Test
//    public void testLength3ItemsInDirectory() {
//        Experiment newExperiment1 = new Experiment("test1");
//        Experiment newExperiment2 = new Experiment("test2");
//        Experiment newExperiment3 = new Experiment("test3");
//
//        directory.addFile(newExperiment1);
//        directory.addFile(newExperiment2);
//        directory.addFile(newExperiment3);
//
//        assertEquals(3, directory.length());
//    }
//
//    //tests for remove file
//    // 1 file in directory, name matches one
//    @Test
//    public void testRemoveFile1FileInDirectoryFirstFile(){
//        Experiment newExperiment1 = new Experiment("test1");
//
//        directory.addFile(newExperiment1);
//
//        assertEquals(1, directory.length());
//
//        directory.removeFile(1);
//        assertEquals(0, directory.length());
//    }
//    //3 files in directory, name matches one
//
//    @Test
//    public void testRemoveFile3FileInDirectoryFirstFile(){
//        Experiment newExperiment1 = new Experiment("test1");
//        Experiment newExperiment2 = new Experiment("test2");
//        Experiment newExperiment3 = new Experiment("test3");
//
//        directory.addFile(newExperiment1);
//        directory.addFile(newExperiment2);
//        directory.addFile(newExperiment3);
//
//        assertEquals(3, directory.length());
//
//        directory.removeFile(1);
//        assertEquals(2, directory.length());
//    }
//
//    @Test
//    public void testRemoveFile3FileInDirectoryMiddleFile(){
//        Experiment newExperiment1 = new Experiment("test1");
//        Experiment newExperiment2 = new Experiment("test2");
//        Experiment newExperiment3 = new Experiment("test3");
//
//        directory.addFile(newExperiment1);
//        directory.addFile(newExperiment2);
//        directory.addFile(newExperiment3);
//
//        assertEquals(3, directory.length());
//
//        directory.removeFile(2);
//        assertEquals(2, directory.length());
//    }
//
//    @Test
//    public void testRemoveFile3FileInDirectoryLastFile(){
//        Experiment newExperiment1 = new Experiment("test1");
//        Experiment newExperiment2 = new Experiment("test2");
//        Experiment newExperiment3 = new Experiment("test3");
//
//        directory.addFile(newExperiment1);
//        directory.addFile(newExperiment2);
//        directory.addFile(newExperiment3);
//
//        assertEquals(3, directory.length());
//
//        directory.removeFile(3);
//        assertEquals(2, directory.length());
//    }

}

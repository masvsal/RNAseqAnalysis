package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import persistence.JsonReader;
import persistence.JsonWriter;


import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class JsonWriterTest extends JsonTest{
    ExperimentDirectory ed;

    @BeforeEach
    public void setup() {
        ed = new ExperimentDirectory();
    }
    //write test
    //invalid file
    @Test
    public void testWriteInvalidPath() {
        try {
            JsonWriter writer = new JsonWriter("data/Persts/InvalidPath.json");
            writer.open();
            fail("IO exception should be thrown");
            writer.write(ed);
            writer.close();
        } catch(IOException e) {
            //should be thrown
        }
    }

    //empty file
    @Test
    public void testWriteEmptyExperimentDirectory() {
        try {

            JsonWriter writer = new JsonWriter("data/Persistence/Tests/EmptyJson.json");
            writer.open();
            writer.write(ed);
            writer.close();

            JsonReader reader = new JsonReader("data/Persistence/Tests/EmptyJson.json");
            ed = reader.read();

            assertEquals(0, ed.length());

        } catch(IOException e) {
           fail("IO exception should not be thrown");
        }
    }

    //regular file containing experiments and data files
    @Test
    public void testWriteFullExperimentDirectory() {
        try {

            ed.addFile(new Experiment("ex1"));
            ed.addFile(new Experiment("ex2"));
            ed.addFile(new Experiment("ex3"));

            ed.getFile(1).addFile(new GenericDataFile("f1", "td"));
            ed.getFile(2).addFile(new GenericDataFile("f2", "td"));
            ed.getFile(3).addFile(new GenericDataFile("f3", "td"));
            ed.getFile(3).addFile(new RnaSeqDataFile("f4", "td",
                    "data/RNAseqDataFileTests/5Down5Up.csv"));

            assertEquals(3, ed.length());
            assertEquals(2, ed.getFile(3).length());

            JsonWriter writer = new JsonWriter("data/Persistence/Tests/FullExperimentDirectory.json");
            writer.open();
            writer.write(ed);
            writer.close();

            JsonReader reader = new JsonReader("data/Persistence/Tests/FullExperimentDirectory.json");
            ed = reader.read();

            checkExperimentDirectory(ed);

        } catch(IOException e) {
            fail("IO exception should not be thrown");
        }
    }


}

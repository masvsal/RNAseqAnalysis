package model;

import org.junit.jupiter.api.Test;
import persistence.JsonReader;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class JsonReaderTest extends JsonTest{

    //test invalid source

    @Test
    public void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            ExperimentDirectory er = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    //test valid source, empty file

    @Test
    public void testReaderEmptyExperimentDirectoryFile() {
        JsonReader reader = new JsonReader("data/Persistence/Tests/EmptyJson.json");
        try {
            ExperimentDirectory er = reader.read();
            assertEquals(0, er.length());

        } catch (IOException e) {
           fail("should not throw exception");
        }
    }
    //valid source, full file

    @Test
    public void testReaderFullExperimentDirectoryFile() {
        JsonReader reader = new JsonReader("data/Persistence/Tests/FullExperimentDirectory.json");
        try {
            ExperimentDirectory ed = reader.read();
            checkExperimentDirectory(ed);

        } catch (IOException e) {
            fail("should not throw exception");
        }
    }
}

package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GenericDataFileTest {
    GenericDataFile datafile;
    String description;

    @BeforeEach
    public void setup() {
        datafile = new GenericDataFile("Blank 123", "sample data");
        description = datafile.getDescription();
        assertEquals("no description", description);
    }

    //tests for get name
    //experiment with name
    @Test
    public void testGetNameNameExists() {
        String name = datafile.getName();
        assertEquals("Blank 123", name);

    }

    //tests for get description
    @Test
    public void testGetDescriptionNoDescription() {}

    @Test
    public void testGetDescriptionYesDescription() {
        datafile.setDescription("blank 321");

        description = datafile.getDescription();

        assertEquals("blank 321", description);
    }

    //tests for give description

    //experiment with no description
    @Test
    public void testSetDescriptionNoDescription() {
        datafile.setDescription("1234");

        description = datafile.getDescription();
        assertEquals("1234", description);

    }

    @Test
    public void testSetDescriptionYesDescription() {
        datafile.setDescription("blank 321");

        String description = datafile.getDescription();
        assertEquals("blank 321", description);

        datafile.setDescription("un-blank 321");

        description = datafile.getDescription();
        assertEquals("un-blank 321", description);
    }

    //tests for get data
    @Test
    public void testGetData() {
        String data = datafile.getData();
        assertEquals("sample data", data);

    }

    //tests for set data

    @Test
    public void testSetData() {
        datafile.setData("new data");

        String data = datafile.getData();
        assertEquals("new data", data);
    }
}

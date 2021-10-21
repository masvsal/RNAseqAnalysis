package model;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonTest {
    public void checkExperimentDirectory(ExperimentDirectory ed) {
        assertEquals(3, ed.length());
        assertEquals(1, ed.getFile(1).length());
        assertEquals("ex1", ed.getFile(1).getName());
        assertEquals("f1", ed.getFile(1).getFile(1).getName());
        assertEquals(1, ed.getFile(2).length());
        assertEquals("ex2", ed.getFile(2).getName());
        assertEquals("f2", ed.getFile(2).getFile(1).getName());
        assertEquals(2, ed.getFile(3).length());
        assertEquals("ex3", ed.getFile(3).getName());
        assertEquals("f3", ed.getFile(3).getFile(1).getName());
        assertEquals("f4", ed.getFile(3).getFile(2).getName());
        assertEquals("class model.RnaSeqDataFile",
                String.valueOf(ed.getFile(3).getFile(2).getClass()));
    }
}

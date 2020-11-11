package persistence;

import model.Assignment;
import model.StudentOrganizer;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

class JsonReaderTest extends JsonTest { // based on code written in JsonSerializationDemo's
    // persistence.JsonReaderTest class https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
    @Test
    public void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            StudentOrganizer studentOrganizer = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    public void testReaderEmptyStudentOrganizer() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyStudentOrganizer.json");
        try {
            StudentOrganizer studentOrganizer = reader.read();
            assertEquals(0, studentOrganizer.size());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    public void testReaderGeneralStudentOrganizer() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralStudentOrganizer.json");
        try {
            StudentOrganizer studentOrganizer = reader.read();
            ArrayList<Assignment> assignments = studentOrganizer.viewAllAssignmentsByCourseCode();
            assertEquals(2, assignments.size());
            checkAssignment("A6", "MATH 200", "10-25", 3, false, assignments.get(0));
            checkAssignment("P2", "CPSC 210", "10-30", 12, false, assignments.get(1));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}

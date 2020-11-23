package persistence;

import model.Assignment;
import model.StudentOrganizer;
import model.exceptions.CourseCodeException;
import model.exceptions.DueDateException;
import model.exceptions.EstimatedHoursException;
import model.exceptions.NameException;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

class JsonWriterTest extends JsonTest { // based on code written in JsonSerializationDemo's
    // persistence.JsonWriterTest class https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
    @Test
    public void testWriterInvalidFile() {
        try {
            StudentOrganizer studentOrganizer = new StudentOrganizer();
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    public void testWriterEmptyStudentOrganizer() throws CourseCodeException, EstimatedHoursException, DueDateException, NameException {
        try {
            StudentOrganizer studentOrganizer = new StudentOrganizer();
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyStudentOrganizer.json");
            writer.open();
            writer.write(studentOrganizer);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyStudentOrganizer.json");
            studentOrganizer = reader.read();
            assertEquals(0, studentOrganizer.size());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    public void testWriterGeneralStudentOrganizer() throws CourseCodeException, EstimatedHoursException, DueDateException, NameException {
        try {
            StudentOrganizer studentOrganizer = new StudentOrganizer();
            studentOrganizer.addAssignment(new Assignment("P2", "CPSC 210", "10-30", 12));
            studentOrganizer.addAssignment(new Assignment("A6", "MATH 200", "10-25", 3));
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralStudentOrganizer.json");
            writer.open();
            writer.write(studentOrganizer);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralStudentOrganizer.json");
            studentOrganizer = reader.read();
            ArrayList<Assignment> assignments = studentOrganizer.viewAllAssignmentsByCourseCode();
            assertEquals(2, assignments.size());
            checkAssignment("A6", "MATH 200", "10-25", 3, false, assignments.get(0));
            checkAssignment("P2", "CPSC 210", "10-30", 12, false, assignments.get(1));
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}

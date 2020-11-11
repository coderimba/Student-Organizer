package persistence;

import model.Assignment;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonTest { // based on code written in JsonSerializationDemo's persistence.JsonTest class
                        // https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
    protected void checkAssignment(String name, String courseCode, String dueDate, double estimatedHours,
                                   boolean complete, Assignment assignment) {
        assertEquals(name, assignment.getName());
        assertEquals(courseCode, assignment.getCourseCode());
        assertEquals(dueDate, assignment.getDueDate());
        assertEquals(estimatedHours, assignment.getEstimatedHours());
        assertEquals(complete, assignment.isComplete());
    }
}

package persistence;

import model.Assignment;
import model.StudentOrganizer;
import model.exceptions.CourseCodeException;
import model.exceptions.DueDateException;
import model.exceptions.EstimatedHoursException;
import model.exceptions.NameException;
import org.json.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

// Represents a reader that reads student organizer from JSON data stored in file
public class JsonReader { // based on code written in JsonSerializationDemo's persistence.JsonReader class
                          // https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads student organizer from file and returns it;
    // throws IOException if an error occurs reading data from file
    public StudentOrganizer read()
            throws IOException, NameException, CourseCodeException, DueDateException, EstimatedHoursException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseStudentOrganizer(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses student organizer from JSON object and returns it
    private StudentOrganizer parseStudentOrganizer(JSONObject jsonObject)
            throws NameException, CourseCodeException, DueDateException, EstimatedHoursException {
        StudentOrganizer studentOrganizer = new StudentOrganizer();
        addAssignments(studentOrganizer, jsonObject);
        return studentOrganizer;
    }

    // MODIFIES: studentOrganizer
    // EFFECTS: parses assignments from JSON object and adds them to studentOrganizer
    private void addAssignments(StudentOrganizer studentOrganizer, JSONObject jsonObject)
            throws NameException, CourseCodeException, DueDateException, EstimatedHoursException {
        JSONArray jsonArray = jsonObject.getJSONArray("assignments");
        for (Object json: jsonArray) {
            JSONObject nextAssignment = (JSONObject) json;
            addAssignment(studentOrganizer, nextAssignment);
        }
    }

    // MODIFIES: studentOrganizer
    // EFFECTS: parses assignment from JSON object and adds it to studentOrganizer
    private void addAssignment(StudentOrganizer studentOrganizer, JSONObject jsonObject)
            throws NameException, CourseCodeException, DueDateException, EstimatedHoursException {
        String name = jsonObject.getString("name");
        String courseCode = jsonObject.getString("courseCode");
        String dueDate = jsonObject.getString("dueDate");
        double estimatedHours = jsonObject.getDouble("estimatedHours");
        boolean complete = jsonObject.getBoolean("complete");
        Assignment assignment = new Assignment(name, courseCode, dueDate, estimatedHours, complete);
        studentOrganizer.addAssignment(assignment);
    }
}

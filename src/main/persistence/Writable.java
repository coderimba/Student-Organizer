package persistence;

import org.json.JSONObject;

public interface Writable { // code extracted from JsonSerializationDemo's persistence.Writable interface
                            // https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
    // EFFECTS: returns this as JSON object
    JSONObject toJson();
}

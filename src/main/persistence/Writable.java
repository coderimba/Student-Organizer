package persistence;

import org.json.JSONObject;

public interface Writable { // code extracted from JsonSerializationDemo's persistence.Writable interface
    // EFFECTS: returns this as JSON object
    JSONObject toJson();
}

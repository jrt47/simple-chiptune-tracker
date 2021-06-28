package persistence;

import org.json.JSONObject;

// represents a writable class
public interface Writable {
    // EFFECTS: returns this as JSON object
    JSONObject toJson();
}

package persistence;

import org.json.JSONObject;

// modelled after JsonSerializationDemo repository
public interface Writable {
    // EFFECTS: returns this as JSON object
    JSONObject toJson();
}

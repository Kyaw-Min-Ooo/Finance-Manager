package persistence;

import org.json.JSONObject;

//Credit to class example thingy 
public interface Writable {
    // EFFECTS: returns this as JSON object
    JSONObject toJson();
}

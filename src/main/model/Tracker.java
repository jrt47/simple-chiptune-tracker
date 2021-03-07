package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.List;

// Represents all tracks stored in the tracker
public class Tracker implements Writable {
    private List<Track> trackList;

    // EFFECTS: Constructs an empty tracker
    public Tracker() {
        trackList = new ArrayList<>();
    }

    // EFFECTS: returns the number of tracks in the tracker
    public int numberOfTracks() {
        return trackList.size();
    }

    // EFFECTS: returns true if the tracker contains the given track, false otherwise
    public boolean contains(Track track) {
        return trackList.contains(track);
    }

    // MODIFIES: this
    // EFFECTS: adds track to tracker
    public void add(Track track) {
        trackList.add(track);
    }

    // EFFECTS: returns the track in the tracker at the given position, if no such track exists return null
    public Track get(int pos) {
        if (pos < 1 || pos > numberOfTracks()) {
            return null;
        } else {
            return trackList.get(pos - 1);
        }
    }

    // EFFECTS: returns the track in the tracker with the given name, if no such track exists return null
    public Track get(String name) {
        for (Track track : trackList) {
            if (name.equals(track.getName())) {
                return track;
            }
        }
        return null;
    }

    // EFFECTS: returns this as JSON object
    // (modelled after JsonSerializationDemo repository)
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("trackList", trackListToJson());
        return json;
    }

    // EFFECTS: returns events in this channel as a JSON array
    // (modelled after JsonSerializationDemo repository)
    private JSONArray trackListToJson() {
        JSONArray jsonArray = new JSONArray();
        for (Track track : trackList) {
            jsonArray.put(track.toJson());
        }
        return jsonArray;
    }
}

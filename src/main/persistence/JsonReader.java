package persistence;

import model.InstrumentChannel;
import model.Track;
import model.Tracker;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

// Represents a reader that reads tracker from JSON data stored in file
// modelled after JSONSerializationDemo repository
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
public class JsonReader {
    private final String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads tracker from file and returns it;
    // throws IOException if an error occurs reading data from file
    public Tracker read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseTracker(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(contentBuilder::append);
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses tracker from JSON object and returns it
    private Tracker parseTracker(JSONObject jsonObject) {
        Tracker tracker = new Tracker();
        addTrackList(tracker, jsonObject);
        return tracker;
    }

    // MODIFIES: tracker
    // EFFECTS: parses tracks from JSON object and adds them to tracker
    private void addTrackList(Tracker tracker, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("trackList");
        for (Object json : jsonArray) {
            JSONObject nextTrack = (JSONObject) json;
            addTrack(tracker, nextTrack);
        }
    }

    // MODIFIES: tracker
    // EFFECTS: parses track from JSON object and adds it to tracker
    private void addTrack(Tracker tracker, JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        int tempo = jsonObject.getInt("tempo");
        Track track = new Track(name);
        track.setTempo(tempo);
        setNumberOfBars(track, jsonObject);
        addInstrumentChannel(track, jsonObject, "pulse1");
        addInstrumentChannel(track, jsonObject, "pulse2");
        addInstrumentChannel(track, jsonObject, "triangle");
        addInstrumentChannel(track, jsonObject, "noise");
        tracker.add(track);
    }

    // MODIFIES: track
    // EFFECTS: parses the number of bars from JSON object and sets the track accordingly
    private void setNumberOfBars(Track track, JSONObject jsonObject) {
        int numberOfBars = jsonObject.getInt("numberOfBars");
        int initialNumOfBars = InstrumentChannel.INITIAL_NUM_OF_BARS;
        if (numberOfBars < initialNumOfBars) {
            track.removeBars(initialNumOfBars - numberOfBars);
        } else if (numberOfBars > initialNumOfBars) {
            track.addBars(numberOfBars - initialNumOfBars);
        }
    }

    // MODIFIES: track
    // EFFECTS: parses instrument channel from JSON object and adds it to track
    private void addInstrumentChannel(Track track, JSONObject jsonObject, String channel) {
        JSONObject instrumentChannel = jsonObject.getJSONObject(channel);
        addEventList(track, instrumentChannel, channel);
    }

    // MODIFIES: track
    // EFFECTS: parses events from JSON object and adds them to track in channel
    private void addEventList(Track track, JSONObject jsonObject, String channel) {
        JSONArray jsonArray = jsonObject.getJSONArray("eventList");
        int row = 1;
        for (Object json : jsonArray) {
            JSONObject nextEvent = (JSONObject) json;
            addEvent(track, nextEvent, channel, row);
            row++;
        }
    }

    // MODIFIES: track
    // EFFECTS: parses event from JSON object and adds it to track in channel at given row
    private void addEvent(Track track, JSONObject jsonObject, String channel, int row) {
        String type = jsonObject.getString("type");
        int pitch = jsonObject.getInt("pitch");
        boolean isStaccato = jsonObject.getBoolean("isStaccato");
        if (type.equals("note")) {
            track.addNote(channel, row, pitch);
            if (isStaccato) {
                track.makeStaccato(channel, row);
            }
        } else if (type.equals("rest")) {
            track.addRest(channel, row);
        }
    }
}

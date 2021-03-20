package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.Objects;

// Represents a music track
// Tracks have a name, tempo,
// and have four instrument channels: pulse 1, pulse 2, triangle, and noise
public class Track implements Writable {
    public static final int DEFAULT_BPM = 120;

    private String name;
    private int tempo;
    private InstrumentChannel pulse1;
    private InstrumentChannel pulse2;
    private InstrumentChannel triangle;
    private InstrumentChannel noise;

    // REQUIRES: trackName has a length greater than 0
    // EFFECTS: creates a new empty 2 bar track with name trackName at 120 BPM
    public Track(String name) {
        this.name = name;
        tempo = DEFAULT_BPM;
        pulse1 = new InstrumentChannel();
        pulse2 = new InstrumentChannel();
        triangle = new InstrumentChannel();
        noise = new InstrumentChannel();
    }

    public String getName() {
        return name;
    }

    public int getTempo() {
        return tempo;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTempo(int tempo) {
        this.tempo = tempo;
    }

    // REQUIRES: numBars > 0
    // MODIFIES: this
    // EFFECTS: adds numBars bars to the track
    public void addBars(int numBars) {
        pulse1.addBars(numBars);
        pulse2.addBars(numBars);
        triangle.addBars(numBars);
        noise.addBars(numBars);
    }

    // REQUIRES: 0 < numBars < numberOfBars()
    // MODIFIES: this
    // EFFECTS: removes numBars bars from the track
    public void removeBars(int numBars) {
        pulse1.removeBars(numBars);
        pulse2.removeBars(numBars);
        triangle.removeBars(numBars);
        noise.removeBars(numBars);
    }

    // EFFECTS: returns the number of rows in the track
    public int numberOfRows() {
        return pulse1.numberOfRows();
    }

    // EFFECTS: returns the number of bars in the track
    public int numberOfBars() {
        return pulse1.numberOfBars();
    }

    // REQUIRES: channel is "pulse1", "pulse2", "triangle", or "noise", 1 <= row <= numberOfRows()
    // EFFECTS: returns the event at the given row in the given channel
    public Event getEvent(String channel, int row) {
        return getChannel(channel).getEvent(row);
    }

    // REQUIRES: channel is "pulse1", "pulse2", "triangle", or "noise", 1 <= row <= numberOfRows()
    // MODIFIES: this
    // EFFECTS: places a note with given pitch into given channel at given row
    public void addNote(String channel, int row, int pitch) {
        getChannel(channel).addNote(row, pitch);
    }

    // REQUIRES: channel is "pulse1", "pulse2", "triangle", or "noise", 1 <= row <= numberOfRows()
    // MODIFIES: this
    // EFFECTS: places a rest into given channel at given row
    public void addRest(String channel, int row) {
        getChannel(channel).addRest(row);
    }

    // REQUIRES: channel is "pulse1", "pulse2", "triangle", or "noise", 1 <= row <= numberOfRows()
    // MODIFIES: this
    // EFFECTS: if event at given row in given channel is a note, make it staccato and return true,
    //          otherwise return false
    public boolean makeStaccato(String channel, int row) {
        return getChannel(channel).makeStaccato(row);
    }

    // REQUIRES: channel is "pulse1", "pulse2", "triangle", or "noise", 1 <= row <= numberOfRows()
    // MODIFIES: this
    // EFFECTS: if event at given row in given channel is a note, make it not staccato and return true,
    //          otherwise return false
    public boolean makeNotStaccato(String channel, int row) {
        return getChannel(channel).makeNotStaccato(row);
    }

    // REQUIRES: channel is "pulse1", "pulse2", "triangle", or "noise", 1 <= row <= numberOfRows()
    // MODIFIES: this
    // EFFECTS: clears the event in given channel at given row
    public void clear(String channel, int row) {
        getChannel(channel).clear(row);
    }

    // REQUIRES: channel is "pulse1", "pulse2", "triangle", or "noise",
    //           1 <= startRow/endRow <= numberOfRows(), endRow >= startRow
    // MODIFIES: this
    // EFFECTS: clears all events in the given channel between startRow and endRow (inclusive)
    public void clear(String channel, int startRow, int endRow) {
        getChannel(channel).clear(startRow, endRow);
    }

    // REQUIRES: 1 <= startRow/endRow <= numberOfRows(), endRow >= startRow
    // MODIFIES: this
    // EFFECTS: clears all events between startRow and endRow (inclusive)
    public void clear(int startRow, int endRow) {
        pulse1.clear(startRow, endRow);
        pulse2.clear(startRow, endRow);
        triangle.clear(startRow, endRow);
        noise.clear(startRow, endRow);
    }

    // REQUIRES: channel is "pulse1", "pulse2", "triangle", or "noise"
    // MODIFIES: this
    // EFFECTS: clears all events in the given channel
    public void clear(String channel) {
        getChannel(channel).clear();
    }

    // MODIFIES: this
    // EFFECTS: clears all events in the track
    public void clear() {
        pulse1.clear();
        pulse2.clear();
        triangle.clear();
        noise.clear();
    }

    // REQUIRES: channel is "pulse1", "pulse2", "triangle", or "noise"
    // MODIFIES: this
    // EFFECTS: transposes all notes in the given channel by numSemitones semitones
    public void transpose(String channel, int numSemitones) {
        getChannel(channel).transpose(numSemitones);
    }

    // MODIFIES: this
    // EFFECTS: transposes all notes in the track by numSemitones semitones
    public void transpose(int numSemitones) {
        pulse1.transpose(numSemitones);
        pulse2.transpose(numSemitones);
        triangle.transpose(numSemitones);
        noise.transpose(numSemitones);
    }

    // REQUIRES: channel is "pulse1", "pulse2", "triangle", or "noise"
    // MODIFIES: this
    // EFFECTS: transposes all notes in the given channel up by an octave
    public void transposeUpByOctave(String channel) {
        getChannel(channel).transposeUpByOctave();
    }

    // MODIFIES: this
    // EFFECTS: transposes all notes in the track up by an octave
    public void transposeUpByOctave() {
        pulse1.transposeUpByOctave();
        pulse2.transposeUpByOctave();
        triangle.transposeUpByOctave();
        noise.transposeUpByOctave();
    }

    // REQUIRES: channel is "pulse1", "pulse2", "triangle", or "noise"
    // MODIFIES: this
    // EFFECTS: transposes all notes in the given channel down by an octave
    public void transposeDownByOctave(String channel) {
        getChannel(channel).transposeDownByOctave();
    }

    // MODIFIES: this
    // EFFECTS: transposes all notes in the track down by an octave
    public void transposeDownByOctave() {
        pulse1.transposeDownByOctave();
        pulse2.transposeDownByOctave();
        triangle.transposeDownByOctave();
        noise.transposeDownByOctave();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Track track = (Track) o;
        return tempo == track.tempo
                && name.equals(track.name)
                && pulse1.equals(track.pulse1)
                && pulse2.equals(track.pulse2)
                && triangle.equals(track.triangle)
                && noise.equals(track.noise);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, tempo, pulse1, pulse2, triangle, noise);
    }

    // EFFECTS: returns this as JSON object
    // (modelled after JsonSerializationDemo repository)
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("tempo", tempo);
        json.put("numberOfBars", numberOfBars());
        json.put("pulse1", pulse1.toJson());
        json.put("pulse2", pulse2.toJson());
        json.put("triangle", triangle.toJson());
        json.put("noise", noise.toJson());
        return json;
    }

    // REQUIRES: channel is "pulse1", "pulse2", "triangle", or "noise"
    // EFFECTS: returns the channel corresponding to the given string
    private InstrumentChannel getChannel(String channel) {
        switch (channel) {
            case "pulse1":
                return pulse1;
            case "pulse2":
                return pulse2;
            case "triangle":
                return triangle;
            default:
                return noise;
        }
    }
}
